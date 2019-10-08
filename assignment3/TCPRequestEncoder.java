public interface TCPRequestEncoder {
byte[] encode(TCPRequest TCPRequest) throws Exception;
byte[] encode(Response Response) throws Exception;
}
