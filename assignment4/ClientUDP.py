import socket
import sys
import time

GROUP_NUMBER = 24
if len(sys.argv) != 2 and len(sys.argv) != 3:
    raise ValueError('Parameter(s): <Destination>  <Port> [<encoding]')

serverAddress = (sys.argv[1], sys.argv[2] + GROUP_NUMBER)

ID = bytes(1)
opCode = bytes(1)
op1 = 0.0
op2 = 0.0

while (True):
    TML = bytes(6)
    operands = bytes(1)

    print("Please Enter The Following Values:\n")
    opString = raw_input(
        '\tOperand Type - \n\t\"0\" = + \n\t\"1\" = - \n\t\"2\" = * \n\t\"3\" = / \n\t\"4\" = >>  \n\t\"5\" = << \n\t\"6\" = ~ \n\t\"exit\" = Terminate \n\tEnter Selection: ')
    if opString == "exit":
        break
    opCodeInt = int(opString)
    opCode = bytes(opCodeInt)

    op1Int = input("\n\tOperand 1: ")
    op1 = float(op1Int)

    if opCode <= 5:
        op2Int = input("\n\tOperand 2: ")
        op2 = float(op2Int)
        TML = bytes(8)
        operands = bytes(2)

    request = Request(TML, ID, opCode, operands, op1, op2)
    sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

    encoder = RequestEncoderBin(sys.argv[2]) if len(
        sys.argv) == 3 else RequestEncoderBin()
    codedRequest = bytes(encoder.encode(request))

    print('\n\nMessage Length: %d' % len(codedRequest))
    print('\n\nRequest Hex String: \n')
    buffer = bytearray(codedRequest)
    for i in codedRequest:
        print('\t0x%d\n' % buffer[i])
    send = sock.sendto(codedRequest, serverAddress)
    start = time.time()

    recv, server = sock.recvfrom()
    finish = time.time()
    elapsed = start - finish

    decoder = RequestDecoderBin(sys.argv(1)) if len(
        sys.argv) == 3 else RequestDecoderBin()

    response = decoder.decodeResponse(recv)
    print('\n\nMessage Length: %d' % len(recv))
    print('\n\nResponse Hex String: \n')
    byteBuffer = bytearray(recv)
    for i in recv:
        print('\t0x%d\n' % byteBuffer[i])
    print('\n%s' % response)
    final = elapsed / 1000000.0
    print('\n\nTime ELapsed: %dms\n' % final)
    ID = ID + 1
sock.close()


class Request(object):

    def __init__(self, TML, ID, opCode, operands, op1, op2):
        self.TML = TML
        self.ID = ID
        self.opCode = opCode
        self.operands = operands
        self.op1 = op1
        self.op2 = op2

    def toString():
        value = "TML: %d\nRequestID: %d\nOp Code: %d\nOperands: %d\nOperand 1: %d\nOperand 2: %d\n" % (
            TML, ID, opCode, operands, op1, op2)
        return value


class RequestEncoderBin():
    def __init__(self, encoding):
        super(RequestEncoderBin, self).__init__()
        encoding = "ISO-8859-1"

    def encode(Request):
        buf = bytearray()
        out = bytearray()
        out.writeByte(Request.TML)
        out.writeByte(Request.ID)
        out.writeByte(Request.opCode)
        out.writeShort(Request.op1)
        out.writeShort(Request.op2)




class RequestDecoderBin(object):

    def __init__(self, arg):
        super(RequestDecoderBin, self).__init__()
        self.arg = arg
