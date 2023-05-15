package safepoint;

import java.util.UUID;

/**
 * @author :   xuchunyang
 * @version :   v1.0
 * @date :   2023-05-15 11:58
 */
public class SafePointTimeoutDemo {

    static final String A;

    static {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            sb.append("A");
        }
        A = sb.toString();
    }

    private static int counter = 0;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            while (true) {
                new BigObject();
            }
        }, "ABC").start();
        Thread t1 = new Thread(SafePointTimeoutDemo::increment, "T1 Thread");
        Thread t2 = new Thread(SafePointTimeoutDemo::increment, "T2 Thread");
        t1.start();
        t2.start();
        Thread.sleep(4000L);
        System.out.println("Counter: " + counter);
    }

    private static void increment() {
        for (int i = 0; i < 600; i++) {
            long startTime = System.currentTimeMillis();
            while (System.currentTimeMillis() - startTime < 500) {
            }
            counter++;
        }
    }


    private static class BigObject {

        private String a;

        public BigObject() {
            a = A + UUID.randomUUID();
        }
    }
}
