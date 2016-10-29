package com.stardust.xml;

import android.content.res.XmlResourceParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Stardust on 2016/7/6.
 */
public abstract class XmlObject {
    private static final String TAG = "XmlObject";
    private static Map<String, String> classNameMap = new TreeMap<>();

    /**
     * @param xmlElementName
     * @param className
     */
    public static void register(String xmlElementName, String className) {
        classNameMap.put(xmlElementName, className);
    }

    public abstract String[] getAttributeNames();

    public void setAttribute(String name, String value) throws NoSuchFieldException, IllegalAccessException {
        Field field = getClass().getField(name);
        field.setAccessible(true);
        field.set(this, value);
    }

    public abstract void addContent(XmlObject content);

    public abstract void setContentText(String text);

    public static void parse(XmlObject target, XmlResourceParser parser) throws XmlPullParserException, IOException {
        int eventType = parser.getEventType();
        if (eventType != XmlPullParser.START_TAG) {
            throw new XmlPullParserException("缺少START_TAG");
        }
        try {
            String[] attributes = target.getAttributeNames();
            for (String attribute : attributes) {
                String value = parser.getAttributeValue(null, attribute);
                if (value != null) {
                    try {
                        target.setAttribute(attribute, value);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                        throw new XmlPullParserException("找不到字段: " + attribute, parser, e);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            eventType = parser.next();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG: {
                        Class xmlClass = null;
                        String name = parser.getName();
                        try {
                            xmlClass = Class.forName(classNameMap.get(parser.getName()));
                        } catch (ClassNotFoundException | NullPointerException e) {
                            throw new XmlPullParserException("找不到xml标志对应的类，请确保已经写了该被并调用register方法注册:" + name, parser, e);
                        }

                        XmlObject xmlObject = (XmlObject) xmlClass.newInstance();
                        parse(xmlObject, parser);
                        target.addContent(xmlObject);
                        break;
                    }
                    case XmlPullParser.TEXT:
                        target.setContentText(parser.getText());
                        break;
                    case XmlPullParser.END_TAG:
                        return;
                    default:
                        throw new XmlPullParserException("eventType不能识别: eventType=" + eventType);
                }
                eventType = parser.next();
            }
        } catch (IllegalAccessException e) {
            throw new XmlPullParserException("无法设置xml类的字段", parser, e);
        } catch (InstantiationException e) {
            throw new XmlPullParserException("无法实例化xml类", parser, e);
        }
        throw new XmlPullParserException("缺少END_TAG");
    }

    public static XmlObject parse(XmlResourceParser parser) {
        try {
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    Class xmlClass = null;
                    String name = parser.getName();
                    try {
                        xmlClass = Class.forName(classNameMap.get(name));
                    } catch (ClassNotFoundException | NullPointerException e) {
                        throw new XmlPullParserException("找不到xml标志对应的类，请确保已经写了该被并调用register方法注册:" + name, parser, e);
                    }
                    XmlObject xmlObject = (XmlObject) xmlClass.newInstance();
                    parse(xmlObject, parser);
                    return xmlObject;
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException | IllegalAccessException | IOException | InstantiationException exc) {
            exc.printStackTrace();
            throw new RuntimeException(exc);
        }
        return null;
    }

}
