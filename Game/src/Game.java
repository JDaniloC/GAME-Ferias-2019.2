import backend.*;
import frontend.Begin;
import frontend.Config;
import frontend.End;
import frontend.Record;
import utils.Score;

import javax.swing.*;
import java.io.FileNotFoundException;

import static javax.swing.JOptionPane.showMessageDialog;

public class Game extends JFrame {
    ListadeMapas niveis = new ListadeMapas("./Config.txt");
    Begin telaInicial = new Begin();
    String nome;
    boolean irJogo, irRecord, irMapa;

    public Game() throws Exception {
        setSize(717, 730);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        telaInicial.getJogar().addActionListener(actionEvent -> {
            irJogo = true;
        });

        telaInicial.getRecord().addActionListener(actionEvent -> {
            irRecord = true;
        });

        telaInicial.getCriar().addActionListener(actionEvent -> {
            irMapa = true;
        });

        nome = JOptionPane.showInputDialog("Digite seu nome");

        add(telaInicial);

        setVisible(true);
    }

    public void comecar() throws InterruptedException {
        while (!irJogo && !irRecord && !irMapa) {
            System.out.print("");
        }
        if (irJogo) {
            dispose();
            start();
        } else if (irRecord) {
            record();
            irRecord = false;
            comecar();
        } else {
            irMapa = false;
            try {
                new Config();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            comecar();
        }
    }

    public static void main(String[] args) throws Exception {
        try {
            Game jogo = new Game();
            jogo.comecar();
        } catch (FileNotFoundException e){
            ListadeMapas niveis = new ListadeMapas((String) null);
            niveis.setCaminho("Config.txt");

            String mapa = "" +
                    "# # # # # # # # # # # # # # # # # # # # # # # # # # # #\n" +
                    "# . . . . . . . . . # # . . . . # # . . . . . . . . . #\n" +
                    "# . # # # # . # # . # # . # # . # # . # # . # # # # . #\n" +
                    "# . # # # # . # # . # # . # # . # # . # # . # # # # . #\n" +
                    "# . . . . s . # # . . . . # # . . . . # # . s . . . . #\n" +
                    "# . # # # # . # # # # # . # # . # # # # # . # # # # . #\n" +
                    "# . # # # # . # # # # # . # # . # # # # # . # # # # . #\n" +
                    "# . # # . . . . . . . . . # # . . . . . . . . . # # . #\n" +
                    "# . # # . # # # # . # # # # # # # # . # # # # . # # . #\n" +
                    "# . # # . # # # # . # # # # # # # # . # # # # . # # . #\n" +
                    ". . . . . . . . . . . . . . . . . . . . . . . . . . . .\n" +
                    "# . # # # # . # # . # # # M M # # # . # # . # # # # . #\n" +
                    "# . # # # # . # # . # M M M M M M # . # # . # # # # . #\n" +
                    "# . # # # # . # # . # M G G G G M # . # # . # # # # . #\n" +
                    "# . . . . . . # # . # M M M M M M # . # # . . . . . . #\n" +
                    "# . # # # # . # # . # # # # # # # # . # # . # # # # . #\n" +
                    "# . # # # # . # # . . . . . . . . . . # # . # # # # . #\n" +
                    "# . . . # # . # # . # # # # # # # # . # # . # # . . . #\n" +
                    "# # # . # # . # # . # # # # # # # # . # # . # # . # # #\n" +
                    "# # # . # # . . . . . . . # # . . . . . . . # # . # # #\n" +
                    ". . . . # # . # # # # # . # # . # # # # # . # # . . . .\n" +
                    "# . # # # # . # # # # # . # # . # # # # # . # # # # . #\n" +
                    "# . # # # # . # # . . . . C . . . . . # # . # # # # . #\n" +
                    "# . . . . . . # # . # # . # # . # # . # # . . . . . . #\n" +
                    "# . # # . # # # # . # # . # # . # # . # # # # . # # . #\n" +
                    "# . # # . # # # # . # # . # # . # # . # # # # . # # . #\n" +
                    "# . # # . s . . . . # # . . . . # # . . . . s . # # . #\n" +
                    "# . # # # # # # # . # # # # # # # # . # # # # # # # . #\n" +
                    "# . # # # # # # # . # # # # # # # # . # # # # # # # . #\n" +
                    "# . . . . . . . . . . . . . . . . . . . . . . . . . . #\n" +
                    "# # # # # # # # # # # # # # # # # # # # # # # # # # # #\n";

            niveis.addMap(mapa);
            showMessageDialog(null, "Arquivo de configuração não encontrado.\nO arquivo será criado, reinicie a aplicação.");
        }
    }


    public void start () throws InterruptedException {
        Engine window = new Engine();

        boolean venceu = true;
        int level = 1;

        while (venceu && niveis.getMap() != null) {
            window.loading(level);
            venceu = window.init(niveis.getMap());
            if (venceu && niveis.hasNext()) {
                level ++;
                niveis.next();
            } else if (!niveis.hasNext()) {
                break;
            }
        }

        window.terminate();
        new End(window.getPontos(), venceu);

        Score.setScore(nome, window.getPontos(), "Score.txt");
    }

    public void record() {
        setVisible(false);
        remove(telaInicial);

        Record scores = new Record();
        scores.getVoltar().addActionListener(actionEvent -> {
            setVisible(false);
            remove(scores);
            add(telaInicial);
            setVisible(true);
        });

        add(scores);
        setVisible(true);
    }
}
