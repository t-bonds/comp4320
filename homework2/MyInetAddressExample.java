import java.net.*;  // for InetAddress
import java.util.Scanner; // for User Input

public class MyInetAddressExample {

  public static void main(String[] args) {

    // Get name and IP address of the local host
    try {
      InetAddress address = InetAddress.getLocalHost();
      System.out.println("Local Host:");
      System.out.println("\t" + address.getHostName());
      System.out.println("\t" + address.getHostAddress());
    } catch (UnknownHostException e) {
      System.out.println("Unable to determine this host's address");
    }


      // Get name(s)/address(es) of hosts given on user input

        Scanner scan = new Scanner(System.in);
        System.out.print("\nPlease Enter A Hostname: ");
        String firstAddress = scan.next();

        try {

        InetAddress address1 = InetAddress.getByName(firstAddress);
        System.out.println(firstAddress + ":");
        // Print the first name.
        System.out.println("\t" + address1.getHostName());

        String hostAddressString = address1.getHostAddress();
        String[] hostAddressStringArray = hostAddressString.split("\\.");

        System.out.print("\tBinary Format: ");

        for (String string : hostAddressStringArray) {
        int hostAddress = Integer.parseInt(string);
        String binary = Integer.toBinaryString(hostAddress);
        System.out.print(binary);
        }

        System.out.print("\n\tBinary Dotted-Quad Format: ");
        int i = 0;
        for (String string : hostAddressStringArray) {
        int hostAddress = Integer.parseInt(string);
        String binary = Integer.toBinaryString(hostAddress);
        i++;
        if (i < 4) {
        System.out.print(binary + ".");
      }
      else {
        System.out.print(binary);
      }
      }

        System.out.print("\n");

        System.out.println("\tDecimal Dotted-Quad Format: " + hostAddressString);


      }
      catch (UnknownHostException e) {
        System.out.println("Unable to find address for " + firstAddress);
      }


}
}
