import java.net.*;  // for Socket
import java.io.*;   // for IOException and Input/OutputStream
import java.util.Scanner; // for Scanner

public class myFirstTCPClient {

public static void main(String[] args) throws IOException {

        if ((args.length < 1) || (args.length > 2)) // Test for correct # of args
                throw new IllegalArgumentException("Parameter(s): <Server> <Word> [<Port>]");

        String server = args[0]; // Server name or IP address



        int servPort = (args.length == 2) ? Integer.parseInt(args[1]) : 7;

        //TODO CREATE FOR LOOP
        //TODO REMOVE PRINT STATEMENT INSIDE IF STATEMENT


        System.out.print("Please enter a message shorter than 128 characters: ");
        Scanner scan = new Scanner(System.in);
        String firstAddress = scan.nextLine();
        if (firstAddress.length() <= 128) {
                System.out.println("Your message is: "+ firstAddress);
        }
        else {
          throw new IllegalArgumentException("Your message must be shorter than 128 characters.");
        }


        // Create socket that is connected to server on specified port
        Socket socket = new Socket(server, servPort);
        System.out.println("Connected to server...sending echo string");

        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();

        out.write(firstAddress.getBytes()); // Send the encoded string to the server

        // Receive the same string back from the server
        int totalBytesRcvd = 0; // Total bytes received so far
        int bytesRcvd;     // Bytes received in last read

        //TODO MEASURE DURATION BETWEEN WHEN MESSAGE WAS SENT AND RECEIVED
        //TODO PRINT TIME MESSAGE RECEIVED
        //TODO COLLECT ROUND TRIP TIME

        while (totalBytesRcvd < firstAddress.getBytes().length) {
                if ((bytesRcvd = in.read(firstAddress.getBytes(), totalBytesRcvd,
                                         firstAddress.getBytes().length - totalBytesRcvd)) == -1)
                        throw new SocketException("Connection close prematurely");
                totalBytesRcvd += bytesRcvd;
        }

        System.out.println("Received: " + new String(firstAddress.getBytes()));

        socket.close(); // Close the socket and its streams
}
}
