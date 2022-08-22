import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import org.sat4j.core.VecInt;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.reader.DimacsReader;
import org.sat4j.reader.ParseFormatException;
import org.sat4j.reader.Reader;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;

public class App {
    public static void main(String[] args) throws Exception {
        ISolver solver =  SolverFactory.newDefault();
        solver.setTimeout(3600);

        int amountOfVariables = 3;
    
        solver.newVar(amountOfVariables);
        solver.setExpectedNumberOfClauses(100);
        int[][] clauses = {{1,2}, {1, -2}, {-1, -2}};
        
        try{
            for(int[] clause : clauses){
                solver.addClause(new VecInt(clause));
            }
            
            IProblem problem = solver;
            if(problem.isSatisfiable()){
                
                System.out.println("satisfiable!");
                int[] model = problem.model();
                for(int i = 1; i < model.length + 1; i++){
                    System.out.println("var "+i+" = "+problem.model(i));
                }
                
            } else{
                System.out.println("unsatisfiable!");
            }
        } catch (ContradictionException e) {
            System.out.println("Unsatisfiable (trivial)!");
        }
    }
}



// USA UN ARCHIVO DE TEXTO EN FORMATO DIMACS
/*

ISolver solver =  SolverFactory.newDefault();
        solver.setTimeout(3600);
        Reader reader = new DimacsReader(solver);
        PrintWriter out = new PrintWriter(System.out, true);
        try{
            IProblem problem = reader.parseInstance("cnf.txt");
            if(problem.isSatisfiable()){
                System.out.println("Satisfiable!");
                reader.decode(problem.model(), out);
            } else{
                System.out.println("Unsatisfiable!");
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
        } catch (ParseFormatException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        } catch (ContradictionException e) {
            System.out.println("Unsatisfiable (trivial)!");
        } catch (TimeoutException e) {
            System.out.println("Timeout, sorry!");      
        } 
        
*/