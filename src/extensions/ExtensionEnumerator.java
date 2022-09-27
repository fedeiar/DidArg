package extensions;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.sat4j.specs.TimeoutException;
import org.sat4j.tools.ModelIterator;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.IVec;
import org.sat4j.specs.IVecInt;

public class ExtensionEnumerator {

    private Map<Integer, String> arguments;
    private IVec<IVecInt> clauses;

    public ExtensionEnumerator(Map<Integer, String> argumentsByInteger, IVec<IVecInt> clauses){
        arguments = argumentsByInteger;
        this.clauses = clauses;
    }

    public Set<Set<String>> getExtensions() throws TimeoutException, ContradictionException{
        ISolver solver = new ModelIterator(SolverFactory.newDefault());
        solver.setTimeout(3600);
        solver.newVar(arguments.size());
        solver.setExpectedNumberOfClauses(clauses.size());
        solver.addAllClauses(clauses);
        IProblem problem = solver;

        Set<Set<String>> setsOfExtensions = new HashSet<Set<String>>();

        while(solver.isSatisfiable()){
            
            int[] model = problem.model();
            Set<String> extension = new HashSet<String>();
            for(int i = 1; i < model.length + 1; i++){
                if(problem.model(i)){
                    extension.add(arguments.get(i));
                }
            }
            setsOfExtensions.add(extension);
        }
        
        return setsOfExtensions;
    }

}
