import FSM.NodeTransition;
import Operations.*;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class TransistTable {
    int lastState = 0;
    ArrayDeque<NodeTransition> fsm = new ArrayDeque<>();

    public TransistTable(ITreeNode tree) {
        fsm.add(new NodeTransition(lastState, lastState + 1, ' ', tree, false));
        lastState++;
    }

    public void breakTransitions() {
        boolean atLeastOneTransitionBroken;

        do {
            atLeastOneTransitionBroken = false;
            for (int i = 0; i < fsm.size(); i++) {
                atLeastOneTransitionBroken = atLeastOneTransitionBroken || breakTransitionsIteration();
            }
        }
        while (atLeastOneTransitionBroken);
    }

    public boolean breakTransitionsIteration() {

        NodeTransition currentNode = fsm.poll();

        if (currentNode.treeCondition instanceof Add) {
            fsm.add(new NodeTransition(currentNode.start, currentNode.end, ' ', currentNode.treeCondition.getLeft(), false));
            fsm.add(new NodeTransition(currentNode.start, currentNode.end, ' ', currentNode.treeCondition.getRight(), false));
            return true;
        } else if (currentNode.treeCondition instanceof Multiply) {
            lastState++;
            fsm.add(new NodeTransition(currentNode.start, lastState, ' ', currentNode.treeCondition.getLeft(), false));
            fsm.add(new NodeTransition(lastState, currentNode.end, ' ', currentNode.treeCondition.getRight(), false));
            return true;
        } else if (currentNode.treeCondition instanceof Loop) {
            lastState++;
            fsm.add(new NodeTransition(lastState, currentNode.end, ' ', null, true));
            fsm.add(new NodeTransition(currentNode.start, lastState, ' ', null, true));
            fsm.add(new NodeTransition(lastState, lastState, ' ', currentNode.treeCondition.getLeft(), false));
            return true;
        } else if (currentNode.isEpsilon || currentNode.treeCondition instanceof Letter) {
            fsm.add(currentNode);
            return false;
        }

        return false;

    }

    public void breakEpsilonFSM() {
        ArrayList<Integer>[] equalStates = new ArrayList[lastState + 1];

        for (int i = 0; i <= lastState; i++) {
            equalStates[i] = findEqualStates(i);
            equalStates[i].add(i);
        }

        printEpsilonTable(equalStates);

        ArrayDeque<NodeTransition> newFSM = new ArrayDeque<>();

        for (int i = 0; i <= lastState; i++) {
            for (var transition : fsm) {
                if (transition.isEpsilon) {
                    continue;
                }
                if (equalStates[i].contains(transition.start)) {
                    newFSM.add(new NodeTransition(i, transition.end, transition.condition, transition.treeCondition, transition.isEpsilon));
                }
            }
        }

        fsm = newFSM;

    }

    private void printEpsilonTable(ArrayList<Integer>[] equalStates) {
        for (int i = 0; i <= lastState; i++) {
            System.out.print(i + ": ");

            for (var entity : equalStates[i]) {
                System.out.print(", " + entity);
            }
            System.out.println("");
        }
    }

    class IntPair {
        public int left;
        public int right;

        public IntPair(int left, int right) {
            this.left = left;
            this.right = right;
        }
    }

    public boolean removeEquals() {
        ArrayList<IntPair> equals = new ArrayList<>();

        for (int i = 0; i <= lastState; i++) {
            for (int j = 0; j <= lastState; j++) {
                if (i == j) {
                    continue;
                }

                boolean areEqual = true;

                for (var transition : fsm) {
                    if (transition.start == i) {
                        System.out.println("(" + j + ", " + transition.end + ", " + ((Letter) transition.treeCondition).getChar() + ") = " + hasTransition(j, transition.end, ((Letter) transition.treeCondition).getChar()));
                        if (!hasTransition(j, transition.end, ((Letter) transition.treeCondition).getChar())) {
                            areEqual = false;
                            break;
                        }
                    }
                }

                if (areEqual) {
                    equals.add(new IntPair(i, j));
                }

            }
        }

        if (equals.size() == 0) {
            return false;
        }

        ArrayList<IntPair> result = new ArrayList<>();

        for (var pair : equals) {

            for (var pair1 : equals) {

                if (pair.right == pair1.left && pair.left == pair1.right) {
                    result.add(pair);
                    System.out.println(pair.left + " : " + pair.right);
                }
            }

        }

        if(result.size() < 1)
        {
            return false;
        }

        ArrayDeque<NodeTransition> newFSM = new ArrayDeque<>();

        for (var transition : fsm) {

            int start = transition.start == result.get(0).left ? result.get(0).right : transition.start;
            int end = transition.end == result.get(0).left ? result.get(0).right : transition.end;

            newFSM.add(new NodeTransition(start, end, transition.condition, transition.treeCondition, transition.isEpsilon));
        }

        fsm = newFSM;

        removeDoubles();

        return true;
    }

    private void removeDoubles() {

        for (int i = 0; i < fsm.size(); i++) {
            NodeTransition currentTransition = fsm.poll();

            if (!hasTransition(currentTransition.start, currentTransition.end, ((Letter) currentTransition.treeCondition).getChar())) {
                fsm.add(currentTransition);
            }
        }

    }

    private boolean hasTransition(int start, int end, char c) {
        for (var transition : fsm) {
            if (
                    transition.start == start &&
                            transition.end == end &&
                            transition.treeCondition instanceof Letter &&
                            ((Letter) transition.treeCondition).getChar() == c
            ) {
                return true;
            }
        }

        return false;
    }

    private ArrayList<Integer> findEqualStates(int state) {
        ArrayList<Integer> result = new ArrayList<>();

        for (var entity : fsm) {
            if (entity.isEpsilon && entity.start == state) {
                result.add(entity.end);
                result.addAll(findEqualStates(entity.end));
            }
        }

        return result;
    }

    public void determine() {

        ArrayList<int[]> sets = new ArrayList<>();

        int[] p0 = {0};

        sets.add(p0);

        int setsSize = sets.size();
        int prevSetsSize;
        int index = 0;

        do
        {

            for(int i = index; i < sets.size(); i++, index++)
            {
                var newSets = handleReachableSets(sets.get(index));

                for (var set : newSets) {
                    if(set.length > 0 && !Utils.listContainsSet(sets, set))
                    {
                        sets.add(set);
                    }
                }
            }

            prevSetsSize = setsSize;
            setsSize = sets.size();
        } while(setsSize > prevSetsSize);

        System.out.println("finished");

        ArrayList<Character> alphabet = getAlphabet();

        ArrayDeque<NodeTransition> newFSM = new ArrayDeque<>();

        for(int i = 0; i < sets.size(); i++)
        {

            for (var letter : alphabet) {
                ArrayList<Integer> temp = new ArrayList<>();

                for (var entity : fsm) {
                    if (Utils.arrayContainsInt(sets.get(i), entity.start) && entity.treeCondition instanceof Letter) {
                        char c = ((Letter) entity.treeCondition).getChar();

                        if(letter == c)
                        {
                            if(!temp.contains(entity.end))
                            {
                                temp.add(entity.end);
                            }
                        }
                    }
                }

                int[] tempInt = new int[temp.size()];

                for (int j = 0; j < tempInt.length; j++)
                {
                    tempInt[j] = temp.get(j);
                }

                int end = Utils.findSet(sets, tempInt);

                if(end > -1)
                {
                    newFSM.add(new NodeTransition(i, end, ' ', new Letter(letter), false));
                }

            }
        }

        fsm = newFSM;

        removeEquals();

    }

    private ArrayList<int[]> handleReachableSets(int[] set)
    {
        System.out.println();
        for (int i = 0; i < set.length; i++)
        {
            System.out.print(set[i] + "|");
        }
        System.out.println();


        ArrayList<int[]> sets = new ArrayList<>();

        ArrayList<Character> alphabet = getAlphabet();

        for (var letter : alphabet) {
            ArrayList<Integer> temp = new ArrayList<>();

            for (var entity : fsm) {
                if (Utils.arrayContainsInt(set, entity.start) && entity.treeCondition instanceof Letter) {
                    char c = ((Letter) entity.treeCondition).getChar();

                    if(letter == c)
                    {
                        if(!temp.contains(entity.end))
                        {
                            temp.add(entity.end);
                        }
                    }
                }
            }

            int[] tempInt = new int[temp.size()];

            for (int i = 0; i < tempInt.length; i++)
            {
                tempInt[i] = temp.get(i);
            }

            sets.add(tempInt);

        }

        return sets;
    }

    public ArrayList<Character> getAlphabet() {
        ArrayList<Character> result = new ArrayList<>();

        for (var entity : fsm) {
            if (entity.treeCondition instanceof Letter) {
                char c = ((Letter) entity.treeCondition).getChar();

                if (!result.contains(c)) {
                    result.add(c);
                }
            }
        }

        return result;
    }

    public void printTable() {
        for (var entity : fsm) {
            System.out.println(entity);
        }
    }
}
