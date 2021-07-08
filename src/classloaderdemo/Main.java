package classloaderdemo;

public class Main {

    public static void main(String[] args) {
        // 自定义class类路径（选了 build 目录下的，如果想要运行，先注释 ClassLoader 相关代码然后运行，再放开运行...）
        String classPath = "/build/classes/java/main/classloaderdemo";
        // 自定义的类加载器实现：TestClassLoader
        SelfClassLoader testClassLoader = new SelfClassLoader(classPath);
        // 通过自定义类加载器加载
        Class<?> object = null;
        try {
            object = testClassLoader.loadClass("classloaderdemo.Main");
            // 这里的打印应该是我们自定义的类加载器：TestClassLoader
            System.out.println(object.getClassLoader());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
