import socket

# --- Create and configure server socket ---
server_socket = socket.socket()
server_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)  # Allow address reuse
server_socket.bind(('localhost', 8080))
server_socket.listen(1)
print("🔹 Server is waiting for connection on port 8080...")

conn, addr = server_socket.accept()
print(f"✅ Connected to client: {addr}")

# --- Receive total number of frames ---
total_frames = int(conn.recv(1024).decode())
print(f"\n📦 Expecting {total_frames} frames...\n")

received = [False] * total_frames

try:
    while True:
        frame = conn.recv(1024).decode()
        if not frame:
            break

        frame_no = int(frame)
        print(f"📥 Received frame: {frame_no}")

        # Ask user whether to send ACK manually
        choice = input(f"Send ACK for frame {frame_no}? (y/n): ").strip().lower()

        if choice == 'y':
            conn.send(str(frame_no).encode())
            received[frame_no] = True
            print(f"✅ ACK for frame {frame_no} sent!\n")
        else:
            print(f"❌ Frame {frame_no} lost! (No ACK sent)\n")

        # Stop when all frames are received
        if all(received):
            print("🎉 All frames received successfully!")
            conn.send(b"COMPLETED")
            break

except Exception as e:
    print("⚠️ Error:", e)

print("🔻 Closing connection...")
conn.close()
server_socket.close()
print("🔸 Server closed successfully.")
