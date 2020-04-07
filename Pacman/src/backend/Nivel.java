package backend;

import Mobs.*;

import java.util.Arrays;

public class Nivel {
    // Variável padrão da classe
    private Mob[][] mapa;

    /* Construtor
     * Recebe um array de String e traduz para um array de Mob
     */
    public Nivel(String[][] array) {
        // Instanciação da nova estrutura
        mapa = new Mob[array.length][array[0].length];

        int ghosts = 0; // Quantidade de fantasmas

        // Mapeamento
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                String character = array[i][j];
                switch (character) {
                    case "#": // Uma parede
                        mapa[i][j] = new Fundo("W");
                        break;
                    case ".": // Uma moeda
                        mapa[i][j] = new Reward("C");
                        break;
                    case "s": // Um doce
                        mapa[i][j] = new Reward("S");
                        break;
                    case "C": // O jogador
                        Player jogador = new Player();
                        jogador.setCoords(i, j);
                        mapa[i][j] = jogador;
                        break;
                    case "G": // Um fantasma
                        mapa[i][j] = new Ghost(ghosts);
                        if (ghosts < 4) { ghosts++; }
                        break;
                    default: // Um background
                        mapa[i][j] = new Fundo("");
                        break;
                }
            }
        }
    }

    public String[][] convert () {
        String[][] resultado = new String[mapa.length][mapa[0].length];

        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[i].length; j++) {
                Mob character = mapa[i][j];
                switch (character.getType()) {
                    case "Wall":
                        resultado[i][j] = "#";
                        break;
                    case "Coin":
                        resultado[i][j] = ".";
                        break;
                    case "Straw":
                        resultado[i][j] = "s";
                        break;
                    case "Pacman":
                        resultado[i][j] = "C";
                        break;
                    case "Ghost":
                        resultado[i][j] = "G";
                        break;
                    default:
                        resultado[i][j] = "M";
                        break;
                }
            }
        }

        return  resultado;
    }

    public String getStringMap() {
        String resultado = "";
        String[][] original = convert();
        for (String[] linhas: original) {
            for (String coluna: linhas) {
                resultado += coluna + " ";
            }
            resultado += "\n";
        }
        return resultado;
    }

    public Mob[][] getMapa() {
        return mapa;
    }
    public void setMapa(Mob[][] mapa) { this.mapa = mapa; }
}
