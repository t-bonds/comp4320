import socket
import sys
import time

GROUP_NUMBER = 24
DEFAULT_ENCODING = 'iso-8859-1'
MAX_WIRE_LENGTH = 1024

ipAddress = sys.argv[1]
port = int(sys.argv[2])
destPort = port + GROUP_NUMBER
destAddr = (ipAddress, destPort)


if len(sys.argv) != 2 and len(sys.argv) != 3:
   raise ValueError('Parameter(s): <Destination>  <Port> [<encoding]')


ID = 1
while(True):
   TML = None
   operands = None
   opCode = None
   op1 = None
   op2 = None

   print("\nPlease Enter The Following Values:\n")

   try:
      opString = raw_input(
          'Operand Type - \n\"0\" = + \n\"1\" = - \n\"2\" = * \n\"3\" = / \n\"4\" = >>  \n\"5\" = << \n\"6\" = ~ \n\"exit\" = Terminate \n\n\tEnter Selection: ')
      if opString == "exit":
         break
      opCode = int(opString)
      if(opCode < 0 or opCode > 6):
         opCode = None
   except:
      pass

   # Enter first operand

   try:
      op1 = int(raw_input("\tOperand 1: "))
      if (op1 < -32768 or op1 > 32767):
         op1 = None
   except:
      pass

   if(opCode < 6):
      TML = 8
      operands = 2

      try:
         op2 = int(raw_input("\tOperand 2: "))
         if (op2 < -32768 or op1 > 32767):
            op2 = None
      except:
         pass
   else:
      TML = 6
      numberOperands = 1
      operand2 = 0

   request = str('x%sx%sx%sx%sx%sx%sx' %
      (TML, ID, opCode, operands, op1, op2))

   codedRequest = bytearray(request)

   print("\nRequest Hex Values:")
   printString = str('%s %s %s %s %s %s' %
      (hex(TML), hex(ID), hex(opCode), hex(operands),
      hex(op1), hex(op2)))
   print(printString)

   sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
   sock.connect(destAddr)
   send = sock.sendall(request)
   start = int(round(time.time() * 1000))

   recv = sock.recv(MAX_WIRE_LENGTH)
   elapsed = int(round(time.time() * 1000)) - start

   response = [str(format(ord(x), 'x')).decode('utf-8') for x in recv]
   response = str(response)
   responseStr = ''

   for c in response:
       if c == 'a' or if c == 'b' or if c == 'c' or if c == 'd' or if c == 'e' or if c == 'f' or
       c == '0' or c == '1' or c == '2' or c == '3' or c == '4' or c == '5' or c == '6' or c == '7' or c == '8' or c == '9'
       responseStr = responseStr + c
       if c == 't':
           responseStr = responseStr + '9'
        if c == 'n':
            responseStr = responseStr + 'a'

    resultHex = int(responseStr[-8:], 16)
    result = -(resultHex & 0x8000) | (resultHex & 0x7fff)

   print("\nResponse Hex Values: ")
   print("%s" % response)
   print("Time Elapsed: %dms" % (elapsed))
   print("RequestID: %i" % ID)
   print("Result: ")
   print("Result: %i" % result)
   ID += 1

sock.close()
