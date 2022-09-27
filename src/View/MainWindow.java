package view;
import java.awt.*;
import javax.swing.*;

public class MainWindow extends javax.swing.JFrame {

    private JLabel lblTitle;
    private JLabel lblLatexFormula;
    private JPanel mainPanel;
    private JScrollPane scrollPane;

    private Latex latex;

    private final int LATEX_FORMULA_SIZE = 20;


    public MainWindow() {
        latex = new Latex();
        initComponents();
    }

    private void initComponents() {
		this.setBounds(100, 100, 751, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);

        mainPanel = new JPanel();
		mainPanel.setBounds(0, 0, 751, 600);
		mainPanel.setLayout(null);
        this.getContentPane().add(mainPanel);

        lblTitle = new JLabel("Boolean Formula Example");
		lblTitle.setBounds(137, 11, 458, 22);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 18));
        mainPanel.add(lblTitle);

        lblLatexFormula = new JLabel("");

        scrollPane = new JScrollPane(lblLatexFormula);
		scrollPane.setBounds(10, 247, 338, 133);
		mainPanel.add(scrollPane);
    }

    public void setLatexLabel(String latexString){
        lblLatexFormula.setIcon(latex.actualizarIconLaTex(latexString, LATEX_FORMULA_SIZE));
    }
}