import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class MainPaint extends JFrame implements KeyListener{
    int x;
    int y;
    String [][]mapa = new String[31][28];
    final PaintPanel paintPan = new PaintPanel();
    JTextField controles;

    public MainPaint(){
        setTitle("Pacman");
        setSize(716, 843);
        setLayout(new BorderLayout());
        Scanner in = new Scanner(System.in);

        JButton testButon = new JButton("Display shape");
        add(paintPan, BorderLayout.CENTER);
        add(testButon, BorderLayout.PAGE_END);
        controles = new JTextField(10);
        controles.addKeyListener(this);
        add(controles, BorderLayout.PAGE_START);
        testButon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                for (int i = 0; i < 31; i++){
                    mapa[i] = in.nextLine().split(" ");
                }
                for (int i = 0; i < 31; i++){
                    for (int j = 0; j < 28; j++){
                        if (mapa[i][j].equals("C")){
                            x = j;
                            y = i;
                            break;
                        }
                    }
                }
                paintPan.updateGraphics(mapa);
                revalidate();
            }
        });
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainPaint();
    }
    public void keyPressed(KeyEvent e) {
        anda(e, mapa);
        paintPan.updateGraphics(mapa);
        revalidate();
    }
    public void keyTyped(KeyEvent e) { }
    public void keyReleased(KeyEvent e) { }
    private void anda(KeyEvent e, String[][] mapa){
        Integer comando = e.getKeyCode();
        if (comando == 38){
            mapa[y-1][x] = "C";
            mapa[y][x] = " ";
            y --;
        } else if (comando == 39){
            mapa[y][x+1] = "C";
            mapa[y][x] = " ";
            x += 1;
        } else if (comando == 40){
            mapa[y+1][x] = "C";
            mapa[y][x] = " ";
            y ++;
        } else if (comando == 37){
            mapa[y][x-1] = "C";
            mapa[y][x] = " ";
            x -= 1;
        }
    }
}

class PaintPanel extends JPanel {

    private String[][] mapa = null;

    public PaintPanel() {
        setBackground(Color.WHITE);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D drawImage = (Graphics2D) g;
        if (mapa != null){
            Image[][] map = new Image[31][28];
            for (int i = 0; i < 28; i++){
                for (int j = 0; j < 31; j++){
                    if (mapa[j][i].equals("#")){
                        map[j][i] = new ImageIcon("src/wall.png").getImage();
                        g.drawImage(map[j][i], i*25, j*25, 25,25,this);
                    } else if(mapa[j][i].equals("C")){
                        map[j][i] = new ImageIcon("src/Pac.png").getImage();
                        g.drawImage(map[j][i], i*25, j*25, 25,25,this);
                    } else if (mapa[j][i].equals("G")){
                        map[j][i] = new ImageIcon("src/Ghost.png").getImage();
                        g.drawImage(map[j][i], i*25, j*25, 25,25,this);
                    }
                }
            }
        }
    }

    public void updateGraphics(String [][]matriz) {
        mapa = matriz;
        repaint();
    }
}