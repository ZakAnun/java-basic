package enumdemo;

/**
 * 枚举定义的字段是 final 的，还有大部分的方法也是 final 的，
 * 特别是 clone、readObject、writeObject这三个方法，
 * 这三个方法和枚举通过静态代码块来进行初始化一起，
 * 它保证了枚举类型的不可变性，不能通过克隆，不能通过序列化和反序列化来复制枚举，
 * 这能保证一个枚举常量只是一个实例，即是单例的（单例的实现）
 *
 * 枚举的本质是通过普通的类实现，不过编译器会帮助我们处理（如生成内部类，添加方法）
 * 所有枚举常量都通过静态代码块来进行初始化，即在类加载期间就初始化。
 * 另外通过把clone、readObject、writeObject这三个方法定义为final的，同时实现是抛出相应的异常。
 * 这样保证了每个枚举类型及枚举常量都是不可变的。可以利用枚举的这两个特性来实现线程安全的单例
 *
 * 所以在 Android 中使用枚举会比两个静态常量的内存要多（在内存优化时可以用得上）
 * Android 提供了 @IntDef 和 @StringDef 注解（用于编译期的类型检查）
 */
public enum Operator {
    ADD("+") {
        @Override
        public int calculate(int a, int b) {
            return a + b;
        }
    },
    SUB("-") {
        @Override
        public int calculate(int a, int b) {
            return a - b;
        }
    },
    MUL("*") {
        @Override
        public int calculate(int a, int b) {
            return a * b;
        }
    },
    DIV("/") {
        @Override
        public int calculate(int a, int b) {
            if (b == 0) {
                throw new IllegalArgumentException("除数不能为 0");
            }
            return a / b;
        }
    };

    Operator(String operator) {
        this.operator = operator;
    }

    private String operator;

    public abstract int calculate(int a, int b);

    public String getOperator() {
        return operator;
    }
}
