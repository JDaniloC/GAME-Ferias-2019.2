import java.util.*;
import java.awt.*;
import javax.swing.*;

public class Pacman extends JFrame{
    private static int x = 0;
    private static int y = 0;
    //private static Keyboard teclado;
    private Imagem canvas = new Imagem();

    public Pacman(){
        setLayout(new BorderLayout());
        setSize(500, 375);
        setTitle("Pacman");
        add("Center", canvas);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args){
        Pacman test = new Pacman();
        Scanner in = new Scanner(System.in);
        String [][]mapa = new String[31][28];
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
        test.canvas.inicializa(mapa);
        //teclado.main();
        while (true) {
            System.out.print("Escolha: ");
            String escolha = in.next();
            mapa = anda(mapa, escolha);
            printa(mapa);
        }
    }
    private static String[][] anda(String[][] mapa, String comando){
        if (comando.equals("w")){
            mapa[y-1][x] = "C";
            mapa[y][x] = " ";
            y --;
        } else if (comando.equals("d")){
            mapa[y][x+1] = "C";
            mapa[y][x] = " ";
            x += 1;
        } else if (comando.equals("s")){
            mapa[y+1][x] = "C";
            mapa[y][x] = " ";
            y ++;
        } else if (comando.equals("a")){
            mapa[y][x-1] = "C";
            mapa[y][x] = " ";
            x -= 1;
        }
        return mapa;
    }
    private static void printa(String[][] mapa){
        for (int i = 0; i < 31; i++){
            for (int j = 0; j < 28; j++){
                System.out.print(mapa[i][j] + " ");
            }
            System.out.println();
        }
    }

    private class Imagem extends JPanel{
        private String[][] mapa = null;
        private int px;
        private int py;
        @Override
        public void paint(Graphics g){
            if (mapa != null){
                Image[][] map = new Image[31][28];
                for (int i = 0; i < 31; i++){
                    for (int j = 0; j < 28; j++){
                        if (mapa[i][j].equals("#")){
                            map[i][j] = new ImageIcon("src/wall.png").getImage();
                            g.drawImage(map[i][j], i*50, j*50, 50,50,this);
                        }
                    }
                }
            }
            Image finalmente = new ImageIcon("src/wall.png").getImage();
            g.drawImage(finalmente, 1, 1, 50, 50, this);
        }
        public void inicializa(String[][] matriz){
            mapa = matriz;
            repaint();
        }
        public void updateGraphics(int x, int y){
            this.px = x;
            this.py = y;
            revalidate();
        }
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            if (mapa != null){
                Image[][] map = new Image[31][28];
                for (int i = 0; i < 31; i++){
                    for (int j = 0; j < 28; j++){
                        if (mapa[i][j].equals("#")){
                            map[i][j] = new ImageIcon("src/wall.png").getImage();
                            g.drawImage(map[i][j], i*50, j*50, 50,50,this);
                        }
                    }
                }
            }
            Image finalmente = new ImageIcon("src/wall.png").getImage();
            g.drawImage(finalmente, 50, 50, 50, 50, this);
        }
    }
}