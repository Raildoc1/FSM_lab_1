import Operations.*;

public class Parser {

    public ITreeNode toTree(String inputString) {

        short nestingLevel = 0;
        inputString = tryToRemoveParentheses(inputString);
        boolean hasAdd = hasAdd(inputString);

        System.out.println("*: " + inputString);

        if(inputString.charAt(inputString.length() - 1) == '*')
        {
            return new Loop(toTree(inputString.substring(0, inputString.length() - 1)));
        }

        for (int i = 0; i < inputString.length() - 1; i++) {

            char current = inputString.charAt(i);
            char next = inputString.charAt(i + 1);

            if (current == '(') {
                nestingLevel++;
            } else if (current == ')') {
                nestingLevel--;
            }

            if (nestingLevel != 0)
            {
                continue;
            }

            if (current == '|') {
                return new Add(toTree(inputString.substring(0, i)), toTree(inputString.substring(i + 1)));
            }

            if(!hasAdd && isMultiply(current, next))
            {
                return new Multiply(toTree(inputString.substring(0, i + 1)), toTree(inputString.substring(i + 1)));
            }
        }

        return new Letter(inputString.charAt(0));

    }

    private boolean isMultiply(char left, char right)
    {

        boolean leftIsLetter = Character.isLetter(left);
        boolean rightIsLetter = Character.isLetter(right);

        if(leftIsLetter && rightIsLetter)
        {
            return true;
        }

        if(left == ')' && right == '(')
        {
            return true;
        }

        if(left == ')' && rightIsLetter)
        {
            return true;
        }

        if(leftIsLetter && right == '(')
        {
            return true;
        }

        return false;
    }

    private boolean hasAdd(String s)
    {
        short nestingLevel = 0;

        for (int i = 0; i < s.length(); i++) {

            char current = s.charAt(i);

            if (current == '(') {
                nestingLevel++;
            } else if (current == ')') {
                nestingLevel--;
            }

            if (nestingLevel == 0 && current == '|') {
                return true;
            }
        }

        return false;
    }

    private String tryToRemoveParentheses(String s)
    {
        if((s.charAt(0) != '(') || s.charAt(s.length() - 1) != ')')
        {
            return s;
        }

        short sem = 0;

        for (int i = 0; i < s.length(); i++) {

            char current = s.charAt(i);

            if (current == '(') {
                sem++;
            } else if (current == ')') {
                sem--;
            }

            if(sem == 0 && i != s.length() - 1)
            {
                return s;
            }
        }

        return s.substring(1, s.length() - 1);
    }

    private String tryToAddLoopParentheses(String s)
    {
        for (int i = 0; i < s.length() - 1; i++) {
            char current = s.charAt(i);
            char next = s.charAt(i + 1);

            if(next == '*')
            {
                if(Character.isLetter(current))
                {
                    s = addParenthesesAt(s, i, i + 3);
                    i = i + 3;
                }
                else if(current == ')')
                {
                    short nestingLevel = 0;
                    for(int j = i; j >= 0; j--)
                    {
                        if(s.charAt(j) == '(')
                        {
                            nestingLevel--;

                            if(nestingLevel == 0)
                            {
                                s = addParenthesesAt(s, j, i + 3);
                                i = i + 3;
                                break;
                            }
                        } else if(s.charAt(j) == ')')
                        {
                            nestingLevel++;
                        }
                    }
                }
            }
        }

        return s;
    }

    private String addParenthesesAt(String s, int l, int r) {
        s = addCharAt(s, '(', l);
        s = addCharAt(s, ')', r);
        return s;
    }

    private String addCharAt(String str, char ch, int position) {
        int len = str.length();
        char[] updatedArr = new char[len + 1];
        str.getChars(0, position, updatedArr, 0);
        updatedArr[position] = ch;
        str.getChars(position, len, updatedArr, position + 1);
        return new String(updatedArr);
    }

    public static void main(String[] args)
    {
        Parser parser = new Parser();

        String testString = "(abc)g*|(sd|f)*(sdf)";
        testString = parser.tryToAddLoopParentheses(testString);
        System.out.println(parser.toTree(testString));
    }
}
