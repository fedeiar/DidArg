package controller;

import java.io.File;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.plaf.FileChooserUI;

import org.sat4j.specs.IVec;
import org.sat4j.specs.IVecInt;

import extensions.ExtensionEnumerator;
import parser.AFDataStructures;
import parser.FileManager;
import parser.Parser;
import semantics.Admisibility;
import semantics.ConflictFreenes;
import semantics.Semantics;
import view.MainWindow;

public class MainWindowController {
    
    private MainWindow mainWindowView;
    private AFDataStructures structures;

    private JFileChooser fileChooser;

    public MainWindowController(){
        structures = new AFDataStructures();
        fileChooser = new JFileChooser();
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
        // TODO: estos if anidados, hay alguna forma de evitarlos? ya que cada vez que agregue una semantica, tendría que venir a modificar este metodo.
        if(semanticsString.equals("Conflict Freenes")){
            semantics = new ConflictFreenes(structures);
        } else if(semanticsString.equals("Admisibility")){
            semantics = new Admisibility(structures);
        }
        
        IVec<IVecInt> clauses = semantics.calculateReduction();
        String latexFormula = semantics.getLatexFormula();

        ExtensionEnumerator extensionEnumerator = new ExtensionEnumerator(structures.argumentsByInteger, clauses);
        try{
            Set<Set<String>> extensions = extensionEnumerator.getExtensions();
            mainWindowView.setTAExtensionsText(extensions);
            mainWindowView.setLatexLabel(latexFormula);
        } catch(Exception e){
            // TODO: analizar que tipo de error puede surgir y tirar una alerta adecuada
            e.printStackTrace();
        }
        
    }



}
