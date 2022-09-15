import java.util.Map;
import java.util.Set;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IVec;
import org.sat4j.specs.IVecInt;

import View.MainWindow;


public class App {
    public static void main(String[] args) throws Exception {

        try{
            Parser parser = new Parser(new FileManager("af_prueba.txt"));
            ConflictFreenes conflictFreenes = new ConflictFreenes(parser); 
            IVec<IVecInt> clauses = conflictFreenes.calculateReduction();

            Map<String, Integer> arguments = conflictFreenes.getArguments();

            
            
            ExtensionEnumerator extensionEnumerator = new ExtensionEnumerator(arguments, clauses);
            Set<Set<String>> extensions = extensionEnumerator.getExtensions();

            System.out.println(extensions);

        } catch (ContradictionException e) {
            System.out.println("Unsatisfiable (trivial)!");
        } catch(Exception e){
            System.out.println(e.getMessage());
        }

        
        MainWindow view = new MainWindow();
        view.setVisible(true);
        view.setLatexLabel("(a \\lor b) \\rightarrow c");

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