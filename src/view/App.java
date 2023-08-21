package view;

import javax.swing.UIManager;
import presenter.PresenterMainWindow;

public class App {
    public static void main(String[] args) throws Exception {

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

        ViewAboutAuthor viewAboutAuthor = new ViewAboutAuthor(presenterMainWindow);
        presenterMainWindow.setViewAboutAuthor(viewAboutAuthor);

        mainWindowView.setVisible(true);
    }

}