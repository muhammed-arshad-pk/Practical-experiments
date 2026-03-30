import socket, time

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.connect(('localhost', 9999))

n = int(input("Enter number of frames: "))
frame = 0

while frame < n:
    print(f"\nSending Frame {frame}")
    s.send(str(frame).encode())
    s.settimeout(5)

    try:
        ack = s.recv(1024).decode()
        if ack == f"ACK{frame}":
            print(f"Received {ack}")
            frame += 1
        else:
            print("Wrong ACK, resending...")
    except socket.timeout:
        print("Timeout! Resending...")
        time.sleep(1)

s.send("end".encode())
print("\nAll Frames Sent Successfully.")
s.close()
