import java.io.*;   // for InputStream and IOException
import java.net.*;  // for DatagramPacket

public interface TCPRequestDecoder {
   TCPRequest decodeRequest(InputStream source) throws IOException;
   TCPRequest decodeRequest(DatagramPacket packet) throws IOException;
   TCPResponse decodeResponse(InputStream source) throws IOException;
   TCPResponse decodeResponse(DatagramPacket packet) throws IOException;
}
