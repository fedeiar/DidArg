package view;
import java.awt.*;
import javax.swing.*;

import presenter.PresenterMainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public abstract class ViewGenericAbout extends javax.swing.JFrame{
    
    protected PresenterMainWindow presenterMainWindow;

    protected JPanel mainPanel, panelBottom;
    protected JEditorPane taAbout;
    protected JScrollPane spAboutArgumentation;
    protected JButton btnClose;


    public ViewGenericAbout(PresenterMainWindow presenterMainWindow, String about){
        this.presenterMainWindow = presenterMainWindow;

        initGUIComponents(about);
        initListeners();
    }

    protected void initGUIComponents(String about){
        this.setTitle("About Argumentation");
        this.setSize(1000, 500);
        this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setDefaultCloseOperation(0);

        mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
        this.setContentPane(mainPanel);

        panelBottom = new JPanel();
        panelBottom.setLayout(new FlowLayout());
        mainPanel.add(panelBottom, BorderLayout.PAGE_END);

        taAbout = new JEditorPane("text/html", "");
        taAbout.setEditable(false);
        taAbout.setFont(new Font("Calibry", Font.PLAIN, 17));
        taAbout.setText(about);
        spAboutArgumentation = new JScrollPane(taAbout);
        spAboutArgumentation.setSize(new Dimension(800, 400));
        mainPanel.add(spAboutArgumentation, BorderLayout.CENTER);

        btnClose = new JButton("Close");
        panelBottom.add(btnClose);
    }

    protected abstract void initListeners();
}
