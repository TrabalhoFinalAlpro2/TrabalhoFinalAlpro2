

package FilaBanco;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
 
public class ArquivoEntrada {
 
    private static Properties properties;
 
    static{
 
        try {
 
            FileInputStream fis = null;
            File file = new File("Arquivo.properties");
 
            if(file.exists()){
                properties = new Properties();
                fis = new FileInputStream(file);
                properties.load(fis);
                fis.close();
            }
 
        }catch(Exception x){
            x.printStackTrace();
        }
    }
 
    public static String lerProperties(String atributo){
 
        String retorno = null;
 
        try {
 
            FileInputStream fis = null;
            File file = new File("Arquivo.properties");
 
            if(properties != null){
                retorno = properties.getProperty(atributo);
            }
 
        }catch(Exception x){
            x.printStackTrace();
        }
 
        return retorno;
    }
}