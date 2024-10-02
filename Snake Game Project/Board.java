import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Board extends JPanel  implements ActionListener{
    private int Dots;

    private final int All_Dots=1600;
    private final int Dot_Size=10;
    private boolean inGame=true;

    private int Apple_x;
    private int Apple_y;

    private final int[]  x=new int[All_Dots];
    private final int[]  y=new int[All_Dots];



    private boolean leftDirection=false;
    private boolean rightDirection=true;
    private boolean upDirection=false;
    private boolean downDirection=false;

    private final int Random_position=29;

    private Image apple;
    private Image head;
    private Image dot;
    private Timer timer;



    Board(){

        setBackground(Color.BLACK);
        setFocusable(true);
        setPreferredSize(new Dimension(400,400));

        addKeyListener(new TAdapte());

        LoadImages();

        initGame();

    }

    private void LoadImages() {
        ImageIcon  i1= new ImageIcon(ClassLoader.getSystemResource("apple.png"));
        apple=i1.getImage();
        ImageIcon  i3= new ImageIcon(ClassLoader.getSystemResource("head.png"));
        head=i3.getImage();
        ImageIcon  i2= new ImageIcon(ClassLoader.getSystemResource("dot.png"));
        dot=i2.getImage();

    }

    private void initGame() {
        Dots=3;
        for(int i=0;i<Dots;i++){

            y[i]=50;
            x[i]=50-(i*Dot_Size);

        }
        locateApple();

        timer= new Timer(140,this);
        timer.start();
    }
    private void locateApple() {

        int r=(int)(Math.random() * Random_position);
        Apple_x=r* Dot_Size;
        r=(int)(Math.random() * Random_position);
        Apple_y=r* Dot_Size;
        
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);


        draw(g);

    }

    private void draw(Graphics g) {

        if(inGame){

        g.drawImage(apple,Apple_x,Apple_y,this);
      for(int i=0;i<Dots;i++){
        if(i==0){
            g.drawImage(head,x[i],y[i],this);
        }
        else{
            g.drawImage(dot,x[i],y[i],this);
        }
      }}else{

        gameOver(g);
      }

      Toolkit.getDefaultToolkit().sync();
    }
    public void gameOver(Graphics g) {
         String  str="Game Over!";
         Font font=new Font("Roboto",Font.BOLD,14);
         
         FontMetrics  metrices=getFontMetrics(font);
         g.setColor(Color.WHITE);
         g.setFont(font);

         g.drawString(str,(400-metrices.stringWidth(str))/2,400/2);
    }

    public void actionPerformed(ActionEvent ae){

        if(inGame){

        checkapple();
        checkCollision();

        move();
        }
        repaint();
    }

    public void checkCollision() {

        for(int i=Dots;i>0;i--){
            if((i>4)&& (x[0]==x[i])&&y[0]==y[i]){
                inGame=false;
            }
        }
        if(y[0]>=400){
            inGame=false;
        }
        if(y[0]<0){
            inGame=false;
        }
        if(x[0]>=400){
            inGame=false;
        }
        if(y[0]<0){
            inGame=false;
        }
        if(!inGame){
            timer.stop();
        }


       
    }

    private void checkapple() {
       if(x[0]==Apple_x && y[0]==Apple_y){
        Dots +=1;
        locateApple();
       }
    }

    private void move() {
       for(int i=Dots;i>0;i--){
        x[i]=x[i-1];
        y[i]=y[i-1];
       }

       if(leftDirection){
        x[0]=x[0]-Dot_Size;
       }
       if(rightDirection){
        x[0]=x[0]+Dot_Size;
       }
       if(upDirection){
        y[0]=y[0]-Dot_Size;
       }
       if(downDirection){
        y[0]=y[0]+Dot_Size;
       }

     //  x[0]+=Dot_Size;
     //  y[0]+=Dot_Size;
    }


    public class TAdapte  extends KeyAdapter{
 
        @Override
        public void  keyPressed(KeyEvent  e){
            int key=e.getKeyCode();

            if(key==KeyEvent.VK_LEFT && (!rightDirection)){
                leftDirection=true;
                upDirection=false;
                downDirection=false;
            }
            if(key==KeyEvent.VK_UP && (!downDirection)){
                upDirection=true;
                leftDirection=false;
                rightDirection=false;
            }
            if(key==KeyEvent.VK_RIGHT && (!leftDirection)){
                rightDirection=true;
                upDirection=false;
                downDirection=false;
            }
            if(key==KeyEvent.VK_DOWN && (!upDirection)){
                downDirection=true;
                leftDirection=false;
                rightDirection=false;
            }
        }

    }
    
    
    
}
