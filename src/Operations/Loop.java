package Operations;

public class Loop implements ITreeNode {

    private ITreeNode l;

    public Loop(ITreeNode l)
    {
        this.l = l;
    }

    @Override
    public ITreeNode getLeft() {
        return l;
    }

    @Override
    public ITreeNode getRight() {
        return null;
    }

    @Override
    public char getChar() {
        return 0;
    }

    @Override
    public String toString() {
        return "Loop(" + l + ")";
    }
}
