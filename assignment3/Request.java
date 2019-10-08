public class Request {

public Byte TML;                 // total Message Length (in bytes) including TML
public Byte requestID;           // request ID incremented with each request sent
public Byte opCode;              // number specifying the desired operation
public Byte numberOperands;      // number of operands; 2 for +, -, *, / and shifts. 1 for compliment
public Short operand1;            // first operand
public Short operand2;            // second operand


public Request(Byte TML, Byte requestID, Byte opCode,
               Byte numberOperands, Short operand1, Short operand2)  {
        this.TML = TML;
        this.requestID = requestID;
        this.opCode = opCode;
        this.numberOperands = numberOperands;
        this.operand1 = operand1;
        this.operand2 = operand2;
}

public String toString() {
        final String EOLN = java.lang.System.getProperty("line.separator");
        String value = "TML = " + TML + EOLN +
                       "Request ID = " + requestID + EOLN +
                       "Op Code  = " + opCode + EOLN +
                       "Number Operands = " + numberOperands + EOLN +
                       "Operand 1 = " + operand1 + EOLN +
                       "Operand 2 = " + operand2 + EOLN;
        return value;
}
}
