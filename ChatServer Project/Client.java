import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import  java.io.*;
import   java.net.*;
//import javax.swing.ImageIcon;


import javax.swing.BorderFactory;
import javax.swing.JFrame;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class Client extends JFrame {

    Socket socket;
    BufferedReader  br;
    PrintWriter   out;

    private JLabel heading=new JLabel("Client Area");
    private JTextArea messagearea=new JTextArea();
    private JTextField messageInput=new JTextField();
    private Font font=new Font("Roboto",Font.PLAIN,20);
  

    public Client(){
        try {
            System.out.println("Starting Connecting");
            socket=new Socket("127.0.0.1",1007);
            System.out.println("Connection Done");
            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out= new PrintWriter(socket.getOutputStream());

            createGUI();
            handleEvent();
            startReading();
            // startWriting();
        } catch (Exception e) {
            // TODO: handle exception
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
               // System.out.println("Key Released"+ e.getKeyCode());
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
    
    private void createGUI(){
        this.setTitle("Client Message");
        this.setSize(600,600);
        this.setLocation(700,0);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        heading.setFont(font);
        messagearea.setFont(font);
        messageInput.setFont(font);
        
      
      //  heading.setIcon(new ImageIcon(".png"));
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
                    System.out.println("Server terminated");
                    JOptionPane.showMessageDialog(this,"Server Terminated");
                    messageInput.setEnabled(false);
                    socket.close();
                   
                    break;
                }
               // System.out.println("Server :"+msg);
               messagearea.append("Server : "+msg+"\n");
     }
    }catch(Exception e){
        System.out.println("Connection Close");

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

    //     }catch(Exception e){
    //         System.out.println("Connection Close");
    //     }
    //     };

    //     new Thread(r2).start();

    // }



    public static void main(String[] args) {
        System.out.println("this is client....");
        new Client();
    }

}