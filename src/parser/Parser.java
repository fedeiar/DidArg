package parser;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import org.sat4j.core.Vec;
import org.sat4j.core.VecInt;
import org.sat4j.specs.IVec;
import org.sat4j.specs.IVecInt;


public class Parser {

    private FileManager fileManager;
    private String contentOfFile;
    private boolean writeContentOfFile;
    private char currentCharacter;

    private IVec<IVecInt> attack_relations;
    private Map<String, Integer> arguments;
    private String currentArgumentName;
    private String firstArgument;
    private String secondArgument;
    private boolean parse_argument;
    private boolean parse_attack;

    public Parser(){

    }

    private void initializeParser(String fileName) throws IOException, ParserException{
        this.fileManager = new FileManager(fileName);
        currentArgumentName = ""; firstArgument = ""; secondArgument = ""; contentOfFile = "";
        arguments = new HashMap<String, Integer>();
        attack_relations = new Vec<IVecInt>();
    }

    private void updateCharacter() throws IOException{
        currentCharacter = fileManager.proximoCaracter();
        if(writeContentOfFile && !fileManager.esEOF(currentCharacter)){
            contentOfFile += currentCharacter;
        }
    }

    private void updateArgumentName() throws IOException{
        currentArgumentName = currentArgumentName + currentCharacter;
    }

    public Map<String, Integer> getArguments(){
        return arguments;
    }

    public String getContentOfFile(){
        return contentOfFile;
    }

    public IVec<IVecInt> ParseArgumentationFramework(String fileName) throws IOException, ParserException{
        initializeParser(fileName);

        parse_argument = true;
        parse_attack = false;
        writeContentOfFile = true;

        initial_state();

        parse_argument = false;
        parse_attack = true;
        writeContentOfFile = false;
        fileManager.restartFile();

        initial_state(); 

        return attack_relations;   
    }

    // Initial state when recognizing an argument or an attack.

    private void initial_state() throws IOException, ParserException{
        updateCharacter();

        if(Character.isWhitespace(currentCharacter)){
            initial_state();
        } else if (currentCharacter == 'a'){
            argument_name_a();
        } else if (currentCharacter == '#'){
            single_line_comment();
        } else if(fileManager.esEOF(currentCharacter)){
            return;
        } else{
            throw new ParserException("invalid argument definition", fileManager.nroLinea());
        }
    }

    // Recognizing an argument

    private void argument_name_a() throws IOException, ParserException{
        updateCharacter();

        if(currentCharacter == 'r'){
            argument_name_r();
        } else if(currentCharacter == 't'){
            attack_relation_t1();
        } else{
            throw new ParserException("invalid argument definition", fileManager.nroLinea());
        }
    }

    private void argument_name_r() throws IOException, ParserException{
        updateCharacter();

        if(currentCharacter == 'g'){
            argument_name_g();
        } else{
            throw new ParserException("invalid argument definition", fileManager.nroLinea());
        }
    }

    private void argument_name_g() throws IOException, ParserException{
        updateCharacter();

        if(currentCharacter == '('){
            argument_name_leftParenthesis();
        } else{
            throw new ParserException("invalid argument definition", fileManager.nroLinea());
        }
    }

    private void argument_name_leftParenthesis() throws IOException, ParserException{
        updateCharacter();

        if(Character.isLetter(currentCharacter) || Character.isDigit(currentCharacter) || currentCharacter == '_'){
            updateArgumentName();
            argument_name_identifier();
        } else{
            throw new ParserException("invalid argument definition", fileManager.nroLinea());
        }
    }

    private void argument_name_identifier() throws IOException, ParserException{
        updateCharacter();

        if(Character.isLetter(currentCharacter) || Character.isDigit(currentCharacter) || currentCharacter == '_'){
            updateArgumentName();
            argument_name_identifier();
        } else if(currentCharacter == ')'){
            argument_name_rightParenthesis();
        } else{
            throw new ParserException("invalid argument definition", fileManager.nroLinea());
        }
    }

    private void argument_name_rightParenthesis() throws IOException, ParserException{
        updateCharacter();

        if(currentCharacter == '.'){
            argument_name_dot();
        } else{
            throw new ParserException("invalid argument definition", fileManager.nroLinea());
        }
    }

