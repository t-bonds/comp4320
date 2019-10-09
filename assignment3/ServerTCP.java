import java.net.*;  // for DatagramSocket and DatagramPacket
import java.io.*;   // for IOException

public class ServerTCP {

   public static void main(String[] args) throws Exception {

      if (args.length != 1 && args.length != 2)  // Test for correct # of args
         throw new IllegalArgumentException("Parameter(s): <Port> [<encoding>]");

      int port = Integer.parseInt(args[0]) + TCPRequestBinConst.GROUP_NUMBER;   // Receiving Port

      ServerSocket servSock = new ServerSocket(port);  // TCP socket for receiving
      Socket clntSock = servSock.accept();

      System.out.println("Server Is Active. Enter \"Control + C\" to Terminate.\n");

      for (;;) {
         System.out.println("Server is Idle. Awaiting Packets.\n");

         TCPRequestDecoder decoder = (args.length == 2 ?   // Which encoding
            new TCPRequestDecoderBin(args[1]) :
            new TCPRequestDecoderBin() );

            InputStream in = clntSock.getInputStream();
            OutputStream out = clntSock.getOutputStream();

         TCPRequest Request = decoder.decodeRequest(in);


         System.out.println("Message length: " + Request.TML);
         System.out.print("\nHex String: ");
         byte[] receiveBuffer = toByteArray(in);
         for (int i = 0; i < 8; i++) {
            System.out.format("\t0x%x\n", receiveBuffer[i]);
         }

      // Print receive confirmation
         System.out.println("Received Binary-Encoded Request");

         System.out.print("\nHex String: ");
         byte[] byteBuffer = toByteArray(in);
         for (int i = 0; i < Request.TML; i++) {
            System.out.format("\t0x%x\n", byteBuffer[i]);
         }

         System.out.println();
         System.out.println(Request);

         byte error = 0;
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

   public static byte[] toByteArray(InputStream in) throws IOException {

   		ByteArrayOutputStream os = new ByteArrayOutputStream();

   		byte[] buffer = new byte[1024];
   		int len;

   		// read bytes from the input stream and store them in buffer
   		while ((len = in.read(buffer)) != -1) {
   			// write bytes from the buffer into output stream
   			os.write(buffer, 0, len);
   		}

   		return os.toByteArray();
   	}

}
