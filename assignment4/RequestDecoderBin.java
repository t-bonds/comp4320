import java.io.*;  // for ByteArrayInputStream
import java.net.*; // for DatagramPacket

public class RequestDecoderBin implements RequestDecoder, RequestBinConst {

private String encoding;     // Character encoding

public RequestDecoderBin() {
        encoding = DEFAULT_ENCODING;
}

public RequestDecoderBin(String encoding) {
        this.encoding = encoding;
}

public Request decodeRequest(InputStream wire) throws IOException {

        BufferedReader decode = new BufferedReader(new InputStreamReader(wire));
        String decodedString = decode.readLine();
        int i = 1;
        Byte TML = (byte) getItem(decodedString, i++);
        Byte ID = (byte) getItem(decodedString, i++);
        Byte opCode = (byte) getItem(decodedString, i++);
        Byte operands = (byte) getItem(decodedString, i++);
        Short op1 = (short) getItem(decodedString, i++);
        Short op2 = (short) getItem(decodedString, i);



        //DataInputStream src = new DataInputStream(wire);

        // Byte TML             = src.readByte();
        // Byte ID       = src.readByte();
        // Byte opCode          = src.readByte();
        // Byte operands  = src.readByte();
        // Short op1       = src.readShort();
        // Short op2       = src.readShort();

        return new Request(TML, ID, opCode, operands, op1, op2);
}

public Response decodeResponse(InputStream wire) throws IOException {
        DataInputStream src = new DataInputStream(wire);

        Byte TML             = src.readByte();
        Byte ID       = src.readByte();
        Byte error       = src.readByte();
        int result           = src.readInt();

        return new Response(TML, ID, error, result);
}

public Request decodeRequest(DatagramPacket p) throws IOException {
        ByteArrayInputStream payload =
                new ByteArrayInputStream(p.getData(), p.getOffset(), p.getLength());
        return decodeRequest(payload);
}

public Response decodeResponse(DatagramPacket p) throws IOException {
        ByteArrayInputStream payload =
                new ByteArrayInputStream(p.getData(), p.getOffset(), p.getLength());
        return decodeResponse(payload);
}
}
