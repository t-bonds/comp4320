import java.net.*;  // for Socket, ServerSocket, and InetAddress
import java.io.*;   // for IOException and Input/OutputStream




public class myFirstTCPServer {

   private static final int BUFSIZE = 128;     // Size of receive buffer

   public static void main(String[] args) throws IOException {

      if (args.length != 1) // Test for correct # of args
         throw new IllegalArgumentException("Parameter(s): <Port>");

      int servPort = Integer.parseInt(args[0]);

        // Create a server socket to accept client connection requests
      ServerSocket servSock = new ServerSocket(servPort);






      for (;;) { // Run forever, accepting and servicing connections
         Socket clntSock = servSock.accept(); // Get client connection

         int recvMsgSize; // Size of received message
         byte[] byteBuffer = new byte[BUFSIZE]; // Receive buffer

         System.out.println("Handling client at " +
                                   clntSock.getInetAddress().getHostAddress() + " on port " +
                                   clntSock.getPort());

         InputStream in = clntSock.getInputStream();
         OutputStream out = clntSock.getOutputStream();

         String rcdMessage = "";
         String rvdMessage = "";
                // Receive until client closes connection, indicated by -1 return
         while ((recvMsgSize = in.read(byteBuffer)) != -1) {
            rcdMessage = new String(byteBuffer);
            rvdMessage = new StringBuilder(rcdMessage.trim()).reverse().toString();
            byteBuffer = rvdMessage.getBytes();
            out.write(byteBuffer);
          }
          System.out.println("Received Message: " + rcdMessage);
          System.out.println("Reversed Message: " + rvdMessage);

         clntSock.close(); // Close the socket.  We are done with this client!
      }
        /* NOT REACHED */
   }
}
