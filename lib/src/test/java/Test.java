import android.graphics.Color;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Stardust on 2016/10/30.
 */

public class Test {


    @org.junit.Test
    public void dynamicFunctionTest() throws Exception {
        triggerColorSelected(new Function() {
            public void invoke(Integer color) {
                assertEquals((int) color, Color.RED);
            }
        });
        executeOperation(new Function() {
            public int invoke(Integer i, Integer j, Integer k) {
                return -(i + j + k);
            }

            public int invoke(Integer i, Integer j) {
                return i + j;
            }

            public int invoke(Integer[] i) {
                int sum = 0;
                for (Integer ii : i) {
                    sum += ii;
                }
                return 2 * sum;
            }

        });
    }

    public void triggerColorSelected(Function f) {
        int color = Color.RED;
        f.call(color);
    }

    public void executeOperation(Function op) {
        assertEquals(3, (int) op.call(1, 2));
        assertEquals(-6, (int) op.call(1, 2, 3));
        assertEquals(12, (int) op.call((Object) new Integer[]{1, 2, 3}));
    }

    @org.junit.Test
    public void clouseTest() {
        Function count = count();
        count.call();
        count.call();
        Function count2 = count();
        assertEquals(1, count2.call());
        assertEquals(3, count.call());
    }

    public Function count() {
        return new Function() {
            private int count = 0;

            public int invoke() {
                return ++count;
            }

        };
    }

    public static class Function {

        public Object call(Object... args) {
            return call("invoke", args);
        }

        public Object call(String name, Object... args) {
            Method targetMethod = null;
            Method[] methods = getClass().getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().equals(name)) {
                    targetMethod = method;
                    if (typeEquals(method.getParameterTypes(), args))
                        break;
                }
            }
            if (targetMethod == null)
                throw new IllegalStateException();
            try {
                return targetMethod.invoke(this, args);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new IllegalArgumentException(e);
            } catch (InvocationTargetException e) {
                throw new IllegalArgumentException(e);
            }
        }

        private boolean typeEquals(Class<?>[] parameterTypes, Object[] args) {
            if (parameterTypes.length != args.length)
                return false;
            for (int i = 0; i < parameterTypes.length; i++) {
                if (parameterTypes[i] != args[i].getClass())
                    return false;
            }
            return true;
        }
    }
}
