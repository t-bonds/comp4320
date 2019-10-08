import java.io.*;  // for ByteArrayInputStream
import java.net.*; // for DatagramPacket

public class TCPRequestDecoderBin implements TCPRequestDecoder, TCPRequestBinConst {

private String encoding;    // Character encoding

public TCPRequestDecoderBin() {
        encoding = DEFAULT_ENCODING;
}

public TCPRequestDecoderBin(String encoding) {
        this.encoding = encoding;
}

public TCPRequest decodeTCPRequest(InputStream wire) throws IOException {
        DataInputStream src = new DataInputStream(wire);

        Byte TML             = src.readByte();
        Byte ID              = src.readByte();
        Byte opCode          = src.readByte();
        Byte operands        = src.readByte();
        Short op1            = src.readShort();
        Short op2            = src.readShort();

        return new TCPRequest(TML, ID, opCode, operands, op1, op2);
}

public Response decodeResponse(InputStream wire) throws IOException {
        DataInputStream src = new DataInputStream(wire);

        Byte TML             = src.readByte();
        Byte ID              = src.readByte();
        Byte error           = src.readByte();
        int result           = src.readInt();

        return new Response(TML, ID, error, result);
}

public TCPRequest decodeTCPRequest(DatagramPacket p) throws IOException {
        ByteArrayInputStream payload =
                new ByteArrayInputStream(p.getData(), p.getOffset(), p.getLength());
        return decodeTCPRequest(payload);
}

public Response decodeResponse(DatagramPacket p) throws IOException {
        ByteArrayInputStream payload =
                new ByteArrayInputStream(p.getData(), p.getOffset(), p.getLength());
        return decodeResponse(payload);
}
}
