package com.stardust.xml;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Stardust on 2016/11/3.
 */

public abstract class XmlFieldObject extends XmlDataObject {

    private Map<String, Setter> mSetterMap = new TreeMap<>();

    protected void registerField(String fieldName, Setter setter) {
        mSetterMap.put(fieldName, setter);
    }

    @Override
    protected void setAttribute(String attribute, String value) throws NoSuchFieldException {
        Setter setter = mSetterMap.get(attribute);
        if (setter == null)
            throw new NoSuchFieldError(attribute);
        setter.set(value);
    }

    @Override
    protected String[] getAttributeNames() {
        return mSetterMap.keySet().toArray(new String[mSetterMap.size()]);
    }

    protected interface Setter {
        void set(String value);
    }

    public abstract class IntSetter implements Setter {

        protected abstract void set(int value);

        @Override
        public void set(String value) {
            set(Integer.parseInt(value));
        }
    }

    public abstract class DateSetterYYMMDD implements Setter {

        @Override
        public void set(String value) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            try {
                set(sdf.parse(value));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        protected abstract void set(Date date);
    }

}
