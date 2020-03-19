package frontend;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Begin extends JPanel {
    JLabel background;
    JButton Jogar;
    JButton Record;
    JButton Criar;

    public Begin () {
        background = new JLabel(new ImageIcon(new ImageIcon("./src/Imagens/Misc/Pacman.png").getImage().getScaledInstance(717, 700, Image.SCALE_DEFAULT)));
        background.setLayout(new FlowLayout());
        add(background);

        setVisible(true);
        componentes();
    }

    private void componentes() {
        JPanel botoes = new JPanel();
        botoes.setBorder(new EmptyBorder(300, 10, 10, 10));
        botoes.setBackground(new Color(0, 0, 0, 0));

        Jogar = new JButton("Jogar");
        Jogar.setPreferredSize(new Dimension(200, 100));
        Jogar.setForeground(Color.WHITE);
        Jogar.setBackground(Color.BLACK);

        Record = new JButton("Records");
        Record.setPreferredSize(new Dimension(200, 100));
        Record.setForeground(Color.WHITE);
        Record.setBackground(Color.BLACK);

        Criar = new JButton("Adicionar Mapa");
        Criar.setPreferredSize(new Dimension(200, 100));
        Criar.setForeground(Color.WHITE);
        Criar.setBackground(Color.BLACK);

        botoes.add(Jogar);
        botoes.add(Record);
        botoes.add(Criar);
        background.add(botoes);

        setVisible(true);
    }

    public JButton getCriar() {
        return Criar;
    }

    public JButton getJogar() {
        return Jogar;
    }

    public JButton getRecord() {
        return Record;
    }
}
