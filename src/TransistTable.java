import FSM.NodeTransition;
import Operations.*;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;

public class TransistTable {
    int lastState = 0;
    ArrayDeque<NodeTransition> nodes = new ArrayDeque<>();

    public TransistTable(ITreeNode tree) {
        nodes.add(new NodeTransition(lastState, lastState + 1, ' ', tree, false));
        lastState++;
    }

    public void breakTransitions()
    {
        boolean atLeastOneTransitionBroken;

        do
        {
            atLeastOneTransitionBroken = false;
            for(int i = 0; i < nodes.size(); i++)
            {
                atLeastOneTransitionBroken = atLeastOneTransitionBroken || breakTransitionsIteration();
//                System.out.println("*******");
//                printTable();
//                System.out.println("*******");
            }
        }
        while(atLeastOneTransitionBroken);
    }

    public boolean breakTransitionsIteration() {
        NodeTransition currentNode = nodes.pop();

        if (currentNode.treeCondition instanceof Add) {
            nodes.add(new NodeTransition(currentNode.start, currentNode.end, ' ', currentNode.treeCondition.getLeft(), false));
            nodes.add(new NodeTransition(currentNode.start, currentNode.end, ' ', currentNode.treeCondition.getRight(), false));
            return true;
        } else if (currentNode.treeCondition instanceof Multiply) {
            nodes.add(new NodeTransition(currentNode.start, lastState + 1, ' ', currentNode.treeCondition.getLeft(), false));
            nodes.add(new NodeTransition(lastState + 1, currentNode.end, ' ', currentNode.treeCondition.getRight(), false));
            lastState++;
            return true;
        } else if (currentNode.treeCondition instanceof Loop) {
            nodes.add(new NodeTransition(lastState + 1, currentNode.end, ' ', null, true));
            nodes.add(new NodeTransition(currentNode.start, lastState + 1, ' ', null, true));
            nodes.add(new NodeTransition(lastState + 1, lastState + 1, ' ', currentNode.treeCondition.getLeft(), false));
            lastState++;
            return true;
        } else if (currentNode.treeCondition instanceof Letter) {
            nodes.add(currentNode);
            return false;
        }

        return false;

    }

    public void printTable()
    {
        for (var entity : nodes) {
            System.out.println(entity);
        }
    }
}
