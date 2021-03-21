package View;

import FSM.FSMTransition;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class FsmFrame {
    JFrame frame;
    ArrayDeque<FSMTransition> fsm;

    public FsmFrame(ArrayDeque<FSMTransition> fsm) {
        this.fsm = fsm;
        frame = new JFrame("FSM View");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(700, 700));
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setMinimumSize(new Dimension(500, 500));
        FsmPanel panel = new FsmPanel(fsm);
        frame.setContentPane(panel);
        frame.setVisible(true);
    }

}
