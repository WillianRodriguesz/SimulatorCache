
package cachesimulator;
/**
 * @author willian
 */
public class cache_simulator {
    public static void main(String[] args) {
        Input input = new Input();
        
        System.out.println("cache_simulator  <nsets>  <bsize>  <assoc>  <substituição>   <flag_saida>  arquivo_de_entrada");
        input.inputCode();
        
        System.out.println("nset: " + input.getNsets());
        System.out.println("bsize: " + input.getBsize());
        System.out.println("assc: " + input.getAssoc());
        System.out.println("Substituicao: " + input.getPoliticaSb());
        System.out.println("flag: " + input.getFlagSaida());
        System.out.println("arquivo: " + input.getArquivoEntrada());
        
    }
    
}
