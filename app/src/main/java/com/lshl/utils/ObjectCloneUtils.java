package com.lshl.utils;

import java.lang.reflect.Field;

/**
 * 作者：吕振鹏
 * 创建时间：09月07日
 * 时间：17:39
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */

public class ObjectCloneUtils {

    /**
     * 将一个对象中所有的属性都克隆到另外一个对象中
     * 特别注意，需要克隆的对象，也就是数据源，必须要小于等于最终克隆形成的对象，
     * 也就是说，要比相应对象的属性少，而且源对象中有的所有成员变量，目的对象也必须有
     *
     * @param objective 目的对象
     * @param source    提供数据的源对象
     * @param <T1>
     * @param <T2>
     */
    public static <T1, T2> T1 Object2Object(Class<T1> objective, T2 source) {
        T1 objectiveObj = null;
        try {
            objectiveObj = objective.newInstance();
            Class sourceCls = source.getClass();
            Field[] sourceFields = sourceCls.getDeclaredFields();
            for (Field sourceField : sourceFields) {
                sourceField.setAccessible(true);
                if (sourceField.getType() == String.class) {
                   try {
                       Field objectiveField = objective.getDeclaredField(sourceField.getName());//根据提供数据源的成员变量名称，获取出目的对象的成员变量对象
                       objectiveField.setAccessible(true);
                       objectiveField.set(objectiveObj, sourceField.get(source));
                   }catch (Exception e){
                       e.printStackTrace();
                   }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return objectiveObj;
    }


}
