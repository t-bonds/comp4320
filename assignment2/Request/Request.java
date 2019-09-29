public class Request {

public int TML;                // Total Message Length
public int ID;                 // Request ID
public int opCode;             // Operand Code, Desired Operation
public int operands;           // Number of Operands
public double op1;             // Operand 1
public double op2;             // Operand 2
public int error = 0;              // Error Code BYTE?
public double result;          // Result Of Calculations



public Request(int TML, int ID, int opCode,
               int operands, double op1, double op2)  {
        this.TML           = TML;
        this.ID            = ID;
        this.opCode        = opCode;
        this.operands      = operands;
        this.op1           = op1;
        this.op2           = op2;

}

public double calculate(double op1, int opCode, double op2) {

        switch (opCode) {

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
                result = ((int) op1 >> (int) op2);
                break;
        case 5:
                result = ((int) op1 << (int) op2);
                break;
        case 6:
                result = ~((int) op1);
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
