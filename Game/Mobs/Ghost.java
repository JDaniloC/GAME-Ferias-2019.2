package Mobs;

import java.util.LinkedList;

public class Ghost extends Mob{
    private Mob pocket = new Fundo("");
    private LinkedList<int[]> rotas = new LinkedList<>();

    private int[] original;
    private int[] coords;

    private String routeType;
    private String ghostType;
    private String path = "./src/Imagens/Ghost/";

    private int rodadas = 0;

    public Ghost(int tipo){
        /* Construtor
         * A depender de uma numeração, irá escolher qual tipo de fantasma será.
         */
        switch (tipo){
            case 0:
                ghostType = "Ghost.png";
                setImage(path + "Right/" + ghostType);
                setRouteType("BFS");
                break;
            case 1:
                ghostType = "Ghost2.png";
                setImage(path + "Right/" + ghostType);
                setRouteType("BFS");
                break;
            case 2:
                ghostType = "Ghost3.png";
                setImage(path + "Right/" + ghostType);
                setRouteType("DFS");
                break;
            default:
                ghostType = "Ghost4.png";
                setImage(path + "Right/" + ghostType);
                setRouteType("DFS");
        }
        setType("Ghost");
    }

    public void anda(int[] novo) {
        rodadas ++;
        if (rodadas == 50 && !(ghostType.equals("Ghost.png") || ghostType.equals("Ghost4.png"))) {
            // Para fazer o Ghost2 e Ghost3 mudar de comportamento
            String tipo = (routeType.equals("BFS")) ? "DFS": "BFS";
            setRouteType(tipo);
            rodadas = 0;
        }
        /* Anda
         * Método para mudar a imagem
         */
        if (coords[0] < novo[0]) {
            // Baixo
            setImage(path + "Down/" + ghostType);
        } else if (coords[0] > novo[0]) {
            // Cima
            setImage(path + "Up/" + ghostType);
        } else if (coords[1] < novo[1]) {
            // Direita
            setImage(path + "Right/" + ghostType);
        } else if (coords[1] > novo[1]) {
            // Esquerda
            setImage(path + "Left/" + ghostType);
        }
    }

    public Mob getPocket () {
        /* Pegar do bolso
         * Retira o Mob guardado no bolso
         */
        Mob item = pocket;
        pocket = new Fundo("");
        return item;
    }

    public void switchState () {
        if (getType().equals("Ghost")) {
            setType("Faint");
            setRouteType("DFS");
        } else {
            setType("Ghost");
            if (ghostType.equals("Ghost.png")) {
                setRouteType("BFS");
            }
        }
    }

    @Override
    public String getImage() {
        if (getType().equals("Faint")) {
            return "./src/Imagens/Ghost/GhostFaint.gif";
        }
        return super.getImage();
    }

    public int[] getCoords() { return coords; }
    public int[] getOriginal() { return original; }
    public LinkedList<int[]> getRotas () { return rotas; }
    public String getRouteType() { return routeType; }

    public void setOriginal(int[] original) { this.original = original; }
    public void setPocket(Mob mob) { pocket = mob; }
    public void setCoords(int[] coords) { this.coords = coords; }
    public void setRotas(LinkedList<int[]> rotas) { this.rotas = rotas; }
    public void setRouteType(String routeType) { this.routeType = routeType; }
}