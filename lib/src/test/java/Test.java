import com.stardust.function.function;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Stardust on 2016/10/30.
 */

public class Test {


    @org.junit.Test
    public void dynamicFunctionTest() throws Exception {
        function<Integer> mul = new function<Integer>() {
            int sum(List<Short> list) {
                int sum = 0;
                for (int i : list) {
                    sum += i;
                }
                return sum;
            }
        };
        List<Integer> list = Arrays.asList(1, 2, 3);
        assertEquals((int) mul.call(list), 6);
    }

    public static int foo(function<Integer> op) {
        return op.call(1, 2, 3, 4);
    }

}
