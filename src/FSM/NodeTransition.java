package FSM;

import Operations.ITreeNode;

public class NodeTransition {

    public NodeTransition(int start, int end, char condition, ITreeNode treeCondition, boolean isEpsilon) {
        this.start = start;
        this.end = end;
        this.condition = condition;
        this.treeCondition = treeCondition;
        this.isEpsilon = isEpsilon;
    }

    public int start;
    public int end;
    public char condition;
    public ITreeNode treeCondition;
    public boolean isEpsilon;

    @Override
    public String toString() {

        if(isEpsilon)
        {
            return start + " --- e ---> " + end;
        }
        else
        {
            return start + " --- " + treeCondition + " ---> " + end;
        }

    }
}