    private void argument_name_dot() throws IOException, ParserException{
        if(parse_argument){
            if(arguments.get(currentArgumentName) == null){
                arguments.put(currentArgumentName, arguments.size() + 1);
            }
        }
        currentArgumentName = "";
        initial_state();
    }

    // Recognizing an attack relation.

    private void attack_relation_t1() throws IOException, ParserException{
        updateCharacter();

        if(currentCharacter == 't'){
            attack_relation_t2();
        } else{
            throw new ParserException("invalid attack relation definition", fileManager.nroLinea());
        }
    }

    private void attack_relation_t2() throws IOException, ParserException{
        updateCharacter();

        if(currentCharacter == '('){
            attack_relation_leftParenthesis();
        } else{
            throw new ParserException("invalid attack relation definition", fileManager.nroLinea());
        }
    }

    private void attack_relation_leftParenthesis() throws IOException, ParserException{
        updateCharacter();

        if(Character.isLetter(currentCharacter) || Character.isDigit(currentCharacter) || currentCharacter == '_'){
            updateArgumentName();
            attack_relation_firstParameter();
        } else{
            throw new ParserException("invalid attack relation definition", fileManager.nroLinea());
        }
    }

    private void attack_relation_firstParameter() throws IOException, ParserException{
        updateCharacter();

        if(Character.isLetter(currentCharacter) || Character.isDigit(currentCharacter) || currentCharacter == '_'){
            updateArgumentName();
            attack_relation_firstParameter();
        } else if(currentCharacter == ','){
            if(arguments.get(currentArgumentName) != null){
                firstArgument = currentArgumentName;
                currentArgumentName = "";
                attack_relation_comma();
            } else{
                
                throw new ParserException("arguments in attack relations must first be defined", fileManager.nroLinea());
            }
        }
        else {
            throw new ParserException("invalid attack relation definition", fileManager.nroLinea());
        }
    }

    private void attack_relation_comma() throws IOException, ParserException{
        updateCharacter();

        if(Character.isLetter(currentCharacter) || Character.isDigit(currentCharacter) || currentCharacter == '_'){
            updateArgumentName();
            attack_relation_secondParameter();
        } else{
            throw new ParserException("invalid attack relation definition", fileManager.nroLinea());
        }
    }

    private void attack_relation_secondParameter() throws IOException, ParserException{
        updateCharacter();

        if(Character.isLetter(currentCharacter) || Character.isDigit(currentCharacter) || currentCharacter == '_'){
            updateArgumentName();
            attack_relation_secondParameter();
        } else if(currentCharacter == ')'){
            if(arguments.get(currentArgumentName) != null){
                secondArgument = currentArgumentName;
                currentArgumentName = "";
                attack_relation_rightParenthesis();
            } else{
                throw new ParserException("arguments in attack relations must first be defined", fileManager.nroLinea());
            }
        }
        else {
            throw new ParserException("invalid attack relation definition", fileManager.nroLinea());
        }
    }

    private void attack_relation_rightParenthesis() throws IOException, ParserException{
        updateCharacter();

        if(currentCharacter == '.'){
            attack_relation_dot();
        } else{
            throw new ParserException("invalid attack relation definition", fileManager.nroLinea());
        }
    }

    private void attack_relation_dot() throws IOException, ParserException{
        if(parse_attack){
            int[] newAttack = {arguments.get(firstArgument), arguments.get(secondArgument)};
            boolean duplicate = checkForDuplicates(newAttack);
            if(!duplicate){
                attack_relations.push(new VecInt(newAttack));
            }
        } 
        firstArgument = "";
        secondArgument = "";
        initial_state();
    }
    private boolean checkForDuplicates(int[] newAttack){
        boolean duplicate = false;
        for(int i = 0; i < attack_relations.size(); i++){
            IVecInt attack = attack_relations.get(i);
            if(newAttack[0] == attack.get(0) && newAttack[1] == attack.get(1)){
                duplicate = true;
                break;
            }
        }
        return duplicate;
    }

    private void single_line_comment() throws IOException, ParserException{
        updateCharacter();

        if(currentCharacter == '\n'){
            initial_state();
        } else if (fileManager.esEOF(currentCharacter)){
            return;
        } else{
            single_line_comment();
        }
    }

}
