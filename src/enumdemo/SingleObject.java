package enumdemo;

/**
 * 使用枚举实现单例（因为枚举类型是线程安全的且只会装在一次）
 */
public class SingleObject {

    private SingleObject() {}

    private enum Singleton {
        INSTANCE;

        private final SingleObject singleObject;

        Singleton() {
            singleObject = new SingleObject();
        }

        private SingleObject getSingleObject() {
            return singleObject;
        }
    }

    public static SingleObject getInstance() {
        return Singleton.INSTANCE.getSingleObject();
    }
}
