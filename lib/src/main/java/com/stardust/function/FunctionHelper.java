package com.stardust.function;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Stardust on 2016/11/2.
 */

public class FunctionHelper {

    private static final Map<Class<?>, Class<?>> PRIMITIVE_TYPE_MAP = new HashMap<>();
    private static boolean defaultIsArgumentPassStrict = false;

    public static int getInvokePriority(Method method, Object[] args, boolean isArgumentStrict) {
        if (Modifier.isPrivate(method.getModifiers()))
            return 0;
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length < args.length)
            return 0;
        if (isArgumentStrict && args.length != parameterTypes.length)
            return 0;
        for (int i = 0; i < parameterTypes.length; i++) {
            if (i >= args.length) {
                return i;
            }
            if (!isAssignableFrom(parameterTypes[i], args[i].getClass())) {
                return 0;
            }
        }
        return args.length + 1;
    }

    public static boolean isAssignableFrom(Class<?> lhs, Class<?> rhs) {
        Class<?> l = getBoxType(lhs);
        Class<?> r = getBoxType(rhs);
        return l.isAssignableFrom(r);
    }

    public static Class<?> getBoxType(Class<?> c) {
        if (!c.isPrimitive())
            return c;
        Class<?> type = PRIMITIVE_TYPE_MAP.get(c);
        return type == null ? c : type;
    }

    static {

        PRIMITIVE_TYPE_MAP.put(Double.TYPE, Double.class);
        PRIMITIVE_TYPE_MAP.put(Float.TYPE, Float.class);
        PRIMITIVE_TYPE_MAP.put(Long.TYPE, Long.class);
        PRIMITIVE_TYPE_MAP.put(Integer.TYPE, Integer.class);
        PRIMITIVE_TYPE_MAP.put(Short.TYPE, Short.class);
        PRIMITIVE_TYPE_MAP.put(Character.TYPE, Character.class);
        PRIMITIVE_TYPE_MAP.put(Byte.TYPE, Byte.class);
        PRIMITIVE_TYPE_MAP.put(Boolean.TYPE, Boolean.class);
    }

    public static boolean getDefaultIsArgumentPassStrict() {
        return defaultIsArgumentPassStrict;
    }

    public static void setDefaultIsArgumentPassStrict(boolean defaultIsArgumentPassStrict) {
        FunctionHelper.defaultIsArgumentPassStrict = defaultIsArgumentPassStrict;
    }

    @SuppressWarnings("unchecked")
    public static <ReturnType> ReturnType invoke(function f, Method targetMethod, Object[] args) throws InvocationTargetException, IllegalAccessException {
        targetMethod.setAccessible(true);
        //如果实际参数数量小于要执行的函数的形参数量，则把后面的参数填为null
        if (args.length < targetMethod.getParameterTypes().length) {
            Object[] filledArgs = new Object[targetMethod.getParameterTypes().length];
            System.arraycopy(args, 0, filledArgs, 0, args.length);
            args = filledArgs;
        }
        return (ReturnType) targetMethod.invoke(f, (Object[]) args);
    }

    public static Method getTargetMethod(Class<? extends function> functionClass, Object[] args, boolean isArgumentPassStrict) {
        Method targetMethod = null;
        int maxPriority = 0;
        Method[] methods = functionClass.getDeclaredMethods();
        for (Method method : methods) {
            int priority = FunctionHelper.getInvokePriority(method, args, isArgumentPassStrict);
            if (priority > maxPriority) {
                maxPriority = priority;
                targetMethod = method;
            }
        }
        return targetMethod;
    }
}
