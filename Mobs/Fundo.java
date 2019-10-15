public class Fundo{
    String imagem;

    public Fundo(String tipo){
        if (tipo.equals("W")){
            imagem = "Wall.jpg";
        } else{
            imagem = "";
        }
    }
}