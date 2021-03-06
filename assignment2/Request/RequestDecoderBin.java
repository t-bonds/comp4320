import java.io.*;  // for ByteArrayInputStream
import java.net.*; // for DatagramPacket

public class RequestDecoderBin implements RequestDecoder, RequestBinConst {

   private String encoding;       // Character encoding

   public RequestDecoderBin() {
      encoding = DEFAULT_ENCODING;
   }

   public RequestDecoderBin(String encoding) {
      this.encoding = encoding;
   }

   public Request decode(InputStream wire) throws IOException {
      DataInputStream src = new DataInputStream(wire);
      int TML           = src.readInt();
      int ID            = src.readInt();
      int opCode  = src.readInt();
      int operands       = src.readInt();
      int op1             = src.readInt();
      int op2             = src.readInt();
   
   
   
      return new Request(TML,ID, opCode, operands,
                        op1, op2);
   }

   public Request decode(DatagramPacket p) throws IOException {
      ByteArrayInputStream payload =
             new ByteArrayInputStream(p.getData(), p.getOffset(), p.getLength());
      return decode(payload);
   }
}
