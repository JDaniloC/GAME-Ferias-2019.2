package frontend;

import utils.Score;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Record extends JPanel {
    JLabel background;
    JButton voltar;

    public Record () {
        background = new JLabel(new ImageIcon(new ImageIcon("./src/Imagens/Misc/Record.jpg").getImage().getScaledInstance(717, 700, Image.SCALE_DEFAULT)));
        background.setLayout(new FlowLayout());
        add(background);

        setVisible(true);
        componentes();
    }

    private void componentes () {
        JPanel cima = new JPanel();
        cima.setBorder(new EmptyBorder(20, 0, 10, 0));
        cima.setBackground(new Color(0, 0, 0, 0));

        JPanel baixo = new JPanel();
        cima.setBorder(new EmptyBorder(60, 600, 10, 600));
        cima.setBackground(new Color(0, 0, 0, 0));

        String[] pontuacoes = Score.getScore("Score.txt");
        JList<String> lista = new JList<>(pontuacoes);
        DefaultListCellRenderer renderer = (DefaultListCellRenderer) lista.getCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);

        JScrollPane scroll = new JScrollPane(lista);
        scroll.setPreferredSize(new Dimension(270, 270));
        scroll.setBackground(new Color(0, 0, 0, 0));
        scroll.setBorder(null);

        voltar = new JButton("Voltar");
        voltar.setPreferredSize(new Dimension(200, 100));

        cima.add(scroll);
        baixo.add(voltar);
        background.add(cima);
        background.add(baixo);
    }

    public JButton getVoltar() {
        return voltar;
    }
}
