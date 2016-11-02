import com.stardust.function.function;

import static org.junit.Assert.assertEquals;

/**
 * Created by Stardust on 2016/10/30.
 */

public class Test {


    @org.junit.Test
    public void dynamicFunctionTest() throws Exception {
        function<Integer> mul = new function<Integer>() {
            int multiply(int i, int j) {
                return i * j;
            }
        };
        function<Integer> mul5 = mul.bind(5);
        int i = mul5.call(4);
        assertEquals(i, 20);
    }

    private void test(Object foo) {
    }

    public static int foo(function<Integer> op) {
        return op.call(1, 2, 3, 4);
    }

}
