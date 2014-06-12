

package FilaBanco;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 *
 * @author Stein
 */
public class ArquivoEntrada {
    private double tempoAtendimento;
    private double tempoEspera;
    private double probabilidadeDeChegada;
    
    public ArquivoEntrada(){
        tempoAtendimento = 10.0;
        tempoEspera = 10.0;
        probabilidadeDeChegada = 0.1;
    }
    
    public boolean leArquivo() {
        
        Path p = Paths.get("arquivo.txt");        
        
        try ( Scanner s = new Scanner (Files.newBufferedReader(p,Charset.defaultCharset())) ) {
            s.useDelimiter(",");
            while(s.hasNextDouble()) {
                tempoAtendimento = s.nextDouble();
                tempoEspera = s.nextDouble();  
                probabilidadeDeChegada = s.nextDouble();
            }
            
        } catch(IOException e) {
            return false; // se deu problema retorna false
        }         
        return true;
    }
    
    public double getTempoAtendimento(){
        return tempoAtendimento;
    }
    public double getTempoEspera(){
        return tempoEspera;
    }
    public double getProbabilidadeDeChegada(){
        return probabilidadeDeChegada;
    }    
    
}
