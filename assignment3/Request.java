public class Request {

public Byte TML;                   // Total Message Length
public Byte ID;                    // Request ID
public Byte opCode;                // Operand Code, Desired Operation
public Byte operands;              // Number Of Operands
public Short op1;                  // Operand 1
public Short op2;                  // Operand 2


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
                       "Operand Code: " + opCode + EOLN +
                       "Number Of Operands: " + operands + EOLN +
                       "Operand 1: " + op1 + EOLN +
                       "Operand 2: " + op2 + EOLN;
        return value;
}
}
