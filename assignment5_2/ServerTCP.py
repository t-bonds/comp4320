import socket
import sys

GROUP_NUMBER = 24
DEFAULT_ENCODING = 'iso-8859-1'
MAX_WIRE_LENGTH = 1024

port = int(sys.argv[1])

if len(sys.argv) != 2:
    raise ValueError("Parameter(s): <Port>")

destPort = port + GROUP_NUMBER
serverAddress = ('localhost', destPort)

sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
sock.bind(serverAddress)
sock.listen(1)

print("Server Is Active. Enter \"Control + C\" to Terminate.\n")

while True:

    print("Server is Idle. Awaiting Stream.\n")

    connection, clientAddress = sock.accept()
    recv = connection.recv(MAX_WIRE_LENGTH)

    request = [str(format(ord(x), 'x')).decode('utf-8') for x in recv]
    print("\nRequest Hex Values: ")
    print("%s" % request)

    TML, ID, opCode, operands, op11, op1, op22, op2 = [e for e in request]

    TML = int(TML)
    ID = int(ID)
    opCode = int(opCode)
    operands = int(operands)
    if op11 != u'0':
        op1 = int(op11) + int(op1)
    else:
        op1 = int(op1)
    if op22 != u'0':
        op2 = int(op22) + int(op2)
    else:
        op2 = int(op2)

    error = 0
    if len(request) != TML:
        error = 127

    result = None
    if opCode == 0:
        result = op1 + op2
    elif opCode == 1:
        result = op1 - op2
    elif opCode == 2:
        result = op1 * op2
    elif opCode == 3:
        result = op1 / op2
    elif opCode == 4:
        result = op1 >> op2
    elif opCode == 5:
        result = op1 << op2
    elif opCode == 6:
        result = ~op1
    else:
        result = 0

    response = str('x%sx%sx%sx%sx' %
       (TML, ID, error, result))

    print("\nResponse Hex Values:")
    printString = str('%s %s %s %s' %
       (hex(TML), hex(ID), hex(error), hex(result)))
    print(printString + "\n")
    print(response)
    response = [str(format(ord(x), 'x')).encode('utf-8') for x in response]
    response = str(response)
    connection.sendall(response)
