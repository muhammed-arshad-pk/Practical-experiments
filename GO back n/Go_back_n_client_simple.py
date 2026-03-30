import socket, time

HOST, PORT = '127.0.0.1', 65432
WIN = 4

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.connect((HOST, PORT))

frames = input("Enter frames (comma separated): ").split(',')
base = next_seq = 0
n = len(frames)
buffer = ""

while base < n:
    # Send frames in window
    while next_seq < base + WIN and next_seq < n:
        print(f"Sending {frames[next_seq]}")
        s.sendall(f"{frames[next_seq]}\n".encode())
        next_seq += 1
        time.sleep(0.2)

    # Receive ACK/NACK
    buffer += s.recv(1024).decode()
    while '\n' in buffer:
        ack, buffer = buffer.split('\n', 1)
        if not ack: continue
        print("Received:", ack)

        if ack.startswith("ACK"):
            frame = ack.split(":")[1]
            base = frames.index(frame) + 1
        elif ack.startswith("NACK"):
            frame = ack.split(":")[1]
            next_seq = frames.index(frame)
            print(f"Resending from {frame}")

s.close()
print("All frames sent successfully.")
