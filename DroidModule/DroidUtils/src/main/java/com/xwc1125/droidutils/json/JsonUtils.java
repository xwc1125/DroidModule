/**
 * <p>
 * Title: JsonUtils.java
 * </p>
 * <p>
 * Description: json格式化类
 * </p>
 * <p>
 * <p>
 * <p>
 * <p>
 * </p>
 *
 * @Copyright: Copyright (c) 2016
 * @author xwc1125
 * @date 2016年8月18日 上午9:29:45
 * @version V1.0
 */
package com.xwc1125.droidutils.json;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.xwc1125.droidutils.UtilsConfig;
import com.xwc1125.droidutils.LogUtils;
import com.xwc1125.droidutils.StringUtils;

/**
 * <p>
 * Title: JsonUtils
 * </p>
 * <p>
 * Description: 格式化工具类
 * </p>
 * <p>
 * 因为jsonObject的key一定是string，所以如果在使用map的时候，一定保持key的字符串型不一致
 * <p>
 * * 目前java.util类型中只支持：
 * <li>java.util.Date</li>
 * <p>
 * <li>java.util.ArrayList，java.util.List，</li>
 * <p>
 * <li>java.util.Map，java.util.TreeMap，java.util.HashMap</li>
 * <p>
 * <p>
 * </p>
 *
 * @author xwc1125
 * @date 2016年3月4日上午10:52:37
 */
public class JsonUtils {

    private static final String TAG = JsonUtils.class.getName();
    private static final boolean isDebug = UtilsConfig.isDebug;

