import java.io.*;  // for ByteArrayInputStream
import java.net.*; // for DatagramPacket

public class RequestDecoderBin implements RequestDecoder, RequestBinConst {

private String encoding;    // Character encoding

public RequestDecoderBin() {
        encoding = DEFAULT_ENCODING;
}

public RequestDecoderBin(String encoding) {
        this.encoding = encoding;
}

public Request decodeRequest(InputStream wire) throws IOException {
        DataInputStream src = new DataInputStream(wire);

        Byte TML             = src.readByte();
        Byte requestID       = src.readByte();
        Byte opCode          = src.readByte();
        Byte numberOperands  = src.readByte();
        Short operand1       = src.readShort();
        Short operand2       = src.readShort();

        return new Request(TML, requestID, opCode, numberOperands, operand1, operand2);
}

public Response decodeResponse(InputStream wire) throws IOException {
        DataInputStream src = new DataInputStream(wire);

        Byte TML             = src.readByte();
        Byte requestID       = src.readByte();
        Byte errorCode       = src.readByte();
        int result           = src.readInt();

        return new Response(TML, requestID, errorCode, result);
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
