package view;

import controller.controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class gui extends JFrame {

    private JPanel mainPanel;
    private JTextField textField1;
    private JButton clickMeButton;
    private  JLabel usr;
    private controller controller;

    public gui() {
        setContentPane(mainPanel);
        setTitle("Simple GUI App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);

        controller = new controller();

        clickMeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String firstName = textField1.getText();
                String result = controller.buttonClick(firstName);
                System.out.println(result);
                JOptionPane.showMessageDialog(gui.this, result);
                chatpage anotherInstance = new chatpage(firstName);
            }
        });
    }

    public String getUser() {
        return textField1.getText();
    }
}
