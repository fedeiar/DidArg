package view;
import java.util.Set;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IVec;
import org.sat4j.specs.IVecInt;

import controller.ControllerMainWindow;
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
            structures.calculateAFDataStructures("C:\\Users\\fede\\Desktop\\cegartix-implementation\\af_examples\\af_2.txt");
            
            Semantics semantics = new Complete(structures); 
            Set<Set<String>> extensions = semantics.getExtensions();
            String latexFormula = semantics.getLatexFullFormula();
            
            System.out.println(extensions.toString());
            IVec<IVecInt> clauses = semantics.clauses;
            for(int i = 0; i < clauses.size(); i++){
                System.out.print("["+ clauses.get(i) +"]");
            }
            System.out.println(semantics.getLatexFormulaBody());

            // ESTO DE ARRIBA DESPUES SE BORRA

            ControllerMainWindow controllerMainWindow = new ControllerMainWindow();
            ViewMainWindow mainWindowView = new ViewMainWindow(controllerMainWindow);
            controllerMainWindow.setMainWindowView(mainWindowView);

            ViewAboutArgumentation viewAboutArgumentation = new ViewAboutArgumentation(controllerMainWindow);
            controllerMainWindow.setViewAboutArgumentation(viewAboutArgumentation);

            // ESTO DE ABAJO DESPUES SE BORRA

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