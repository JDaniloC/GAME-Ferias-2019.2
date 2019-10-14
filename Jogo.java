import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class Jogo extends JFrame implements KeyListener{
    int x;
    int y;
    String [][]mapa = new String[31][28];
    final Tela paintPan = new Tela();
    JTextField controles;

    public Jogo() throws FileNotFoundException {
        setTitle("Pacman");
        setSize(717, 860);
        setLayout(new BorderLayout());
        Scanner in = new Scanner(new File("Config.txt"));

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
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        try {
            new Jogo();
        } catch (Exception e) {
            System.out.println(e);
        } 
    }
    public void keyPressed(KeyEvent e) {
        anda(e, mapa);
        paintPan.updateGraphics(mapa);
        revalidate();
    }
    public void keyTyped(KeyEvent e) { }
    public void keyReleased(KeyEvent e) { }
    private void anda(KeyEvent e, String[][] mapa){
        int comando = e.getKeyCode();
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