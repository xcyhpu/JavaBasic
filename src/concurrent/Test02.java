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
public class Test02 {

//    private static List<Integer> list = new ArrayList<>();

//    private static volatile List<Integer> list = new ArrayList<>();

    private static List<Integer> list =  Collections.synchronizedList(new ArrayList<>());

    private static Object lock = new Object();

    static Thread t1 = new Thread(() -> {
        synchronized (lock) {
            for (int i = 1; i <= 10; i++) {
                list.add(i);
                System.out.println("add " + i);
                if(list.size()==5) {

                    // notify不释放锁
                    lock.notify();

                    // 释放锁，让t2执行
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    static Thread t2 = new Thread(() -> {

        synchronized (lock) {

            if (list.size() != 5) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 唤醒t1，否则t1无法结束
            lock.notify();
            System.out.println("t2 结束");
        }
    });

    public static void main(String[] args) {

        t2.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t1.start();

    }

}
