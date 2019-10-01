import java.net.*;  // for DatagramSocket and DatagramPacket
import java.io.*;   // for IOException
import java.util.Scanner; // For Scanner

public class ServerUDP {

   public static void main(String[] args) throws Exception {
   
      if (args.length != 1 && args.length != 2) // Test for correct # of args
         throw new IllegalArgumentException("Parameter(s): <Port> [<encoding>]");
   
      int port = Integer.parseInt(args[0]); // Receiving Port
   
      int gPort = port + RequestBinConst.GROUP_NUMBER;
   
      DatagramSocket sock = new DatagramSocket(gPort); // UDP socket for receiving
      DatagramPacket packet = new DatagramPacket(new byte[1024],1024);
      Scanner scan = new Scanner(System.in);
      String exit = "";
      System.out.println("Server Is Active. Enter \"Control + C\" to Terminate.\n");
      for (;;) {
      
         System.out.println("Server is Idle. Awaiting Packets.\n");
      
         sock.receive(packet);
      
      
                // Receive binary-encoded request
                // RequestDecoder decoder = new RequestDecoderBin();
         RequestDecoder decoder = (args.length == 2 ? // Which encoding
                                          new RequestDecoderBin(args[1]) :
                                          new RequestDecoderBin() );
      
      
         Request receivedRequest = decoder.decode(packet);
      
         System.out.println("Received Binary-Encoded Request: ");
         System.out.println(receivedRequest);
      
         RequestEncoder encoder = (args.length == 2 ? // Which encoding
                                          new RequestEncoderBin(args[1]) :
                                          new RequestEncoderBin() );
      
         byte[] sendRequest = encoder.encode(receivedRequest);
         packet.setData(sendRequest);
         sock.send(packet);
         packet.setLength(sendRequest.length);
      }
   }
//sock.close();
}
