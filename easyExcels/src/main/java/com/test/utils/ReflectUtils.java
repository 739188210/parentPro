package com.test.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;

/**
 * 反射demo
 */
public class ReflectUtils {

    public static void main(String[] args) throws NoSuchMethodException {
        Student student = new Student();
        student.setName("lili");
        student.setAge(11);
        getObj(student);
    }

    /**
     *
     *
     * @param entity T
     */
    public static <T> Object getFieldValue(T entity, String filedName) throws InvocationTargetException, IllegalAccessException {
        Object obj = null;
        Class<?> tClass = entity.getClass();
        Method[] declaredMethods = tClass.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            String name = declaredMethod.getName();
            if (("get" + filedName).toLowerCase().equals(name.toLowerCase())) {
                obj = declaredMethod.invoke(entity);
            }
        }
        return obj;
    }

    public static <T> void setFieldValue(T obj, Class<?> clazz, String filedName, Class<?> typeClass, Object value) {
        String methodName = "set" + filedName.substring(0, 1).toUpperCase() + filedName.substring(1);
        try {
//            Method method = clazz.getDeclaredMethod(methodName, new Class[] { typeClass });
//            method.invoke(obj, new Object[] { getClassTypeValue(typeClass, value) });
            Method declaredMethod = clazz.getDeclaredMethod(methodName, typeClass);
            declaredMethod.invoke(obj, value);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过class类型获取获取对应类型的值
     *
     * @param typeClass class类型
     * @param value     值
     * @return Object
     */
    private static Object getClassTypeValue(Class<?> typeClass, Object value) {
        if (typeClass == int.class || value instanceof Integer) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (typeClass == short.class) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (typeClass == byte.class) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (typeClass == double.class) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (typeClass == long.class) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (typeClass == String.class) {
            if (null == value) {
                return "";
            }
            return value;
        } else if (typeClass == boolean.class) {
            if (null == value) {
                return true;
            }
            return value;
        } else if (typeClass == BigDecimal.class) {
            if (null == value) {
                return new BigDecimal(0);
            }
            return new BigDecimal(value + "");
        } else {
            return typeClass.cast(value);
        }
    }
    /**
     * 处理字符串 如： abc_dex ---> abcDex
     *
     * @param str
     * @return
     */
    public static String removeLine(String str) {
        if (null != str && str.contains("_")) {
            int i = str.indexOf("_");
            char ch = str.charAt(i + 1);
            char newCh = (ch + "").substring(0, 1).toUpperCase().toCharArray()[0];
            String newStr = str.replace(str.charAt(i + 1), newCh);
            String newStr2 = newStr.replace("_", "");
            return newStr2;
        }
        return str;
    }



    //  public static
    /**
     * 拼接某属性的 get方法
     * @param fieldName
     * @return String
     */
    private static String preGetName(String fieldName) {
        if (null == fieldName || "".equals(fieldName)) {
            return null;
        }
        return "get" + fieldName.substring(0, 1).toUpperCase()
                + fieldName.substring(1);
    }

    public static <T> T getObj(T obj) throws NoSuchMethodException {
        Class<?> aClass = obj.getClass();
        Constructor<?> constructor = aClass.getConstructor();
        constructor.setAccessible(true);
        Method[] declaredMethods = aClass.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            //    System.out.println(declaredMethod.getName());
        }
        return null;
    }
}

// jackson
//            ObjectMapper objectMapper = new ObjectMapper();
//            String jsonString = "{\"name\":\"Mahesh\", \"age\":21}";
//            // json -> string
//            Student student = objectMapper.readValue(jsonString, Student.class);
//            // string -> json
//            String s = objectMapper.writeValueAsString(student);
class Student {
    private String name;
    private int age;
    public Student(){}
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String toString(){
        return "Student [ name: "+name+", age: "+ age+ " ]";
    }

}