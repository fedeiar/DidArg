import java.util.Collection;

import org.sat4j.minisat.SolverFactory;

import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.IVec;
import org.sat4j.specs.IVecInt;
import org.sat4j.tools.ModelIterator;

public class App {
    public static void main(String[] args) throws Exception {

        try{
            ConflictFreenes conflictFreenes = new ConflictFreenes(new FileManager("af_prueba.txt"));
            IVec<IVecInt> clauses = conflictFreenes.calculateReduction();
            Collection<Integer> arguments = conflictFreenes.arguments.values();

            // PRINT ARGUMENTS
            /* for(String argument : conflictFreenes.arguments.keySet()){
                System.out.println(argument +" - "+ conflictFreenes.arguments.get(argument));
            } */

            // PRINT CLAUSES
           /*  System.out.println("CLAUSES:"); 
            for(int i = 0 ; i < clauses.size() ; i++){
                IVecInt clause = clauses.get(i);
                for(int j = 0 ; j < clause.size() ; j++){
                    System.out.print(clause.get(j)+" ");
                }
                System.out.println();
            } */
            
            
            ISolver solver =  new ModelIterator(SolverFactory.newDefault());
            solver.setTimeout(3600);
        
            solver.newVar(arguments.size());
            solver.setExpectedNumberOfClauses(clauses.size());

            solver.addAllClauses(clauses);
            
            IProblem problem = solver;
            boolean unsat = true;
            while(solver.isSatisfiable()){
                unsat = false;
                int[] model = problem.model();
                for(int i = 1; i < model.length + 1; i++){
                    System.out.print("var "+i+" = "+problem.model(i)+" - ");
                }
                System.out.println("");
            }
                
            if(unsat){
                System.out.println("unsatisfiable!");
            }

            
        } catch (ContradictionException e) {
            System.out.println("Unsatisfiable (trivial)!");
        } catch(Exception e){
            System.out.println(e.getMessage());
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