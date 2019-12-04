import java.net.*;  // for DatagramSocket and DatagramPacket
import java.io.*;   // for IOException

public class ServerTCP {

   public static void main(String[] args) throws Exception {

      if (args.length != 1) // Test for correct # of args
         throw new IllegalArgumentException("Parameter(s): <Port>");

      int port = Integer.parseInt(args[0]) + TCPRequestBinConst.GROUP_NUMBER; // Receiving Port

      ServerSocket servSock = new ServerSocket(port); // TCP socket for receiving

      System.out.println("Server Is Active. Enter \"Control + C\" to Terminate.\n");

      for (;;) { // Run forever, receiving and echoing datagrams
         System.out.println("Server is Idle. Awaiting Packets.\n");
         Socket clntSock = servSock.accept();
                // Receive binary-encoded request
                // RequestDecoder decoder = new RequestDecoderBin();
         TCPRequestDecoder decoder = (args.length == 2 ? // Which encoding
                                             new TCPRequestDecoderBin(args[1]) :
                                             new TCPRequestDecoderBin() );

         InputStream in = clntSock.getInputStream();
         OutputStream out = clntSock.getOutputStream();


         TCPRequest Request = decoder.decodeRequest(in);

                // Print received confirmation
         System.out.println("\nReceived Binary-Encoded Request");
         System.out.print("Request Hex Values: ");
         Request.printHex();
         System.out.println();



         byte TML = (byte) 7;
         byte error = 0;
         byte[] byteBuffer = Request.toByteArray();
         if (byteBuffer.length != Request.TML) {
            error = (byte) 127;
         }

         int result = calculate(Request.opCode, Request.op1, Request.op2);

         TCPResponse Response = new TCPResponse(Request.TML, Request.ID, error, result);

         TCPRequestEncoder encoder = (args.length == 3 ?
                                             new TCPRequestEncoderBin(args[2]) :
                                             new TCPRequestEncoderBin());

         byte[] codedResponse = encoder.encode(Response); // Encode Request
         out.write(codedResponse);

                //Print response in hexadecimal
         System.out.print("Response Hex Values: ");
         Response.printHex();
         System.out.println("\n");
      }

   }

   public static int calculate(byte opCodeIn, short op1In, short op2In) {
      int opCode = (int) opCodeIn;
      int op1 = (int) op1In;
      int op2 = (int) op2In;
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
