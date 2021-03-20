package View;

import Calculations.FsmHandler;
import Calculations.Parser;

import javax.swing.*;
import java.awt.*;

public class InputMenu {

    public InputMenu() {
        JFrame frame = new JFrame("FSM");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(505, 100));
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JLabel label = new JLabel("Enter regex:");
        label.setBounds(20, 20, 100,20);

        JTextField regexField = new JTextField();
        regexField.setBounds(110, 21, 250, 20);

        JButton startButton = new JButton("Create FSM");
        startButton.setBounds(370, 21, 100, 20);
        startButton.addActionListener(e -> {
            Parser parser = new Parser();
            String testString = regexField.getText();
            testString = parser.tryToAddLoopParentheses(testString);

            FsmHandler fsmHandler = new FsmHandler(parser.toTree(testString));

            fsmHandler.makeDetermineFSM();
            fsmHandler.printTable();

            frame.setVisible(false);
            FsmFrame fsmFrame = new FsmFrame(fsmHandler.getFsm());

        });

        frame.add(label);
        frame.add(regexField);
        frame.add(startButton);

        frame.setVisible(true);
    }

//    private void startParser(ActionEvent e) {
//        Parser parser = new Parser();
//        testString = parser.tryToAddLoopParentheses(testString);
//        System.out.println(parser.toTree(testString));
//
//        FsmHandler fsmHandler = new FsmHandler(parser.toTree(testString));
//
//        fsmHandler.makeDetermineFSM();
//        fsmHandler.printTable();
//    }
}
