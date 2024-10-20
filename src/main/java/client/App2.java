package main.java.client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class App2 extends Frame implements ActionListener, Runnable {
    TextField chatBox, ipField, nameField;
    TextArea chatArea;
    Button send, connect;
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    Thread chat;
    String userName,otherUserName;
    
    Frame inputFrame;

    App2() {
        // Setup for user input form
        inputFrame = new Frame("Connect to Server");
        Label ipLabel = new Label("Server IP Address:");
        Label nameLabel = new Label("Your Name:");

        ipField = new TextField();
        nameField = new TextField();
        connect = new Button("Connect");

        ipLabel.setBounds(50, 50, 120, 30);
        ipField.setBounds(180, 50, 150, 30);
        nameLabel.setBounds(50, 100, 120, 30);
        nameField.setBounds(180, 100, 150, 30);
        connect.setBounds(150, 150, 100, 30);

        inputFrame.add(ipLabel);
        inputFrame.add(ipField);
        inputFrame.add(nameLabel);
        inputFrame.add(nameField);
        inputFrame.add(connect);

        inputFrame.setSize(400, 250);
        inputFrame.setLayout(null);
        inputFrame.setVisible(true);

        // Connect button action to launch the chat app
        connect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String serverIp = ipField.getText();
                userName = nameField.getText();
                if (!serverIp.isEmpty() && !userName.isEmpty()) {
                    inputFrame.setVisible(false);  // Hide the input form
                    startChatWindow(serverIp);
                }
            }
        });

        inputFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    // Method to start chat window after IP and name are provided
    private void startChatWindow(String serverIp) {
        setTitle(userName + " - ChatApp");

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
            // Create socket connection with the entered server IP address
            socket = new Socket(serverIp, 9999);

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
            System.out.println("Message Sending Error: " + ex.getMessage());
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
        chatBox.setBackground(Color.blue);
        chatArea.setBackground(Color.black);
        send.setBackground(Color.CYAN);
        chatBox.setForeground(Color.DARK_GRAY);
        send.setForeground(Color.BLACK);
    }

    public static void main(String[] args) {
        new App2();
    }
}
