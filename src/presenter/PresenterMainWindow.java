package presenter;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import javax.swing.JFileChooser;

import parser.AFDataStructures;
import parser.ParserException;
import semantics.Admissibility;
import semantics.Complete;
import semantics.ConflictFreenes;
import semantics.Preferred;
import semantics.Semantics;
import semantics.Stable;
import view.ViewAboutArgumentation;
import view.ViewAboutAuthor;
import view.ViewGenericAbout;
import view.ViewMainWindow;

public class PresenterMainWindow {

    private ViewMainWindow viewMainWindow;
    private ViewAboutArgumentation viewAboutArgumentation;
    private ViewAboutAuthor viewAboutAuthor;
    private AFDataStructures structures;

    private JFileChooser fileChooser;
    private boolean isFileLoaded;

    public PresenterMainWindow() {
        structures = new AFDataStructures();
        fileChooser = new JFileChooser();
        isFileLoaded = false;
    }

    public void setMainWindowView(ViewMainWindow mainWindowView) {
        this.viewMainWindow = mainWindowView;
    }

    public void setViewAboutArgumentation(ViewAboutArgumentation viewAboutArgumentation) {
        this.viewAboutArgumentation = viewAboutArgumentation;
    }

    public void setViewAboutAuthor(ViewAboutAuthor viewAboutAuthor) {
        this.viewAboutAuthor = viewAboutAuthor;
    }

    public void loadArgumentationFramework() {
        int returnValue = fileChooser.showOpenDialog(viewMainWindow);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                String path = file.getAbsolutePath();
                isFileLoaded = true;
                structures.calculateAFDataStructures(path);
                viewMainWindow.setTAFileText(structures.contentOfFile);
                viewMainWindow.enableExtensionsButton();
            } catch (ParserException e) {
                viewMainWindow.showErrorDialog(e.getMessage());
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void calculateExtensions(String semanticsString) {
        Semantics semantics = null;

        if (semanticsString.equals("Conflict Freenes")) {
            semantics = new ConflictFreenes(structures);
        } else if (semanticsString.equals("Admissibility")) {
            semantics = new Admissibility(structures);
        } else if (semanticsString.equals("Complete")) {
            semantics = new Complete(structures);
        } else if (semanticsString.equals("Preferred")) {
            semantics = new Preferred(structures);
        } else if (semanticsString.equals("Stable")) {
            semantics = new Stable(structures);
        }

        try {
            Set<Set<String>> extensions = semantics.getExtensions();
            String text_extensions = extensions.toString().replace("[", "{").replace("]", "}");
            String latexFormula = semantics.getLatexFullFormula();
            String explanation = semantics.getExplanation();

            viewMainWindow.setTAExtensionsText(text_extensions);
            viewMainWindow.setLatexLabel(latexFormula);
            viewMainWindow.setTAExplanationText(explanation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openAboutArgumentationWindow() {
        viewAboutArgumentation.setVisible(true);
        viewMainWindow.disableAllButtons();
    }

    public void closeAboutArgumentationWindow() {
        viewAboutArgumentation.setVisible(false);
        viewMainWindow.recoverButtons(isFileLoaded);
    }

    public void openAboutAuthorWindow() {
        viewAboutAuthor.setVisible(true);
        viewMainWindow.disableAllButtons();
    }

    public void closeAboutAuthorWindow() {
        viewAboutAuthor.setVisible(false);
        viewMainWindow.recoverButtons(isFileLoaded);
    }

}
