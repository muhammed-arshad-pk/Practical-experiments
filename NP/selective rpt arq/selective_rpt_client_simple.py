import socket, time

HOST, PORT = '127.0.0.1', 8080
s = socket.socket(); s.connect((HOST, PORT))
n = int(input("Total frames to send: "))
s.send(str(n).encode())

acked = [False] * n

while not all(acked):
    for i in range(n):
        if acked[i]: continue
        print("Send frame", i)
        s.send(str(i).encode())
        s.settimeout(3)
        try:
            r = s.recv(1024).decode()
            if r == "DONE":
                acked = [True] * n
                print("All ACKed. Done.")
                break
            acked[int(r)] = True
            print("ACK for", r)
            time.sleep(0.3)
        except socket.timeout:
            print("Timeout on", i, "- will retry")

s.close()
