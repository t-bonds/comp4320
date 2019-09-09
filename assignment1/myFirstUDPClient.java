import java.net.*;  // for DatagramSocket, DatagramPacket, and InetAddress
import java.io.*;   // for IOException
import java.util.Scanner; // for Scanner

public class myFirstUDPClient {

private static final int TIMEOUT = 3000;     // Resend timeout (milliseconds)
private static final int MAXTRIES = 5;       // Maximum retransmissions

public static void main(String[] args) throws IOException {

        if ((args.length < 1) || (args.length > 2)) // Test for correct # of args
                throw new IllegalArgumentException("Parameter(s): <Server> [<Port>]");

        InetAddress serverAddress = InetAddress.getByName(args[0]); // Server address



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

                DatagramSocket socket = new DatagramSocket();

                socket.setSoTimeout(TIMEOUT); // Maximum receive blocking time (milliseconds)

                DatagramPacket sendPacket = new DatagramPacket(byteBuffer, // Sending packet
                                                               byteBuffer.length, serverAddress, servPort);


                DatagramPacket receivePacket =                  // Receiving packet
                                               new DatagramPacket(new byte[byteBuffer.length], byteBuffer.length);


                int tries = 0; // Packets may be lost, so we have to keep trying
                boolean receivedResponse = false;
                long start = System.nanoTime();
                do {
                        socket.send(sendPacket); // Send the echo string

                        try {
                                socket.receive(receivePacket); // Attempt echo reply reception


                                if (!receivePacket.getAddress().equals(serverAddress)) // Check source
                                        throw new IOException("Received packet from an unknown source");

                                receivedResponse = true;
                        } catch (InterruptedIOException e) { // We did not get anything
                                tries += 1;
                                System.out.println("Timed out, " + (MAXTRIES-tries) + " more tries...");
                        }

                } while ((!receivedResponse) && (tries < MAXTRIES));
                long finish = System.nanoTime();
                long elapsed = finish - start;
                if (receivedResponse) {
                        System.out.println("Received: " + new String(receivePacket.getData()));
                        System.out.println("Time Elapsed: " + elapsed/(1000000.0) + "ms");
                      }
                else
                        System.out.println("No response -- giving up.");

                socket.close();
        }
}
}
