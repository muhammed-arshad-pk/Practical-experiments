import socket
import time

# --- Create and connect client socket ---
client_socket = socket.socket()
client_socket.connect(('localhost', 8080))
print("✅ Connected to server on port 8080!")

# --- Get user input ---
total_frames = int(input("Enter total number of frames to send: "))
client_socket.send(str(total_frames).encode())

ack_received = [False] * total_frames

try:
    while not all(ack_received):
        for i in range(total_frames):
            if not ack_received[i]:
                print(f"🚀 Sending frame: {i}")
                client_socket.send(str(i).encode())

                client_socket.settimeout(3)
                try:
                    ack = client_socket.recv(1024).decode()

                    if ack == "COMPLETED":
                        print("🎉 All frames acknowledged. Transmission complete!")
                        raise StopIteration

                    ack_no = int(ack)
                    ack_received[ack_no] = True
                    print(f"✅ ACK received for frame {ack_no}\n")
                    time.sleep(0.5)

                except socket.timeout:
                    print(f"⏱️ Timeout! No ACK for frame {i}. Resending later...\n")
                    time.sleep(1)

except StopIteration:
    pass
except Exception as e:
    print("⚠️ Error:", e)

# --- Close connection safely ---
print("🔻 Closing connection...")
client_socket.shutdown(socket.SHUT_RDWR)
client_socket.close()
print("🔸 Client closed successfully.")
