public class Request {

   public Byte TML;             // total Message Length (in bytes) including TML
   public Byte ID;       // request ID incremented with each request sent
   public Byte opCode;          // number specifying the desired operation
   public Byte operands;  // number of operands; 2 for +, -, *, / and shifts. 1 for compliment
   public Short op1;        // first operand
   public Short op2;        // second operand


   public Request(Byte TML, Byte ID, Byte opCode,
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
      String value = "TML: " + TML + EOLN +
                   "Request ID: " + ID + EOLN +
                   "Op Code: " + opCode + EOLN +
                   "Operands: " + operands + EOLN +
                   "Operand 1: " + op1 + EOLN +
                   "Operand 2: " + op2 + EOLN;
      return value;
   }
}
