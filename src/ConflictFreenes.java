import java.io.IOException;
import java.util.Map;
import org.sat4j.core.Vec;
import org.sat4j.core.VecInt;
import org.sat4j.specs.IVec;
import org.sat4j.specs.IVecInt;

public class ConflictFreenes {
    
    private Parser parser;
    private IVec<IVecInt> attacks;
    private Map<Integer, String> arguments; 
    private IVec<IVecInt> clauses;
    private String latexFormula;

    //TODO: sacar una clase abstracta "Semantics" que tenga este constructor ya que será igual para cada una de las semanticas. Luego cada semantica hereda de Semantics.
    public ConflictFreenes(Parser parser) throws IOException, ParserException{
        this.parser = parser;
        attacks = parser.ParseArgumentationFramework();
        arguments = Utilities.exchangeKeyValue(parser.getArguments());
        clauses = new Vec<IVecInt>();
        latexFormula = "";
    }

    public Map<String, Integer> getArguments(){
        return parser.getArguments();
    }
    
    public IVec<IVecInt> calculateReduction() throws IOException, ParserException{
        
        for(int i = 0; i < attacks.size(); i++){
            IVecInt attack = attacks.get(i);
            int[] array = {attack.get(0) * -1, attack.get(1) * -1};
            clauses.push(new VecInt(array));

            //TODO: acomodar el último and.
            latexFormula += "(V_"+arguments.get(attack.get(0))+" \\rightarrow \\neg V_"+arguments.get(attack.get(1))+") \\land \\\\";
        }
        return clauses;
    }

    public String getLatexFormula(){
        return latexFormula;
    }

}
