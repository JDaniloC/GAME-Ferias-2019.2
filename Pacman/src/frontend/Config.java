package frontend;

import backend.ListadeMapas;
import backend.Nivel;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Config extends JFrame {
    private ListadeMapas criador = new ListadeMapas((String) null);
    private ListadeMapas exemplos = new ListadeMapas("Maps.txt");

    public Config() throws Exception {
        criador.setCaminho("Config.txt");
        setTitle("Criador de mapas");
        setSize(1280, 840);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.gray);
        setLayout(null);

        componentes();
        setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        new Config();
    }

    @SuppressWarnings("serial")
    static
    class CustomeBorder extends AbstractBorder {
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y,
                                int width, int height) {
            // TODO Auto-generated method stubs
            super.paintBorder(c, g, x, y, width, height);
            Graphics2D g2d = (Graphics2D)g;
            g2d.setStroke(new BasicStroke(12));
            g2d.setColor(Color.gray);
            g2d.drawRoundRect(x, y, width - 1, height - 1, 25, 25);
        }
        // Créditos: https://stackoverflow.com/questions/6008058/customizing-jtextfield
    }

    private void componentes () {
        JTextArea input = new JTextArea(exemplos.getMap().getStringMap());
        input.setBorder(BorderFactory.createCompoundBorder(
                new CustomeBorder(),
                new EmptyBorder(new Insets(15, 25, 15, 25)))
        );
        input.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));

        Tela tela = new Tela();
        tela.setBorder(BorderFactory.createCompoundBorder(
                new CustomeBorder(),
                new EmptyBorder(0, 0, 0, 0))
        );

        JButton visualizar = new JButton("Visualizar");
        visualizar.addActionListener(actionEvent -> {
            String[][] mapa = ListadeMapas.formatadorDeString(input.getText());
            if (mapa!= null) {
                tela.atualizar(new Nivel(mapa), new int[]{0, 0}); // Nível / Status
            }
        });

        JButton criar = new JButton("Criar");
        criar.addActionListener(actionEvent -> {
            String[][] mapa = ListadeMapas.formatadorDeString(input.getText());
            if (mapa!= null) {
                boolean verificador = false;
                for (String[] linha: mapa) {
                    for (String coluna: linha) {
                        if (coluna.equals("C")) {
                            verificador = true;
                            break;
                        }
                    }
                }
                if (!verificador) {
                    JOptionPane.showMessageDialog(null, "Precisa do pacman!");
                } else {
                    try {
                        criador.addMap(input.getText());
                        JOptionPane.showMessageDialog(null, "Mapa adicionado com sucesso!");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        });

        JButton esquerda = new JButton("Esquerda");
        esquerda.addActionListener(actionEvent -> {
            if (exemplos.hasPrev()) {
                exemplos = exemplos.prev();
                input.setText(exemplos.getMap().getStringMap());
            }
        });
        JButton direita = new JButton("Direita");
        direita.addActionListener(actionEvent -> {
            if (exemplos.hasNext()) {
                exemplos = exemplos.next();
                input.setText(exemplos.getMap().getStringMap());
            }
        });

        input.setBounds(10, 10, 490, 650);
        tela.setBounds(550, 10, 707, 782);
        visualizar.setBounds(180, 670, 150, 60);
        criar.setBounds(180, 740, 150, 40);
        esquerda.setBounds(16, 670, 150, 110);
        direita.setBounds(340, 670, 150, 110);

        add(input);
        add(tela);
        add(visualizar);
        add(criar);
        add(esquerda);
        add(direita);
    }
}
