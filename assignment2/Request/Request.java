public class Request {

   public int TML;                // Total Message Length
   public int ID;                 // Request ID
   public int opCode;             // Operand Code, Desired Operation
   public int operands;           // Number of Operands
   public int op1;             // Operand 1
   public int op2;             // Operand 2
   public int error = 0;              // Error Code BYTE?
   public int result;          // Result Of Calculations



   public Request(int TML, int ID, int opCode,
              int operands, int op1, int op2)  {
      this.TML           = TML;
      this.ID            = ID;
      this.opCode        = opCode;
      this.operands      = operands;
      this.op1           = op1;
      this.op2           = op2;

   }

   public int calculate(int op1, int opCode, int op2) {

      switch (opCode) {
      //TODO determine calculation errors
         case 0:
            result = op1 + op2;
            break;
         case 1:
            result = op1 - op2;
            break;
         case 2:
            result = op1 * op2;
            break;
         case 3:
            result = op1 / op2;
            break;
         case 4:
            result =  op1 >> op2;
            break;
         case 5:
            result = op1 <<  op2;
            break;
         case 6:
            result = ~op1;
            break;
         default:
            error = 1;
            break;
      }
      return result;
   }

   public String toString() {
      final String EOLN = java.lang.System.getProperty("line.separator");
      String value = "Total Message Length: " + TML + EOLN +
                    "Request ID: " + ID + EOLN +
                    "Error Code: " + error + EOLN +
                    "Result: " + calculate(op1, opCode, op2) + EOLN;
      return value;
   }
}
