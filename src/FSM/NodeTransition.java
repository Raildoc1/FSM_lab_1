package FSM;

import Operations.ITreeNode;

public class NodeTransition {
    public int start;
    public int end;
    public char condition;
    public ITreeNode treeCondition;
    public boolean isEpsilon;
}
