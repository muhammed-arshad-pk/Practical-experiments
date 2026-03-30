import socket, time

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.bind(('localhost', 9999))
s.listen(1)
print("Receiver: Waiting for connection...")

conn, addr = s.accept()
print(f"Connected to {addr}\n")

while True:
    data = conn.recv(1024).decode()
    if not data or data.lower() == "end":
        print("Receiver: Transmission complete.")
        break

    print(f"Received Frame {data}")
    ack = input(f"Send ACK for Frame {data}? (y/n): ").lower()

    if ack == 'y':
        time.sleep(1)
        conn.send(f"ACK{data}".encode())
        print(f"ACK{data} sent\n")
    else:
        print(f"ACK{data} lost!\n")

conn.close()
s.close()
