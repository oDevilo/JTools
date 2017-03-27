package org.devil.jtools.base;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * 工具类
 *  
 * @author Devil
 */
public class TransUtils {
    
    
    /**
     * obj是否在arr中
     * @param obj
     * @param arr
     * @return
     */
    public static boolean objIn(Object obj,Object... arr){
        if(arr == null){
            return false;
        }
        for(Object val : arr){
            if(obj == val || (null != obj && obj.equals(val))){
                return true;
            }
        }
        return false;
    }
    
    
    public static Map<String, Object> transBean2Map(Object obj) {

        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    map.put(key, value);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T  transMap2Bean(Map<String, Object> map, Class<T> clazz) {

        if (map == null) {
            return null;
        }
        Object obj = null;
        try {
            obj = clazz.newInstance();
            Set<String> keys = map.keySet();
            for (String key : keys) {
                // 过滤class属性
                if (!key.equals("class")) {
                    Object value = map.get(key);
                    try {
                        Field field = clazz.getDeclaredField(key);
                        if (field != null) {
                            field.setAccessible(true);
                            field.set(obj, value);
                        }
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) obj;
    }
    
    @SuppressWarnings("unchecked")
    public static <V> V transBean(Object obj, Class<V> toClass) {
        
        if (obj == null) {
            return null;
        }
        Object toObj = null;
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            toObj = toClass.newInstance();
            
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    try {
                        Field field = toClass.getDeclaredField(key);
                        if (field != null) {
                            field.setAccessible(true);
                            field.set(toObj, value);
                        }
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                }
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (V) toObj;
    }
}
