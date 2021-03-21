package View;

import FSM.FSMTransition;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Random;

public class FsmPanel extends JPanel {
    final Random random = new Random();
    HashMap<Integer, Point> states = new HashMap<>();
    ArrayDeque<FSMTransition> fsm;

    BufferedImage image = new BufferedImage(5000, 5000, BufferedImage.TYPE_INT_RGB);

    public FsmPanel(ArrayDeque<FSMTransition> fsm) {
        this.fsm = fsm;

        setSize(5000, 5000);
        drawFSM();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }

    private void drawFSM() {

        Graphics2D graphics2D = initGraphics();

        initStatesMap();
        drawTransitions(graphics2D);
        drawStates(graphics2D);
        addStateLabels(graphics2D);

    }

    private void drawStates(Graphics2D graphics2D) {
        for (var p : states.entrySet()) {
            graphics2D.fillOval(p.getValue().x, p.getValue().y, 40, 40);
        }
    }

    private void drawTransitions(Graphics2D graphics2D) {
        Color oldColor = graphics2D.getColor();

        for (var p : fsm) {

            var startState = states.get(p.start);
            var endState = states.get(p.end);

            if (startState == endState) {

                graphics2D.setStroke(new BasicStroke(3));
                graphics2D.drawOval(endState.x + 10, endState.y + 10, 50, 50);
                graphics2D.drawString(String.valueOf(p.treeCondition), endState.x + 45, endState.y + 45);
                graphics2D.setColor(new Color(random.nextInt(200), random.nextInt(200), random.nextInt(200)));


            } else {

                double directionX = endState.x - startState.x;
                double directionY = endState.y - startState.y;

                double directionLen = Math.sqrt(directionX * directionX + directionY * directionY);

                double normalX = endState.y - startState.y;
                double normalY = startState.x - endState.x;

                double normalLen = Math.sqrt(normalX * normalX + normalY * normalY);

                directionX /= directionLen;
                directionY /= directionLen;

                normalX /= normalLen;
                normalY /= normalLen;

                float k = 30;
                float t = 7.5f;

                int stringX = (int) (startState.x + 20 + k * directionX + t * normalX);
                int stringY = (int) (startState.y + 20 + k * directionY + t * normalY);

                graphics2D.setStroke(new BasicStroke(3));

                graphics2D.drawString(String.valueOf(p.treeCondition), stringX, stringY);
                graphics2D.drawLine(startState.x + 20, startState.y + 20, endState.x + 20, endState.y + 20);

                graphics2D.fillOval((int) (endState.x - k * directionX + 15), (int) (endState.y - k * directionY + 15), 10, 10);

                graphics2D.setColor(new Color(random.nextInt(200), random.nextInt(200), random.nextInt(200)));

            }

        }

        graphics2D.setColor(oldColor);
    }

    private Graphics2D initGraphics() {
        Graphics2D graphics2D = (Graphics2D) image.getGraphics();
        graphics2D.fillRect(0, 0, 5000, 5000);
        graphics2D.setColor(Color.BLACK);
        return graphics2D;
    }

    private void initStatesMap() {

        int x = 100;
        int y = 100;

        for (var p : fsm) {

            if (addState(p.start, x, y)) {
                int deltaX = 100 + random.nextInt(100);

                x += deltaX;
                y = random.nextInt(600);

            }

            if (addState(p.end, x, y)) {
                int deltaX = 50 + random.nextInt(100);

                x += deltaX;
                y = random.nextInt(600);
            }

        }
    }

    private boolean addState(int state, int x, int y) {

        if (!states.containsKey(state)) {
            states.put(state, new Point(x, y));
            return true;
        }

        return false;
    }

    private void addStateLabels(Graphics2D graphics2D) {

        Color oldColor = graphics2D.getColor();
        graphics2D.setColor(Color.WHITE);

        for (var state : fsm) {
            var statePosition = states.get(state.start);
            graphics2D.drawString(String.valueOf(state.start), statePosition.x + 20, statePosition.y + 20);
        }

        for (var state : fsm) {
            var statePosition = states.get(state.end);
            graphics2D.drawString(String.valueOf(state.end), statePosition.x + 20, statePosition.y + 20);
        }

        graphics2D.setColor(oldColor);
    }

}
