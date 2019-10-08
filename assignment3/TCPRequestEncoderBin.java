import java.io.*;  // for ByteArrayOutputStream and DataOutputStream

public class TCPRequestEncoderBin implements TCPRequestEncoder, TCPRequestBinConst {

private String encoding;    // Character encoding

public TCPRequestEncoderBin() {
        encoding = DEFAULT_ENCODING;
}

public TCPRequestEncoderBin(String encoding) {
        this.encoding = encoding;
}

public byte[] encode(TCPRequest TCPRequest) throws Exception {

        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(buf);

        out.writeByte(TCPRequest.TML);
        out.writeByte(TCPRequest.ID);
        out.writeByte(TCPRequest.opCode);
        out.writeByte(TCPRequest.operands);
        out.writeShort(TCPRequest.op1);
        out.writeShort(TCPRequest.op2);

        out.flush();
        return buf.toByteArray();
}

public byte[] encode(Response Response) throws Exception {

        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(buf);

        out.writeByte(Response.TML);
        out.writeByte(Response.ID);
        out.writeByte(Response.error);
        out.writeInt(Response.result);

        out.flush();
        return buf.toByteArray();
}
}
