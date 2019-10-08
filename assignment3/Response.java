public class Response {

public Byte TML;                 // Total Message Length
public Byte ID;                  // Request ID
public Byte error;               // Error Code
public int result;               // Calculated Answer


public Response(Byte TML, Byte ID, Byte error, int result)  {
        this.TML = TML;
        this.ID = ID;
        this.error = error;
        this.result = result;
}

public String toString() {
        final String EOLN = java.lang.System.getProperty("line.separator");
        String value = "TML:" + TML + EOLN +
                       "Request ID: " + ID + EOLN +
                       "Error Code: " + error + EOLN +
                       "Result: " + result + EOLN;
        return value;
}
}
