public class Response {

   public int TML;                // Total Message Length
   public int ID;                 // Request ID
   public int error = 0;              // Error Code BYTE?
   public int result;          // Result Of Calculations



   public Response(int TML, int ID, 
               int error, int result)  {
      this.TML           = TML;
      this.ID            = ID;
      this.error           = error;
      this.result           = result;
   
   }

   public String toString() {
      final String EOLN = java.lang.System.getProperty("line.separator");
      String value = "Total Message Length: " + TML + EOLN +
                    "Request ID: " + ID + EOLN +
                    "Error Code: " + error + EOLN +
                    "Result: " + result + EOLN;
      return value;
   }
}
