package backend;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class ListadeMapas {
    private String caminho;
    private Nivel level;
    private ListadeMapas proximo;
    private ListadeMapas anterior;

    /* Construtor
     * Se houver caminho ele irá ler o arquivo, se não, irá dar append.
     */
    public ListadeMapas(Nivel level) throws Exception{
        this.level = level;
        proximo = null;
        anterior = null;
    }

    public ListadeMapas(String caminho) throws Exception {
        if (caminho != null) {
            this.caminho = caminho;
            init();
        }
    }

    /* init
     * Método para a leitura do arquivo config.txt e sua tradução
     */
    public void init() throws Exception {
        File arquivo = new File(caminho);
        Scanner leitor = new Scanner(arquivo);

        // Matriz temporária para criação de um mapa
        String [][]novo = new String[31][];
        int x = 0;

        while (leitor.hasNextLine()){
            String entrada = leitor.nextLine();
            if (entrada.equals("")){
                x = 0;
                append(novo);
            } else{
                novo[x] = entrada.split(" ");
                x++;
            }
        }
        append(novo);

        leitor.close();
    }

    public void append(String[][] mapa) throws Exception {
        /* append
         * Método para adicionar a instanciação de um novo mapa
         */
        if (level == null){
            level = new Nivel(mapa);
        } else {
            if (proximo != null){
                proximo.append(mapa);
            } else{
                Nivel novo = new Nivel(mapa);
                proximo = new ListadeMapas(novo);
                proximo.anterior = this;
            }
        }
    }

    public void addMap(String mapa) throws Exception{
        /* Adicionar mapa
         * Método para adicionar um novo mapa reescrevendo o arquivo config.txt
         */
        String [][] verificador = formatadorDeString(mapa);
        if (verificador != null) {
            File arquivo = new File(caminho);
            BufferedWriter escreve = new BufferedWriter(new FileWriter(arquivo, true));
            if (arquivo.length() != 0) {
                mapa = "\n" + mapa;
            }
            escreve.write(mapa + "\n");
            escreve.close();
        }
    }

    public static String[][] formatadorDeString(String mapa) {
        String[] map = mapa.split("\n");
        String[][] novo = new String[31][28];
        if (map.length == 31){ // O mapa tem 31 por default!
            boolean ver = true;
            for (int i = 0; i < 31 && ver; i++){
                novo[i] = map[i].split(" ");
                if (novo[i].length != 28){
                    ver = false;
                }
            }
            // É verificado se o tamanho do mapa está correto, então adicionado.
            if (ver){
                return novo;
            }
        }
        showMessageDialog(null, "ERROR: Mapa inválido\nÉ necessário ter 31 caracteres de altura");
        return null;
    }

    public ListadeMapas next(){
        if (proximo != null){
            return proximo;
        }
        return this;
    }

    public ListadeMapas prev() {
        if (anterior != null) {
            return anterior;
        }
        return this;
    }

    public Nivel getMap(){
        return level;
    }
    public void setCaminho(String caminho) { this.caminho = caminho; }

    public boolean hasNext() { return proximo != null; }
    public boolean hasPrev() { return anterior != null; }
}

