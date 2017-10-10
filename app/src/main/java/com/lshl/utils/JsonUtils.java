package com.lshl.utils;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 作者：吕振鹏
 * 创建时间：08月23日
 * 时间：19:37
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */
public class JsonUtils {

    private static final String TAG = JsonUtils.class.toString();

    public static <T> List<T> json2Array(Context context, JSONArray jsonArray, int resStringArray, Class<T> cls) throws Exception {

        Map<String, String> fieldsMap = getFieldMap(context, resStringArray);

        List<T> array = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            array.add(json2Object(fieldsMap, jsonObject, cls));
        }

        return array;
    }


    public static <T> T json2Object(Context context, JSONObject jsonObject, int resStringArray, Class<T> cls) throws IllegalAccessException, InstantiationException, NoSuchFieldException, JSONException, NoSuchMethodException, InvocationTargetException {


        Map<String, String> fieldsMap = getFieldMap(context, resStringArray);

        return json2Object(fieldsMap, jsonObject, cls);
    }


    private static Map<String, String> getFieldMap(Context context, int resStringArray) {
        Map<String, String> fieldsMap = new HashMap<>();
        String[] configField = context.getResources().getStringArray(resStringArray);

        for (String aConfigField : configField) {
            String[] objectFields = aConfigField.split(",");
            fieldsMap.put(objectFields[0], objectFields[1]);
        }
        return fieldsMap;
    }

    private static <T> T json2Object(Map<String, String> fieldsMap, JSONObject jsonObject, Class<T> cls) throws IllegalAccessException, InstantiationException, NoSuchFieldException, JSONException, NoSuchMethodException, InvocationTargetException {
        T object = null;
        Constructor[] constructors = cls.getConstructors();
        for (Constructor constructor : constructors) {
            //获取构造方法中的参数类型
            Class[] sClasses = constructor.getParameterTypes();
            //当构造方法中没有参数时，直接创建一个无参的构造方法
            if (sClasses.length <= 0) {
                object = cls.newInstance();
            } else {
                for (Class class1 : sClasses) {
                    Log.e(TAG, "-----不要用带有构造方法的实体类---- 当前构造方法中的类型为---" + class1.getSimpleName());
                }
                return null;
            }
        }
        Set<String> fieldKey = fieldsMap.keySet();
        for (String field : fieldKey) {
            String jsonKey = fieldsMap.get(field);
            if (jsonObject.has(jsonKey)) {
                String value = jsonObject.getString(jsonKey);
                Field nameField = cls.getDeclaredField(field);
                // 如果是 private 或者 package 权限的，一定要赋予其访问权限
                nameField.setAccessible(true);
                nameField.set(object, value);
            }
        }

        return object;
    }

}
