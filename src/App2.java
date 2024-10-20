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


public class App2 extends Frame implements ActionListener, Runnable{
    TextField chatBox;
    TextArea chatArea;
    Button send;

    Socket socket;

    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    Thread chat;

   App2(){

        // Creating Components

        chatBox = new TextField("");
        chatArea =  new TextArea("");
        send = new Button("Send");

        // Adding Components to the Frame

        chatBox.setBounds(10, 270, 245, 25); 
        chatArea.setBounds(10, 30, 285, 230); 
        send.setBounds(260, 270, 35, 25);

        // Adding Actionlistener For Send Button 

        send.addActionListener(this);

        try {
            socket = new Socket("localhost",9999);

            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());

        } catch (IOException e) {
            e.getMessage();
        }
        

        chat = new Thread(this);
        chat.setDaemon(true);
        chat.start();


        add(chatBox);
        add(chatArea);
        add(send);



        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });

        
        setSize(320,320);
        setLayout(null);
        setVisible(true);
    }


    public void actionPerformed(ActionEvent e){
        String data = chatBox.getText();
        chatArea.append("sanithu: "+data+"\n");
        chatBox.setText("");

        try {
            dataOutputStream.writeUTF(data);
            dataOutputStream.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());

        }

    }

    public void run(){
        while (true) { 
            try {
                String msg = dataInputStream.readUTF();
                chatArea.append("Aashif: "+msg+"\n");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) throws Exception {
        new App2();
    }
}
