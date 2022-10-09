package view;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import controller.MainWindowController;

public class MainWindow extends javax.swing.JFrame {

    private MainWindowController mainWindowController;

    private JLabel lblLatexFormula, lblTitleFormula, lblTitleFile, lblTitleSelectSemantics, lblTitleExtensions;
    private JPanel mainPanel;
    private JScrollPane SPFormula, SPFile, SPExtensions;
    private JTextArea TAFile, TAExtensions;
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
		this.setBounds(100, 100, 1145, 646);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
        //this.setResizable(false);

        mainPanel = new JPanel();
		mainPanel.setLayout(null);
        this.setContentPane(mainPanel);

        lblTitleFormula = new JLabel("Boolean Formula");
		lblTitleFormula.setHorizontalAlignment(SwingConstants.LEFT);
		lblTitleFormula.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTitleFormula.setBounds(749, 11, 153, 35);
		mainPanel.add(lblTitleFormula);

        lblLatexFormula = new JLabel("");

        SPFormula = new JScrollPane(lblLatexFormula);
		SPFormula.setBounds(749, 57, 333, 359);
		mainPanel.add(SPFormula);

        lblTitleFile = new JLabel("Argumentation Framework");
		lblTitleFile.setHorizontalAlignment(SwingConstants.LEFT);
		lblTitleFile.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTitleFile.setBounds(191, 11, 219, 35);
		mainPanel.add(lblTitleFile);

        TAFile = new JTextArea();
        TAFile.setEditable(false);
        SPFile = new JScrollPane(TAFile);
		SPFile.setBounds(191, 50, 301, 237);
		mainPanel.add(SPFile);

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

        lblTitleExtensions = new JLabel("Extensions");
		lblTitleExtensions.setHorizontalAlignment(SwingConstants.LEFT);
		lblTitleExtensions.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTitleExtensions.setBounds(749, 422, 153, 35);
		mainPanel.add(lblTitleExtensions);

        TAExtensions = new JTextArea();
        TAExtensions.setEditable(false);
        SPExtensions = new JScrollPane(TAExtensions);
		SPExtensions.setBounds(749, 468, 353, 95);
		mainPanel.add(SPExtensions);
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
        cbSelectSemantics.addItem("Admisibility");
    }

    public void setLatexLabel(String latexString){
        lblLatexFormula.setIcon(latex.actualizarIconLaTex(latexString, LATEX_FORMULA_SIZE));
    }

    public void setTAFileText(String AFText){
        TAFile.setText(AFText);
    }

    public void setTAExtensionsText(Set<Set<String>> extensions){
        TAExtensions.setText(extensions.toString());
    }
}