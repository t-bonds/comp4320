import socket
import sys
import time
# from Request.java import *
# from Response.java import *
from RequestBinConst.java import *
# from RequestEncoder.java import *
# from RequestDecoder.java import *

if len(sys.argv) != 2 and len(sys.argv) != 3:
    raise ValueError('Parameter(s): <Destination>  <Port> [<encoding]')

serverAddress = (sys.argv[1], sys.argv[2] + RequestBinConst.GROUP_NUMBER)

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

    encoder = RequestEncoder(RequestEncoderBin(sys.argv[2]) if len(sys.argv) == 3 else RequestEncoderBin())
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

    decoder = RequestDecoder(RequestDecoderBin(sys.argv(1)) if len(sys.argv) == 3 else RequestDecoderBin())

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
