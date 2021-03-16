package Operations;

public class Multiply implements ITreeNode{
    private ITreeNode l;
    private ITreeNode r;

    public Multiply(ITreeNode l, ITreeNode r) {
        this.l = l;
        this.r = r;
    }

    @Override
    public ITreeNode getLeft() {
        return l;
    }

    @Override
    public ITreeNode getRight() {
        return r;
    }

    @Override
    public char getChar() {
        return 0;
    }

    @Override
    public String toString() {
        return "Multiply(" + l + ", " + r + ")";
    }
}
