package cachesimulator;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
cache_simulator 256 4 1 R 1 bin_100.bin
cache_simulator 128 2 4 R 1 bin_1000.bin
cache_simulator 256 1 2 R 1 bin_10000.bin
cache_simulator 512 8 2 R 1 vortex.in.sem.persons.bin
 */
public class Cache {

    //Declaração de todas as variáveis para o funcionamento da Cache;
    private List<List<String>> cache;
    private String arquivoEntrada;
    private Integer bSize;
    private Integer nSets;
    private Integer assoc;
    private float missCompulsorio;
    private float missConflito;
    private float missCapacidade;
    private float hit;
    private int flagSaida; //Flag para saber qual tipo de saída o usuário quer 1 ou 0;
    private int totalAcessos;

    public Cache(Input input) {
        //Cada variável recebe seus respectivos valores de acordo com a entrada
        this.arquivoEntrada = input.getArquivoEntrada();// arquivo entrada recebe o arquivo .bin
        Integer nLinhas = input.getNsets(); // input.getAssoc();
        this.bSize = input.getBsize();
        this.nSets = input.getNsets();
        this.cache = new ArrayList();
        this.assoc = input.getAssoc();
        this.flagSaida = input.getFlagSaida();
        for (int i = 0; i < nLinhas; i++) {
            this.cache.add(Arrays.asList(new String[input.getAssoc()]));//Adiciona os valores da cache em uma matriz;
        }
    }

    public void codeCache() {
        lerEndereço();//Le todos os endereços da Matriz de valores da cache
    }

    //pega endereço .bin transforma byte>int>StringBinary
    private void lerEndereço() {
        try {
            FileInputStream arquivo = new FileInputStream(new File(this.arquivoEntrada));
            DataInputStream arquivoOut = new DataInputStream(arquivo);
            while (true) {
                String nextLine = Integer.toBinaryString(arquivoOut.readInt());//Transforma os números binarios em Ints e depois Strings;
                this.totalAcessos++; //Para cada passada no while significa que ele passou para o próximo número ou seja aumenta +1 no total de acessos;
                formataBinario(nextLine);//Ativa a função que pega e transforma o binario do arquivo .bin em 32 bits, ou seja adiciona zeros a esquerda até ficar com 32;

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

    private void separaBinario(String binario) {//Faz os calculos para achar o Offset, indice e o tag do endereço específico
        Integer nBitsOffset = log2(bSize);
        Integer nBitsIndice = log2(nSets);
        Integer nBitsTag = 32 - nBitsIndice - nBitsOffset;
        String offset = binario.substring(32 - nBitsOffset);
        String indice = binario.substring(nBitsTag, 32 - nBitsOffset);
        String tag = binario.substring(0, nBitsTag);
        addCache(indice, tag);//Função que adiciona o número a cache
    }

    private void addCache(String indice, String tag) {
        int endereco = Integer.parseInt(indice, 2) % this.nSets;
        //ImprimirCacheTESTE();

        for (int i = 0; i < this.assoc; i++) {

            if (cache.get(endereco).get(i) == null) { // veririfca se o bit val = 0 ou seja verifica se este local da cache não está vazio
                this.missCompulsorio++;
                cache.get(endereco).set(i, tag);//adiciona o número neste local
                break;
            }
            if (cache.get(endereco).get(i).equals(tag)) {//verficia se o tag é igual, se for é hit
                this.hit++;
                break;
            }
            if (i == this.assoc - 1) {
                if (verificaCacheCheia()) {
                    this.missCapacidade++;
                } else {
                    this.missConflito++;
                }
                cache.get(endereco).set(random(), tag);
            }

        }

    }

    public boolean verificaCacheCheia() {    //função que verifica se é miss compusório ou de capacidade, verificando se a cache está vazia ou não
        for (int f = 0; f < this.nSets; f++) {
            for (int j = 0; j < this.assoc; j++) {
                if (cache.get(f).get(j) == null) {
                    return false; // cache n esta cheia
                }
            }
        }
        return true; // cache cheia
    }

    public void resultado() {//Printa o resultado de acordo com a flag de saida: 0 ou 1
        float missesTotal = this.missCapacidade + this.missCompulsorio + this.missConflito;
        if (this.flagSaida == 1) {
            //System.out.println("<numero Acesso> <taxa hit> <taxa miss> <taxaCompulsorio> <taxa Miss Capacidade> <Miss Conflito>");
            System.out.println(this.totalAcessos + " " + String.format("%.2f", this.hit / this.totalAcessos) + " "
                    + String.format("%.2f", missesTotal / this.totalAcessos) + " " + String.format("%.2f", this.missCompulsorio / missesTotal) + " " + String.format("%.2f", this.missCapacidade / missesTotal) + " " + String.format("%.2f", this.missConflito / missesTotal));

        } else {
            System.out.println("Taxa de hits: " + String.format("%.2f", this.hit / this.totalAcessos));
        }

    }

    //metodo para calcular log2 //
    private int log2(Integer number) {//Faz um log de base 2 para qualquer valor

        int resultado = (int) (Math.log(number) / Math.log(2));
        return resultado;
    }

    //gera um numero aleatorio //
    private int random() {//Randomiza um valor para qualquer faixa de valores
        return (int) ((Math.random() * (this.assoc - 0)) + 0);
    }

    public String getArquivoEntrada() {
        return arquivoEntrada;
    }

    public void setArquivoEntrada(String arquivoEntrada) {
        this.arquivoEntrada = arquivoEntrada;
    }

}
