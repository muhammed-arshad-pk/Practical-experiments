import socket

HOST, PORT = '127.0.0.1', 8080
s = socket.socket(); s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
s.bind((HOST, PORT)); s.listen(1)
print("Server: waiting on 8080...")
conn, addr = s.accept(); print("Connected:", addr)

total = int(conn.recv(1024).decode())
print("Expecting", total, "frames")
got = [False] * total

while True:
    data = conn.recv(1024).decode()
    if not data: break
    i = int(data)
    print("Recv frame", i)
    if input(f"ACK frame {i}? (y/n): ").strip().lower() == 'y':
        conn.send(str(i).encode())
        got[i] = True
    else:
        print("No ACK sent")

    if all(got):
        conn.send(b"DONE")
        print("All frames received.")
        break

conn.close(); s.close()
