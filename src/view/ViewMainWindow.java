package view;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.ControllerMainWindow;

public class ViewMainWindow extends javax.swing.JFrame{

    private ControllerMainWindow controllerMainWindow;

    private JLabel lblLatexFormula, lblTitleBooleanFormula, lblTitleArgumentationFramework, lblTitleExtensions, lblTitleExplanation;
    private JPanel mainPanel, panelLeft, panelCenter, panelRight, panelBottom;
    private JScrollPane spLatexFormula, spFile, spExtensions, spExplanation;
    private JTextArea taArgumentationFramework, taExtensions, taExplanation;
    private JButton btnFile, btnCalculateExtensions, btnAboutArgumentation;
    private JComboBox<String> cbSelectSemantics;

    private Latex latex;

    private final int LATEX_FORMULA_SIZE = 20;


    public ViewMainWindow(ControllerMainWindow controllerMainWindow){
        latex = new Latex();
        this.controllerMainWindow = controllerMainWindow;
        initGUIComponents();
        initListeners();
    }

    private void initGUIComponents() {
        this.setTitle("Didactic Argumentation");
        this.setSize(1400, 646);
        this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);

        
        mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
        this.setContentPane(mainPanel);

        panelLeft = new JPanel();
        panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.PAGE_AXIS));
        mainPanel.add(panelLeft, BorderLayout.LINE_START);

        panelCenter = new JPanel();
        panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.PAGE_AXIS));
        mainPanel.add(panelCenter, BorderLayout.CENTER);

        panelRight = new JPanel();
        panelRight.setLayout(new BoxLayout(panelRight, BoxLayout.PAGE_AXIS));
        mainPanel.add(panelRight, BorderLayout.LINE_END);

        panelBottom = new JPanel();
        panelBottom.setLayout(new FlowLayout());
        mainPanel.add(panelBottom, BorderLayout.PAGE_END);

        lblTitleArgumentationFramework = new JLabel("Argumentation Framework");
		lblTitleArgumentationFramework.setFont(new Font("Century", Font.PLAIN, 18));
        lblTitleArgumentationFramework.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        lblTitleArgumentationFramework.setPreferredSize(new Dimension(240, 35));
		panelLeft.add(lblTitleArgumentationFramework);

        taArgumentationFramework = new JTextArea();
        taArgumentationFramework.setEditable(false);
        taArgumentationFramework.setFont(new Font("Calibri", Font.PLAIN, 16));
        spFile = new JScrollPane(taArgumentationFramework);
        spFile.setPreferredSize(new Dimension(200, 500));
		panelLeft.add(spFile);

        btnAboutArgumentation = new JButton("About Argumentation");
		panelBottom.add(btnAboutArgumentation);

        btnFile = new JButton("Select File");
        btnFile.setToolTipText("Select a file containing an argumentation framework.");
		panelBottom.add(btnFile);

        cbSelectSemantics = new JComboBox<String>();
        cbSelectSemantics.setToolTipText("Choose the semantics of which you want to enumerate extensions");
        fillComboBox();
		panelBottom.add(cbSelectSemantics);

        btnCalculateExtensions = new JButton("Calculate Extensions");
        btnCalculateExtensions.setEnabled(false);
        btnCalculateExtensions.setToolTipText("Calculate the extensions for the selected semantics");
		panelBottom.add(btnCalculateExtensions);

        lblTitleBooleanFormula = new JLabel("Boolean Formula");
		lblTitleBooleanFormula.setFont(new Font("Century", Font.PLAIN, 20));
        lblTitleBooleanFormula.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        lblTitleBooleanFormula.setPreferredSize(new Dimension(153,35));
		panelCenter.add(lblTitleBooleanFormula);

        lblLatexFormula = new JLabel("");
        spLatexFormula = new JScrollPane(lblLatexFormula);
        spLatexFormula.setPreferredSize(new Dimension(400, 400));
		panelCenter.add(spLatexFormula);

        lblTitleExtensions = new JLabel("Extensions");
		lblTitleExtensions.setFont(new Font("Century", Font.PLAIN, 20));
        lblTitleExtensions.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        lblTitleExtensions.setPreferredSize(new Dimension(153, 35));
		panelRight.add(lblTitleExtensions);

        taExtensions = new JTextArea();
        taExtensions.setEditable(false);
        taExtensions.setLineWrap(true);
        taExtensions.setWrapStyleWord(true);
        taExtensions.setFont(new Font("Calibri", Font.PLAIN, 16));
        spExtensions = new JScrollPane(taExtensions);
        spExtensions.setPreferredSize(new Dimension(200, 95));
		panelRight.add(spExtensions);

        lblTitleExplanation = new JLabel("Explanation");
		lblTitleExplanation.setFont(new Font("Century", Font.PLAIN, 20));
        lblTitleExplanation.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        lblTitleExplanation.setPreferredSize(new Dimension(153, 35));
		panelRight.add(lblTitleExplanation);

        taExplanation = new JTextArea();
        taExplanation.setEditable(false);
        taExplanation.setLineWrap(true);
        taExplanation.setWrapStyleWord(true);
        taExplanation.setFont(new Font("Calibri", Font.PLAIN, 16));
        spExplanation = new JScrollPane(taExplanation);;
        spExplanation.setPreferredSize(new Dimension(250, 200));
		panelRight.add(spExplanation);
    }

    private void initListeners(){
        btnFile.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent actionEvent){
                controllerMainWindow.loadArgumentationFramework();
            }
        });

        btnCalculateExtensions.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent actionEvent){
                controllerMainWindow.calculateExtensions(cbSelectSemantics.getSelectedItem().toString());
            }
        });

        btnAboutArgumentation.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent actionevent){
                controllerMainWindow.openAboutArgumentationWindow();
            }
        });

    }

    private void fillComboBox(){
        cbSelectSemantics.addItem("Conflict Freenes");
        cbSelectSemantics.addItem("Admissibility");
        cbSelectSemantics.addItem("Complete");
        cbSelectSemantics.addItem("Preferred");
    }

    public void setLatexLabel(String latexString){
        lblLatexFormula.setIcon(latex.actualizarIconLaTex(latexString, LATEX_FORMULA_SIZE));
    }

    public void setTAFileText(String AFText){
        taArgumentationFramework.setText(AFText);
    }

    public void setTAExtensionsText(String extensions){
        taExtensions.setText(extensions);
    }

    public void setTAExplanationText(String explanation){
        taExplanation.setText(explanation);
    }

    public void enableExtensionsButton(){
        btnCalculateExtensions.setEnabled(true);
    }

    public void disableAllButtons(){
        btnAboutArgumentation.setEnabled(false);
        btnFile.setEnabled(false);
        cbSelectSemantics.setEnabled(false);
        btnCalculateExtensions.setEnabled(false);
    }

    public void recoverButtons(boolean isFileLoaded){
        btnAboutArgumentation.setEnabled(true);
        btnFile.setEnabled(true);
        cbSelectSemantics.setEnabled(true);
        if(isFileLoaded){
            btnCalculateExtensions.setEnabled(true);
        }
    }

    public void showErrorDialog(String message){
        JOptionPane.showMessageDialog(this, message+" Correct the error and upload the file again", "Syntax error in file", JOptionPane.ERROR_MESSAGE);
    }
}