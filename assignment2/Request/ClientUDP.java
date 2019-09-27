import java.net.*;  // for DatagramSocket, DatagramPacket, and InetAddress
import java.io.*;   // for IOException
import java.util.Scanner; // for Scanner

public class ClientUDP {

public static void main(String args[]) throws Exception {

        if (args.length != 2 && args.length != 3) // Test for correct # of args
                throw new IllegalArgumentException("Parameter(s): <Destination>" +
                                                   " <Port> [<encoding]");


        InetAddress destAddr = InetAddress.getByName(args[0]); // Destination address
        int destPort = Integer.parseInt(args[1]) + RequestBinConst.GROUP_NUMBER;             // Destination port

        int TML, opCode, op1;
        int operands = 1;
        int op2 = 0;

        Scanner scan = new Scanner(System.in);
        System.out.println("Please Enter The Following Values:");
        System.out.print("\tOperand Type: \n\t(0 = +, 1 = -, 2 = *, 3 = /, 4 = >>, 5 = <<, 6 = ~): ");
        opCode = Integer.parseInt(scan.nextLine());

        System.out.print("\n\tOperand 1: ");
        op1 = Integer.parseInt(scan.nextLine());

        //TODO CALCULATE TML FOR ~
        //TML =

        if (opCode <=5) {

                System.out.print("\n\tOperand 2: ");
                op2 = Integer.parseInt(scan.nextLine());
                operands = 2;
                //TODO CALCULATE TML FOR ALL OTHER OPCODE
                //TML =

        }
        //TODO CREATE ID


        Request request = new Request(TML, ID, opCode, operands, op1, op2);

        DatagramSocket sock = new DatagramSocket(); // UDP socket for sending


        // Use the encoding scheme given on the command line (args[2])
        RequestEncoder encoder = (args.length == 3 ?
                                  new RequestEncoderBin(args[2]) :
                                  new RequestEncoderBin());


        byte[] codedRequest = encoder.encode(request); // Encode request

        DatagramPacket message = new DatagramPacket(codedRequest, codedRequest.length,
                                                    destAddr, destPort);
        sock.send(message);

        sock.close();
}
}
