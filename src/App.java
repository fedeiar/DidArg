import java.util.Set;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IVec;
import org.sat4j.specs.IVecInt;

import extensions.ExtensionEnumerator;
import parser.AFDataStructures;
import parser.FileManager;
import parser.Parser;
import semantics.Admisibility;
import semantics.ConflictFreenes;
import semantics.Semantic;
import view.MainWindow;


public class App {
    public static void main(String[] args) throws Exception {

        try{
            Parser parser = new Parser(new FileManager("af_examples/af_1.txt"));
            AFDataStructures structures = new AFDataStructures(parser);
            
            Semantic semantic = new ConflictFreenes(structures); 
            IVec<IVecInt> clauses = semantic.calculateReduction();
            String latexFormula = semantic.getLatexFormula();
            
            ExtensionEnumerator extensionEnumerator = new ExtensionEnumerator(structures.argumentsByInteger, clauses);
            Set<Set<String>> extensions = extensionEnumerator.getExtensions();

            System.out.println(extensions);
            System.out.println(latexFormula);

            MainWindow view = new MainWindow();
            view.setVisible(true);
            view.setLatexLabel(latexFormula);

        } catch (ContradictionException e) {
            System.out.println("Unsatisfiable (trivial)!");
        } catch(Exception e){
            System.out.println(e.getMessage());
        }

        

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
        
    }


   
}