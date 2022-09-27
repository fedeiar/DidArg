package parser;
import java.io.IOException;
import java.util.Map;

import org.sat4j.specs.IVec;
import org.sat4j.specs.IVecInt;

import utils.Utilities;

public class AFDataStructures{

    public IVec<IVecInt> attacks;
    public Map<String, Integer> argumentsByString; 
    public Map<Integer, String> argumentsByInteger;

    public AFDataStructures(Parser parser) throws IOException, ParserException{
        attacks = parser.ParseArgumentationFramework();
        argumentsByString = parser.getArguments();
        argumentsByInteger = Utilities.exchangeKeyValue(argumentsByString);
    }
}