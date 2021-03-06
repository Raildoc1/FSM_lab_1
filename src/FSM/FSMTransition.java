package FSM;

import Operations.ITreeNode;

public class FSMTransition {

    public FSMTransition(int start, int end, char condition, ITreeNode treeCondition, boolean isEpsilon) {
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
        //System.out.println("start: "+start+" end: "+end);
        String startString = start == 0 ? "START" : String.valueOf(start);
        String endString = end == 1 ? "END" : String.valueOf(end);

        if(isEpsilon)
        {
            return startString + " --- e ---> " + endString;
        }
        else
        {
            return startString + " --- " + treeCondition + " ---> " + endString;
        }

    }
}
