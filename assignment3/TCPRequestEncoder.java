public interface TCPRequestEncoder {
   byte[] encode(TCPRequest Request) throws Exception;
   byte[] encode(TCPResponse Response) throws Exception;
}
