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

    public Cache(Input input) {
        this.arquivoEntrada = input.getArquivoEntrada();
        Integer nLinhas = input.getNsets() / input.getAssoc();
        this.bSize = input.getBsize();
        this.nSets = input.getNsets();
        this.cache = new ArrayList();
        for (int i = 0; i < input.getAssoc(); i++) {
            this.cache.add(Arrays.asList(new String[nLinhas]));
        }
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
                System.out.println(nextLine);
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
        System.out.println(binario);
        separaBinario(binario);
    }

    private void separaBinario(String binario) {
        Integer nBitsOffset = log2(bSize);
        Integer nBitsIndice = log2(nSets);
        Integer nBitsTag = 32 - nBitsIndice - nBitsOffset;
        String offset = binario.substring(32 - nBitsOffset);
        String indice = binario.substring(nBitsTag, 32 - nBitsOffset);
        String tag = binario.substring(0, nBitsTag);

        System.out.println("tag=" + tag);
        System.out.println("indice=" + indice);
        System.out.println("offset=" + offset);
        System.out.println("\n");
    }

    //metodo para calcular log2 //
    private int log2(Integer number) {

        int resultado = (int) (Math.log(number) / Math.log(2));
        return resultado;
    }

    public String getArquivoEntrada() {
        return arquivoEntrada;
    }

    public void setArquivoEntrada(String arquivoEntrada) {
        this.arquivoEntrada = arquivoEntrada;
    }

}
