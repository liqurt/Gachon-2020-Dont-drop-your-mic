userDataLocation = "C:/Users/Y190313/Desktop/Apocrypha/4-2(19-2)/Graduation Work 2/DDYMProject/Networking/server/voiceSample/User"

import socket

s = socket.socket()
host = "192.9.11.248"
port = 8080

s.bind((host, port))
print (host)
s.listen(5)
c = None

while True:
   if c is None:
       print ('[Waiting for connection...]')
       c, addr = s.accept()
       print ('Got connection from', addr)
   else:
       print ('[Waiting for response...]')
       print (c.recv(1024))