package View;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainWindow extends javax.swing.JFrame {

    private JLabel lblTitle;
    private JLabel lblLatexFormula;
    private JPanel mainPanel;

    private Latex latex;


    public MainWindow() {
        latex = new Latex();
        initComponents();
    }

    private void initComponents() {
		this.setBounds(100, 100, 751, 371);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);

        mainPanel = new JPanel();
		mainPanel.setBounds(0, 0, 735, 332);
		mainPanel.setLayout(null);
        this.getContentPane().add(mainPanel);
		
		lblLatexFormula = new JLabel("");
		lblLatexFormula.setBounds(64, 186, 618, 50);
		mainPanel.add(lblLatexFormula);
		
		lblTitle = new JLabel("Boolean Formula Example");
		lblTitle.setBounds(137, 11, 204, 22);
		mainPanel.add(lblTitle);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 18));
    }

    public void setLatexLabel(String latexString){
        lblLatexFormula.setIcon(latex.actualizarIconLaTex(latexString, 20));
    }
}