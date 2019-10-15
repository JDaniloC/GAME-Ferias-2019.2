import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;

import Mobs.Player;

class ListadeMapas{
    private String caminho;
    private Level nivel;
    private ListadeMapas proximo;

    public ListadeMapas(String caminho, Level nivel) throws Exception{
        this.nivel = nivel;
        proximo = null;
        if (caminho != null){
            this.caminho = caminho;
            init();
        }
    }
    public static void main(String[] args) throws Exception{
        String OS = System.getProperty("os.name");
        String pre = "";
        if (OS.equals("Linux")){
            pre = "./";
        }
        ListadeMapas test = new ListadeMapas(pre + "Config.txt", null);
        test.addMap("\n");
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

    public void init() throws Exception{
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

    public void addMap(String mapa) throws Exception{
        String[] map = mapa.split("\n");
        String[][] novo = new String[31];
        if (map.length == 31){ // O mapa tem 31 por default!
            boolean ver = true;
            for (int i = 0; i < 31 && ver; i++){
                novo[i] = map[i].split(" ");
                if (novo[i].length != 28){
                    ver = false;
                }
            }
            if (ver){
                File arquivo = new File(caminho);
                BufferedWriter escreve = new BufferedWriter(new FileWriter(arquivo, true));
                escreve.write(mapa);
                escreve.close();
            } else{
                System.out.println("ERROR: Mapa inválido");
            }
        } else{
            System.out.println("ERROR: Mapa inválido");
        }
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

    public Level getMap(){
        return nivel;
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
        int quant = 0;
        for (int i = 0; i < valor.length; i++){
            for (int j = 0; j < valor[0].length; j++){
                String objeto = valor[i][j];
                if (objeto.equals("#")){
                    novo[i][j] = new Fundo("W"); // Especificar os tipos.
                } else if (objeto.equals(".")){
                    novo[i][j] = new Reward("C");
                } else if (objeto.equals("s")){
                    novo[i][j] = new Reward("S");
                } else if (objeto.equals("C")){
                    novo[i][j] = new Player();
                } else if (objeto.equals("G")){
                    novo[i][j] = new Ghost(quant);
                    quant ++;
                } else{
                    novo[i][j] = new Fundo();
                }
            }
        }
        return novo;
    }
}