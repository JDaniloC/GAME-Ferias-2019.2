import java.awt.*;
import javax.swing.*;

public class Mobs extends JFrame{
    private Imagem canvas = new Imagem();

    public static void main(String[] args) {
        Mobs test = new Mobs();
    }

    public Mobs(){
        setLayout(new BorderLayout());
        setSize(500, 375);
        setTitle("Pacman");
        add("Center", canvas);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);
        setVisible(true);
    }
    private class Imagem extends Canvas{
        @Override
        public void paint(Graphics g){
            g.drawString("Isso é um texto", 100, 20);
            g.drawOval(50, 50, 100, 25);
            g.drawRect(200, 50, 100, 25);
            g.setColor(Color.YELLOW);
            g.fillOval(50, 100, 70, 70);
            g.fillRect(200, 100, 90, 90);
            Image finalmente = new ImageIcon("src/wall.png").getImage();
            g.drawImage(finalmente, 1, 1, 50, 50, this);
        }
    }
}
