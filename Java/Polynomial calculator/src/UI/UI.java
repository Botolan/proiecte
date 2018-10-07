package UI;

import input.ProcessInput;
import objects.Polynom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UI {
    public static void userInterface(){
        JFrame frame = new JFrame("Calculator de polinoame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,150);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);


        JPanel mainPanel = new JPanel();
        JPanel polynomInputPanel = new JPanel();
        JPanel operationPanel = new JPanel();
        JPanel resultPanel = new JPanel();


        //panel pentru introducerea polinoamelor
        JTextField inputFirstPolynom = new JTextField();
        inputFirstPolynom.setPreferredSize( new Dimension(150,20));
        JTextField inputSecondPolynom = new JTextField();
        inputSecondPolynom.setPreferredSize( new Dimension(150,20));

        JLabel firstPolyonm = new JLabel("Primul polinom");
        JLabel secondPolyonm = new JLabel("Al doilea polinom");


        polynomInputPanel.setLayout(new FlowLayout());
        polynomInputPanel.add(firstPolyonm);
        polynomInputPanel.add(inputFirstPolynom);
        polynomInputPanel.add(secondPolyonm);
        polynomInputPanel.add(inputSecondPolynom);


        operationPanel.setLayout(new FlowLayout());



        //panel pentru rezultat
        JLabel operationResult = new JLabel();
        JLabel operationResultText = new JLabel("Rezultat");
        operationResultText.setHorizontalAlignment(JLabel.CENTER);
        operationResult.setHorizontalAlignment(JLabel.CENTER);

        resultPanel.setLayout(new BorderLayout());
        resultPanel.add(operationResultText, BorderLayout.PAGE_START);
        resultPanel.add(operationResult, BorderLayout.CENTER);


        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(polynomInputPanel);
        mainPanel.add(resultPanel);
        mainPanel.add(operationPanel);


        //panel pentru operatii
        JButton addition = new JButton("Adunare");
        addition.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Polynom p = ProcessInput.processInput(inputFirstPolynom.getText());
                Polynom q = ProcessInput.processInput(inputSecondPolynom.getText());
                if((p == null)||(q == null)) {
                    operationResult.setText("Date invalide");
                }
                else {
                    p.addition(q);
                    operationResult.setText(p.toString());
                }
            }
        });
        JButton substraction = new JButton("Scadere");
        substraction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Polynom p = ProcessInput.processInput(inputFirstPolynom.getText());
                Polynom q = ProcessInput.processInput(inputSecondPolynom.getText());
                if((p == null)||(q == null)) {
                    operationResult.setText("Date invalide");
                }
                else {
                    p.substraction(q);
                    operationResult.setText(p.toString());
                }
            }
        });
        JButton multiplication = new JButton("Inmultire");
        multiplication.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Polynom p = ProcessInput.processInput(inputFirstPolynom.getText());
                Polynom q = ProcessInput.processInput(inputSecondPolynom.getText());
                if((p == null)||(q == null)) {
                    operationResult.setText("Date invalide");
                }
                else {
                    p.multiply(q);
                    operationResult.setText(p.toString());
                }
            }
        });
        JButton integrate = new JButton("Integrare");
        integrate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Polynom p = ProcessInput.processInput(inputFirstPolynom.getText());
                if(p == null) {
                    operationResult.setText("Date invalide");
                }
                else {
                    p.integrate();
                    operationResult.setText(p.toString());
                }
            }
        });
        JButton derivate = new JButton("Derivare");
        derivate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Polynom p = ProcessInput.processInput(inputFirstPolynom.getText());
                if(p == null) {
                    operationResult.setText("Date invalide");
                }
                else {
                    p.derivate();
                    operationResult.setText(p.toString());
                }
            }
        });



        operationPanel.add(addition);
        operationPanel.add(substraction);
        operationPanel.add(multiplication);
        operationPanel.add(integrate);
        operationPanel.add(derivate);


        frame.setContentPane(mainPanel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        userInterface();
    }
}
