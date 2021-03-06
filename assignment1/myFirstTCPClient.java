import java.net.*;  // for Socket
import java.io.*;   // for IOException and Input/OutputStream
import java.util.Scanner; // for Scanner


public class myFirstTCPClient {

public static void main(String[] args) throws IOException {

        if ((args.length < 1) || (args.length > 2)) // Test for correct # of args
                throw new IllegalArgumentException("Parameter(s): <Server> [<Port>]");

        String server = args[0]; // Server name or IP address
        int servPort = (args.length == 2) ? Integer.parseInt(args[1]) : 7;



        for (;;) {

                System.out.print("Please enter a message shorter than 128 characters (bytes) or type \"exit\" to close client: ");
                Scanner scan = new Scanner(System.in);
                String firstAddress = scan.nextLine(); //nextLine allows for spaces inside the string
                if (firstAddress.length() > 128) {
                        throw new IllegalArgumentException("Your message must be shorter than 128 characters.");
                }
                if (firstAddress.equals("exit") ) {
                        break;
                }
                byte[] byteBuffer = firstAddress.getBytes();

                // Create socket that is connected to server on specified port
                Socket socket = new Socket(server, servPort);
                System.out.println("Connected to server...sending echo string");

                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();

                out.write(byteBuffer); // Send the encoded string to the server
                long start = System.nanoTime();

                // Receive the same string back from the server
                int totalBytesRcvd = 0; // Total bytes received so far
                int bytesRcvd; // Bytes received in last read



                while (totalBytesRcvd < firstAddress.getBytes().length) {
                        if ((bytesRcvd = in.read(byteBuffer, totalBytesRcvd,
                                                 firstAddress.getBytes().length - totalBytesRcvd)) == -1)
                                throw new SocketException("Connection close prematurely");
                        totalBytesRcvd += bytesRcvd;
                }
                long finish = System.nanoTime();
                long elapsed = finish - start;

                System.out.println("Client Received: " + new String(byteBuffer));
                System.out.println("Time Elapsed: " + elapsed/(1000000.0) + "ms");

                socket.close(); // Close the socket and its streams
        }
}
}
