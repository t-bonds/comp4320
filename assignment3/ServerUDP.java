import java.net.*;  // for DatagramSocket and DatagramPacket
import java.io.*;   // for IOException

public class ServerUDP {

public static void main(String[] args) throws Exception {

        if (args.length != 1 && args.length != 2) // Test for correct # of args
                throw new IllegalArgumentException("Parameter(s): <Port> [<encoding>]");

        int port = Integer.parseInt(args[0]) + TCPRequestBinConst.GROUP_NUMBER; // Receiving Port

        DatagramSocket sock = new DatagramSocket(port); // UDP socket for receiving
        DatagramPacket packet = new DatagramPacket(new byte[1024],1024);

        System.out.println("Server Is Active. Enter \"Control + C\" to Terminate.\n");

        for (;;) { // Run forever, receiving and echoing datagrams
                System.out.println("Server is Idle. Awaiting Packets.\n");

                sock.receive(packet);

                // D E B U G G I N G   S T U F F
                System.out.println("Message length: " + packet.getLength());
                System.out.print("\nHex String: ");
                byte[] receiveBuffer = packet.getData();
                for (int i = 0; i < 8; i++) {
                        System.out.format(" 0x%x", receiveBuffer[i]);
                }


                // Receive binary-encoded request
                // TCPRequestDecoder decoder = new TCPRequestDecoderBin();
                TCPRequestDecoder decoder = (args.length == 2 ? // Which encoding
                                          new TCPRequestDecoderBin(args[1]) :
                                          new TCPRequestDecoderBin() );

                TCPRequest TCPRequest = decoder.decodeTCPRequest(packet);


                // Print receive confirmation
                System.out.println("Received Binary-Encoded TCPRequest");

                System.out.print("\nMessage bytes: ");
                byte[] sendBuffer = packet.getData();
                for (int i = 0; i < TCPRequest.TML; i++) {
                        System.out.format(" 0x%x", sendBuffer[i]);
                }

                System.out.println();
                System.out.println(TCPRequest);

                byte errorCode = 0;
                if (packet.getLength() != TCPRequest.TML) {
                        errorCode = (byte) 127;
                }

                int result = calculate(TCPRequest.opCode, TCPRequest.op1, TCPRequest.op2);

                Response Response = new Response(TCPRequest.TML, TCPRequest.ID, error, result);

                TCPRequestEncoder encoder = (args.length == 3 ?
                                          new TCPRequestEncoderBin(args[2]) :
                                          new TCPRequestEncoderBin());

                byte[] sendResponse = encoder.encode(Response); // Encode TCPRequest
                packet.setData(sendResponse);
                packet.setLength(sendResponse.length);
                sock.send(packet);
        }

}

public static int calculate(byte opCodeVar, short op1Var, short op2Var) {
        int opCode = (int) opCodeVar;
        int op1 = (int) op1Var;
        int op2 = (int) op2Var;
        int result;
        switch (opCode) {
        case 0: // + operator
                result = (op1 + op2);
                break;
        case 1: // - operator
                result = (op1 - op2);
                break;
        case 2: // * operator
                result = (op1 * op2);
                break;
        case 3: // / operator
                result = (op1 / op2);
                break;
        case 4: // >> operator
                result =  (op1 >> op2);
                break;
        case 5: // << operator
                result = (op1 <<  op2);
                break;
        case 6: // ~ operator
                result = (~op1);
                break;
        default:
                result = 0;
                break;
        }
        return result;
}
}
