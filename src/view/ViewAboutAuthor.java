package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import presenter.PresenterMainWindow;

public class ViewAboutAuthor extends ViewGenericAbout {

    private final static String aboutAuthor = "<h1>Author(s) and References</h1>" +
    "<p>This tool was done by <b>Federico Iarlori</b>. The github repository for this project can be found at <a href=https://github.com/fedeiar/DidArg>https://github.com/fedeiar/DidArg</a></p>";

    public ViewAboutAuthor(PresenterMainWindow presenterMainWindow) {
        super(presenterMainWindow, aboutAuthor);
    }

    protected void initListeners(){
        btnClose.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent actionEvent){
                presenterMainWindow.closeAboutAuthorWindow();
            }
        });
    }

}
