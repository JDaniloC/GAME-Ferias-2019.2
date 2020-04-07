package backend;

import frontend.Tela;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Engine extends JFrame implements KeyListener{
    Nivel mapa;
    final Tela canvas = new Tela();
    Comandos command;

    int vidas = 3;
    int pontos = 0;
    boolean[] venceu = new boolean[] {false, false};

    public Engine() {
        /*
        Explicação das funções Swing
        super()                    Adiciona o título
        setSize()                  Modifica o tamanho da tela
        setLocationRelativeTo()    Onde a tela irá iniciar
        setDefaultCloseOperation() O que a tela irá fazer ao clicar X
        setResizable()             Se a tela pode ser redimensionada
        setVisible()               Fazer a tela aparecer
         */
        super("Pacman");
        setSize(717, 860);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        addKeyListener(this);
        setVisible(true);

        inicio();
    }

    private void inicio() {
        add(canvas, BorderLayout.CENTER);
    }

    /* rodar
     * A função jogo em uma variável do tipo Runnable
     * que está em outro Thread (ver o método init).
     */
    private Runnable rodar = new Runnable(){
        @Override
        public void run() {
            setVisible(true);

            boolean verificador = true;
            int acao = 0;
            int[] status = new int[2];
            while (verificador) {
                acao = command.getComando();
                command.action(acao);

                try {
                    Thread.sleep(180); // Quanto menor, mais rápido o jogo!
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                status = command.getStatus();

                if (acao == 27 || status[0] == 0 || acao == -1) { // Acao 27 == ESC
                    vidas = status[0];
                    pontos += status[1];
                    verificador = false;
                }

                command.moveGhost();
                atualizar(mapa, status);
            }
            if (acao == -1) {
                venceu[1] = true;
            } else if (status[0] == 0) {
                venceu[1] = false;
            }
            venceu[0] = true;
        }
    };
    public boolean init(Nivel mapa) {
        this.mapa = mapa;
        this.command = new Comandos(mapa);

        this.command.setStatus(vidas, pontos); // Perpeturar as vidas e pontos

        new Thread(rodar).start();
        while (!venceu[0]) {
            System.out.print(""); // Pra esperar
        }
        venceu[0] = false;

        return venceu[1];
    }
    public void atualizar(Nivel mapa, int[] status) {
        canvas.atualizar(mapa, status);
    }

    public void loading (int nivel) throws InterruptedException {
        remove(canvas);

        ImageIcon imagem = new ImageIcon(new ImageIcon("./src/Imagens/Misc/Ready.jpg").getImage().getScaledInstance(717, 600, Image.SCALE_DEFAULT));
        this.getContentPane().setBackground(Color.BLACK);
        JLabel label = new JLabel(imagem);
        this.getContentPane().add(label, BorderLayout.PAGE_START);

        Font  fonte  = new Font(Font.SANS_SERIF,  Font.BOLD, 70);
        JLabel texto = new JLabel("Nível", SwingConstants.CENTER);
        texto.setFont(fonte);
        texto.setForeground(Color.WHITE);
        texto.setText("Level " + Integer.toString(nivel));
        add(texto, BorderLayout.PAGE_END);

        setVisible(true);
        Thread.sleep(1500);

        remove(label);
        remove(texto);
        add(canvas);
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int tecla = keyEvent.getKeyCode();

        command.setComando(tecla);
    }
    @Override
    public void keyTyped(KeyEvent keyEvent) { }

    @Override
    public void keyReleased(KeyEvent keyEvent) { }

    public int getPontos() { return pontos; }
    public void terminate() { dispose(); }
}
