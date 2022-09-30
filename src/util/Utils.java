package util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Utils {
    

    public static Map<Integer, String> exchangeKeyValue(Map<String, Integer> argumentsByString){
        Map<Integer, String> arguments = new HashMap<Integer, String>();
        for(Entry<String, Integer> argument : argumentsByString.entrySet()){
            arguments.put(argument.getValue(), argument.getKey());
        }

        return arguments;
    }

    public static String removeLastOperatorFromLatexFormula(String latexFormula, int longitudeToRemove){
        return latexFormula.substring(0, latexFormula.length() - longitudeToRemove);
    }
}
