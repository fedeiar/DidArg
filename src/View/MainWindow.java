package view;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import controller.MainWindowController;

public class MainWindow extends javax.swing.JFrame {

    private MainWindowController mainWindowController;

    private JLabel lblLatexFormula, lblTitleBooleanFormula, lblTitleArgumentationFramework, lblTitleSelectSemantics, lblTitleExtensions, lblTitleExplanation;
    private JPanel mainPanel;
    private JScrollPane spLatexFormula, spFile, spExtensions, spExplanation;
    private JTextArea taArgumentationFramework, taExtensions, taExplanation;
    private JButton btnFile, btnCalculateExtensions;
    private JComboBox<String> cbSelectSemantics;

    private Latex latex;

    private final int LATEX_FORMULA_SIZE = 20;


    public MainWindow(MainWindowController mainWindowController){
        latex = new Latex();
        this.mainWindowController = mainWindowController;
        initGUIComponents();
        initListeners();
    }

    private void initGUIComponents() {
		this.setBounds(200, 150, 1400, 646);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
        this.setResizable(false);

        mainPanel = new JPanel();
		mainPanel.setLayout(null);
        this.setContentPane(mainPanel);

        lblTitleArgumentationFramework = new JLabel("Argumentation Framework");
		lblTitleArgumentationFramework.setHorizontalAlignment(SwingConstants.LEFT);
		lblTitleArgumentationFramework.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTitleArgumentationFramework.setBounds(191, 11, 219, 35);
		mainPanel.add(lblTitleArgumentationFramework);

        taArgumentationFramework = new JTextArea();
        taArgumentationFramework.setEditable(false);
        taArgumentationFramework.setFont(new Font("Calibri", Font.PLAIN, 16));
        spFile = new JScrollPane(taArgumentationFramework);
		spFile.setBounds(191, 57, 301, 237);
		mainPanel.add(spFile);

        btnFile = new JButton("Select File");
		btnFile.setBounds(24, 76, 100, 23);
		mainPanel.add(btnFile);

        lblTitleSelectSemantics = new JLabel("Select Semantics");
		lblTitleSelectSemantics.setHorizontalAlignment(SwingConstants.LEFT);
		lblTitleSelectSemantics.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTitleSelectSemantics.setBounds(24, 422, 181, 35);
		mainPanel.add(lblTitleSelectSemantics);

        btnCalculateExtensions = new JButton("Calculate Extensions");
		btnCalculateExtensions.setBounds(24, 551, 165, 23);
		mainPanel.add(btnCalculateExtensions);

        cbSelectSemantics = new JComboBox<String>();
		cbSelectSemantics.setBounds(24, 482, 165, 22);
        fillComboBox();
		mainPanel.add(cbSelectSemantics);

        lblTitleBooleanFormula = new JLabel("Boolean Formula");
		lblTitleBooleanFormula.setHorizontalAlignment(SwingConstants.LEFT);
		lblTitleBooleanFormula.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTitleBooleanFormula.setBounds(575, 11, 153, 35);
		mainPanel.add(lblTitleBooleanFormula);

        lblLatexFormula = new JLabel("");
        spLatexFormula = new JScrollPane(lblLatexFormula);
		spLatexFormula.setBounds(575, 57, 350, 359);
		mainPanel.add(spLatexFormula);

        lblTitleExtensions = new JLabel("Extensions");
		lblTitleExtensions.setHorizontalAlignment(SwingConstants.LEFT);
		lblTitleExtensions.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTitleExtensions.setBounds(575, 422, 153, 35);
		mainPanel.add(lblTitleExtensions);

        taExtensions = new JTextArea();
        taExtensions.setEditable(false);
        taExtensions.setLineWrap(true);
        taExtensions.setWrapStyleWord(true);
        taExtensions.setFont(new Font("Calibri", Font.PLAIN, 16));
        spExtensions = new JScrollPane(taExtensions);
		spExtensions.setBounds(575, 468, 350, 95);
		mainPanel.add(spExtensions);

        lblTitleExplanation = new JLabel("Explanation");
		lblTitleExplanation.setHorizontalAlignment(SwingConstants.LEFT);
		lblTitleExplanation.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTitleExplanation.setBounds(950, 11, 153, 35);
		mainPanel.add(lblTitleExplanation);

        taExplanation = new JTextArea();
        taExplanation.setEditable(false);
        taExplanation.setLineWrap(true);
        taExplanation.setWrapStyleWord(true);
        taExplanation.setFont(new Font("Calibri", Font.PLAIN, 16));
        spExplanation = new JScrollPane(taExplanation);
		spExplanation.setBounds(950, 57, 350, 359);
		mainPanel.add(spExplanation);
    }

    private void initListeners(){
        btnFile.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent actionEvent){
                mainWindowController.loadArgumentationFramework();
            }
        });

        btnCalculateExtensions.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent actionEvent){
                mainWindowController.calculateExtensions(cbSelectSemantics.getSelectedItem().toString());
            }
        });

    }

    private void fillComboBox(){
        cbSelectSemantics.addItem("Conflict Freenes");
        cbSelectSemantics.addItem("Admissibility");
        cbSelectSemantics.addItem("Preferred");
    }

    public void setLatexLabel(String latexString){
        lblLatexFormula.setIcon(latex.actualizarIconLaTex(latexString, LATEX_FORMULA_SIZE));
    }

    public void setTAFileText(String AFText){
        taArgumentationFramework.setText(AFText);
    }

    public void setTAExtensionsText(Set<Set<String>> extensions){
        taExtensions.setText(extensions.toString());
    }

    public void setTAExplanationText(String explanation){
        taExplanation.setText(explanation);
    }
}