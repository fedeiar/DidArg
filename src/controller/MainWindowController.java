package controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.plaf.FileChooserUI;

import org.sat4j.specs.IVec;
import org.sat4j.specs.IVecInt;

import extensions.ExtensionEnumerator;
import parser.AFDataStructures;
import parser.FileManager;
import parser.Parser;
import semantics.Admissibility;
import semantics.Complete;
import semantics.ConflictFreenes;
import semantics.Preferred;
import semantics.Semantics;
import view.MainWindow;

public class MainWindowController {
    
    private MainWindow mainWindowView;
    private AFDataStructures structures;

    private JFileChooser fileChooser;

    private Map<String, String> semanticsClassNames;

    public MainWindowController(){
        structures = new AFDataStructures();
        fileChooser = new JFileChooser();

        semanticsClassNames = new HashMap<String, String>();
        semanticsClassNames.put("Conflict Freenes", "ConflictFreenes");
        semanticsClassNames.put("Admisibility", "Admissibility");
    }

    public void setMainWindowView(MainWindow mainWindowView){
        this.mainWindowView = mainWindowView;
    }

    public void loadArgumentationFramework(){
        int returnValue = fileChooser.showOpenDialog(mainWindowView);

        if(returnValue == JFileChooser.APPROVE_OPTION){
            try{
                File file = fileChooser.getSelectedFile();
                String path = file.getAbsolutePath();
                structures.calculateAFDataStructures(path);
                mainWindowView.setTAFileText(structures.contentOfFile);
            } catch(Exception e){
                //TODO: lanzar alguna alerta.
                e.printStackTrace();
            }
            
        }
    }

    public void calculateExtensions(String semanticsString){
        Semantics semantics = null;
        // TODO: https://stackoverflow.com/questions/12485912/java-create-an-object-based-on-content-of-a-string
     
        if(semanticsString.equals("Conflict Freenes")){
            semantics = new ConflictFreenes(structures);
        } else if(semanticsString.equals("Admissibility")){
            semantics = new Admissibility(structures);
        } else if(semanticsString.equals("Complete")){
            semantics = new Complete(structures);
        } else if(semanticsString.equals("Preferred")){
            semantics = new Preferred(structures);
        }

        try{
            Set<Set<String>> extensions = semantics.getExtensions();
            String text_extensions = extensions.toString().replace("[", "{").replace("]", "}");
            String latexFormula = semantics.getLatexFullFormula();
            String explanation = semantics.getExplanation();

            mainWindowView.setTAExtensionsText(text_extensions);
            mainWindowView.setLatexLabel(latexFormula);
            mainWindowView.setTAExplanationText(explanation);
        } catch(Exception e){
            // TODO: analizar que tipo de error puede surgir y tirar una alerta adecuada
            e.printStackTrace();
        }
        
    }



}
