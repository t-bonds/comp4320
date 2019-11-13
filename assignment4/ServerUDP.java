import java.net.*;  // for DatagramSocket and DatagramPacket
import java.io.*;   // for IOException

public class ServerUDP {

public static void main(String[] args) throws Exception {

        if (args.length != 1 && args.length != 2) // Test for correct # of args
                throw new IllegalArgumentException("Parameter(s): <Port> [<encoding>]");

        int port = Integer.parseInt(args[0]) + RequestBinConst.GROUP_NUMBER; // Receiving Port

        DatagramSocket sock = new DatagramSocket(port); // UDP socket for receiving


        System.out.println("Server Is Active. Enter \"Control + C\" to Terminate.\n");

        for (;;) { // Run forever, receiving and echoing datagrams
                DatagramPacket packet = new DatagramPacket(new byte[1024],1024);
                System.out.println("Server is Idle. Awaiting Packets.\n");

                sock.receive(packet);

                // Receive binary-encoded request
                // RequestDecoder decoder = new RequestDecoderBin();
                RequestDecoder decoder = (args.length == 2 ? // Which encoding
                                          new RequestDecoderBin(args[1]) :
                                          new RequestDecoderBin() );

                Request Request = decoder.decodeRequest(packet);


                // Print receive confirmation
                System.out.println("Received Binary-Encoded Request");
                System.out.println("\nMessage Length: " + Request.TML);
                System.out.println("\nRequest Hex String:");
                Request.Hex();
                System.out.println();
                System.out.println(Request);

                byte TML = (byte) 7;
                byte error = 0;
                if (packet.getLength() != Request.TML) {
                        error = (byte) 127;
                }

                int result = calculate(Request.opCode, Request.op1, Request.op2);

                Response Response = new Response(Request.TML, Request.ID, error, result);

                RequestEncoder encoder = (args.length == 3 ?
                                          new RequestEncoderBin(args[2]) :
                                          new RequestEncoderBin());

                byte[] codedResponse = encoder.encode(Response); // Encode Request
                packet.setData(codedResponse);
                packet.setLength(codedResponse.length);
                sock.send(packet);
        }
        /* NOT REACHED */
}

public static int calculate(byte opCodeVar, short op1Var, short op2Var) {
        int opCode = (int) opCodeVar;
        int op1 = (int) op1Var;
        int op2 = (int) op2Var;
        int result;
        switch (opCode) {
        case 0:  // + operator
                result = (op1 + op2);
                break;
        case 1:  // - operator
                result = (op1 - op2);
                break;
        case 2:  // * operator
                result = (op1 * op2);
                break;
        case 3:  // / operator
                result = (op1 / op2);
                break;
        case 4:  // >> operator
                result =  (op1 >> op2);
                break;
        case 5:  // << operator
                result = (op1 <<  op2);
                break;
        case 6:  // ~ operator
                result = (~op1);
                break;
        default:
                result = 0;
                break;
        }
        return result;
}
}
