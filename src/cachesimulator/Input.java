package cachesimulator;

import java.util.Scanner;

public class Input {

    private String politicaSb, arquivoEntrada;
    private Integer nsets, bsize, assoc, flagSaida;

    // metodo para pegar a entrada do usuario atraves de uma String //  
    public void inputCode() {
        Scanner input = new Scanner(System.in);
        splitCode(input.nextLine());

    }

// metodo que separa subString dentro da String de entrada //    
    private void splitCode(String inputCode) {
        String[] arraySplit = inputCode.split(" ");//Arraya de Vetores para a entrada de valores Ex: cache_simulator 256 4 1 R 1 bin_100.bin

        if (arraySplit.length == 7) { //Pega cada valor de acordo com usa posição no vetor String de entrada
            this.nsets = Integer.parseInt(arraySplit[1]);
            this.bsize = Integer.parseInt(arraySplit[2]);
            this.assoc = Integer.parseInt(arraySplit[3]);
            this.politicaSb = arraySplit[4];
            this.flagSaida = Integer.parseInt(arraySplit[5]);
            this.arquivoEntrada = arraySplit[6];
            
        } else {
            System.out.println("Error: > Entrada de parametros invalida");
        }
    }

    public Integer getNsets() {
        return nsets;
    }

    public Integer getBsize() {
        return bsize;
    }

    public Integer getAssoc() {
        return assoc;
    }

    public String getPoliticaSb() {
        return politicaSb;
    }

    public Integer getFlagSaida() {
        return flagSaida;
    }

    public String getArquivoEntrada() {
        return arquivoEntrada;
    }

}