package frontend;

import javax.swing.*;
import java.awt.*;

public class End extends JFrame {

    public End(int pontos, boolean venceu) {
        setSize(717, 730);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        ImageIcon imagem;
        if (venceu) {
            imagem = new ImageIcon(new ImageIcon("./src/Imagens/Misc/Win.gif").getImage().getScaledInstance(717, 600, Image.SCALE_DEFAULT));
            this.getContentPane().setBackground(Color.BLACK);
        } else {
            imagem = new ImageIcon("./src/Imagens/Misc/GameOver.jpg");
            this.getContentPane().setBackground(Color.decode("#222222"));
        }
        JLabel label = new JLabel(imagem);
        this.getContentPane().add(label, BorderLayout.PAGE_START);

        Font  fonte  = new Font(Font.SANS_SERIF,  Font.BOLD, 70);
        JLabel texto = new JLabel("Pontuação", SwingConstants.CENTER);
        texto.setFont(fonte);
        texto.setForeground(Color.WHITE);

        texto.setText("Pontuação: " + pontos);
        add(texto, BorderLayout.PAGE_END);

        setVisible(true);
    }
}
