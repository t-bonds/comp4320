import socket
import sys
import time

GROUP_NUMBER = 24
DEFAULT_ENCODING = 'iso-8859-1'
MAX_WIRE_LENGTH  = 1024

ipAddress = sys.argv[1]
port = int(sys.argv[2])
gPort = port + GROUP_NUMBER

if len(sys.argv) != 2 and len(sys.argv) != 3:
    raise ValueError('Parameter(s): <Destination>  <Port> [<encoding]')

serverAddress = (ipAddress, gPort)

ID = 1
opCode = 1
op1 = 0
op2 = 0
exitStr = 'exit'

while (True):
    TML = 6
    operands = 1

    print("Please Enter The Following Values:\n")
    opString = input('\tOperand Type - \n\t\"0\" = + \n\t\"1\" = - \n\t\"2\" = * \n\t\"3\" = / \n\t\"4\" = >>  \n\t\"5\" = << \n\t\"6\" = ~ \n\t\"exit\" = Terminate \n\tEnter Selection: ')
    if str(opString) in "exit":
        sys.exit(0)

    opCode = int(opString)
    op1 = int(input("\n\tOperand 1: "))

    if opCode <= 5:
        op2 = int(input("\n\tOperand 2: "))
        TML = 8
        operands = 2

    requestStr = "TML: %d\nRequestID: %d\nOp Code: %d\nOperands: %d\nOperand 1: %d\nOperand 2: %d\n" % (
        TML, ID, opCode, operands, op1, op2)
    sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

    request = "%d%d%d%d%d%d" % (TML, ID, opCode, operands, op1, op2)
    codedRequest = bytearray()
    codedRequest.extend(request)



    print('\n\nMessage Length: %s' % TML)
    print('\n\nRequest Hex String: \n')
    # buffer = bytearray(codedRequest)
    for i in range(len(codedRequest)):
        print('\t0x%s\n' % int(codedRequest[i]))    #TODO LIST INDICES MUST BE INTEGERS, NOT STR
    send = sock.sendto(codedRequest, serverAddress)
    start = time.time()

    recv, server = sock.recvfrom(MAX_WIRE_LENGTH)
    finish = time.time()
    elapsed = start - finish

    response = [str(x).decode(DEFAULT_ENCODING) for x in recv]
    print('\n\nMessage Length: %d' % len(response))
    print('\n\nResponse Hex String: \n')
    byteBuffer = bytearray(recv)
    for i in recv:
        print('\t0x%s\n' % byteBuffer[i])
    print('\n%s' % response)
    final = elapsed / 1000000.0
    print('\n\nTime Elapsed: %dms\n' % final)
    ID = ID + 1
sock.close()
