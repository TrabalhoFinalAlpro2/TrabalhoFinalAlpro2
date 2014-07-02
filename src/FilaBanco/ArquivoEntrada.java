

package FilaBanco;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Scanner;


public class ArquivoEntrada {
    private int array[];
    String textoEntrada;
    
    public ArquivoEntrada() {
        textoEntrada = ("");
    }
    
     
    public Boolean leArquivo(String textoEntrada) {
        // Primeiro cria o Path
        Path p = Paths.get(textoEntrada);
        
        // Depois "abre o stream" para o arquivo
        // e usa um Scanner para facilitar a 
        // leitura do arquivo. Usar try-com-recursos.
        try ( Scanner s = new Scanner (Files.newBufferedReader(p,Charset.defaultCharset())) ) {
            s.useDelimiter(",");
            int i=0;
            while(s.hasNext()) {
                String str = s.next();
                int num = Integer.parseInt(str);
                array[i] = num;
                i++;
            }
            
        } catch(IOException e) {
            return false; // se deu problema retorna false
        }         
        return true;
    }
    

    @Override
    public String toString() {
        String s = "";
        
        for (int i=0; i<array.length-1; i++) {
            s += array[i] + ", ";
        }
        s += array[array.length-1];
        
        return s;         
    }
    
    
}