import socket
import time

HOST = '127.0.0.1'
PORT = 65432
WINDOW_SIZE = 4

client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
client.connect((HOST, PORT))

data = input("Enter frames to send (comma separated, e.g., A,B,C,D,E): ")
frames = data.split(',')

base = 0
next_seq = 0
n = len(frames)
ack_buffer = ""

while base < n:
    # Send frames in window
    while next_seq < base + WINDOW_SIZE and next_seq < n:
        print(f"Sending frame {frames[next_seq]}")
        client.sendall(f"{frames[next_seq]}\n".encode())
        next_seq += 1
        time.sleep(0.2)

    # Receive ACK/NACK
    ack_buffer += client.recv(1024).decode()
    while '\n' in ack_buffer:
        ack, ack_buffer = ack_buffer.split('\n', 1)
        print(f"Received: {ack}")
        if ack.startswith("ACK"):
            frame_ack = ack.split(":")[1]
            if frame_ack in frames[base:]:
                base = frames.index(frame_ack) + 1
        elif ack.startswith("NACK"):
            frame_nack = ack.split(":")[1]
            next_seq = frames.index(frame_nack)
            print(f"Resending from frame {frames[next_seq]}")

client.close()
print("All frames sent and acknowledged.")
