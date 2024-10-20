package server;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class App extends Frame implements ActionListener, Runnable {
    TextField chatBox, nameField;
    TextArea chatArea;
    Button send, startServer;
    
    ServerSocket serverSocket;
    Socket socket;
    
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    
    Thread chat;
    String userName,otherUserName;
    
    Frame serverFrame;
    
    public App() {
        // Setup for user input form
        serverFrame = new Frame("Server Setup");
        Label nameLabel = new Label("Your Name:");
        
        nameField = new TextField();
        startServer = new Button("Start Server");
        
        nameLabel.setBounds(50, 50, 100, 30);
        nameField.setBounds(160, 50, 150, 30);
        startServer.setBounds(130, 100, 100, 30);
        
        serverFrame.add(nameLabel);
        serverFrame.add(nameField);
        serverFrame.add(startServer);
        
        serverFrame.setSize(400, 200);
        serverFrame.setLayout(null);
        serverFrame.setVisible(true);
        
        startServer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                userName = nameField.getText();
                if (!userName.isEmpty()) {
                    serverFrame.setVisible(false); // Hide the server input frame
                    startChatWindow(); // Start chat window
                }
            }
        });

        serverFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
    
    // Method to start chat window after name is provided
    private void startChatWindow() {
        setTitle(userName + " - Server");
        
        // Creating Components for chat window
        chatBox = new TextField("");
        chatArea = new TextArea("");
        send = new Button("Send");
        
        // Adding Components to the Frame
        chatBox.setBounds(10, 270, 245, 25);
        chatArea.setBounds(10, 30, 285, 230);
        send.setBounds(260, 270, 35, 25);
        
        // Adding ActionListener for Send Button
        send.addActionListener(this);
        
        try {
            serverSocket = new ServerSocket(9999);
            socket = serverSocket.accept();
            
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());

            dataOutputStream.writeUTF(userName);
            dataOutputStream.flush();

            otherUserName = dataInputStream.readUTF();
        } catch (IOException e) {
            System.out.println("Connection Error: " + e.getMessage());
        }
        
        chat = new Thread(this);
        chat.setDaemon(true);
        chat.start();
        
        add(chatBox);
        add(chatArea);
        add(send);
        
        applyColors();
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        
        setSize(320, 320);
        setLayout(null);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e) {
        String data = chatBox.getText();
        chatArea.append(userName + ": " + data + "\n");
        chatBox.setText("");
        
        try {
            dataOutputStream.writeUTF(data);
            dataOutputStream.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void run() {
        while (true) {
            try {
                String msg = dataInputStream.readUTF();
                chatArea.append(otherUserName+":"+ msg + "\n");
            } catch (Exception e) {
                
            }
        }
    }
    
    private void applyColors() {
        chatBox.setBackground(Color.LIGHT_GRAY);
        chatArea.setBackground(Color.WHITE);
        send.setBackground(Color.CYAN);
        chatBox.setForeground(Color.DARK_GRAY);
        send.setForeground(Color.BLACK);
    }
    
    /* public static void main(String[] args) throws Exception {
        new App();
    }*/
} 
    
