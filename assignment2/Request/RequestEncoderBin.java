import java.io.*;  // for ByteArrayOutputStream and DataOutputStream

public class RequestEncoderBin implements RequestEncoder, RequestBinConst {

   private String encoding;    // Character encoding

   public RequestEncoderBin() {
      encoding = DEFAULT_ENCODING;
   }

   public RequestEncoderBin(String encoding) {
      this.encoding = encoding;
   }

   public byte[] encode(Request request) throws Exception {

      ByteArrayOutputStream buf = new ByteArrayOutputStream();
      DataOutputStream out = new DataOutputStream(buf);
      out.writeInt(request.TML);
      out.writeInt(request.ID);
      out.writeInt(request.opCode);
      out.writeInt(request.operands);
      out.writeInt(request.op1);
      out.writeInt(request.op2);
      out.writeInt(request.result);
      out.flush();
      return buf.toByteArray();
   }
}
