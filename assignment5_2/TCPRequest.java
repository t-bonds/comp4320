import java.io.*;  // for ByteArrayOutputStream and DataOutputStream

public class TCPRequest {

   public Byte TML;                 // total Message Length (in bytes) including TML
   public Byte ID;           // request ID incremented with each request sent
   public Byte opCode;              // number specifying the desired operation
   public Byte operands;      // number of operands; 2 for +, -, *, / and shifts. 1 for compliment
   public Short op1;            // first operand
   public Short op2;            // second operand


   public TCPRequest(Byte TML, Byte ID, Byte opCode,
                 Byte operands, Short op1, Short op2)  {
      this.TML = TML;
      this.ID = ID;
      this.opCode = opCode;
      this.operands = operands;
      this.op1 = op1;
      this.op2 = op2;
   }

   public String toString() {
      final String EOLN = java.lang.System.getProperty("line.separator");
      String value = "TML = " + TML + EOLN +
                    "Request ID = " + ID + EOLN +
                    "Op Code  = " + opCode + EOLN +
                    "Number Operands = " + operands + EOLN +
                    "Operand 1 = " + op1 + EOLN +
                    "Operand 2 = " + op2 + EOLN;
      return value;
   }

   public byte[] toByteArray() throws IOException {
      ByteArrayOutputStream os = new ByteArrayOutputStream();
      DataOutputStream out = new DataOutputStream(os);
   
      out.writeByte(TML);
      out.writeByte(ID);
      out.writeByte(opCode);
      out.writeByte(operands);
      out.writeShort(op1);
      out.writeShort(op2);
      out.flush();
   
      return os.toByteArray();
   }

   public void printHex() throws IOException {
      byte[] byteBuffer = toByteArray();
      for (int i = 0; i < TML; i++) {
         System.out.format(" 0x%x", byteBuffer[i]);
      }
      System.out.println();
   }
}
