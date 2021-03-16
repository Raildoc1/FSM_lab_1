import Operations.Add;
import Operations.ITreeNode;
import Operations.Loop;
import Operations.Multiply;

public class TransistTable {

    public void toTransistTable(ITreeNode tree) {
        //some cycle
        var leaf = tree.getLeft();

        if (leaf != null) {
            toTransistTable(leaf);
        }

        if (leaf instanceof Add) {
            //some code
        } else if (leaf instanceof Multiply) {
            //some code
        } else if (leaf instanceof Loop) {
            //some code
        }

    }
}
