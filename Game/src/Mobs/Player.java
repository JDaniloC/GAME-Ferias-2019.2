package Mobs;

public class Player extends Mob{
    private int pontos = 0;
    private int vidas = 3;
    private int[] coords;
    private int[] original;
    private boolean open = true;
    private String path = "./src/Imagens/Pacman/";

    public Player () {
        setImage(path + "Right.gif");
        setType("Pacman");
    }

    public void move(String sentido) {
        /*if (open) {
            setImage(path + "Close/" + sentido + ".png");
            open = false;
        } else {
            setImage(path + "Open/" + sentido + ".png");
            open = true;
        }
         */
        setImage(path + sentido + ".gif");
    }

    public void plusVida(int vida) {
        this.vidas += vida;
    }

    public boolean morto() {
        return vidas == 0;
    }

    public int[] getCoords() {
        return coords;
    }
    public int getPontos() {
        return pontos;
    }
    public int getVidas() {
        return vidas;
    }
    public int[] getOriginal() { return original; }

    public void setCoords(int x, int y) { coords = new int[]{x, y}; }
    public void setOriginal(int x, int y) { original = new int[]{x, y}; }
    public void minusVida(int vida) {
        this.vidas -= vida;
    }
    public void plusPontos(int pontos) {
        this.pontos += pontos;
    }
}
