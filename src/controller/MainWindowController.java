package controller;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.plaf.FileChooserUI;

import parser.AFDataStructures;
import parser.FileManager;
import parser.Parser;
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

    public void calculateConflictFreenes(){
        //TODO
    }

    public void calculateAdmisibility(){
        //TODO
    }

}
