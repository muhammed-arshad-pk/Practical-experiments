# stop_and_wait_server.py
import socket
import time

# Create a TCP/IP socket
server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_socket.bind(('localhost', 9999))
server_socket.listen(1)

print("Receiver: Waiting for connection...")
conn, addr = server_socket.accept()
print(f"Receiver: Connected to sender at {addr}\n")

expected_frame = 0

while True:
    data = conn.recv(1024).decode()
    if not data:
        break

    if data.lower() == "end":
        print("Receiver: Transmission complete.")
        break

    print(f"Receiver: Received Frame {data}")

    # Simulate ACK behavior (you can enter y/n)
    ack_input = input(f"Do you want to send ACK for Frame {data}? (y/n): ").strip().lower()

    if ack_input == 'y':
        time.sleep(1)
        conn.send(f"ACK{data}".encode())
        print(f"Receiver: Sent ACK for Frame {data}\n")
        expected_frame += 1
    else:
        print(f"Receiver: ACK for Frame {data} lost!\n")

conn.close()
server_socket.close()
