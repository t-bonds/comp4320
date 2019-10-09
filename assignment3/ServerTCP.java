import java.net.*;  // for DatagramSocket and DatagramPacket
import java.io.*;   // for IOException

public class ServerTCP {

   public static void main(String[] args) throws Exception {

      if (args.length != 1 && args.length != 2)  // Test for correct # of args
         throw new IllegalArgumentException("Parameter(s): <Port> [<encoding>]");

      int port = Integer.parseInt(args[0]) + TCPRequestBinConst.GROUP_NUMBER;   // Receiving Port

      DatagramSocket sock = new DatagramSocket(port);  // UDP socket for receiving
      DatagramPacket packet = new DatagramPacket(new byte[1024],1024);

      System.out.println("Server Is Active. Enter \"Control + C\" to Terminate.\n");

      for (;;) {
         System.out.println("Server is Idle. Awaiting Packets.\n");

         sock.receive(packet);


         System.out.println("Message length: " + packet.getLength());
         System.out.print("\nHex String: ");
         byte[] receiveBuffer = packet.getData();
         for (int i = 0; i < 8; i++) {
            System.out.format("\t0x%x\n", receiveBuffer[i]);
         }



         TCPRequestDecoder decoder = (args.length == 2 ?   // Which encoding
            new TCPRequestDecoderBin(args[1]) :
            new TCPRequestDecoderBin() );

         TCPRequest Request = decoder.decodeRequest(packet);


      // Print receive confirmation
         System.out.println("Received Binary-Encoded Request");

         System.out.print("\nHex String: ");
         byte[] byteBuffer = packet.getData();
         for (int i = 0; i < Request.TML; i++) {
            System.out.format("\t0x%x\n", byteBuffer[i]);
         }

         System.out.println();
         System.out.println(Request);

         byte error = 0;
         if (packet.getLength() != Request.TML) {
            error = (byte) 127;
         }

         int result = calculate(Request.opCode, Request.op1, Request.op2);

         TCPResponse Response = new TCPResponse(Request.TML, Request.ID, error, result);

         TCPRequestEncoder encoder = (args.length == 3 ?
            new TCPRequestEncoderBin(args[2]) :
            new TCPRequestEncoderBin());

         byte[] codedResponse = encoder.encode(Response); // Encode Request
         packet.setData(codedResponse);
         packet.setLength(codedResponse.length);
         sock.send(packet);
      }
    /* NOT REACHED */
   }

   public static int calculate(byte opCodeVar, short op1Var, short op2Var) {
      int opCode = (int) opCodeVar;
      int op1 = (int) op1Var;
      int op2 = (int) op2Var;
      int result;
      switch (opCode) {
         case 0: // + operator
            result = (op1 + op2);
            break;
         case 1: // - operator
            result = (op1 - op2);
            break;
         case 2: // * operator
            result = (op1 * op2);
            break;
         case 3: // / operator
            result = (op1 / op2);
            break;
         case 4: // >> operator
            result =  (op1 >> op2);
            break;
         case 5: // << operator
            result = (op1 <<  op2);
            break;
         case 6: // ~ operator
            result = (~op1);
            break;
         default:
            result = 0;
            break;
      }
      return result;
   }
}
