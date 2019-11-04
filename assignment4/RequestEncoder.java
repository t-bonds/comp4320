public interface RequestEncoder {
  byte[] encode(Request Request) throws Exception;
  byte[] encode(Response Response) throws Exception;
}
