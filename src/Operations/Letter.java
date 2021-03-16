package Operations;

public class Letter implements ITreeNode {

    private char letter;

    public Letter(char c) {
        letter = c;
    }

    @Override
    public ITreeNode getLeft() {
        return null;
    }

    @Override
    public ITreeNode getRight() {
        return null;
    }

    @Override
    public char getChar() {
        return letter;
    }

    @Override
    public String toString() {
        return "Letter(" + letter + ")";
    }
}
