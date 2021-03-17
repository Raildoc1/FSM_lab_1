import FSM.NodeTransition;
import Operations.Add;
import Operations.ITreeNode;
import Operations.Loop;
import Operations.Multiply;

import java.util.ArrayList;
import java.util.Stack;

public class TransistTable {
    int lastState = 0;
    Stack<NodeTransition> nodes = new Stack<>();

    public TransistTable(ITreeNode tree) {
        nodes.add(new NodeTransition(lastState, lastState + 1, ' ', tree, false));
        lastState++;
    }

    public void toTransistTable() {
        NodeTransition currentNode = nodes.pop();

        if (currentNode.treeCondition instanceof Add) {
            nodes.add(new NodeTransition(currentNode.start, currentNode.end, ' ', currentNode.treeCondition.getLeft(), false));
            nodes.add(new NodeTransition(currentNode.start, currentNode.end, ' ', currentNode.treeCondition.getRight(), false));
            lastState++;
        } else if (currentNode.treeCondition instanceof Multiply) {
            nodes.add(new NodeTransition(currentNode.start, lastState + 1, ' ', currentNode.treeCondition.getLeft(), false));
            lastState++;
            nodes.add(new NodeTransition(lastState + 1, currentNode.end, ' ', currentNode.treeCondition.getRight(), false));
            lastState++;
        } else if (currentNode.treeCondition instanceof Loop) {
            nodes.add(new NodeTransition(lastState + 1, currentNode.end, ' ', null, true));
            nodes.add(new NodeTransition(currentNode.start, lastState + 1, ' ', null, true));
            nodes.add(new NodeTransition(lastState + 1, lastState + 1, ' ', currentNode.treeCondition.getLeft(), false));
            lastState++;
        }

    }
}
