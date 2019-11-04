import java.net.*;  // for DatagramSocket and DatagramPacket
import java.io.*;   // for IOException

public class ServerUDP {

  public static void main(String[] args) throws Exception {

    if (args.length != 1 && args.length != 2)  // Test for correct # of args
	    throw new IllegalArgumentException("Parameter(s): <Port> [<encoding>]");

    int port = Integer.parseInt(args[0]) + RequestBinConst.GROUP_NUMBER;   // Receiving Port

    DatagramSocket sock = new DatagramSocket(port);  // UDP socket for receiving
    DatagramPacket packet = new DatagramPacket(new byte[1024],1024);

    System.out.println("Server Is Active. Enter \"Control + C\" to Terminate.\n");

    for (;;) {  // Run forever, receiving and echoing datagrams
      System.out.println("Waiting for packets from client");

      sock.receive(packet);

      // D E B U G G I N G   S T U F F
      System.out.println("Message length: " + packet.getLength());
      System.out.print("\nD E B U G\nMessage bytes: ");
      byte[] byteBufferDebug = packet.getData();
      for (int i = 0; i < 8; i++) {
        System.out.format(" 0x%x", byteBufferDebug[i]);
      }
      System.out.println("\nD E B U G\n");
      // D E B U G G I N G   S T U F F

      // Receive binary-encoded request
      // RequestDecoder decoder = new RequestDecoderBin();
      RequestDecoder decoder = (args.length == 2 ?   // Which encoding
          new RequestDecoderBin(args[1]) :
          new RequestDecoderBin() );

      Request Request = decoder.decodeRequest(packet);


      // Print receive confirmation
      System.out.println("Received Binary-Encoded Request");

      System.out.print("\nMessage bytes: ");
      byte[] byteBuffer = packet.getData();
        for (int i = 0; i < Request.TML; i++) {
          System.out.format(" 0x%x", byteBuffer[i]);
        }

      System.out.println();
      System.out.println(Request);

      byte errorCode = 0;
      if (packet.getLength() != Request.TML) {
        errorCode = (byte) 127;
      }

      int result = calculateResult(Request.opCode, Request.operand1, Request.operand2);

      Response Response = new Response(Request.TML, Request.requestID, errorCode, result);

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

  public static int calculateResult(byte opCodeIn, short operand1In, short operand2In) {
    int opCode = (int) opCodeIn;
    int operand1 = (int) operand1In;
    int operand2 = (int) operand2In;
    int result;
    switch (opCode) {
        case 0: // + operator
          result = (operand1 + operand2);
          break;
        case 1: // - operator
          result = (operand1 - operand2);
          break;
        case 2: // * operator
          result = (operand1 * operand2);
          break;
        case 3: // / operator
          result = (operand1 / operand2);
          break;
        case 4: // >> operator
          result =  (operand1 >> operand2);
          break;
        case 5: // << operator
          result = (operand1 <<  operand2);
          break;
        case 6: // ~ operator
          result = (~operand1);
          break;
        default:
          result = 0;
          break;
    }
    return result;
  }
}
