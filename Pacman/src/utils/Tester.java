package utils;

import Mobs.Fundo;
import Mobs.Ghost;
import Mobs.Mob;
import Mobs.Player;
import backend.ListadeMapas;
import backend.Nivel;
import frontend.Begin;
import frontend.Tela;

import javax.swing.*;
import java.util.LinkedList;

public class Tester extends JFrame {
    private static int[] coords;
    final Tela canvas = new Tela();

    public Tester() {
        setSize(717, 860);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        add(canvas);
    }

    public void atualizar(Nivel mapa, int[] status) {
        canvas.atualizar(mapa, status);
    }

    public static void main(String[] args) throws Exception {
        /* TESTER
         * Feito para ver o comportamento do fantasma.
         */
        FindPacman finder = new FindPacman();
        Tester tela = new Tester();
        Nivel exemplo = new Nivel(new String[][]{{}});
        ListadeMapas criador = new ListadeMapas("teste.txt");
        Mob[][] mapa = criador.getMap().getMapa();
        int[] local = new int[1];

        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[i].length; j++) {
                if (mapa[i][j] instanceof Ghost) {
                    setCoords(new int[]{i, j});
                } else if (mapa[i][j] instanceof Player) {
                    local = new int[]{i, j};
                }
            }
        }
        LinkedList<int[]> resultado = finder.caminho(getCoords(), local, mapa, "BFS");
        int x = 0, y = 0;


        System.out.println(resultado.size());
        while (!resultado.isEmpty()) {

            int[] caminho = resultado.removeLast();

            x = caminho[1];
            y = caminho[0];
            mapa[y][x] = new Fundo("-");
            exemplo.setMapa(mapa);

            // Atualização da tela
            tela.atualizar(exemplo, new int[]{1, 0});
            Thread.sleep(100);
        }
    }

    public static int[] getCoords() {
        return coords;
    }

    public static void setCoords(int[] novo) {
        coords = novo;
    }
}