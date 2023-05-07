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
    "<p>An argumentation framework (AF) is a directed graph (Ar, att), where the nodes in Ar are <b>arguments</b> and edges in att are <b>attacks</b> between the arguments in the framework.</p>" +
    "<p>Given any two arguments a, b that belongs to Ar, if (a, b) belongs to att we say that a attacks b<p>" + 
    "<p>The <b>semantics</b> (procedure for finding winning arguments) of an AF can be expressed by selecting a subset of the arguments, called an extension, that would be the accepted arguments. Those arguments that were not selected are rejected or undecided.</p>"+
    "<p>All semantics considered here are based on the concept of conflict-freenes.</p>"+
    "<hr><h2>Conflict-freenes and Defense of Arguments</h2>"+
        "<li> <b>Conflict-freenes</b>: Given an AF = (Ar, att), a set S that is a subset of Ar is <b>conflict-free</b> if there doesn't exist two arguments <em>a</em> and <em>b</em> in S such that <em>a</em> attacks <em>b</em> or <em>b</em> attacks <em>a</em> </li>"+
        "<li> <b>Defense of arguments</b>: Given an argument <em>a</em> in Ar and a set of arguments S that is a subset of Ar, we say that <em>a</em> is defended by S if for every argument <em>b</em> in Ar such that <em>b</em> attacks <em>a</em>, there exists an argument <em>c</em> in S such that <em>c</em> attacks <em>b</em>.</li> " +
    "<hr><h2>Semantics</h2>"+
    "<p>Given an AF = (Ar, att), then a conflict-free set S from Ar is a(n):</p>"+
    "<ul>"+
    "<li> <b>Admissible extension</b> if each argument belonging to S is defended by S.</li>"+
    "<li> <b>Complete extension</b> if S is an admissible extension and for every argument <em>a</em> in Ar defended by S, it holds that <em>a</em> belongs to S.</li>"+
    "<li> <b>Preferred extension</b> if S is a maximal admissible extension with respect to set inclusion.</li>"+
    "<li> <b>Stable extension</b> if each argument that doesn't belong to S is attacked by S.</li>"+
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
