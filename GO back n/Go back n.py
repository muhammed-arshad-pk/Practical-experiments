import socket
import time
import random

HOST = '127.0.0.1'
PORT = 65432
WINDOW_SIZE = 4

server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server.bind((HOST, PORT))
server.listen(1)
print(f"Server listening on {HOST}:{PORT}...")

conn, addr = server.accept()
print(f"Connected by {addr}")

buffer = ""
while True:
    try:
        data = conn.recv(1024).decode()
        if not data:
            break
        buffer += data
        while '\n' in buffer:
            frame, buffer = buffer.split('\n', 1)
            if random.random() < 0.2:
                print(f"Frame {frame} lost!")
                conn.sendall(f"NACK:{frame}\n".encode())
            else:
                print(f"Frame {frame} received successfully")
                time.sleep(0.2)
                conn.sendall(f"ACK:{frame}\n".encode())
    except ConnectionResetError:
        print("Client disconnected abruptly.")
        break

conn.close()
print("Connection closed.")
