import javax.swing.*;
import java.awt.*;

class Tela extends JPanel {
    String OS = System.getProperty("os.name");
    String pre;
    private String[][] mapa = null;

    public Tela() {
        if (OS.equals("Linux")){
            pre = "./Imagens/";
        } else{
            pre = "Imagens\\";
        }
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
                        map[j][i] = new ImageIcon(pre + "Wall.jpg").getImage();
                        g.drawImage(map[j][i], i*25, j*25, 25,25,this);
                    } else if(mapa[j][i].equals("C")){
                        map[j][i] = new ImageIcon(pre +"Pac.png").getImage();
                        g.drawImage(map[j][i], i*25, j*25, 25,25,this);
                    } else if (mapa[j][i].equals("G")){
                        map[j][i] = new ImageIcon(pre + "Ghost.png").getImage();
                        g.drawImage(map[j][i], i*25, j*25, 25,25,this);
                    } else if(mapa[j][i].equals(".")) {
                        map[j][i] = new ImageIcon(pre + "Coin.png").getImage();
                        g.drawImage(map[j][i], i * 25, j * 25, 25, 20, this);
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