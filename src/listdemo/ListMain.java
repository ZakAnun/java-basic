package listdemo;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.UnaryOperator;

/**
 * @author zak
 * @date 2021/8/1
 * @email linhenji@163.com / linhenji17@gmail.com
 */
public class ListMain {

    private static List<Integer> list = new ArrayList<>(10);

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            list.add(i + 1);
        }
        System.out.println("原集合 = " + Arrays.toString(list.toArray()));

        // 删除集合元素 demo
        int size = list.size();

        // 删除下标以及定位到遍历位置（移除值为 5 的元素）
        for (int i = 0; i < size; i++) {
            if (list.get(i) == 5) {
                list.remove(i);
                size--;
                i--;
            }
        }
        System.out.println("低到高删除元素 5 后的集合 = " + Arrays.toString(list.toArray()));

        // 使用 Iterator 实现删除（这个方法封装了，删除元素 2）
        list.removeIf(i -> i == 2);
        System.out.println("迭代器删除元素 2 后的集合 = " + Arrays.toString(list.toArray()));

        // 通过 CopyOnWriteArrayList 删除元素 4
        CopyOnWriteArrayList<Integer> cowList = new CopyOnWriteArrayList<>(list);
        for (int item : cowList) {
            if (item == 4) {
                cowList.remove((Integer) item);
            }
        }
        list = cowList;
        System.out.println("COW 删除元素 4 后的集合 = " + Arrays.toString(list.toArray()));

        // 由高到低删除
        int len = list.size();
        for (int i = len - 1; i >= 0; i--) {
            if (list.get(i) == 7) {
                list.remove(list.get(i));
            }
        }
        System.out.println("高到低删除元素 7 后的集合 = " + Arrays.toString(list.toArray()));

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == 6) {
                list.remove(list.get(i));
            }
        }
        System.out.println("低到高删除 6 元素后的集合 = " + Arrays.toString(list.toArray()));
    }
}
