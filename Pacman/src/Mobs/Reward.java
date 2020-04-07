package Mobs;

public class Reward extends Mob{

    public Reward (String tipo){
        if (tipo.equals("C")){
            setImage("./src/Imagens/Misc/Coin.gif");
            setType("Coin");
        } else {
            setImage("./src/Imagens/Misc/Straw.png");
            setType("Straw");
        }
    }
}