import java.net.*;  // for DatagramSocket, DatagramPacket, and InetAddress
import java.io.*;   // for IOException
import java.util.Scanner;

public class ClientUDP {

   public static void main(String args[]) throws Exception {
   
      if (args.length != 2 && args.length != 3) // Test for correct # of args
         throw new IllegalArgumentException("Parameter(s): <Destination>" +
                                                   " <Port> [<encoding]");
   
   
      InetAddress destAddr = InetAddress.getByName(args[0]); // Destination address
      int destPort = Integer.parseInt(args[1]) + RequestBinConst.GROUP_NUMBER; // Destination port
      Byte ID = 1;
      Byte opCode;
      Short op1 = 0;
      Short op2 = 0;
   
      for(;;) {
         Byte TML = 6;
         Byte operands = 1;
      
      
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
      
         Request Request = new Request(TML, ID, opCode, operands, op1, op2);
      
         DatagramSocket sock = new DatagramSocket(); // UDP socket for sending
      
      
                // Use the encoding scheme given on the command line (args[2])
         RequestEncoder encoder = (args.length == 3 ?
                                          new RequestEncoderBin(args[2]) :
                                          new RequestEncoderBin());
      
         byte[] codedRequest = encoder.encode(Request); // Encode Request
      
         DatagramPacket message = new DatagramPacket(codedRequest, codedRequest.length,
                                                            destAddr, destPort);
      
         System.out.println("\nMessage length: " + codedRequest.length);
         System.out.println("\nRequest Hex String:");
         byte[] buffer = message.getData();
         for (int i = 0; i < message.getLength(); i++) {
            System.out.format("\t0x%x\n", buffer[i]);
         }
      
      
         sock.send(message);
         long start = System.nanoTime();
      
         sock.receive(message);
         long finish = System.nanoTime();
         long elapsed = finish - start;
         RequestDecoder decoder = (args.length == 2 ? // Which encoding
                                          new RequestDecoderBin(args[1]) :
                                          new RequestDecoderBin() );
      
         Response Response = decoder.decodeResponse(message);
      
      
         System.out.print("Response Hex String: \n");
         byte[] byteBuffer = message.getData();
         for (int i = 0; i < Response.TML; i++) {
            System.out.format("\t0x%x\n", byteBuffer[i]);
         }
         System.out.println("\n" + Response);
         System.out.println("Time Elapsed: " + elapsed / 1000000.0 + "ms\n");
         ID++;
      }
   
   
        //sock.close();
   }
}
