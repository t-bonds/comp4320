import java.io.*;  // for ByteArrayInputStream
import java.net.*; // for DatagramPacket

public class TCPRequestDecoderBin implements TCPRequestDecoder, TCPRequestBinConst {

   private String encoding;  // Character encoding

   public TCPRequestDecoderBin() {
      encoding = DEFAULT_ENCODING;
   }

   public TCPRequestDecoderBin(String encoding) {
      this.encoding = encoding;
   }

   public TCPRequest decodeRequest(InputStream wire) throws IOException {
      DataInputStream src = new DataInputStream(wire);
   
      Byte TML             = src.readByte();
      Byte ID              = src.readByte();
      Byte opCode          = src.readByte();
      Byte operands        = src.readByte();
      Short op1            = src.readShort();
      Short op2            = src.readShort();
   
      return new TCPRequest(TML, ID, opCode, operands, op1, op2);
   }

   public TCPResponse decodeResponse(InputStream wire) throws IOException {
      DataInputStream src = new DataInputStream(wire);
   
      Byte TML             = src.readByte();
      Byte ID              = src.readByte();
      Byte error           = src.readByte();
      int result           = src.readInt();
   
      return new TCPResponse(TML, ID, error, result);
   }

   public TCPRequest decodeRequest(DatagramPacket p) throws IOException {
      ByteArrayInputStream payload =
         new ByteArrayInputStream(p.getData(), p.getOffset(), p.getLength());
      return decodeRequest(payload);
   }

   public TCPResponse decodeResponse(DatagramPacket p) throws IOException {
      ByteArrayInputStream payload =
         new ByteArrayInputStream(p.getData(), p.getOffset(), p.getLength());
      return decodeResponse(payload);
   }
}
