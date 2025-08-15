package portal.question;

/**
 * 两个线程，一个打印奇数，一个打印偶数，然后顺序打印出1-100
 */
public class SequentialPrinter {

    private static int num = 1;

    private static final Object lock = new Object();

    private static final int max = 100;


    public static void main(String[] args) {
        Thread thread1 = new Thread(new Printer(0));
        Thread thread2 = new Thread(new Printer(1));
        thread1.start();
        thread2.start();
    }

    static class Printer implements Runnable{

        private int index;

        public Printer(int index) {
            this.index = index;
        }

        @Override
        public void run() {
            while (num <= max){
                synchronized (lock){
                    if (num % 2 == index){
                        System.out.println("thread" + index + " print:" + num);
                        num ++;
                        lock.notifyAll();
                    }else {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
