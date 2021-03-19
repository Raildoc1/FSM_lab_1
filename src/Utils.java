import java.util.ArrayList;

public class Utils {

    public static boolean setsAreEqual(int [] a, int[] b)
    {
        for (var i : a) {
            if(!arrayContainsInt(b, i))
            {
                return false;
            }
        }

        for (var i : b) {
            if(!arrayContainsInt(a, i))
            {
                return false;
            }
        }

        return true;
    }

    public static boolean arrayContainsInt(int[] array, int number)
    {
        for(int i = 0; i < array.length; i++)
        {
            if(array[i] == number)
            {
                return true;
            }
        }

        return false;
    }

    public static boolean listContainsSet(ArrayList<int[]> sets, int[] set)
    {
        for (var s : sets) {
            if(setsAreEqual(set, s))
            {
                return true;
            }
        }

        return false;
    }

    public static int findSet(ArrayList<int[]> sets, int[] set)
    {
        for(int i = 0; i < sets.size(); i++)
        {
            if(setsAreEqual(set, sets.get(i)))
            {
                return i;
            }
        }

        return -1;
    }
}
