import socket, time, random
HOST, PORT = '127.0.0.1', 65432
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.bind((HOST, PORT))
s.listen(1)
print(f"Server listening on {HOST}:{PORT}...")

conn, addr = s.accept()
print(f"Connected to {addr}")

buffer = ""
while True:
    try:
        data = conn.recv(1024).decode()
        if not data: break
        buffer += data

        while '\n' in buffer:
            frame, buffer = buffer.split('\n', 1)
            if not frame: continue

            if random.random() < 0.2:
                print(f"Frame {frame} lost!")
                conn.sendall(f"NACK:{frame}\n".encode())
            else:
                print(f"Frame {frame} received")
                time.sleep(0.2)
                conn.sendall(f"ACK:{frame}\n".encode())
    except ConnectionResetError:
        print("Client disconnected.")
        break

conn.close()
print("Server closed.")
