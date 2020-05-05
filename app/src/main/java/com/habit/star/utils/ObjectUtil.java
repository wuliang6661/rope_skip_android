package com.habit.star.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ObjectUtil {

	public static HashMap<String, String> objToHashMapValueString (Object c) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			if (c != null) {
				Field[] fields = c.getClass().getDeclaredFields();
				for (Field f : fields) {
					f.setAccessible(true);
					Object value;
					if ((value = f.get(c)) != null) {
						map.put(f.getName(), value.toString());
					} else {
						map.put(f.getName(), "");
					}
				}
			}
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		}
		return map;
	}
	
	public static HashMap<String, Object> objToHashMapValueObject (Object c) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			if (c != null) {
				Field[] fields = c.getClass().getDeclaredFields();
				for (Field f : fields) {
					f.setAccessible(true);
					Object value;
					if ((value = f.get(c)) != null) {
						map.put(f.getName(), value);
					} else {
						map.put(f.getName(), null);
					}
				}
			}
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		}
		return map;
	}

	/**
	 * 将复杂对象转换为map
	 * @param c
	 * @return
	 */
	public static HashMap<String, Object> objToHashMapValueObjectFor (Object c) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			if (c != null) {
				Field[] fields = c.getClass().getDeclaredFields();
				for (Field f : fields) {
					f.setAccessible(true);
					Object value;
					if ((value = f.get(c)) != null) {
//						if(value instanceof Integer||value instanceof Boolean||value instanceof Double){
//							map.put(f.getName(),value);
//						}else if(value instanceof ArrayList){
//							ArrayList list= new ArrayList();
//							for(Object obj:(ArrayList)value){
//								list.add(objToHashMapValueObjectFor(obj));
//							}
//							map.put(f.getName(),list);
//						}else {
//							map.put(f.getName(), objToHashMapValueString(value));
//						}
						if(value instanceof ArrayList){
							ArrayList list= new ArrayList();
							for(Object obj:(ArrayList)value){
								list.add(objToHashMapValueObjectFor(obj));
							}
							map.put(f.getName(),list);
						}else {
							map.put(f.getName(), value);
						}
					} else {
						map.put(f.getName(), null);
					}
				}
			}
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		}
		return map;
	}

	
	/**
     * 循环向上转型, 获取对象的 DeclaredField
     * @param c : 类
     * @param list : 属性队列
     */
    
    public static void getDeclaredFieldList(ArrayList<Field> list, Class<?> c){
        Class<?> clazz = c;
        if (clazz.getSuperclass() != Object.class) {
            getDeclaredFieldList(list, clazz.getSuperclass());
        }
        List<Field> l = Arrays.asList(clazz.getDeclaredFields());
        if (l != null) {
            list.addAll(l);
        }
    }   
	
	/**
     * 循环向上转型, 获取对象的 DeclaredField
     * @param object : 子类对象
     * @param list : 属性队列
     */
    
    public static void getDeclaredFieldList(ArrayList<Field> list, Object object){
        getDeclaredFieldList(list, object.getClass());
    }   
    
    /**
     * 循环向上转型, 获取对象的 DeclaredField
     * @param c : 类
     * @param fieldName : 属性名
     * @return 该属性
     */
    
    public static Field getDeclaredField(Class<?> c, String fieldName){
        Class<?> clazz = c;
        try {
            return c.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            return getDeclaredField(clazz.getSuperclass(), fieldName);
        }
    }   
    
    /**
     * 循环向上转型, 获取对象的 DeclaredField
     * @param object : 子类对象
     * @param fieldName : 属性名
     * @return 属性
     */
    
    public static Field getDeclaredField(Object object, String fieldName){
        return getDeclaredField(object.getClass(), fieldName);
    }
}