    /**
     * <p>
     * Title: jsonToList
     * </p>
     * <p>
     * Description: jsonString to List<TreeMap<String, Object>>
     * </p>
     * <p>
     * <p>
     * </p>
     *
     * @param jsonString
     * @return
     * @throws JSONException
     * @author xwc1125
     * @date 2016年3月4日上午11:20:11
     */
    public static List<TreeMap<String, Object>> jsonToList(String jsonString)
            throws JSONException {
        List<TreeMap<String, Object>> result = new ArrayList<TreeMap<String, Object>>();
        if (StringUtils.isEmpty(jsonString)) {
            return result;
        }
        JSONObject jsonObj = null;
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObj = (JSONObject) jsonArray.get(i);
                TreeMap<String, Object> map = (TreeMap<String, Object>) jsonToMap(jsonObj);
                result.add(map);
            }
        } catch (Exception e) {
            jsonObj = new JSONObject(jsonString);
            TreeMap<String, Object> map = (TreeMap<String, Object>) jsonToMap(jsonObj);
            result.add(map);
        }
        return result;
    }

    /**
     * <p>
     * Title: jsonObjToMap
     * </p>
     * <p>
     * Description: jsonObject 转为 Map
     * </p>
     * <p>
     * <p>
     * </p>
     *
     * @param object
     * @return
     * @author xwc1125
     * @date 2016年3月4日上午11:14:13
     */
    public static Map<String, Object> jsonToMap(JSONObject object) {
        if (object == null) {
            return null;
        }
        Map<String, Object> treeMap = new TreeMap<String, Object>();
        try {
            Iterator<String> it = object.keys();
            while (it.hasNext()) {
                String key = it.next().toString();
                Object value;
                value = object.get(key);
                treeMap.put(key, value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return treeMap;
    }

    /**
     * <p>
     * Title: toJsonString
     * </p>
     * <p>
     * Description:object转换为jsonString
     * </p>
     * <p>
     * 注意 该方法仅针对简单的object
     * </p>
     *
     * @param object
     * @return
     * @author xwc1125
     * @date 2016年3月30日上午9:05:43
     */
    @SuppressWarnings("unchecked")
    public static String toJsonString(Object object) {
        String result = null;
        try {
            Class<?> objectClass = Class.forName(object.getClass().getName());
            Field[] fields = objectClass.getDeclaredFields();
            JSONObject jsonObject = new JSONObject();
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);
                String name = field.getName();
                Object value = field.get(object);
                Class<?> type = field.getType();
                if (type.equals(ArrayList.class)) {
                    JSONArray jsonArray = new JSONArray();
                    ArrayList<Object> arrayList = (ArrayList<Object>) value;
                    if (arrayList != null) {
                        for (int j = 0; j < arrayList.size(); j++) {
                            Object objValue = arrayList.get(j);
//                            Class<? extends Object> itemClass = objValue
//                                    .getClass();
                            jsonArray.put(toJsonString(objValue));
                        }
                    }
                    jsonObject.put(name, jsonArray);
                } else {
                    jsonObject.put(name, value);
                }
            }
            result = jsonObject.toString();
        } catch (Exception e) {
            LogUtils.e(TAG, e.getMessage(), isDebug);
        }
        return result;
    }

    /**
     * json字符串转成javaBean
     *
     * @param jsonStr
     * @param clz
     * @return
     * @throws JSONException
     */
    public static Object jsonToBean(String jsonStr, Class clz) throws JSONException {
        if (jsonStr.startsWith("[")) {
            JSONArray jsonArray = new JSONArray(jsonStr);
            ArrayList<Object> list = new ArrayList<Object>();
            for (int i = 0, len = jsonArray.length(); i < len; i++) {
                Object jsonArrayItem = jsonArray.get(i);
                if (jsonArrayItem instanceof JSONObject) {
                    Object object = jsonToBean(jsonArray.getJSONObject(i), clz);
                    list.add(object);
                } else {
                    list.add(jsonArrayItem);
                }
            }
            return list;
        } else {
            JSONObject jsonObject = new JSONObject(jsonStr);
            return jsonToBean(jsonObject, clz);
        }
    }

    /**
     * JSONObject对象转JavaBean
     *
     * @param json
     * @param cls  JavaBean的class
     * @return 转换结果（异常情况下返回null）
     */
    public static Object jsonToBean(JSONObject json, Class cls) {
        Field[] fields = cls.getDeclaredFields();//获取自己声明的各种字段
        for (Field f : fields) {
            Class fc = f.getType();
            if (fc.isPrimitive()) {
                String type = fc.getName();
                System.out.println("基本数据类型： " + f.getName() + "  " + fc.getName());
            } else {
                if (fc.isAssignableFrom(List.class)) { //判断是否为List
                    String type = fc.getName();
                    System.out.println("List类型：" + f.getName());
                    Type gt = f.getGenericType();//得到泛型类型
                    ParameterizedType pt = (ParameterizedType) gt;
                    Class nei = (Class) pt.getActualTypeArguments()[0];//List<T>,T的类
                    System.out.println("\t\t" + nei.getName());
                }
            }
        }

        Object obj = null;
        try {
            obj = cls.newInstance();
            // 取出Bean里面的所有方法
            Method[] methods = cls.getMethods();
            for (int i = 0; i < methods.length; i++) {
                // 取出方法名
                String methodName = methods[i].getName();
                // 取出方法的类型
                Class[] clss = methods[i].getParameterTypes();

                if (clss.length != 1) {
                    continue;
                }

                // 若是方法名不是以set开始的则退出本次循环
                if (methodName.indexOf("set") < 0) {
                    continue;
                }

                Class cl = methods[i].getDeclaringClass();
                Type returnType = methods[i].getGenericReturnType();//返回的类型
                Type[] parameterTypes = methods[i].getGenericParameterTypes();//传入的参数个数及其类型
                //ParameterizedType pt = (ParameterizedType) parameterTypes[0];
                //Class nei = (Class) pt.getActualTypeArguments()[0];//List<T>,T的类

                // 类型
                String type = clss[0].getName();
                String key = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);

                // 如果map里有该key
                if (json.has(key) && json.get(key) != null) {
                    setValue(type, json.get(key), methods[i], obj, clss[0]);
                }
            }
        } catch (Exception ex) {
            LogUtils.e(TAG, "JSONObject转JavaBean失败:" + ex.getMessage(), isDebug);
        }

        return obj;
    }

    /**
     * 给JavaBean的每个属性设值
     *
     * @param type   类型
     * @param value  值
     * @param method 方法
     * @param bean   对象
     * @param cls
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    private static void setValue(String type, Object value, Method method, Object bean, Class cls) {
        if (value != null && !"".equals(value)) {
            try {
                //===============基础类型=======================
                invokeJavaBaseType(method, type, value, bean);
                //===============基础类型[end]=======================
                //===============java.lang=====================
                if (type.startsWith("java.lang") || type.startsWith("[Ljava.lang")) {
                    invokeJavaLang(method, type, value, bean);
                }
                //===============java.lang[end]=====================

                //=================java.math========================
                else if (type.startsWith("java.math") || type.startsWith("[Ljava.math")) {
                    invokeJavaMath(method, type, value, bean);
                }
                //=================java.math========================

                //==================java.sql========================
                else if (type.startsWith("java.sql") || type.startsWith("[Ljava.sql")) {
                    invokeJavaSql(method, type, value, bean);
                }
                //==================java.sql[end]========================

                //==================java.util========================
                else if (type.startsWith("java.util") || type.startsWith("[Ljava.util")) {
                    invokeJavaUtil(method, type, value, bean);
                }
                //==================java.util[end]========================

                //=================自定义bean对象类型==================
                else {
                    //自定义的对象
                    Object beanObject = jsonToBean(value.toString(), cls);
                    method.invoke(bean, beanObject);
                }
                //=================自定义bean对象类型[end]==================
            } catch (Exception ex) {
                LogUtils.e(TAG, "JSONObject赋值给JavaBean失败:" + ex.getMessage(), isDebug);
            }
        }
    }

    /**
     * java基础类型的反射
     *
     * @param method 方法
     * @param type   类型
     * @param value  值
     * @param bean   对象
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws JSONException
     */
    private static void invokeJavaBaseType(Method method, String type, Object value, Object bean) throws IllegalAccessException, InvocationTargetException, JSONException {
        if ("int".equals(type)) {
            method.invoke(bean, new Object[]{new Integer("" + value)});
        } else if ("double".equals(type)) {
            method.invoke(bean, new Object[]{new Double("" + value)});
        } else if ("float".equals(type)) {
            method.invoke(bean, new Object[]{new Float("" + value)});
        } else if ("long".equals(type)) {
            method.invoke(bean, new Object[]{new Long("" + value)});
        } else if ("int".equals(type)) {
            method.invoke(bean, new Object[]{new Integer("" + value)});
        } else if ("boolean".equals(type)) {
            method.invoke(bean, new Object[]{Boolean.valueOf("" + value)});
        } else if ("byte".equals(type)) {
            method.invoke(bean, new Object[]{Byte.valueOf("" + value)});
        } else if ("char".equals(type)) {
            char[] ch = value.toString().toCharArray();
            method.invoke(bean, new Object[]{ch[0]});
        } else if ("short".equals(type)) {
            method.invoke(bean, new Object[]{Short.valueOf("" + value)});
        } else if ("[I".equals(type)) {
            JSONArray jsonArray = (JSONArray) (value);
            int[] intArray = new int[jsonArray.length()];
            for (int i = 0, len = jsonArray.length(); i < len; i++) {
                intArray[i] = (int) jsonArray.get(i);
            }
            method.invoke(bean, new Object[]{intArray});
        } else if ("[D".equals(type)) {
            JSONArray jsonArray = (JSONArray) (value);
            double[] paramsArray = new double[jsonArray.length()];
            for (int i = 0, len = jsonArray.length(); i < len; i++) {
                paramsArray[i] = Double.parseDouble(jsonArray.get(i) + "");
            }
            method.invoke(bean, new Object[]{paramsArray});
        } else if ("[F".equals(type)) {
            JSONArray jsonArray = (JSONArray) (value);
            float[] paramsArray = new float[jsonArray.length()];
            for (int i = 0, len = jsonArray.length(); i < len; i++) {
                paramsArray[i] = Float.parseFloat(jsonArray.get(i) + "");
            }
            method.invoke(bean, new Object[]{paramsArray});
        } else if ("[J".equals(type)) {
            JSONArray jsonArray = (JSONArray) (value);
            long[] paramsArray = new long[jsonArray.length()];
            for (int i = 0, len = jsonArray.length(); i < len; i++) {
                paramsArray[i] = Long.parseLong(jsonArray.get(i) + "");
            }
            method.invoke(bean, new Object[]{paramsArray});
        } else if ("[Z".equals(type)) {
            JSONArray jsonArray = (JSONArray) (value);
            boolean[] paramsArray = new boolean[jsonArray.length()];
            for (int i = 0, len = jsonArray.length(); i < len; i++) {
                paramsArray[i] = (boolean) jsonArray.get(i);
            }
            method.invoke(bean, new Object[]{paramsArray});
        } else if ("[B".equals(type)) {
            JSONArray jsonArray = (JSONArray) (value);
            byte[] paramsArray = new byte[jsonArray.length()];
            for (int i = 0, len = jsonArray.length(); i < len; i++) {
                Integer integer = jsonArray.getInt(i);
                paramsArray[i] = integer.byteValue();
            }
            method.invoke(bean, new Object[]{paramsArray});
        } else if ("[C".equals(type)) {
            JSONArray jsonArray = (JSONArray) (value);
            char[] paramsArray = new char[jsonArray.length()];
            for (int i = 0, len = jsonArray.length(); i < len; i++) {
                char[] ch = jsonArray.get(i).toString().toCharArray();
                paramsArray[i] = ch[0];
            }
            method.invoke(bean, new Object[]{paramsArray});
        } else if ("[S".equals(type)) {
            JSONArray jsonArray = (JSONArray) (value);
            short[] paramsArray = new short[jsonArray.length()];
            for (int i = 0, len = jsonArray.length(); i < len; i++) {
                paramsArray[i] = Short.parseShort(jsonArray.get(i) + "");
            }
            method.invoke(bean, new Object[]{paramsArray});
        }
    }

    /**
     * 判断是否是基础类型或基础类型的数组
     *
     * @param type
     * @return
     */
    public static boolean isJavaBaseType(Class type) {
        if (type.isPrimitive()) {
            return true;
        } else if ("[I".equals(type.getName())) {
            return true;
        } else if ("[D".equals(type.getName())) {
            return true;
        } else if ("[F".equals(type.getName())) {
            return true;
        } else if ("[J".equals(type.getName())) {
            return true;
        } else if ("[Z".equals(type.getName())) {
            return true;
        } else if ("[B".equals(type.getName())) {
            return true;
        } else if ("[C".equals(type.getName())) {
            return true;
        } else if ("[S".equals(type)) {
            return true;
        }
        return false;
    }

    /**
     * java.util类型的反射
     *
     * @param method 方法
     * @param type   类型
     * @param value  值
     * @param bean   对象
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws JSONException
     */
    private static void invokeJavaUtil(Method method, String type, Object value, Object bean) throws ParseException, IllegalAccessException, InvocationTargetException, JSONException {
        /////////////////////Date////////////////////
        if ("java.util.Date".equals(type)) {
            Date date = null;
            if ("String".equals(value.getClass().getSimpleName())) {
                String time = String.valueOf(value);
                String format = null;
                try {
                    SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
                    date = sdf1.parse(time);
                } catch (Exception e) {
                    if (time.indexOf(":") > 0) {
                        if (time.indexOf(":") == time.lastIndexOf(":")) {
                            format = "yyyy-MM-dd H:mm";
                        } else {
                            format = "yyyy-MM-dd H:mm:ss";
                        }
                    } else {
                        format = "yyyy-MM-dd";
                    }
                    SimpleDateFormat sf = new SimpleDateFormat();
                    sf.applyPattern(format);
                    date = sf.parse(time);
                }
            } else {
                date = (Date) value;
            }

            if (date != null) {
                method.invoke(bean, new Object[]{date});
            }
        }

        /////////////////////List////////////////////
        else if ("java.util.List".equals(type)) {
            JSONArray jsonArray = (JSONArray) (value);
            List<Object> list = new ArrayList<>();
            for (int i = 0, len = jsonArray.length(); i < len; i++) {
                list.add(jsonArray.get(i));
            }
            method.invoke(bean, new Object[]{list});
        } else if ("java.util.ArrayList".equals(type)) {
            JSONArray jsonArray = (JSONArray) (value);
            ArrayList<Object> list = new ArrayList<>();
            for (int i = 0, len = jsonArray.length(); i < len; i++) {
                list.add(jsonArray.get(i));
            }
            method.invoke(bean, new Object[]{list});
        }

        /////////////////////map////////////////////
        else if ("java.util.TreeMap".equals(type)) {
            JSONObject jsonObject = (JSONObject) (value);
            TreeMap treeMap = new TreeMap();
            Iterator iterator = jsonObject.keys();
            while (iterator.hasNext()) {
                Object key = iterator.next();
                Object keyValues = jsonObject.get(key + "");
                treeMap.put(key, keyValues);
            }
            method.invoke(bean, new Object[]{treeMap});
        } else if ("java.util.HashMap".equals(type) || "java.util.Map".equals(type)) {
            JSONObject jsonObject = (JSONObject) (value);
            HashMap hashMap = new HashMap();
            Iterator iterator = jsonObject.keys();
            while (iterator.hasNext()) {
                Object key = iterator.next();
                Object keyValues = jsonObject.get(key + "");
                hashMap.put(key, keyValues);
            }
            method.invoke(bean, new Object[]{hashMap});
        }
    }

    /**
     * java.sql类型的反射
     *
     * @param method 方法
     * @param type   类型
     * @param value  值
     * @param bean   对象
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws JSONException
     */
    private static void invokeJavaSql(Method method, String type, Object value, Object bean) throws ParseException, IllegalAccessException, InvocationTargetException {
        if ("java.sql.Date".equals(type)) {
            Date date = null;
            if ("String".equals(value.getClass().getSimpleName())) {
                String time = String.valueOf(value);
                String format = null;
                try {
                    SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
                    date = sdf1.parse(time);
                } catch (Exception e) {
                    if (time.indexOf(":") > 0) {
                        if (time.indexOf(":") == time.lastIndexOf(":")) {
                            format = "yyyy-MM-dd H:mm";
                        } else {
                            format = "yyyy-MM-dd H:mm:ss";
                        }
                    } else {
                        format = "yyyy-MM-dd";
                    }
                    SimpleDateFormat sf = new SimpleDateFormat();
                    sf.applyPattern(format);
                    date = new Date(sf.parse(time).getTime());
                }
            } else {
                date = (Date) value;
            }

            if (date != null) {
                method.invoke(bean, new Object[]{date});
            }
        } else if ("java.sql.Timestamp".equals(type)) {
            Timestamp timestamp = null;
            if ("String".equals(value.getClass().getSimpleName())) {
                String time = String.valueOf(value);
                String format = null;
                try {
                    SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
                    new Timestamp(sdf1.parse(time).getTime());
                } catch (Exception e) {
                    if (time.indexOf(":") > 0) {
                        if (time.indexOf(":") == time.lastIndexOf(":")) {
                            format = "yyyy-MM-dd H:mm";
                        } else {
                            format = "yyyy-MM-dd H:mm:ss";
                        }
                    } else {
                        format = "yyyy-MM-dd";
                    }
                    SimpleDateFormat sf = new SimpleDateFormat();
                    sf.applyPattern(format);
                    timestamp = new Timestamp(sf.parse(time).getTime());
                }
            } else {
                timestamp = (Timestamp) value;
            }

            if (timestamp != null) {
                method.invoke(bean, new Object[]{timestamp});
            }
        }
    }

    /**
     * java.math类型的反射
     *
     * @param method 方法
     * @param type   类型
     * @param value  值
     * @param bean   对象
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws JSONException
     */
    private static void invokeJavaMath(Method method, String type, Object value, Object bean) throws IllegalAccessException, InvocationTargetException {
        if ("java.math.BigDecimal".equals(type)) {
            method.invoke(bean, new Object[]{new BigDecimal("" + value)});
        } else if ("[Ljava.math.BigDecimal;".equals(type)) {
            method.invoke(bean, new Object[]{getArrayObject(value, BigDecimal.class)});
        } else if ("java.math.BigInteger".equals(type)) {
            method.invoke(bean, new Object[]{new BigInteger("" + value)});
        } else if ("[Ljava.math.BigInteger;".equals(type)) {
            method.invoke(bean, new Object[]{getArrayObject(value, BigInteger.class)});
        }
    }

    /**
     * 判断是否是java.math类型或数组
     *
     * @param type
     * @return
     */
    public static boolean isJavaMath(Class type) {
        if ("java.math.BigDecimal".equals(type.getName())) {
            return true;
        } else if ("[Ljava.math.BigDecimal;".equals(type.getName())) {
            return true;
        } else if ("java.math.BigInteger".equals(type.getName())) {
            return true;
        } else if ("[Ljava.math.BigInteger;".equals(type.getName())) {
            return true;
        }
        return false;
    }

    /**
     * java.lang类型的反射
     *
     * @param method 方法
     * @param type   类型
     * @param value  值
     * @param bean   对象
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws JSONException
     */
    private static void invokeJavaLang(Method method, String type, Object value, Object bean) throws IllegalAccessException, InvocationTargetException {
        if ("java.lang.String".equals(type)) {
            method.invoke(bean, new Object[]{value});
        } else if ("java.lang.Integer".equals(type)) {
            method.invoke(bean, new Object[]{new Integer("" + value)});
        } else if ("java.lang.Double".equals(type)) {
            method.invoke(bean, new Object[]{new Double("" + value)});
        } else if ("java.lang.Float".equals(type)) {
            method.invoke(bean, new Object[]{new Float("" + value)});
        } else if ("java.lang.Long".equals(type)) {
            method.invoke(bean, new Object[]{new Long("" + value)});
        } else if ("java.lang.Integer".equals(type)) {
            method.invoke(bean, new Object[]{new Integer("" + value)});
        } else if ("java.lang.Boolean".equals(type)) {
            method.invoke(bean, new Object[]{Boolean.valueOf("" + value)});
        } else if ("java.lang.Byte".equals(type)) {
            method.invoke(bean, new Object[]{Byte.valueOf("" + value)});
        } else if ("java.lang.Character".equals(type)) {
            char[] ch = value.toString().toCharArray();
            method.invoke(bean, new Object[]{ch[0]});
        } else if ("java.lang.Short".equals(type)) {
            method.invoke(bean, new Object[]{Short.valueOf("" + value)});
        } else if ("[Ljava.lang.String;".equals(type)) {
            method.invoke(bean, new Object[]{getArrayObject(value, String.class)});
        } else if ("[Ljava.lang.Integer;".equals(type)) {
            method.invoke(bean, new Object[]{getArrayObject(value, Integer.class)});
        } else if ("[Ljava.lang.Double;".equals(type)) {
            method.invoke(bean, new Object[]{getArrayObject(value, Double.class)});
        } else if ("[Ljava.lang.Float;".equals(type)) {
            method.invoke(bean, new Object[]{getArrayObject(value, Float.class)});
        } else if ("[Ljava.lang.Long;".equals(type)) {
            method.invoke(bean, new Object[]{getArrayObject(value, Long.class)});
        } else if ("[Ljava.lang.Boolean;".equals(type)) {
            method.invoke(bean, new Object[]{getArrayObject(value, Boolean.class)});
        } else if ("[Ljava.lang.Byte;".equals(type)) {
            method.invoke(bean, new Object[]{getArrayObject(value, Byte.class)});
        } else if ("[Ljava.lang.Character;".equals(type)) {
            method.invoke(bean, new Object[]{getArrayObject(value, Character.class)});
        } else if ("[Ljava.lang.Short;".equals(type)) {
            method.invoke(bean, new Object[]{getArrayObject(value, Short.class)});
        }
    }

    /**
     * 判断是否是java.lang的基本类型或其数组
     *
     * @param type
     * @return
     */
    public static boolean isJavaLang(Class type) {
        if ("java.lang.String".equals(type.getName())) {
            return true;
        } else if ("java.lang.Integer".equals(type.getName())) {
            return true;
        } else if ("java.lang.Double".equals(type.getName())) {
            return true;
        } else if ("java.lang.Float".equals(type.getName())) {
            return true;
        } else if ("java.lang.Long".equals(type.getName())) {
            return true;
        } else if ("java.lang.Integer".equals(type.getName())) {
            return true;
        } else if ("java.lang.Boolean".equals(type.getName())) {
            return true;
        } else if ("java.lang.Byte".equals(type.getName())) {
            return true;
        } else if ("java.lang.Character".equals(type.getName())) {
            return true;
        } else if ("java.lang.Short".equals(type.getName())) {
            return true;
        } else if ("[Ljava.lang.String;".equals(type.getName())) {
            return true;
        } else if ("[Ljava.lang.Integer;".equals(type.getName())) {
            return true;
        } else if ("[Ljava.lang.Double;".equals(type.getName())) {
            return true;
        } else if ("[Ljava.lang.Float;".equals(type.getName())) {
            return true;
        } else if ("[Ljava.lang.Long;".equals(type.getName())) {
            return true;
        } else if ("[Ljava.lang.Boolean;".equals(type.getName())) {
            return true;
        } else if ("[Ljava.lang.Byte;".equals(type.getName())) {
            return true;
        } else if ("[Ljava.lang.Character;".equals(type.getName())) {
            return true;
        } else if ("[Ljava.lang.Short;".equals(type.getName())) {
            return true;
        }
        return false;
    }

    /**
     * 将Model转换成JSONObject
     *
     * @param object
     * @return
     */
    @SuppressWarnings("unchecked")
    public static JSONObject toJson(Object object) {
        JSONObject jsonObject = new JSONObject();
        try {
//            Class<?> clazz = Class.forName(object.getClass().getName());
            Class clazz = object.getClass();
            //再来获取此类中的所有字段
            Field[] fields = clazz.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                try {
                    Field field = fields[i];
                    //获取字段的修饰符
                    //int fieldValue = field.getModifiers();//如：private、static、final等
                    //与某个具体的修饰符进行比较
                    //Modifier.isStatic(fieldValue);//看此修饰符是否为静态(static)
                    //获取字段的声明类型
                    //field.getType();//返回的是一个class
                    //与某个类型进行比较
                    //field.getType() == Timestamp.class
                    //获取指定对象中此字段的值
                    //Object fieldObject = field.get(user);//user可以看做是从数据库中查找出来的对象
                    field.setAccessible(true);
                    //获取字段的名称
                    String name = field.getName();
                    Object value = field.get(object);
                    Class<?> type = field.getType();
                    if (value != null) {
                        //List
                        if (type.equals(ArrayList.class) || type.equals(List.class)
                                || type.equals(LinkedList.class) || type.equals(LinkedHashMap.class)
                                || type.equals(LinkedHashSet.class)) {
                            Class clsNei = null;
                            try {
                                Type gt = field.getGenericType();//得到泛型类型
                                ParameterizedType pt = (ParameterizedType) gt;
                                clsNei = (Class) pt.getActualTypeArguments()[0];//List<T>,T的类
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            JSONArray jsonArray = new JSONArray();
                            ArrayList<Object> arrayList = (ArrayList<Object>) value;
                            if (arrayList != null) {
                                for (int j = 0; j < arrayList.size(); j++) {
                                    Object objValue = arrayList.get(j);
                                    //判断clsNei是基础类型还是自定义类型
                                    if (isPrimitiveAll(clsNei)) {
                                        jsonArray.put(objValue);
                                    } else {
                                        jsonArray.put(toJson(objValue));
                                    }
                                }
                            }
                            jsonObject.put(name, jsonArray);
                        } else {
                            //判断是基础类型还是自定义类型
                            if (isPrimitiveAll(type)) {
                                jsonObject.put(name, value);
                            } else {
                                jsonObject.put(name, toJson(value));
                            }
                        }
                    }
//                try {
//                    jsonObject.put(field.getName(), invokeGetMethod(clazz, field.getName(), object));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private static boolean isPrimitiveAll(Class clsNei) {
        //java.lang
        if (clsNei.isPrimitive()) {
            return true;
        }
        if (isJavaBaseType(clsNei)) {
            return true;
        }
        if (isJavaLang(clsNei)) {
            return true;
        }
        //java.math
        if (clsNei.isAssignableFrom(BigDecimal.class) || clsNei.isAssignableFrom(BigInteger.class)) {
            return true;
        }
        if (isJavaMath(clsNei)) {
            return true;
        }
        if (clsNei.getName().contains("java.math")) {
            return true;
        }
        //java.sql
        if (clsNei.isAssignableFrom(java.sql.Date.class) || clsNei.isAssignableFrom(Timestamp.class)) {
            return true;
        }
        if (clsNei.getName().contains("java.sql")) {
            return true;
        }
        //java.util
        if (clsNei.isAssignableFrom(Date.class)) {
            return true;
        }
        if (clsNei.isAssignableFrom(List.class) || clsNei.isAssignableFrom(ArrayList.class)
                || clsNei.isAssignableFrom(LinkedList.class)) {
            return true;
        }
        if (clsNei.isAssignableFrom(Map.class) || clsNei.isAssignableFrom(HashMap.class)
                || clsNei.isAssignableFrom(TreeMap.class)) {
            return true;
        }

        if (clsNei.getName().contains("java.util")) {
            return true;
        }

        return false;
    }


    /**
     * 将list转换成JSONArray
     *
     * @param list
     * @return
     * @throws Exception
     */
    public static JSONArray toJsonArray(List list) throws Exception {
        JSONArray array = null;
        if (list.isEmpty()) {
            return array;
        }
        array = new JSONArray();
        for (Object o : list) {
            array.put(toJson(o));
        }
        return array;
    }

    /**
     * 反射get方法
     *
     * @param c
     * @param fieldName
     * @param o
     * @return
     */
    @SuppressWarnings("unchecked")
    private static Object invokeGetMethod(Class c, String fieldName, Object o) {
        String methodName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        Method method = null;
        try {
            method = c.getMethod("get" + methodName);
            return method.invoke(o);
        } catch (Exception e) {
            LogUtils.e(TAG, e.getMessage(), isDebug);
            return "";
        }
    }

    /**
     * 获取数据内容
     *
     * @param value
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> T[] getArrayObject(Object value, Class<T> clz) {
        try {
            JSONArray jsonArray = (JSONArray) (value);
            T[] objectArray = null;
            if (clz.isAssignableFrom(Integer.class)) {
                objectArray = (T[]) (new Integer[jsonArray.length()]);
            } else if (clz.isAssignableFrom(String.class)) {
                objectArray = (T[]) (new String[jsonArray.length()]);
            } else if (clz.isAssignableFrom(Double.class)) {
                objectArray = (T[]) (new Double[jsonArray.length()]);
            } else if (clz.isAssignableFrom(Float.class)) {
                objectArray = (T[]) (new Float[jsonArray.length()]);
            } else if (clz.isAssignableFrom(Long.class)) {
                objectArray = (T[]) (new Long[jsonArray.length()]);
            } else if (clz.isAssignableFrom(Boolean.class)) {
                objectArray = (T[]) (new Boolean[jsonArray.length()]);
            } else if (clz.isAssignableFrom(Character.class)) {
                objectArray = (T[]) (new Character[jsonArray.length()]);
            } else if (clz.isAssignableFrom(Byte.class)) {
                objectArray = (T[]) (new Byte[jsonArray.length()]);
            } else if (clz.isAssignableFrom(Short.class)) {
                objectArray = (T[]) (new Short[jsonArray.length()]);
            }
            //=================java.math===============
            else if (clz.isAssignableFrom(BigDecimal.class)) {
                objectArray = (T[]) (new BigDecimal[jsonArray.length()]);
            } else if (clz.isAssignableFrom(BigInteger.class)) {
                objectArray = (T[]) (new BigInteger[jsonArray.length()]);
            }
            //=================java.math[end]===============
            for (int i = 0, len = jsonArray.length(); i < len; i++) {
                if (objectArray == null) {
                    return objectArray;
                }
                Object obj = jsonArray.get(i);
                if (clz.isAssignableFrom(Float.class)) {
                    Float f = Float.parseFloat(obj.toString());
                    objectArray[i] = (T) f;
                } else if (clz.isAssignableFrom(Long.class)) {
                    Long f = Long.parseLong(obj.toString());
                    objectArray[i] = (T) f;
                } else if (clz.isAssignableFrom(Character.class)) {
                    char[] ch = obj.toString().toCharArray();
                    Character c = ch[0];
                    objectArray[i] = (T) c;
                } else if (clz.isAssignableFrom(Byte.class)) {
                    Integer integer = jsonArray.getInt(i);
                    Byte b = integer.byteValue();
                    objectArray[i] = (T) b;
                } else if (clz.isAssignableFrom(Short.class)) {
                    Short s = Short.parseShort(jsonArray.get(i) + "");
                    objectArray[i] = (T) s;
                }
                //=================java.math===============
                else if (clz.isAssignableFrom(BigDecimal.class)) {
                    BigDecimal s = new BigDecimal(jsonArray.get(i) + "");
                    objectArray[i] = (T) s;
                } else if (clz.isAssignableFrom(BigInteger.class)) {
                    BigInteger s = new BigInteger(jsonArray.get(i) + "");
                    objectArray[i] = (T) s;
                }
                //=================java.math[end]===============
                else {
                    objectArray[i] = ((T) obj);
                }
            }
            return objectArray;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
