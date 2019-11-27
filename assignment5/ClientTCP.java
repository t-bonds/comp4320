import java.net.*;  // for DatagramSocket, DatagramPacket, and InetAddress
import java.io.*;   // for IOException
import java.util.Scanner; // for Scanner


public class ClientTCP {

   public static void main(String args[]) throws Exception {
   
      if (args.length != 2 && args.length != 3) // Test for correct # of args
         throw new IllegalArgumentException("Parameter(s): <Destination>" +
                                                   " <Port> [<encoding]");
   
   
      InetAddress destAddr = InetAddress.getByName(args[0]); // Destination address
      int destPort = Integer.parseInt(args[1]) + TCPRequestBinConst.GROUP_NUMBER; // Destination port
      Byte TML = 6;
      Byte ID = 1;
      Byte operands = 1;
      Byte opCode;
      Short op1 = 0;
      Short op2 = 0;
   
      for(;;) {
         
      
         Scanner scan = new Scanner(System.in);
         System.out.println("Please Enter The Following Values:");
         System.out.print("\tOperand Type - \n\t\"0\" = +, \n\t\"1\" = -, \n\t\"2\" = *, \n\t\"3\" = /, \n\t\"4\" = >>, \n\t\"5\" = <<, \n\t\"6\" = ~, \n\t\"exit\" = Terminate \n\tEnter Selection: ");
      
         String opString = scan.nextLine();
      
         if (opString.equals("exit")) {
            break;
         }
         int opCodeInt = Integer.parseInt(opString);
         opCode = (byte) opCodeInt;
      
      
      
         System.out.print("\n\tOperand 1: ");
         int op1Int = Integer.parseInt(scan.nextLine());
         op1 = (short) op1Int;
      
      
         if (opCode <= 5) {
            System.out.print("\n\tOperand 2: ");
            int op2Int = Integer.parseInt(scan.nextLine());
            op2 = (short) op2Int;
            TML = (byte) 8;
            operands = (byte) 2;
         }
      
         TCPRequest Request = new TCPRequest(TML, ID, opCode, operands, op1, op2);
      
         Socket sock = new Socket(destAddr, destPort); // TCP socket for sending
      
      
                // Use the encoding scheme given on the command line (args[2])
         TCPRequestEncoder encoder = (args.length == 3 ?
                                             new TCPRequestEncoderBin(args[2]) :
                                             new TCPRequestEncoderBin());
      
         byte[] codedRequest = encoder.encode(Request); // Encode Request
      
      
         System.out.println("\nMessage length: " + codedRequest.length);
         System.out.print("Request Hex String: \n");
         //byte[] requestBuffer = message.getData();
         for (int i = 0; i < Request.TML; i++) {
            System.out.format("\t0x%x\n", codedRequest[i]);
         }
         InputStream in = sock.getInputStream();
         OutputStream out = sock.getOutputStream();
      
      
         TCPRequestDecoder decoder = (args.length == 2 ? // Which encoding
                                             new TCPRequestDecoderBin(args[1]) :
                                             new TCPRequestDecoderBin() );
      
         out.write(codedRequest);
         long start = System.nanoTime();
      
                //TODO DETERMINE IF DECODE CAN BE REMOVED FROM THIS STATEMENT
                //  TO GET ACCURATE SYSTEM TIME
         TCPResponse Response = decoder.decodeResponse(in);
         long finish = System.nanoTime();
         long elapsed = finish - start;
      
      
                //TODO FIX!!!
         System.out.print("Response Hex String: \n");
         byte[] codedResponse = toByteArray(Response);
         for (int i = 0; i < 7; i++) {
            System.out.format("\t0x%x\n", codedResponse[i]);
         }
      
         System.out.println("\n" + Response);
         System.out.println("Time Elapsed: " + elapsed / 1000000.0 + "ms\n");
         ID++;
      }
      //sock.close();
   }

   public static byte[] toByteArray(TCPResponse Response) throws IOException {
   
      ByteArrayOutputStream os = new ByteArrayOutputStream();
      DataOutputStream out = new DataOutputStream(os);
   
      out.writeByte(Response.TML);
      out.writeByte(Response.ID);
      out.writeByte(Response.error);
      out.writeInt(Response.result);
      return os.toByteArray();
   }

}
