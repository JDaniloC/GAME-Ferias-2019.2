package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Score {

    public static void setScore(String nome, int pontos, String caminho) {
        /* Modifica pontuação
         * Recebe a nova pontuação, e refaz o arquivo pegando o anterior e
         * adicionando a nova pontuação de forma ordenada
         */

        String[] pontuacao = organizador(getScore(caminho), nome, pontos);
        File arquivo = new File(caminho);

        try {
            FileWriter escritor = new FileWriter(arquivo);

            for (String score: pontuacao) {
                escritor.write(score + "\n");
            }
            escritor.close();
        } catch (IOException error) {
            System.out.println(error.getMessage());
        }
    }

    public static String[] getScore(String caminho) {
        /* Pegar pontuação
         * Devolve a pontuação do arquivo passado, completando com
         * "SEM PONTUAÇÃO" caso estiver faltando
         */
        try {
            File arquivo = new File(caminho);
            Scanner leitor = new Scanner(arquivo);

            String[] novo = new String[15];
            for (int i = 0; i < 15; i++) {
                if (leitor.hasNextLine()) {
                    novo[i] = leitor.nextLine();
                } else {
                    novo[i] = "SEM PONTUAÇÃO";
                }
            }

            return novo;
        } catch (FileNotFoundException e) {
            File arquivo = new File(caminho);

            try {
                FileWriter escritor = new FileWriter(arquivo);
                escritor.write("");
                escritor.close();
            } catch (IOException error) {
                System.out.println(error.getMessage());
            }

            System.out.println(e.getMessage());
            String[] novo = new String[15];

            Arrays.fill(novo, "SEM PONTUAÇÂO");

            return novo;
        }
    }

    private static String[] organizador (String[] antigo, String nome, int pontos) {
        /* Organizador
         * Recebe a antiga lista de pontuação já formatada e os novos pontos
         * Desformata, ordena e devolve ordenado e formatado
         */
        String[] nomes = new String[15], dividido;
        int[] pontuacoes = new int[15];
        nomes[0] = nome;
        pontuacoes[0] = pontos;
        int x = 0;

        for (String pontuacao: antigo) {
            if (!pontuacao.equals("SEM PONTUAÇÃO") && !pontuacao.equals("")) {
                dividido = pontuacao.split("-");
                nome = dividido[0].strip();
                pontos = Integer.parseInt(dividido[dividido.length - 1].strip());

                while (x < 15 && nomes[x] != null && pontuacoes[x] >= pontos) {
                    x ++;
                }
                if (x < 15) {
                    if (nomes[x] != null) {
                        int y = 14;
                        while (y > x) {
                            nomes[y] = nomes[y - 1];
                            pontuacoes[y] = pontuacoes[y - 1];
                            y --;
                        }
                    }
                    pontuacoes[x] = pontos;
                    nomes[x] = nome;
                }

                x = 0;
            }
        }

        String[] resultado = new String[15];
        for (x = 0; x < 15; x ++) {
            if (nomes[x] != null) {
                resultado[x] = formatador(nomes[x], pontuacoes[x]);
            } else {
                resultado[x] = "SEM PONTUAÇÃO";
            }
        }

        return resultado;
    }

    private static String formatador (String nome, int pontos) {
        /* Formatador
         * Recebe o nome e pontos e formata para caber de forma mais eficiente na tela
         * deixando o nome em maiúsculo, até 10 caracteres e adicionando "-"
         */
        nome = nome.toUpperCase();
        if (nome.length() > 10) {
            nome = nome.substring(0, 10) + " ";
        }
        nome = nome + "-".repeat((int) (50 + (10 - nome.length() * 2) - Math.log10(pontos)));
        nome += " " + pontos;

        return nome;
    }
}
