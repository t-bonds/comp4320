import java.net.*;  // for DatagramSocket, DatagramPacket, and InetAddress
import java.io.*;   // for IOException
import java.util.Scanner; // for Scanner
import java.util.Random; // for random ID variable

public class ClientUDP {

   public static void main(String args[]) throws Exception {
   
      if (args.length != 2 && args.length != 3) // Test for correct # of args
         throw new IllegalArgumentException("Parameter(s): <Destination>" +
                                                   " <Port> [<encoding]");
   
   
      InetAddress destAddr = InetAddress.getByName(args[0]); // Destination address
      int destPort = Integer.parseInt(args[1]) + RequestBinConst.GROUP_NUMBER;       // Destination port
   
   
      int TML = 6;
      int opCode, op1;
      int ID = 1;
      int operands = 1;
      int op2 = 0;
      for (;;) {
         Scanner scan = new Scanner(System.in);
         System.out.println("Please Enter The Following Values:");
         System.out.print("\tOperand Type - \n\t\"0\" = +, \n\t\"1\" = -, \n\t\"2\" = *, \n\t\"3\" = /, \n\t\"4\" = >>, \n\t\"5\" = <<, \n\t\"6\" = ~, \n\t\"exit\" = Terminate \n\tEnter Selection: ");
      
         String opString = scan.nextLine();
      
         if (opString.equals("exit")) {
            break;
         }
         opCode = Integer.parseInt(opString);
      
      
         System.out.print("\n\tOperand 1: ");
         op1 = Integer.parseInt(scan.nextLine());
         if (opCode <=5) {
         
            System.out.print("\n\tOperand 2: ");
            op2 = Integer.parseInt(scan.nextLine());
            operands = 2;
            TML = 8;
         
         }
      
                //Request request;
                //DatagramSocket sock = new DatagramSocket(); // UDP socket for sending
                //byte[] codedRequest = new byte[1024];
                //for (int i = 0; i <=1; i++) {
      
         Request request = new Request(TML, ID, opCode, operands, op1, op2);
      
         DatagramSocket sock = new DatagramSocket();
      
      
                // Use the encoding scheme given on the command line (args[2])
         RequestEncoder encoder = (args.length == 3 ?
                                          new RequestEncoderBin(args[2]) :
                                          new RequestEncoderBin());
      
      
         byte[] codedRequest = encoder.encode(request); // Encode request
      
                // if (TML == -1) {
                //
                //    TML = codedRequest.length + 1;
                //    continue;
                //
                // }
                //}
      
         DatagramPacket message = new DatagramPacket(codedRequest, codedRequest.length,
                                                            destAddr, destPort);
         sock.send(message);
         long start = System.nanoTime();
         ID++;
      
         sock.receive(message);
         long finish = System.nanoTime();
         long elapsed = finish - start;
      
         RequestDecoder decoder = (args.length == 2 ? // Which encoding
                                          new RequestDecoderBin(args[1]) :
                                          new RequestDecoderBin() );
      
      
         Request receivedRequest = decoder.decode(message);
      
         System.out.println("\n" + receivedRequest);
         System.out.println("Time Elapsed: " + elapsed/(1000000.0) + "ms");
         System.out.println("\nHex Bytes:");
      
         byte[] buffer = message.getData();
      
                
                
         for (int i = 0; i < buffer.length; i++) {
          
            if (i % 4 == 3) {
            
               System.out.format("\t0x%x\n", buffer[i]);
             
            }
          
         }       
                
         //for (byte b : buffer) {
            //System.out.format("\t0x%x\n", b);
         //}
      
      }
        //sock.close();
   }
}
