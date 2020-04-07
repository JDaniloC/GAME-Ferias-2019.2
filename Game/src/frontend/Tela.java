package frontend;

import Mobs.Mob;
import backend.Nivel;

import javax.swing.*;
import java.awt.*;

/* TELA
 * Classe para atualização dos componentes do jogo
 */

public class Tela extends JPanel {
    private Mob[][] mapa = null;
    private int vida = 3;
    private String pontos = new String("");

    public Tela() {
        setBackground(Color.WHITE); // Cor de fundo
    }

    @Override
    public void paintComponent(Graphics g) {
        /* paintComponent
         * Ao dar repaint ele irá rodar essa função.
         */
        super.paintComponent(g);

        if (mapa != null) {
            for (int i = 0; i < mapa.length; i++) {
                for (int j = 0; j < mapa[i].length; j++) {
                    Image figura = new ImageIcon(mapa[i][j].getImage()).getImage();
                    g.drawImage(figura, j * 25, i * 25, 25, 25, this);
                }
            }
        }

        // Modificar a fonte
        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize() * 2F);
        g.setFont(newFont);

        Image life = new ImageIcon("./src/Imagens/Misc/Life.jpg").getImage();
        Image death = new ImageIcon("./src/Imagens/Misc/Death.jpg").getImage();
        for (int i = 0; i < vida; i++) {
            g.drawImage(life, 80 + i * 40, 785, 40, 30, this);
        }
        for (int i = 3; i > vida; i --) {
            g.drawImage(death, 160 - 40 * (3 - i), 785, 40, 30, this);
        }
        g.drawString("Lifes ", 20, 807);
        g.drawString("Pontuação ", 400, 807);
        g.drawString(pontos, 550, 807);
    }

    public void atualizar(Nivel mapa, int[] status) {
        /* atualizar
         * Método que repassa o novo mapa para o canvas
         */
        vida = status[0];
        pontos = Integer.toString(status[1]);
        this.mapa = mapa.getMapa();
        repaint();
    }
}
