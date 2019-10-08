import java.net.*;  // for DatagramSocket, DatagramPacket, and InetAddress
import java.io.*;   // for IOException
import java.util.Scanner;

public class ClientUDP {

public static void main(String args[]) throws Exception {

        if (args.length != 2 && args.length != 3) // Test for correct # of args
                throw new IllegalArgumentException("Parameter(s): <Destination>" +
                                                   " <Port> [<encoding]");


        InetAddress destAddr = InetAddress.getByName(args[0]); // Destination address
        int destPort = Integer.parseInt(args[1]) + RequestBinConst.GID; // Destination port

        for(;;) {
                Byte TML = 6;
                Byte requestID = 1;
                Byte numberOperands = 1;
                Byte opCode;
                Short operand1 = 0;
                Short operand2 = 0;

                Scanner scan = new Scanner(System.in);
                System.out.println("List of Op Codes: ");
                System.out.println("\tOperation: \"+\"\tOpcode: 0" +
                                   "\n\tOperation: \"-\"\tOpcode: 1" +
                                   "\n\tOperation: \"*\"\tOpcode: 2" +
                                   "\n\tOperation: \"/\"\tOpcode: 3" +
                                   "\n\tOperation: \">>\"\tOpcode: 4" +
                                   "\n\tOperation: \"<<\"\tOpcode: 5" +
                                   "\n\tOperation: \"~\"\tOpcode: 6");

                do {
                        System.out.print("Enter the Op Code of the operation you wish to perform: ");
                        int opCode_INT = Integer.parseInt(scan.nextLine());
                        opCode = (byte) opCode_INT;
                } while (opCode > 6);

                System.out.print("Enter the first operand: ");
                int operand1_INT = Integer.parseInt(scan.nextLine());
                operand1 = (short) operand1_INT;


                if (opCode < 6) {
                        TML = (byte) 8;
                        numberOperands = (byte) 2;
                        System.out.print("Enter the second operand: ");
                        int operand2_INT = Integer.parseInt(scan.nextLine());
                        operand2 = (short) operand2_INT;
                }

                Request Request = new Request(TML, requestID, opCode, numberOperands, operand1, operand2);

                DatagramSocket sock = new DatagramSocket(); // UDP socket for sending


                // Use the encoding scheme given on the command line (args[2])
                RequestEncoder encoder = (args.length == 3 ?
                                          new RequestEncoderBin(args[2]) :
                                          new RequestEncoderBin());

                byte[] codedRequest = encoder.encode(Request); // Encode Request

                DatagramPacket message = new DatagramPacket(codedRequest, codedRequest.length,
                                                            destAddr, destPort);

                // D E B U G G I N G   S T U F F
                System.out.println("Message length: " + message.getLength());
                System.out.print("\nD E B U G\nMessage bytes: ");
                byte[] byteBufferDebug = message.getData();
                for (int i = 0; i < Request.TML; i++) {
                        System.out.format(" 0x%x", byteBufferDebug[i]);
                }
                System.out.println("\nD E B U G\n");
                // D E B U G G I N G   S T U F F

                sock.send(message);
                long timeAtSend = System.nanoTime();

                sock.receive(message);
                long timeElapsed = System.nanoTime() - timeAtSend;

                RequestDecoder decoder = (args.length == 2 ? // Which encoding
                                          new RequestDecoderBin(args[1]) :
                                          new RequestDecoderBin() );

                Response Response = decoder.decodeResponse(message);

                //print stuff
                System.out.print("Message bytes: ");
                byte[] byteBuffer = message.getData();
                for (int i = 0; i < Response.TML; i++) {
                        System.out.format(" 0x%x", byteBuffer[i]);
                }

                System.out.println("\n" + Response);
                System.out.println("Time Elapsed: " + timeElapsed / 1000000.0 + "ms\n");
        }


        //sock.close();
}
}
