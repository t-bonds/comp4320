import java.io.*;   // for InputStream and IOException
import java.net.*;  // for DatagramPacket

public interface TCPRequestDecoder {
TCPRequest decodeTCPRequest(InputStream source) throws IOException;
TCPRequest decodeTCPRequest(DatagramPacket packet) throws IOException;
Response decodeResponse(InputStream source) throws IOException;
Response decodeResponse(DatagramPacket packet) throws IOException;
}
