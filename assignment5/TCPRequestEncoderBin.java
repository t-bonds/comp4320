import java.io.*;  // for ByteArrayOutputStream and DataOutputStream

public class TCPRequestEncoderBin implements TCPRequestEncoder, TCPRequestBinConst {

   private String encoding;    // Character encoding

   public TCPRequestEncoderBin() {
      encoding = DEFAULT_ENCODING;
   }

   public TCPRequestEncoderBin(String encoding) {
      this.encoding = encoding;
   }

   public byte[] encode(TCPRequest Request) throws Exception {
   
      ByteArrayOutputStream buf = new ByteArrayOutputStream();
      DataOutputStream out = new DataOutputStream(buf);
   
      out.writeByte(Request.TML);
      out.writeByte(Request.ID);
      out.writeByte(Request.opCode);
      out.writeByte(Request.operands);
      out.writeShort(Request.op1);
      out.writeShort(Request.op2);
   
      out.flush();
      return buf.toByteArray();
   }

   public byte[] encode(TCPResponse Response) throws Exception {
   
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
