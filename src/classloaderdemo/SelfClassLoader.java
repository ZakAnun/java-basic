package classloaderdemo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Java 提供 3 种类加载器
 *  - 启动类加载器（Bootstrap ClassLoader）：属于虚拟机一部分，用 c++ 实现的顶层加载类，负责加载 %JAVA_HOME%/lib 下的 jar 包或类或被 -Xbootclasspath 参数指定的路径中的所有类
 *  - 拓展类加载器（Extension ClassLoader）：独立于JVM，用Java实现的，负责加载目录 %JRE_HOME%/lib/ext 目录下的jar包和类，或被 java.ext.dirs 系统变量所指定的路径下的jar包
 *  - 应用程序类加载器（Application ClassLoader）：独立于 JVM，用 Java 实现，负责加载用户类路径(classPath)上的类库，如果我们没有实现自定义的类加载器那就是我们程序中的默认加载器
 *
 * 双亲委派模型
 *  - 当一个类加载器收到类加载的请求时，首先会请求其父加载器加载，每一层都是如此，当父加载器无法找到这个类时(根据类的全限定名称)，子类加载器才会自己去加载
 *  好处
 *   - 避免类的重复加载（JVM 区分不同类的方式还包括类加载器，相同类文件被不同的类加载器加载产生的是两个不同的类）
 *   - 保证 Java 核心 API 不被修改（避免与 Java 包里的类冲突，如 Object）
 *  实现
 *   - 双亲委派的实现在 ClassLoader#loadClass 中（通过递归实现）
 *  注意
 *   - 自定义类加载器，若不想打破双亲委派模型，重写 findClass 即可，想要打破则重写 loadClass
 *   - equals 默认比较的是内存地址，所以两个类 equals 为 true 的前提条件为同一个类加载器加载
 */
public class SelfClassLoader extends ClassLoader {

    private String classPath;

    public SelfClassLoader(String classPath) {
        this.classPath = classPath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        // getDate方法会根据自定义的路径扫描class，并返回class的字节
        byte[] classData = getDate(name);
        if (classData == null) {
            throw new ClassNotFoundException();
        } else {
            // 生成class实例
            return defineClass(name, classData, 0, classData.length);
        }
    }

    private byte[] getDate(String name) {
        // 拼接目标class文件路径
        String path = classPath + File.separatorChar + name.replace('.', File.separatorChar) + ".class";
        try {
            InputStream is = new FileInputStream(path);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            byte[] buffer = new byte[2048];
            int num = 0;
            while ((num = is.read(buffer)) != -1) {
                stream.write(buffer, 0 ,num);
            }
            return stream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
