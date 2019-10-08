public class Response {

public Byte TML;                 // total Message Length (in bytes) including TML
public Byte requestID;           // request ID incremented with each request sent
public Byte errorCode;           // 0 if the request was valid. 127 if the request was invalid (length not matching TML)
public int result;              // the result of the requested operation


public Response(Byte TML, Byte requestID, Byte errorCode, int result)  {
        this.TML = TML;
        this.requestID = requestID;
        this.errorCode = errorCode;
        this.result = result;
}

public String toString() {
        final String EOLN = java.lang.System.getProperty("line.separator");
        String value = "TML = " + TML + EOLN +
                       "Request ID = " + requestID + EOLN +
                       "Error Code  = " + errorCode + EOLN +
                       "result = " + result + EOLN;
        return value;
}
}
