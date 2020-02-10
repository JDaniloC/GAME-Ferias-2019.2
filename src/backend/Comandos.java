package backend;

import Mobs.*;
import utils.Actions;

import java.util.HashMap;
import java.util.Vector;

public class Comandos {
    private HashMap<Integer, Command> comandos = new HashMap<Integer, Command>();
    private Actions acoes;

    private Player jogador;

    private int comando = 0;

    private interface Command {
        /* Command
         * Para a tabela hash!
         */
        void fazer();
    }

    public Comandos(Nivel mapa) {
        /* Comandos
         * Mapeamento de comandos
         */
        Mob[][] nivel = mapa.getMapa();

        int moedas = 0;
        Vector<Ghost> fantasmas = new Vector<>();
        Ghost fantasma = null;
        for (int i = 0; i < nivel.length; i++) {
            for (int j = 0; j < nivel[i].length; j++) {
                if (nivel[i][j] instanceof Player) {
                    jogador = (Player) nivel[i][j];
                    jogador.setOriginal(i, j);
                } else if (nivel[i][j] instanceof Ghost) {
                    fantasma = (Ghost) nivel[i][j];
                    fantasma.setOriginal(new int[] {i, j});
                    fantasma.setCoords(new int[] {i, j});
                    fantasmas.add(fantasma);
                } else if (nivel[i][j].getType().equals("Coin")) {
                    moedas ++;
                }
            }
        }
        acoes = new Actions(mapa, fantasmas, jogador, moedas);

        // Esquerda
        comandos.put(37, new Command() {
            @Override
            public void fazer() {
                Mob[][] nivel = mapa.getMapa();
                int[] coords = jogador.getCoords();
                if (coords[1] > 0) {
                    acoes.troca(new int[]{coords[0], coords[1] - 1});
                } else if (coords[1] == 0) {
                    acoes.troca(new int[]{coords[0], nivel[coords[0]].length - 1});
                }
                jogador.move("Left");
            }
        });
        comandos.put(65, new Command() {
            @Override
            public void fazer() {
                Mob[][] nivel = mapa.getMapa();
                int[] coords = jogador.getCoords();
                if (coords[1] > 0) {
                    acoes.troca(new int[]{coords[0], coords[1] - 1});
                } else if (coords[1] == 0) {
                    acoes.troca(new int[]{coords[0], nivel[coords[0]].length - 1});
                }
                jogador.move("Left");
            }
        });
        // Cima
        comandos.put(38, new Command() {
            @Override
            public void fazer() {
                Mob[][] nivel = mapa.getMapa();
                int[] coords = jogador.getCoords();
                if (coords[0] > 0) {
                    acoes.troca(new int[]{coords[0] - 1, coords[1]});
                } else if (coords[0] == 0) {
                    acoes.troca(new int[]{nivel.length - 1, coords[1]});
                }
                jogador.move("Up");
            }
        });
        comandos.put(87, new Command() {
            @Override
            public void fazer() {
                Mob[][] nivel = mapa.getMapa();
                int[] coords = jogador.getCoords();
                if (coords[0] > 0) {
                    acoes.troca(new int[]{coords[0] - 1, coords[1]});
                } else if (coords[0] == 0) {
                    acoes.troca(new int[]{nivel.length - 1, coords[1]});
                }
                jogador.move("Up");
            }
        });
        // Direita
        comandos.put(39, new Command() {
            @Override
            public void fazer() {
                Mob[][] nivel = mapa.getMapa();
                int[] coords = jogador.getCoords();
                if (coords[1] < nivel[coords[0]].length - 1) {
                    acoes.troca(new int[]{coords[0], coords[1] + 1});
                } else if (coords[1] == nivel[coords[0]].length - 1) {
                    acoes.troca(new int[]{coords[0], 0});
                }
                jogador.move("Right");
            }
        });
        comandos.put(68, new Command() {
            @Override
            public void fazer() {
                Mob[][] nivel = mapa.getMapa();
                int[] coords = jogador.getCoords();
                if (coords[1] < nivel[coords[0]].length - 1) {
                    acoes.troca(new int[]{coords[0], coords[1] + 1});
                } else if (coords[1] == nivel[coords[0]].length - 1) {
                    acoes.troca(new int[]{coords[0], 0});
                }
                jogador.move("Right");
            }
        });
        // Baixo
        comandos.put(40, new Command() {
            @Override
            public void fazer() {
                Mob[][] nivel = mapa.getMapa();
                int[] coords = jogador.getCoords();
                if (coords[0] < nivel.length - 1) {
                    acoes.troca(new int[]{coords[0] + 1, coords[1]});
                } else if (coords[0] == nivel.length -1) {
                    acoes.troca(new int[]{0, coords[1]});
                }
                jogador.move("Down");
            }
        });
        comandos.put(83, new Command() {
            @Override
            public void fazer() {
                Mob[][] nivel = mapa.getMapa();
                int[] coords = jogador.getCoords();
                if (coords[0] < nivel.length - 1) {
                    acoes.troca(new int[]{coords[0] + 1, coords[1]});
                } else if (coords[0] == nivel.length -1) {
                    acoes.troca(new int[]{0, coords[1]});
                }
                jogador.move("Down");
            }
        });
    }

    public void action(int codigo){
        // Verifica se está no dicionário o comando o executa
        if (comandos.containsKey(codigo)) {
            comandos.get(codigo).fazer();
        } else if (comando != 0){
            System.out.printf("A tecla que você pressionou tem código %d\n", codigo);
        }
    }

    public void moveGhost() {
        acoes.moveGhost();
    }

    public int getComando () { return acoes.end() ? -1 : comando; }
    public int[] getStatus() { return new int[] {jogador.getVidas(), jogador.getPontos()}; }

    public void setComando(int comando) { this.comando = comando; }
}

