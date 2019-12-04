import java.net.*;  // for DatagramSocket and DatagramPacket
import java.io.*;   // for IOException

public class ServerTCP {

   public static void main(String[] args) throws Exception {

      if (args.length != 1 && args.length != 2)  // Test for correct # of args
         throw new IllegalArgumentException("Parameter(s): <Port> [<encoding>]");

      int port = Integer.parseInt(args[0]) + TCPRequestBinConst.GROUP_NUMBER;   // Receiving Port

      ServerSocket servSock = new ServerSocket(port);  // TCP socket for receiving


      System.out.println("Server Is Active. Enter \"Control + C\" to Terminate.\n");

      for (;;) {
         System.out.println("Server is Idle. Awaiting Packets.\n");
         Socket clntSock = servSock.accept();

         TCPRequestDecoder decoder = (args.length == 2 ?   // Which encoding
            new TCPRequestDecoderBin(args[1]) :
            new TCPRequestDecoderBin() );

         InputStream in = clntSock.getInputStream();
         OutputStream out = clntSock.getOutputStream();


         TCPRequest Request = decoder.decodeRequest(in);




         // System.out.println("Message length: " + Request.TML);
         // System.out.print("\nHex String: ");
         // byte[] receiveBuffer = toByteArray(in);
         // for (int i = 0; i < 8; i++) {
            // System.out.format("\t0x%x\n", receiveBuffer[i]);
         // }

      // Print receive confirmation
         System.out.println("Received Binary-Encoded Request");
         System.out.println("Message length: " + Request.TML);
         System.out.print("\nHex String: \n");
         byte[] byteBuffer = toByteArray(Request);
         for (int i = 0; i < Request.TML; i++) {
            System.out.format("\t0x%x\n", byteBuffer[i]);
         }

         System.out.println();
         System.out.println(Request);

         byte error = 0;
         if (byteBuffer.length != 8) {
            error = (byte) 127;
         }

         int result = calculate(Request.opCode, Request.op1, Request.op2);

         TCPResponse Response = new TCPResponse(Request.TML, Request.ID, error, result);

         TCPRequestEncoder encoder = (args.length == 3 ?
            new TCPRequestEncoderBin(args[2]) :
            new TCPRequestEncoderBin());

         byte[] codedResponse = encoder.encode(Response); // Encode Request
         out.write(codedResponse);
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

   public static byte[] toByteArray(TCPRequest Request) throws IOException {



      ByteArrayOutputStream os = new ByteArrayOutputStream();
      DataOutputStream out = new DataOutputStream(os);

      out.writeByte(Request.TML);
      out.writeByte(Request.ID);
      out.writeByte(Request.opCode);
      out.writeByte(Request.operands);
      out.writeShort(Request.op1);
      out.writeShort(Request.op2);



      out.flush();



      return os.toByteArray();
   }

}
