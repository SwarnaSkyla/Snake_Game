import javax.swing.*;
import javax.tools.Tool;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener {
    int height=400;
    int width=400;
    int dots;

    int dotsize=10;
    int allDots=40*40;

    int DELAY=120;

    int x[]=new int[allDots];
    int y[]=new int[allDots];

    int apple_x;
    int apple_y;

    boolean leftdirection=true;
    boolean rightdirection=false;
    boolean updirection=false;
    boolean downdirection=false;

    Timer timer;

    Image head;
    Image apple;
    Image bodydot;

    boolean inGame=true;

    Board(){
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setFocusable(true);
        setPreferredSize(new Dimension(height,width));
        loadImages();
        initGame();

    }
    public  void initGame(){
        locateApple();
        dots=3;
        for(int z=0;z<dots;z++){
            y[z]=250;
            x[z]=250+(z*dotsize);
        }
        timer =new Timer(DELAY, (ActionListener) this);
        timer.start();
    }

    public void loadImages(){


        ImageIcon b=new ImageIcon("src/resources/head.png");
        head=b.getImage();

        ImageIcon a=new ImageIcon("src/resources/apple.png");
        apple=a.getImage();

        ImageIcon c=new ImageIcon("src/resources/dot.png");
        bodydot=c.getImage();


    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        doDrawing(g);
    }
    public void doDrawing(Graphics g){
        if(inGame) {
            g.drawImage(apple, apple_x, apple_y, this);
            g.drawImage(head, x[0], y[0], this);
            for (int z = 1; z < dots; z++) {
                g.drawImage(bodydot, x[z], y[z], this);
            }
            Toolkit.getDefaultToolkit().sync();
        }
        else{
            gameOver(g);
        }

    }
    public void checkApple(){
        if(x[0]==apple_x && y[0]==apple_y){
            dots++;
            locateApple();
        }
    }

    public void checkCollision(){
        for(int z=1;z<dots;z++){
            if((z>3) && (x[0]==x[z]) && (y[0]==y[z])){
                inGame=false;
            }

        }
        if(x[0]<0){
            inGame=false;
        }
        if(x[0]>=width){
            inGame=false;
        }
        if(y[0]<0){
            inGame=false;
        }
        if(y[0]>=height){
            inGame=false;
        }
        if(!inGame){
            timer.stop();
        }
    }
    public void locateApple(){
        int x=(int)(Math.random()*39);
        apple_x=x*10;
        int y=(int)(Math.random()*39);
        apple_y=y*10;
    }

    public void gameOver(Graphics g){
        String msg="Game Over";
        Font font=new Font("Helvetica",Font.BOLD,14);
        FontMetrics fontMetrics=getFontMetrics(font);

        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(msg,(width-fontMetrics.stringWidth(msg))/2,height/2);
    }
    public void move(){
        for(int z=dots-1;z>0;z--){
            x[z]=x[z-1];
            y[z]=y[z-1];
        }
        if(leftdirection){
            x[0]-=dotsize;
        }
        if(rightdirection){
            x[0]+=dotsize;
        }
        if(updirection){
            y[0]-=dotsize;
        }
        if(downdirection){
            y[0]+=dotsize;
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(inGame) {
            checkCollision();
            checkApple();
            move();
        }
        repaint();

    }
    public class TAdapter extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            int key=e.getKeyCode();
            if((key==KeyEvent.VK_LEFT)&&(!rightdirection)){
                leftdirection=true;
                updirection=false;
                downdirection=false;
            }
            if((key==KeyEvent.VK_RIGHT)&&(!leftdirection)){
                rightdirection=true;
                updirection=false;
                downdirection=false;
            }
            if((key==KeyEvent.VK_UP)&&(!downdirection)){
                rightdirection=false;
                updirection=true;
                leftdirection=false;
            }
            if((key==KeyEvent.VK_DOWN)&&(!updirection)){
                rightdirection=false;
                downdirection=true;
                leftdirection=false;
            }


        }
    }

}
