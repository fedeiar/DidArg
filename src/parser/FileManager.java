package parser;
import java.io.*;


public class FileManager {

    private final char EOF = '\u001a';

    private BufferedReader reader;
    private String fileName;
    private boolean abierto;

    private String lineaActual;
    private String lineaAnterior;
    private int char_pos;

    private int nroLineaActual;
    private boolean salteDeLinea;

    public FileManager(String file) throws FileNotFoundException, IOException{
        fileName = file;
        initializeReader();
    }

    private void initializeReader() throws FileNotFoundException, IOException{
        char_pos = 0;
        nroLineaActual = 1;
        salteDeLinea = false;
        
        reader = new BufferedReader(new FileReader(fileName));
        abierto = true;
        lineaActual = reader.readLine();
        lineaAnterior = null;
    }

    public int nroLinea(){
        if (salteDeLinea){
            return nroLineaActual - 1;
        }else{
            return nroLineaActual;
        }
    }

    public int nroColumna(){
        return char_pos + 1;
    }

    public String lineaCaracterAnterior(){
        if(salteDeLinea){
            return lineaAnterior;
        }
        return lineaActual;
    }

    public char proximoCaracter() throws IOException{
        char character = EOF;
        salteDeLinea = false;
        
        if(lineaActual != null){
            if(char_pos < lineaActual.length()){
                character = lineaActual.charAt(char_pos);
                char_pos++;
            }
            else{
                lineaAnterior = lineaActual;
                lineaActual = reader.readLine();
                salteDeLinea = true;
                nroLineaActual++;
                if(lineaActual != null){
                    char_pos = 0;
                    character = '\n';
                }
            }
        }

        if(abierto && esEOF(character)){
            abierto = false;
            reader.close();
        }
        
        return character;
    }

    public boolean esEOF(char c){
        return c == EOF;
    }

    public void restartFile() throws FileNotFoundException, IOException{
        initializeReader();
    }
}
