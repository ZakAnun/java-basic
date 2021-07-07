package reflectiondemo;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射（通过 Class 类获取 class 信息的方式）
 */
public class ReflectionDemo {

    private String privateString = "zak";
    public int publicInt = 100;

    public static void main(String[] args) {
        ReflectionDemo reflectionDemo = new ReflectionDemo();

        System.out.println("获取 Class 的方式");
        System.out.print("方式一: ");
        reflectionDemo.reflectMethod1();
        System.out.print("方式二: ");
        reflectionDemo.reflectMethod2();
        System.out.print("方式三: ");
        reflectionDemo.reflectMethod3();

        System.out.println();

        System.out.println("通过反射访问字段");
        reflectionDemo.obtainField(reflectionDemo);

        System.out.println();

        System.out.println("通过反射修改字段");
        reflectionDemo.setField(reflectionDemo);

        System.out.println();

        System.out.println("通过反射调用方法");
        reflectionDemo.obtainAndExecuteMethod(reflectionDemo);
    }

    public String getPrivateString() {
        return privateString;
    }

    /**
     * 方式一: 通过静态变量 class 获取
     */
    private void reflectMethod1() {
        Class<String> cls = String.class;
        System.out.println("method1 cls name = " + cls.getName());
    }

    /**
     * 方式二: 通过实例变量的 getClass() 获取
     */
    private void reflectMethod2() {
        Integer clsStr = 123;
        Class cls = clsStr.getClass();
        System.out.println("method2 cls name = " + cls.getName());
    }

    /**
     * 方式三: 通过静态方法 Class.forName 获取（需要知道全类名）
     */
    private void reflectMethod3() {
        try {
            Class cls = Class.forName("java.lang.Double");
            System.out.println("method3 cls name = " + cls.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过反射获取字段
     *
     * @param reflectionDemo 实例
     */
    private void obtainField(ReflectionDemo reflectionDemo) {
        try {
            Class cls = reflectionDemo.getClass();
            // 获取 public 字段 publicInt
            Field publicField = cls.getField("publicInt");
            System.out.println("获取 public 字段: " + publicField + ", value = " + publicField.get(reflectionDemo));
            // 获取 private 字段
            Field privateField = cls.getDeclaredField("privateString");
            System.out.println("获取 private 字段: " + privateField + ", value = " + privateField.get(reflectionDemo));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过反射修改字段
     *
     * @param reflectionDemo 实例
     */
    private void setField(ReflectionDemo reflectionDemo) {
        System.out.println("修改之前 publicInt = " + reflectionDemo.publicInt);
        System.out.println("修改之前 privateString = " + reflectionDemo.privateString);

        try {
            // 获取并设置 public 字段
            Class cls = reflectionDemo.getClass();
            Field publicField = cls.getField("publicInt");
            publicField.set(reflectionDemo, 1000);
            // 获取并设置 private 字段
            Field privateField = cls.getDeclaredField("privateString");
            privateField.setAccessible(true);
            privateField.set(reflectionDemo, "Zak_李铭淋");

            System.out.println("修改之后 publicInt = " + reflectionDemo.publicInt);
            System.out.println("修改之后 privateString = " + reflectionDemo.privateString);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过反射获取并调用方法
     *
     * @param reflectionDemo 实例
     */
    private void obtainAndExecuteMethod(ReflectionDemo reflectionDemo) {
        Class cls = reflectionDemo.getClass();
        try {
            // 获取 private 方法
            Method getPrivateString = cls.getDeclaredMethod("getPrivateString");
            System.out.println("私有方法带返回值 getPrivateString = " + getPrivateString);
            System.out.print("调用方法: ");
            getPrivateString.setAccessible(true);
            // 调用方法
            String result = (String) getPrivateString.invoke(reflectionDemo);
            System.out.println(result);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
