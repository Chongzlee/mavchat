package view;

import controller.controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class chatpage extends JFrame {
    private JTextField entryText;
    private JButton enterConnect;
    private JLabel chatText;
    private JLabel nameLabel;
    private JPanel chatPanel;
    private String firstName;

    public chatpage(String firstName) {
        this.firstName = firstName;

        setContentPane(chatPanel);
        setTitle("Chat Page");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);

        enterConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller controller = new controller();
                String input = entryText.getText();
                String result = controller.chatClick(firstName, input);
                setChat(result);
            }
        });
    }

    private void setChat(String result) {
        chatText.setText(result);
        entryText.setText("");
    }

    public void doSomething() {
        System.out.println("ChatPage is doing something.");
    }
}
