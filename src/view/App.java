package view;
import java.util.Set;
import org.sat4j.specs.ContradictionException;

import controller.MainWindowController;
import parser.AFDataStructures;
import semantics.Admissibility;
import semantics.Complete;
import semantics.ConflictFreenes;
import semantics.Preferred;
import semantics.Semantics;


public class App {
    public static void main(String[] args) throws Exception {

        try{
            AFDataStructures structures = new AFDataStructures();
            structures.calculateAFDataStructures("C:\\Users\\fede\\Desktop\\cegartix-implementation\\af_examples\\af_1.txt");
            
            Semantics semantics = new Complete(structures); 
            Set<Set<String>> extensions = semantics.getExtensions();
            String latexFormula = semantics.getLatexFullFormula();
            
            System.out.println(extensions.toString());
            System.out.println(semantics.getLatexFormulaBody());

            MainWindowController mainWindowController = new MainWindowController();
            MainWindow mainWindowView = new MainWindow(mainWindowController);
            mainWindowController.setMainWindowView(mainWindowView);

            mainWindowView.setLatexLabel(latexFormula);
            mainWindowView.setTAExtensionsText(extensions.toString());
            mainWindowView.setTAExplanationText(semantics.getExplanation());
            mainWindowView.setVisible(true);

        } catch (ContradictionException e) {
            System.out.println("Unsatisfiable (trivial)!");
        } catch(Exception e){
            System.out.println(e.getMessage());
        }

    }


   
}