package view;
import java.awt.*;
import javax.swing.*;

import presenter.PresenterMainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ViewAboutArgumentation extends javax.swing.JFrame{
    
    private PresenterMainWindow presenterMainWindow;

    private JPanel mainPanel, panelBottom;
    private JEditorPane taAboutArgumentation;
    private JScrollPane spAboutArgumentation;
    private JButton btnClose;

    private final String aboutArgumentation = "<h1>Argumentation Frameworks and Extensions</h1>" +
    "<p>An argumentation framework (AF) is a directed graph, where nodes are <b>arguments</b> and edges are <b>attacks</b> between the arguments.</p>" +
    "<p>The <b>semantics</b> (procedure for finding winning arguments) of an AF can be expressed by selecting a subset of the arguments, called an extension, that would be the accepted arguments. Those arguments that were not selected are unaccepted or undecided.</p>"+
    "<p>All semantics are based on the concept of conflict-freenes.</p>"+
    "<hr><h2>Conflict-free sets</h2>"+
    "<p> A subset S from the arguments of an AF is conflict-free if there doesn't exists two arguments <em>a</em> and <em>b</em> in S such that <em>a</em> attacks <em>b</em> or <em>a</em> attacks <em>a</em> </p>"+
    "<hr><h2>Semantics</h2>"+
    "<p>Given an AF = (Ar, att), then a conflict-free set S from AF is a(an):</p>"+
    "<ul>"+
    "<li> <b>Admissible extension</b> if each argument belonging to S is defended by S.</li>"+
    "<li> <b>Complete extension</b> if S is an admissible extension and for every argument <em>a</em> from Ar defended by S, it holds that <em>a</em> belongs to S.</li>"+
    "<li> <b>Preferred extension</b> if S is a maximal admissible extension with respect to set inclusion.</li>"+
    "</ul>";

    public ViewAboutArgumentation(PresenterMainWindow presenterMainWindow){
        this.presenterMainWindow = presenterMainWindow;

        initGUIComponents();
        initListeners();
    }

    private void initGUIComponents(){
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

        /* panelCenter = new JPanel();
        panelCenter.setLayout(new FlowLayout());
        mainPanel.add(panelCenter, BorderLayout.CENTER); */

        taAboutArgumentation = new JEditorPane("text/html", "");
        taAboutArgumentation.setEditable(false);
        //taAboutArgumentation.setLineWrap(true);
        //taAboutArgumentation.setWrapStyleWord(true);
        taAboutArgumentation.setFont(new Font("Calibry", Font.PLAIN, 17));
        taAboutArgumentation.setText(aboutArgumentation);
        spAboutArgumentation = new JScrollPane(taAboutArgumentation);
        spAboutArgumentation.setSize(new Dimension(800, 400));
        mainPanel.add(spAboutArgumentation, BorderLayout.CENTER);

        btnClose = new JButton("Close");
        panelBottom.add(btnClose);
    }

    private void initListeners(){
        btnClose.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent actionEvent){
                presenterMainWindow.closeAboutArgumentationWindow();
            }
        });
    }
}
