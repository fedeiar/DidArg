package view;
import java.awt.*;
import javax.swing.*;

import controller.ControllerMainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;


public class ViewAboutArgumentation extends javax.swing.JFrame{
    
    private ControllerMainWindow controllerMainWindow;

    private JPanel mainPanel, panelBottom, panelCenter;
    private JTextArea taAboutArgumentation;
    private JScrollPane spAboutArgumentation;
    private JButton btnClose;

    public ViewAboutArgumentation(ControllerMainWindow controllerMainWindow){
        this.controllerMainWindow = controllerMainWindow;

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

        //TODO: ver de usar un textArea
        taAboutArgumentation = new JTextArea();
        taAboutArgumentation.setEditable(false);
        taAboutArgumentation.setLineWrap(true);
        taAboutArgumentation.setWrapStyleWord(true);
        taAboutArgumentation.setFont(new Font("Calibri", Font.PLAIN, 16));
        taAboutArgumentation.setText("a ver q pasaa ver q pasaa ver q pasaa ver q pasaa ver q pasaa ver q pasaa ver q pasaa ver q pasaa ver q pasaa ver q pasaa ver q pasaa ver q pasaa ver q pasaa ver q pasaa ver q pasaa ver q pasa");
        spAboutArgumentation = new JScrollPane(taAboutArgumentation);
        spAboutArgumentation.setSize(new Dimension(800, 400));
        mainPanel.add(spAboutArgumentation, BorderLayout.CENTER);

        btnClose = new JButton("Close");
        panelBottom.add(btnClose);
    }

    private void initListeners(){
        btnClose.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent actionEvent){
                controllerMainWindow.closeAboutArgumentationWindow();
            }
        });
    }
}
