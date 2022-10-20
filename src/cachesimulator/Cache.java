
package cachesimulator;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

public class Cache {
    private Integer nLinhas, nColunas;
    
    public void initCache(Integer nset, Integer assoc, String arquivoEntrada){
       
       lerArquivo(arquivoEntrada);
       
       this.nLinhas = nset/assoc;
       this.nColunas = 3*assoc;
       
       int cache[][] = new int [this.nLinhas][this.nColunas];
         
       for (int i=0; i < nLinhas; i++){
           for(int j=0; j < nColunas; j++){
               cache[i][j] = -1;
           }
       }
       /*
        for (int[]row:cache){
            System.out.println(Arrays.toString(row));
        }*/
       
       }   
        private void lerArquivo(String arquivoEntrada){
            try {            
            FileInputStream arquivo = new FileInputStream(new File(arquivoEntrada));
            DataInputStream arquivoOut = new DataInputStream(arquivo);
            
            int x;
            while(arquivo != null){
                try{
                    x = arquivoOut.readInt();
                    System.out.println(x);
                }catch(EOFException eofe){
                    break;
                }
            }

            arquivo.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
 
    
    
    
}
