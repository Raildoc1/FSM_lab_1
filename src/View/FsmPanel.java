package View;

import FSM.FSMTransition;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Random;

public class FsmPanel extends JPanel {
    final Random random = new Random();
    HashMap<Integer, Point> states = new HashMap<>();
    ArrayDeque<FSMTransition> fsm;

    BufferedImage image = new BufferedImage(700, 700, BufferedImage.TYPE_INT_RGB);

    public FsmPanel(ArrayDeque<FSMTransition> fsm) {
        this.fsm = fsm;

        setSize(700, 700);
        drawFSM();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0,0, null);
    }

    private void drawFSM() {
        Graphics2D graphics2D = (Graphics2D) image.getGraphics();
        graphics2D.fillRect(0,0, 700, 700);
        graphics2D.setColor(Color.BLACK);
        for (var p: fsm) {
            if (!states.containsKey(p.start)) {
                var newX = random.nextInt(650);
                var newY = random.nextInt(650);
                states.put(p.start, new Point(newX, newY));
                graphics2D.drawString(String.valueOf(p.start), newX + 20, newY + 20);
            }
            if (!states.containsKey(p.end)) {
                var newX = (random.nextInt(600) + 100) % 600;
                var newY = (random.nextInt(600) + 100) % 600;
                states.put(p.end, new Point(newX, newY));
                graphics2D.drawString(String.valueOf(p.end), newX + 20, newY + 20);
                //System.out.println(p.end);
            }
        }
        for (var p: states.entrySet()) {
            graphics2D.drawOval(p.getValue().x, p.getValue().y, 40, 40);
        }
        int i = 1;
        int mult;
        for (var p: fsm) {
            if (i % 2 == 0) {
                mult = -1;
            } else {mult = 1;}
            var startState = states.get(p.start);
            var endState = states.get(p.end);
            int[] xPoints = new int []{startState.x+20, (startState.x + endState.x)/2 + ((startState.x - endState.x)/2) + 50, endState.x + 20};
            int[] yPoints = new int []{startState.y+20, (startState.y + endState.y)/2 + ((startState.y - endState.y)/2) + (100 * mult), endState.y + 20};
            graphics2D.drawPolyline(xPoints, yPoints, 3);
            graphics2D.drawString(String.valueOf(p.treeCondition), ((startState.x + endState.x)/2 + (startState.x - endState.x)/2)+ 50 + 20,
                    (startState.y + endState.y)/2 + ((startState.y - endState.y)/2)+ (100 * mult) + 20);
            i++;
            //graphics2D.drawLine(startState.x+20, startState.y+20, endState.x+20, endState.y+20);
        }
        //System.out.println("draw?");
    }
}
