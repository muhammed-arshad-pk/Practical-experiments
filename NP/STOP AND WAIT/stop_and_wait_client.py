# stop_and_wait_client.py
import socket
import time

# Create a TCP/IP socket
client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
client_socket.connect(('localhost', 9999))

n = int(input("Enter number of frames to send: "))
frame_no = 0

while frame_no < n:
    print(f"\nSender: Sending Frame {frame_no}")
    client_socket.send(str(frame_no).encode())

    # Wait for ACK
    client_socket.settimeout(5)  # 5-second timeout
    try:
        ack = client_socket.recv(1024).decode()
        if ack == f"ACK{frame_no}":
            print(f"Sender: Received {ack}")
            frame_no += 1  # Move to next frame
        else:
            print("Sender: Wrong ACK received, resending frame...")
    except socket.timeout:
        print("Sender: Timeout! Resending frame...")
        time.sleep(1)

# End transmission
client_socket.send("end".encode())
print("\n===== All Frames Sent Successfully =====")
client_socket.close()
