import java.io.File;
import java.util.*;

import Mobs.Player;

class ListadeMapas{
    private Level nivel;
    private ListadeMapas proximo;

    public ListadeMapas(String caminho, Level nivel) throws Exception{
        this.nivel = nivel;
        proximo = null;
        if (caminho != null){
            init(caminho);
        }
    }
    public static void main(String[] args) throws Exception{
        new ListadeMapas("C:\\Users\\Rengar\\Documents\\Program\\Projeto-IF669-2019\\Config.txt", null);
    }

    public void adiciona(String[][] mapa) throws Exception {
        if (nivel == null){
            nivel = new Level(mapa);
        } else{
            if (proximo != null){
                proximo.adiciona(mapa);
            } else{
                Level novo = new Level(mapa);
                proximo = new ListadeMapas(null, novo);
            }
        }
    }

    public void init(String caminho) throws Exception{
        File arquivo = new File(caminho);
        Scanner leitor = new Scanner(arquivo);
        String [][]novo = new String[31][];
        int x = 0;
        while (leitor.hasNextLine()){
            String entrada = leitor.nextLine();
            if (entrada.equals(" ")){
                x = 0;
                adiciona(novo);
            } else{
                novo[x] = entrada.split(" ");
                x++;
            }
        }
        leitor.close();
    }

    public ListadeMapas next(){
        if (proximo != null){
            return proximo;
        } 
        return null;
    }

    public boolean hasNext(){
        if (proximo != null){
            return true;
        }
        return false;
    }
}

class Level{
    private Player[][] valor; // Substituir por Mob
    public Level(String[][] array){
        valor = criaMob(array);
    }
    public Player[][] getValor(){
        return valor;
    }
    private Player[][] criaMob(String[][] valor){
        Player[][] novo = new Player[valor.length][valor[0].length];
        for (int i = 0; i < valor.length; i++){
            for (int j = 0; j < valor[0].length; j++){
                if (valor[i][j].equals("#")){
                    novo[i][j] = new Player(); // Especificar os tipos.
                }
            }
        }
        return novo;
    }
}