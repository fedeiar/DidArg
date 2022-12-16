package parser;
public class ParserException extends Exception{
    

    public ParserException(String message, int lineNumber){
        super(buildMessage(message, lineNumber));
    }

    private static String buildMessage(String message, int lineNumber){
        return "Error at line "+lineNumber+": "+message+".";
    }
}
