
# ChatApp - Java AWT

A simple yet efficient **Chat Application** built using **Java AWT**. This project demonstrates a basic GUI-based chat system where users can send messages over a local network.

## Features

- **User-friendly interface** using AWT (Abstract Window Toolkit)
- Supports **real-time messaging** between multiple users
- **Local server** setup for managing connections
- Simple yet effective **message display** with time stamps
- **Multi-threaded** for handling multiple clients simultaneously

## Screenshots

*(Add screenshots of your app here if available)*

## Installation

### Prerequisites

- **Java Development Kit (JDK)** installed (version 8 or higher)
- A compatible **IDE** (Eclipse, IntelliJ IDEA, or VSCode with Java support)

### How to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/aashif-sajah/chatApp-java-awt.git
   cd chatApp-java-awt
   ```

2. Compile the server and client:
   ```bash
   javac ChatServer.java ChatClient.java
   ```

3. Start the server:
   ```bash
   java ChatServer
   ```

4. Start the clients (on separate terminals or devices):
   ```bash
   java ChatClient
   ```

5. You can now chat between multiple clients connected to the server!

## Project Structure

- `ChatServer.java`: Handles incoming client connections and message broadcasting using multi-threading.
- `ChatClient.java`: The client-side implementation, responsible for connecting to the server and sending/receiving messages.
- **AWT Components**: The GUI is built using Java's AWT, providing an easy-to-use chat interface.

## How it Works

1. **Server**: The server waits for client connections and manages them using threads. Each connected client runs in its own thread, ensuring that the server can handle multiple clients at once.
   
2. **Client**: The client connects to the server via a socket and starts a chat session. Messages sent by any client are broadcast to all other connected clients.

3. **AWT Interface**: The GUI consists of basic AWT components such as `TextField`, `TextArea`, and `Button`. The `TextArea` displays messages, while the `TextField` allows users to input and send messages.

## Future Enhancements

- **Improved UI**: Migrate to Java Swing for a more modern interface.
- **File Transfer**: Implement the ability to send files.
- **Authentication**: Add user login and authentication.
- **Security**: Implement message encryption for secure communication.

## Contributing

If you would like to contribute to the project, feel free to open an issue or submit a pull request.

---

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
