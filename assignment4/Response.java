public class Response {

public Byte TML;                // total Message Length (in bytes) including TML
public Byte ID;          // request ID incremented with each request sent
public Byte error;          // 0 if the request was valid. 127 if the request was invalid (length not matching TML)
public int result;             // the result of the requested operation


public Response(Byte TML, Byte ID, Byte error, int result)  {
        this.TML = TML;
        this.ID = ID;
        this.error = error;
        this.result = result;
}

public String toString() {
        final String EOLN = java.lang.System.getProperty("line.separator");
        String value = "TML: " + TML + EOLN +
                       "Request ID: " + ID + EOLN +
                       "Error Code: " + error + EOLN +
                       "Result: " + result + EOLN;
        return value;
}

public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(byteStream);

        out.writeByte(TML);
        out.writeByte(ID);
        out.writeByte(error);
        out.writeInt(result);

        return byteStream.toByteArray();
}

public void Hex() throws IOException {
        byte[] buffer = toByteArray();
        for (int i = 0; i < TML; i++) {
                System.out.format("\t0x%x\n", buffer[i]);
        }
}

}
