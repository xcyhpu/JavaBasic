package concurrent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 描述：
 *
 * @author ：xuchunyang
 * @date ：2/4/21
 */
public class Test01 {

//    private static List<Integer> list = new ArrayList<>();

//    private static volatile List<Integer> list = new ArrayList<>();

    private static List<Integer> list =  Collections.synchronizedList(new ArrayList<>());

    static Thread t1 = new Thread(() -> {
        for (int i = 1; i <= 10; i++) {
            list.add(i);
            System.out.println("add " + i);
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    }, "t1");

    static Thread t2 = new Thread(() -> {
        while (true) {
            if (list.size() == 5) {
                break;
            }
        }
        System.out.println("t2 结束");
    }, "t2");

    public static void main(String[] args) {

        t1.start();

        t2.start();
    }

}
