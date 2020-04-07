package utils;

import Mobs.*;
import backend.Nivel;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Vector;

public class Actions {
    private Nivel mapa;
    private Vector<Ghost> fantasmas;
    private Player jogador;

    private FindPacman finder = new FindPacman();

    private int moedas;
    private int contador = 0;

    public Actions (Nivel mapa, Vector<Ghost> fantasmas, Player jogador, int moedas) {
        this.mapa = mapa;
        this.fantasmas = fantasmas;
        this.moedas = moedas;
        this.jogador = jogador;
    }

    public void troca(int[] coords) {
        /* troca
         * Método para interagir o pacman com um Mob
         */
        Mob[][] nivel = mapa.getMapa();
        int[] pac = jogador.getCoords();
        Mob mob = nivel[coords[0]][coords[1]];

        if (mob instanceof Reward) {
            Mob item = nivel[coords[0]][coords[1]];

            nivel[pac[0]][pac[1]] = new Fundo("");
            jogador.setCoords(coords[0], coords[1]);
            nivel[coords[0]][coords[1]] = jogador;

            if (item.getType().equals("Coin")) {
                jogador.plusPontos(100);
                this.moedas --;
            } else {
                for (Ghost gaspar: fantasmas) {
                    if (gaspar.getType().equals("Ghost")) {
                        gaspar.switchState();
                    }
                }
                contador = 0;
            }
        } else if (mob instanceof Ghost) {
            if (mob.getType().equals("Faint")) {
                Ghost fantasma = (Ghost) mob;
                Mob item = fantasma.getPocket();
                if (!(item instanceof Fundo)) {
                    if (item.getType().equals("Coin")) {
                        jogador.plusPontos(100);
                        this.moedas --;
                    } else {
                        for (Ghost gaspar: fantasmas) {
                            if (gaspar.getType().equals("Ghost")) {
                                gaspar.switchState();
                            }
                        }
                        fantasma.setRotas(new LinkedList<>());
                    }
                    fantasma.setPocket(new Fundo(""));
                }
                nivel[pac[0]][pac[1]] = new Fundo("");
                jogador.setCoords(coords[0], coords[1]);
                nivel[coords[0]][coords[1]] = jogador;

                nivel[fantasma.getOriginal()[0]][fantasma.getOriginal()[1]] = fantasma;
                fantasma.setCoords(fantasma.getOriginal());
                fantasma.switchState();
                fantasma.setRotas(new LinkedList<>());

                jogador.plusPontos(500);
            } else {
                jogador.minusVida(1);

                for (Ghost gaspar : fantasmas) {
                    int[] local = gaspar.getCoords();
                    Mob item = gaspar.getPocket();

                    nivel[local[0]][local[1]] = (item != null) ? item : new Fundo("");
                    nivel[gaspar.getOriginal()[0]][gaspar.getOriginal()[1]] = gaspar;
                    gaspar.setCoords(gaspar.getOriginal());
                    gaspar.setRotas(new LinkedList<>());
                }

                nivel[jogador.getCoords()[0]][jogador.getCoords()[1]] = new Fundo("");
                nivel[jogador.getOriginal()[0]][jogador.getOriginal()[1]] = jogador;
                nivel[pac[0]][pac[1]] = new Fundo("");
                jogador.setCoords(jogador.getOriginal()[0], jogador.getOriginal()[1]);
            }
        } else if (!mob.getType().equals("Wall")) {
            jogador.setCoords(coords[0], coords[1]);
            nivel[coords[0]][coords[1]] = jogador;
            nivel[pac[0]][pac[1]] = new Fundo("");
        }
    }

    public void moveGhost() {
        /* Movimentar Fantasmas
         * Método para calcular o tempo de cada ação dos fantasmas
         */

        // Para voltar os fantasmas ao normal
        contador ++;
        if (contador % 61 == 0) {
            for (Ghost gaspar: fantasmas) {
                if (gaspar.getType().equals("Faint")) {
                    gaspar.switchState();
                }
            }
            contador = 0;
        } else if (contador == 1 || contador % 3 == 0) {
            for (Ghost ghost: fantasmas) {
                if (ghost.getRouteType().equals("BFS")) {
                    LinkedList<int[]> ghostRoutes = finder.caminho(ghost.getCoords(), jogador.getCoords(), mapa.getMapa(), "BFS");
                    ghost.setRotas(ghostRoutes);
                }
            }
        }

        for (Ghost ghost: fantasmas) {
            if (ghost.getRotas().isEmpty()) {
                ghost.setRotas(finder.caminho(ghost.getCoords(), jogador.getCoords(), mapa.getMapa(), ghost.getType()));
            }
            int[] coords;
            coords = ghost.getRotas().removeLast();

            if (!Arrays.equals(jogador.getCoords(), coords)) {
                ghost.anda(coords);
                Mob[][] nivel = mapa.getMapa();
                nivel[ghost.getCoords()[0]][ghost.getCoords()[1]] = ghost.getPocket();

                if (!(nivel[coords[0]][coords[1]] instanceof Ghost)) {
                    ghost.setPocket(nivel[coords[0]][coords[1]]);
                }
                nivel[coords[0]][coords[1]] = ghost;
                ghost.setCoords(new int[]{coords[0], coords[1]});
            } else {
                troca(ghost.getCoords());
            }
        }
    }

    public boolean end(){
        /* Final
         * Verifica se as moedas chegaram a zero
         * Recalcula com moedas abaixo de 30 para ter certeza
         */
        if (moedas < 50) {
            int coin = 0;
            for (Mob[] mobs: mapa.getMapa()) {
                for (Mob pivo: mobs) {
                    if (pivo.getType().equals("Coin")) {
                        coin ++;
                    }
                }
            }
            return coin == 0;
        } else {
            System.out.println(moedas);
        }
        return false;
    }
}
