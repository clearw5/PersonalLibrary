package com.stardust.function;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Stardust on 2016/11/2.
 */

public class function<ReturnType> {

    private boolean mIsArgumentPassStrict;
    private Object[] arguments;

    protected function() {
        mIsArgumentPassStrict = FunctionHelper.getDefaultIsArgumentPassStrict();
    }

    public function(boolean isArgumentPassStrict) {
        mIsArgumentPassStrict = isArgumentPassStrict;
    }

    protected Object[] getArguments() {
        return arguments;
    }

    public ReturnType call(Object... args) {
        Method targetMethod = FunctionHelper.getTargetMethod(getClass(), args, mIsArgumentPassStrict);
        if (targetMethod == null)
            throw new FunctionCallException(new NoSuchMethodException("You must implement a appropriate public method for the arguments"));
        try {
            arguments = args;
            return FunctionHelper.invoke(this, targetMethod, args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new FunctionCallException(e);
        } catch (InvocationTargetException e) {
            throw new FunctionCallException(e);
        }
    }

    public function<ReturnType> bind(final Object... argLeft) {
        if (argLeft.length == 0)
            return this;
        return new function<ReturnType>() {
            public ReturnType call(Object... argRight) {
                Object[] argCombine = new Object[argLeft.length + argRight.length];
                System.arraycopy(argLeft, 0, argCombine, 0, argLeft.length);
                if (argRight.length > 0) {
                    System.arraycopy(argRight, 0, argCombine, argLeft.length, argRight.length);
                }
                return function.this.call(argCombine);
            }
        };
    }
}
