package parser;
import java.io.IOException;
import java.util.Map;

import org.sat4j.specs.IVec;
import org.sat4j.specs.IVecInt;

import util.Utils;

public class AFDataStructures{

    private Parser parser;

    public IVec<IVecInt> attacks;
    public Map<String, Integer> argumentsByString; 
    public Map<Integer, String> argumentsByInteger;
    public String contentOfFile;

    public AFDataStructures(){
        this.parser = new Parser();
    }

    public void calculateAFDataStructures(String fileName) throws IOException, ParserException{
        attacks = parser.ParseArgumentationFramework(fileName);
        argumentsByString = parser.getArguments();
        argumentsByInteger = Utils.exchangeKeyValue(argumentsByString);
        contentOfFile = parser.getContentOfFile();
    }
}