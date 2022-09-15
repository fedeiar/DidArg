import java.io.IOException;
import java.util.Map;
import org.sat4j.core.Vec;
import org.sat4j.core.VecInt;
import org.sat4j.specs.IVec;
import org.sat4j.specs.IVecInt;

public class ConflictFreenes {
    
    private Parser parser;
    IVec<IVecInt> attacks;
    private IVec<IVecInt> clauses;

    public ConflictFreenes(Parser parser) throws IOException, ParserException{
        this.parser = parser;
        attacks = parser.ParseArgumentationFramework();
        clauses = new Vec<IVecInt>();
    }

    public Map<String, Integer> getArguments(){
        return parser.getArguments();
    }
    

    public IVec<IVecInt> calculateReduction() throws IOException, ParserException{
        
        for(int i = 0; i < attacks.size(); i++){
            IVecInt attack = attacks.get(i);
            int[] array = {attack.get(0) * -1, attack.get(1) * -1};
            clauses.push(new VecInt(array));
        }
        return clauses;
    }

}
