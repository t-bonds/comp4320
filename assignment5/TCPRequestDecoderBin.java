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

   public TCPRequest decodeRequest(InputStream wire) throws IOException {
   
      byte[] messageBuffer = new byte[MAX_WIRE_LENGTH];
      wire.read(messageBuffer);
      String decodedString = new String(messageBuffer);
            
      int item = 1;
      Byte TML = (byte) getItem(decodedString, item++);
      Byte ID = (byte) getItem(decodedString, item++);
      Byte opCode = (byte) getItem(decodedString, item++);
      Byte operands = (byte) getItem(decodedString, item++);
      Short op1 = (short) getItem(decodedString, item++);
      Short op2 = (short) getItem(decodedString, item);
   
   
      // DataInputStream src = new DataInputStream(wire);
   
      // Byte TML             = src.readByte();
      // Byte requestID       = src.readByte();
      // Byte opCode          = src.readByte();
      // Byte numberOperands  = src.readByte();
      // Short operand1       = src.readShort();
      // Short operand2       = src.readShort();
   
      return new TCPRequest(TML, ID, opCode, operands, op1, op2);
   }

   public TCPResponse decodeResponse(InputStream wire) throws IOException {
      DataInputStream src = new DataInputStream(wire);
   
      Byte TML             = src.readByte();
      Byte ID       = src.readByte();
      Byte error       = src.readByte();
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

   public static int getItem(String decodedString, int itemNumber) {
   
      int idx1 = -1;
      int idx2 = -1;
   
      for (int i = 0; i < itemNumber; i++) {
         idx1 = decodedString.indexOf('x', ++idx1);
         idx2 = decodedString.indexOf('x', idx1 + 1);
      }
   
      int item = Integer.parseInt(decodedString.substring(idx1 + 1, idx2));
   
      return item;
   }
}
