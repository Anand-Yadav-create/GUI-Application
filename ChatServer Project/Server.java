import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

import java.net.*;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Server extends JFrame {

    ServerSocket server;
    Socket socket;
    BufferedReader  br;
    PrintWriter   out;
    private JLabel  heading=new JLabel("Server area");
    private JTextArea messagearea=new JTextArea();
    private JTextField messageInput=new JTextField();

    private Font font=new Font("Roboto",Font.PLAIN,20);
    public Server(){
        try {
            server=new ServerSocket(1007);
            System.out.println("Server is ready to establish connection");
            System.out.println("waiting>>>");
            socket=server.accept();
            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out= new PrintWriter(socket.getOutputStream());


            createGU();
            handleEvent();
            startReading();
            // startWriting();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        

    }
    private void handleEvent() {
        messageInput.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
               
            }

            @Override
            public void keyPressed(KeyEvent e) {
             
            }

            @Override
            public void keyReleased(KeyEvent e) {

                if(e.getKeyCode()==10){
                    String contenttosend=messageInput.getText();
                    messagearea.append("Me :" + contenttosend+"\n");
                    out.println(contenttosend);
                    out.flush();
                    messageInput.setText("");
                    messageInput.requestFocus();

                }
            }
            
        });
    }
    private void createGU() {
        this.setTitle("Server Message");
       
        this.setSize(600,600);
      //  this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        

        heading.setFont(font);
        messagearea.setFont(font);
        messageInput.setFont(font);

        heading.setHorizontalTextPosition(SwingConstants.CENTER);
        heading.setVerticalTextPosition(SwingConstants.BOTTOM);
        heading.setHorizontalAlignment(SwingConstants.CENTER);

        heading.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        messagearea.setEditable(false);
        messageInput.setHorizontalAlignment(SwingConstants.CENTER);

        this.setLayout(new BorderLayout());
        this.add(heading,BorderLayout.NORTH);
        JScrollPane jScrollPane=new JScrollPane(messagearea);
        this.add(jScrollPane,BorderLayout.CENTER);
        this.add(messageInput,BorderLayout.SOUTH);

        this.setVisible(true);

    }
    public void startReading(){
        Runnable r1=()->{
            System.out.println("reader started");
            try{

            while(!socket.isClosed()){
                
                String msg=br.readLine();
                if(msg.equals("exit")){
                    
                     JOptionPane.showMessageDialog(this,"Client Terminated");
                    messageInput.setEnabled(false);
                    socket.close();
                   
                    break;
                }
                messagearea.append("Client :"+msg+"\n");
            
            }
        }
        catch(Exception e){
            System.out.println("Connection closed");
        }

        };

        new Thread(r1).start();
    }

    // public void startWriting(){

    //     System.out.println("Writer Started");

    //     Runnable r2=()->{

    //         try{
    //         while(!socket.isClosed()){
               
    //                 BufferedReader  br1= new BufferedReader(new InputStreamReader(System.in));
    //                 String content=br1.readLine();
    //                 out.println(content);

    //                 out.flush();
    //                 if(content.equals("exit")){
    //                     socket.close();
                       
    //                     break;
    //                 }
               
    //         }
    //     }
    //     catch(Exception e){
    //         System.out.println("Connection Closed");
    //     }
    //     };

    //     new Thread(r2).start();

    // }


    public static void main(String[] args) {
        System.out.println("this is Server....");
        new Server();
    }
    
}
