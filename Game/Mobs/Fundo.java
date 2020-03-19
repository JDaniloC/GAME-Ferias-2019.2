package Mobs;

public class Fundo extends Mob {
    public Fundo(String tipo){
        if (tipo.equals("W")){
            setImage("./src/Imagens/Misc/Wall.jpg");
            setType("Wall");
        } else if (tipo.equals("-")){
            setImage("./src/Imagens/Misc/Red.png");
            setType("Red");
        }
    }
}