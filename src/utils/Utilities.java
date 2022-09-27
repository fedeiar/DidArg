package utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Utilities {
    

    public static Map<Integer, String> exchangeKeyValue(Map<String, Integer> argumentsByString){
        Map<Integer, String> arguments = new HashMap<Integer, String>();
        for(Entry<String, Integer> argument : argumentsByString.entrySet()){
            arguments.put(argument.getValue(), argument.getKey());
        }

        return arguments;
    }
}
