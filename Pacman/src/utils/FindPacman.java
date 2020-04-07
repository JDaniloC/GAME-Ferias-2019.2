package utils;

import Mobs.Mob;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class FindPacman {
    private int[] GhostCoords, PacmanCoords;
    private Mob[][] mapa;

    public LinkedList<int[]> caminho(int[] GhostCoords, int[] PacmanCoords, Mob[][] mapa, String tipo) {
        /* Caminho
         * Devolve o caminho até o pacman
         * Onde percorre do pacman até o fantasma
         */

        this.GhostCoords = GhostCoords;
        this.PacmanCoords = PacmanCoords;
        this.mapa = mapa;

        LinkedList<int[]> resultado = new LinkedList<>();
        LinkedList<int[][]> rota = null;

        int x = 0, y = 0, indice = 0;
        if (tipo.equals("BFS")) {
            rota = BFSFinder();

            while (!Arrays.equals(new int[]{y, x}, GhostCoords) && indice != -1) {
                // Procurando o passo "anterior"
                indice = indexDe(rota, PacmanCoords);
                if (indice != -1) {
                    // O novo caminho
                    int[][] caminho = rota.remove(indice);
                    PacmanCoords = caminho[1];

                    // Atualiza a posição da procura
                    x = caminho[0][1];
                    y = caminho[0][0];

                    resultado.add(new int[] {y, x});
                }
            }
        } else {
            rota = DFSFinder();

            while (!rota.isEmpty()) {
                int[][] caminho = rota.removeLast();
                PacmanCoords = caminho[1];

                x = caminho[0][1];
                y = caminho[0][0];

                resultado.add(new int[] {y, x});
            }
        }

        //resultado.removeLast();
        return resultado;
    }

    private int indexDe(LinkedList<int[][]> lista, int[] local) {
        /* indexDe
         * Devolve o indice de um local em um LinkedList
         */
        for (int i = 0; i < lista.size(); i ++) {
            if (Arrays.equals(lista.get(i)[0], local)) {
                return i;
            }
        }
        return -1;
    }

    private LinkedList<int[][]> BFSFinder() {
        /* Encontrador de rotas BFS
         * Método para encontrar um possível caminho até chegar ao pacman
         * Irá fazer um tipo de BFS a procura do pacman a partir do Ghost
         * Devolvendo uma lista encadeada onde:
         * {indiceX, indiceParaChegarEmX}
         */

        LinkedList<int[][]> visitar = new LinkedList<>(), visitou = new LinkedList<>();
        int indice = 0;

        visitar.add(new int[][] {GhostCoords, GhostCoords});
        while (!visitar.isEmpty() && pacNotFound(visitou, indice)) {
            indice = visitou.size();
            if (coordNotFound(visitou, visitar.getFirst())) {
                visitou.add(visitar.getFirst());
                visitar.addAll(getAdjacentes(visitar.removeFirst()[0]));
            } else {
                visitar.removeFirst();
            }
        }

        return visitou;
    }

    private LinkedList<int[][]> DFSFinder() {
        /* Encontrador de rotas DFS
         * Método para encontrar um possível caminho até chegar ao pacman
         * Irá fazer um tipo de DFS a procura do pacman a partir do Ghost
         * Devolvendo uma lista encadeada onde:
         * {indiceX, indiceParaChegarEmX}
         */

        LinkedList<int[][]> visitar = new LinkedList<>(), visitou = new LinkedList<>();

        visitar.add(new int[][] {GhostCoords, GhostCoords});
        while (!visitar.isEmpty() && pacNotFound(visitou, 0)) {
            if (coordNotFound(visitou, visitar.getFirst())) {
                visitou.add(visitar.getFirst());

                LinkedList<int[][]> adjacentes = getAdjacentes(visitar.removeFirst()[0]);


                Random rand = new Random();
                int aux = rand.nextInt(adjacentes.size());

                if (coordNotFound(visitou, adjacentes.get(aux))) {
                    visitar.add(adjacentes.get(aux));
                } else {
                    for (int[][] coords : adjacentes) {
                        if (coordNotFound(visitou, coords)) {
                            visitar.add(coords);
                            break;
                        }
                    }
                }
            } else {
                visitar.removeFirst();
            }
        }

        return visitou;
    }

    private boolean pacNotFound(LinkedList<int[][]> lista, int indice) {
        /* Encontrou Pacman
         * Verifica se a coordenada do pacman está no LinkedList a partir de um índice
         * Percorre a lista em comparando as coordenadas.
         */
        if (!lista.isEmpty()) {
            for (; indice < lista.size(); indice ++) {
                if (Arrays.equals(lista.get(indice)[0], PacmanCoords)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean coordNotFound(LinkedList<int[][]> lista, int[][] coord) {
        /* Encontrou a coordenada
         * Verifica se a coordenada está na lista
         */
        if (!lista.isEmpty()) {
            for ( int[][] tentativa: lista ) {
                if (Arrays.equals(tentativa[0], coord[0])) {
                    return false;
                }
            }
        }
        return true;
    }

    private LinkedList<int[][]> getAdjacentes(int[] local) {
        /* Pega adjacentes
         * Função que verifica e devolve as posições possíveis ao redor do local.
         * Excluindo os fora de alcance e paredes.
         */

        LinkedList<int[][]> locais = new LinkedList<>();
        int y = local[0], x = local[1];

        if (y < mapa.length - 1 && !mapa[y + 1][x].getType().equals("Wall")) {
            locais.add(new int[][] { new int[] {y + 1, x}, local});
        }

        if (y > 0 && !mapa[y - 1][x].getType().equals("Wall")) {
            locais.add(new int[][] { new int[] {y - 1, x}, local});
        }

        if (y == 0 && !mapa[mapa.length - 1][x].getType().equals("Wall")) {
            locais.add(new int[][] { new int[] {mapa.length - 1, x}, local});
        }

        if (y == mapa.length -1 && !mapa[0][x].getType().equals("Wall")) {
            locais.add(new int[][] { new int[] {0, x}, local});
        }

        if (x < mapa[y].length - 1 && !mapa[y][x + 1].getType().equals("Wall")) {
            locais.add(new int[][] { new int[] {y, x + 1}, local});
        }

        if (x > 0 && !mapa[y][x - 1].getType().equals("Wall")) {
            locais.add(new int[][] { new int[] {y, x - 1}, local});
        }

        if (x == 0 && !mapa[y][mapa[y].length - 1].getType().equals("Wall")) {
            locais.add(new int[][] { new int[] {y, mapa[y].length - 1}, local});
        }

        if (x == mapa[y].length - 1 && !mapa[y][0].getType().equals("Wall")) {
            locais.add(new int[][] { new int[] {y, 0}, local});
        }

        return locais;
    }
}
