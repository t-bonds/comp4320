public class Request {

public int TML;                // Total Message Length
public int ID;                 // Request ID
public int opCode;             // Operand Code, Desired Operation
public int operands;           // Number of Operands
public double op1;             // Operand 1
public double op2;             // Operand 2




public Request(int TML, int ID, int opCode,
               int operands, double op1, double op2)  {
        this.TML           = TML;
        this.ID            = ID;
        this.opCode        = opCode;
        this.operands      = operands;
        this.op1           = op1;
        this.op2           = op2;

}
//TODO REWRITE WITH ERROR CODE AND RESULT
public String toString() {
        final String EOLN = java.lang.System.getProperty("line.separator");
        String value = "Total Message Length: " + TML + EOLN +
                       "Request ID: " + ID + EOLN +
                       "Operand Code = " + opCode + EOLN +
                       "Operand = " + operands + EOLN;
        // if (single)
        //         value += "Single" + EOLN;
        // else
        //         value += "Married" + EOLN;
        //
        // if (rich)
        //         value += "Rich" + EOLN;
        // else
        //         value += "Poor" + EOLN;
        //
        // if (female)
        //         value += "Female" + EOLN;
        // else
        //         value += "Male" + EOLN;
         return value;
}
}
