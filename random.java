import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class random {
    public static void main(String args[]) {

        List<String> intList = Arrays.asList("1, 2, 3,sdf");
        System.out.println(intList); // Output: [1, 2, 3]
        System.out.println(intList.getClass());

        // List<Integer> myList = new ArrayList<>(Arrays.asList(intList.split(",")));
        // System.out.println(myList);
    }


}