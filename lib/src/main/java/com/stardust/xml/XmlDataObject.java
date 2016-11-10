package com.stardust.xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Stardust on 2016/11/3.
 */

public abstract class XmlDataObject {

    public interface OnFieldChangeListener<T extends XmlDataObject> {
        void onFieldValueChange(T xmlDataObject, String fieldName, String newValue);
    }

    private static final String TAG = "XmlObject";
    private OnFieldChangeListener mOnFieldChangeListener;
    private Map<String, OnFieldChangeListener> mOnFieldChangeListenerMap = new TreeMap<>();


    public static <T extends XmlDataObject> void parse(T target, XmlPullParser parser) throws XmlPullParserException, IOException {
        String[] attributes = target.getAttributeNames();
        int eventType = parser.getEventType();
        while (true) {
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    String name = parser.getName();
                    boolean valid = false;
                    if (name.equals(target.getTagName())) {
                        for (String attribute : attributes) {
                            String value = parser.getAttributeValue(null, attribute);
                            if (value != null) {
                                try {
                                    target.setAttributeInternal(attribute, value);
                                    valid = true;
                                } catch (NoSuchFieldException e) {
                                    e.printStackTrace();
                                    throw new XmlPullParserException("找不到字段: " + attribute, parser, e);
                                }
                            }
                        }
                        if (valid) {
                            do {
                                eventType = parser.next();
                            }
                            while (eventType != XmlPullParser.END_TAG && eventType != XmlPullParser.END_DOCUMENT);
                            return;
                        }
                    }
                    break;
                case XmlPullParser.END_DOCUMENT:
                    return;
            }
            eventType = parser.next();
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends XmlDataObject> List<T> parse(Class<T> targetClass, String groupTag, XmlPullParser parser) throws XmlPullParserException, IOException {
        List<T> xmlDataObjects = new LinkedList<>();
        boolean inGroup = false;
        T targetInstance = newXmlDataObjectInstance(targetClass, parser);
        int eventType = parser.getEventType();
        while (true) {
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    String tag = parser.getName();
                    if (parser.getName().equals(groupTag)) {
                        inGroup = true;
                    } else if (inGroup && parser.getName().equals(targetInstance.getTagName())) {
                        T xmlDataObject = newXmlDataObjectInstance(targetClass, parser);
                        parse(xmlDataObject, parser);
                        xmlDataObjects.add(xmlDataObject);
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (parser.getName().equals(groupTag)) {
                        return xmlDataObjects;
                    }
                    break;
                case XmlPullParser.END_DOCUMENT:
                    return xmlDataObjects;
            }
            eventType = parser.next();
        }
    }

    private static <T extends XmlDataObject> T newXmlDataObjectInstance(Class<T> c, XmlPullParser parser) throws XmlPullParserException {
        try {
            return c.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new XmlPullParserException("无法创建XmlDataObject实例", parser, e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new XmlPullParserException("无法创建XmlDataObject实例", parser, e);
        }
    }

    protected void setStringAttribute(String fieldName, String value) throws NoSuchFieldException {
        Field field = getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        try {
            field.set(this, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    protected abstract String getTagName();

    @SuppressWarnings("unchecked")
    protected <T extends XmlDataObject> void setAttributeInternal(String attribute, String value) throws NoSuchFieldException {
        setAttribute(attribute, value);
        OnFieldChangeListener listener = mOnFieldChangeListenerMap.get(attribute);
        if (listener != null) {
            listener.onFieldValueChange(this, attribute, value);
        }
        if (mOnFieldChangeListener != null) {
            mOnFieldChangeListener.onFieldValueChange(this, attribute, value);
        }
    }

    public void setOnFieldChangeListener(OnFieldChangeListener listener) {
        mOnFieldChangeListener = listener;
    }

    public void setOnFieldChangeListener(String fieldName, OnFieldChangeListener listener) {
        mOnFieldChangeListenerMap.put(fieldName, listener);
    }

    public void removeOnFieldChangeListener(String fieldName) {
        mOnFieldChangeListenerMap.remove(fieldName);
    }


    protected abstract void setAttribute(String attribute, String value) throws NoSuchFieldException;

    protected abstract String[] getAttributeNames();

}
