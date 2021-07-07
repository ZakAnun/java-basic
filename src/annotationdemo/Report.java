package annotationdemo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 使用 @interface 定义注解
 * 使用 default 设置默认值
 *
 * Java 定义的元注解（可以修饰其他注解）
 *
 * @Target: 定义 Annotation 能够被应用于源码的哪些位置
 *  ElementType.TYPE -- 接口或字段
 *  ElementType.FIELD -- 字段
 *  ElementType.METHOD -- 方法
 *  ElementType.CONSTRUCTOR -- 构造方法
 *  ElementType.PARAMETER -- 方法参数
 *
 * @Retention: 定义 Annotation 的生命周期
 *  RetentionPolicy.SOURCE -- 仅编译期（在编译期就会被丢弃）
 *  RetentionPolicy.CLASS -- 仅 class 文件（会保存在 class 文件中，但不会被加载进 JVM）
 *  RetentionPolicy.RUNTIME -- 运行期（会被加载进 JVM，并且在程序运行时可以被程序读取）
 *
 * @Repeatable: 使用这个注解修饰后的注解可以添加多次
 *
 * @Inherited: 定义子类是否可继承父类定义的Annotation, 仅针对 @Target(ElementType.TYPE) 的注解有效
 *
 * 注解在 Retrofit 中的应用比较明显
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Report {
    int type() default 0;
    String level() default "info";
    String value() default "";
}
