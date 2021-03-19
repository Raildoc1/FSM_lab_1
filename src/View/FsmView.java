package View;

import FSM.FSMTransition;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Random;


public class FsmView {
    JFrame frame;
    ArrayDeque<FSMTransition> fsm;
    final Random random = new Random();
    ArrayList<Point> states = new ArrayList<>();

    public FsmView(ArrayDeque<FSMTransition> fsm) {
        this.fsm = fsm;
        frame = new JFrame("FSM View");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(700, 700));
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
    }

    private void drawFSM() {
        for (var p: fsm) {
            //some code
        }
    }
}
