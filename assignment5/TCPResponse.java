import java.io.*;  // for ByteArrayOutputStream and DataOutputStream

public class TCPResponse {

   public Byte TML;                 // total Message Length (in bytes) including TML
   public Byte ID;           // request ID incremented with each request sent
   public Byte error;           // 0 if the request was valid. 127 if the request was invalid (length not matching TML)
   public int result;              // the result of the requested operation


   public TCPResponse(Byte TML, Byte ID, Byte error, int result)  {
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
      ByteArrayOutputStream os = new ByteArrayOutputStream();
      DataOutputStream out = new DataOutputStream(os);
   
      out.writeByte(TML);
      out.writeByte(ID);
      out.writeByte(error);
      out.writeInt(result);
   
      return os.toByteArray();
   }

   public void printHex() throws IOException {
      byte[] byteBuffer = toByteArray();
      for (int i = 0; i < TML - 1; i++) {
         System.out.format(" 0x%x", byteBuffer[i]);
      }
      System.out.println();
   }
}
