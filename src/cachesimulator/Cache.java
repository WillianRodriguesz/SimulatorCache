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
    private float totalAcessos;

    public Cache(Input input) {
        //Cada variável recebe seus respectivos valores de acordo com a entrada
        this.arquivoEntrada = input.getArquivoEntrada();// arquivo entrada recebe o arquivo .bin
        Integer nLinhas = input.getNsets(); // input.getAssoc();
        this.bSize = input.getBsize();
        this.nSets = input.getNsets();
        this.cache = new ArrayList();
        this.assoc = input.getAssoc();
        this.flagSaida = input.getFlagSaida();
        for (int i = 0; i < input.getAssoc(); i++) {
            this.cache.add(Arrays.asList(new String[nLinhas]));//Adiciona os valores da cache em uma matriz;
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
        int flag = 0;
        for (int i = 0; i < this.assoc; i++) {
            if (cache.get(i).get(endereco) == null) { // veririfca se o bit val = 0 ou seja verifica se este local da cache não está vazio
                this.missCompulsorio++;
                cache.get(i).set(endereco, tag);//adiciona o número neste local
                break;
            } else if (cache.get(i).get(endereco).equals(tag)) {//verficia se o tag é igual, se for é hit
                this.hit++;
                break;
            } else { //verifica se é miss conflito ou capacidade
                if (verificaCache() == false) {
                    this.missConflito++;
                    flag = 1;
                } else {
                    this.missCapacidade++;
                    flag = 1;
                }
            }
            if (flag == 1) {
                cache.get(random()).set(endereco, tag); //adiciona de forma random o tag na cache
            }
        }
    }

    public boolean verificaCache() {    //função que verifica se é miss compusório ou de capacidade, verificando se a cache está vazia ou não
        for (int f = this.assoc - 1; f >= 0; f--) {
            for (int j = this.nSets - 1; j >= 0; j--) {
                if (cache.get(f).get(j) == null) {
                    return false; // cache n esta cheia
                }
            }
        }
        return true; // cache cheia
    }

    public void resultado() {//Printa o resultado de acordo com a flag de saida: 0 ou 1

        if (this.flagSaida == 1) {
            float missesTotal = this.missCapacidade + this.missCompulsorio + this.missConflito;
            System.out.println(this.totalAcessos + " " + this.hit / this.totalAcessos + " " + missesTotal / this.totalAcessos + " " + this.missCompulsorio / missesTotal + " " + this.missCapacidade / missesTotal + " " + this.missConflito / missesTotal);
        } else {
            System.out.println("Taxa de hits: " + this.hit / this.totalAcessos);
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
