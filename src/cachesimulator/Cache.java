package cachesimulator;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//  cache_simulator 256 4 1 R 1 bin_100.bin  //
public class Cache {

    private List<List<String>> cache;
    private String arquivoEntrada;
    private Integer bSize;
    private Integer nSets;
    private Integer assoc;
    private int missCompulsorio;
    private int missConflito;
    private int missCapacidade;
    private int hit;

    public Cache(Input input) {
        this.arquivoEntrada = input.getArquivoEntrada();
        Integer nLinhas = input.getNsets() / input.getAssoc();
        this.bSize = input.getBsize();
        this.nSets = input.getNsets();
        this.cache = new ArrayList();
        this.assoc = input.getAssoc() - 1;             //começa em -1 pois o array começa com 0 
        for (int i = 0; i < input.getAssoc(); i++) {
            this.cache.add(Arrays.asList(new String[nLinhas]));
        }
        this.hit = 0;
        this.missCapacidade = 0;
        this.missConflito = 0;
        this.missConflito = 0;
    }

    public void codeCache() {
        lerEndereço();
    }

    //pega endereço .bin transforma byte>int>StringBinary
    private void lerEndereço() {
        try {
            FileInputStream arquivo = new FileInputStream(new File(this.arquivoEntrada));
            DataInputStream arquivoOut = new DataInputStream(arquivo);
            while (true) {
                String nextLine = Integer.toBinaryString(arquivoOut.readInt());
                //System.out.println(nextLine);
                formataBinario(nextLine);

            }
        } catch (IOException ex) {
        }
    }

    private void formataBinario(String binario) {
        int tamanhoAux = 32 - binario.length();
        String aux = "0";
        aux = aux.repeat(tamanhoAux);
        binario = aux + binario; // concatena 0 a esquerda do endereço
        //System.out.println(binario);
        separaBinario(binario);
    }

    private void separaBinario(String binario) {
        Integer nBitsOffset = log2(bSize);
        Integer nBitsIndice = log2(nSets);
        Integer nBitsTag = 32 - nBitsIndice - nBitsOffset;
        String offset = binario.substring(32 - nBitsOffset);
        String indice = binario.substring(nBitsTag, 32 - nBitsOffset);
        String tag = binario.substring(0, nBitsTag);

        //System.out.println("tag=" + tag);
        System.out.println("indice=" + indice);
        addCache(indice, tag);
        //System.out.println("offset=" + offset);
        System.out.println("\n");
    }

    private void addCache(String indice, String tag) {
        int endereco = Integer.parseInt(indice, 2);
        //System.out.println("endereço em decimal => " + endereco);
        //System.out.println("qtd de assoc da cache aqui" + cache.size());

        for (int i = this.assoc; i <= 0; i--) {
            if (cache.get(i).get(endereco) == null) {
                cache.get(i).set(endereco, tag);
                //this.missCompulsorio++;

                this.missCompulsorio++;

            } else if (cache.get(i).get(endereco).equals(tag)) {
                this.hit++;

            } else {
                for (i = this.assoc; i <= 0; i--) {
                    for (int j = this.nSets; j <= 0; j--) {
                        if (cache.get(i).get(j) == null) {
                            this.missConflito++;
                            break;
                        } else {
                            this.missCapacidade++;
                        }

                    }
                }

                cache.get(random()).set(endereco, tag);
            }

        }
        resultado();
    }

    private void resultado() {
        System.out.println("qtd de hit: " + this.hit);
        System.out.println("qtd de compulsorio: " + this.missCompulsorio);
        System.out.println("qtd de conflito: " + this.missConflito);
        System.out.println("qtd de capacidade: " + this.missCapacidade);
       
    }

    //metodo para calcular log2 //
    private int log2(Integer number) {

        int resultado = (int) (Math.log(number) / Math.log(2));
        return resultado;
    }

    //gera um numero aleatorio //
    private int random() {
        return (int) ((Math.random() * (this.assoc - 0)) + 0);
    }

    public String getArquivoEntrada() {
        return arquivoEntrada;
    }

    public void setArquivoEntrada(String arquivoEntrada) {
        this.arquivoEntrada = arquivoEntrada;
    }

}
