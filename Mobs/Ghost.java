public class Ghost{
    private String imagem; // Uma função define Sprites

    public Ghost(int tipo){
        switch (tipo){
            case 0:
                imagem = "Ghost.png";
                break;
            case 1:
                imagem = "Ghost2.png";
                break;
            case 2:
                imagem = "Ghost3.png";
                break;
            default:
                imagem = "Ghost4.png";
        }
    }
}