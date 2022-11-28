import javax.swing.*;

public class Snakegame {
    JFrame frame;

    Snakegame(){
        frame=new JFrame();

        Board board=new Board();
        frame.add(board);
        frame.setTitle("Snake Game");
        frame.setResizable(false);

        frame.setVisible(true);
        frame.pack();
//        frame.setBounds(100,100,400,400);
    }

    public static void main(String[] args) {

        Snakegame snakegame=new Snakegame();
    }
}