package view;
import java.util.Set;

import javax.swing.UIManager;

import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IVec;
import org.sat4j.specs.IVecInt;

import parser.AFDataStructures;
import presenter.PresenterMainWindow;
import semantics.Admissibility;
import semantics.Complete;
import semantics.ConflictFreenes;
import semantics.Preferred;
import semantics.Semantics;

public class App {
    public static void main(String[] args) throws Exception {
/* 
        try{
            AFDataStructures structures = new AFDataStructures();
            structures.calculateAFDataStructures("C:\\Users\\fede\\Desktop\\DidArg\\af_examples\\af_2.txt");
            
            Semantics semantics = new Complete(structures); 
            Set<Set<String>> extensions = semantics.getExtensions();
            String latexFormula = semantics.getLatexFullFormula();
            
            System.out.println(extensions.toString());
            IVec<IVecInt> clauses = semantics.clauses;
            for(int i = 0; i < clauses.size(); i++){
                System.out.print("["+ clauses.get(i) +"]");
            }
            System.out.println(semantics.getLatexFormulaBody()); */

            // ESTO DE ARRIBA DESPUES SE BORRA

            try {
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            } catch (Exception e) {
                e.printStackTrace();
            } 

            PresenterMainWindow presenterMainWindow = new PresenterMainWindow();
            ViewMainWindow mainWindowView = new ViewMainWindow(presenterMainWindow);
            presenterMainWindow.setMainWindowView(mainWindowView);

            ViewAboutArgumentation viewAboutArgumentation = new ViewAboutArgumentation(presenterMainWindow);
            presenterMainWindow.setViewAboutArgumentation(viewAboutArgumentation);

            mainWindowView.setVisible(true);

            // ESTO DE ABAJO DESPUES SE BORRA
/* 
            mainWindowView.setLatexLabel(latexFormula);
            mainWindowView.setTAExtensionsText(extensions.toString());
            mainWindowView.setTAExplanationText(semantics.getExplanation());

        } catch (ContradictionException e) {
            System.out.println("Unsatisfiable (trivial)!");
        } catch(Exception e){
            System.out.println(e.getMessage());
        } */

    }


   
}