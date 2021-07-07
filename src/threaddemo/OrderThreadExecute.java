package threaddemo;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 让线程 T1、T2、T3 按顺序执行
 */
public class OrderThreadExecute {


    public static void main(String[] args) {
        OrderThreadExecute orderThreadExecute = new OrderThreadExecute();

//        System.out.println("使用 join 的方式");
//        orderThreadExecute.useJoin();

//        System.out.println("使用 CountDownLatch 的方式");
//        orderThreadExecute.userCountDownLatch();

//        System.out.println("使用 FutureTask 的方式");
//        orderThreadExecute.userFutureTask();

//        System.out.println("使用 BlockingQueue 的方式");
//        orderThreadExecute.userBlockingQueue();

        System.out.println("使用单个线程的线程池 newSingleThreadExecutor 的方式");
        orderThreadExecute.userNewSingleThreadExecutor();
    }

    /**
     * 第一种方式: 在 Runnable 中调用上一个线程的 join()
     *
     * join() 会让当前线程进入 WAITING/TIMED_WAITING
     */
    private void useJoin() {
        Thread t1 = new Thread(new JoinWorker(null), "join-1");
        Thread t2 = new Thread(new JoinWorker(t1), "join-2");
        Thread t3 = new Thread(new JoinWorker(t2), "join-3");

        t1.start();
        t2.start();
        t3.start();
    }

    static class JoinWorker implements Runnable {

        private final Thread lastThread;

        JoinWorker(Thread lastThread) {
            this.lastThread = lastThread;
        }

        @Override
        public void run() {
            if (lastThread != null) {
                try {
                    lastThread.join();
                    System.out.println("thread static: " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("thread static: " + Thread.currentThread().getName());
            }
        }
    }

    /**
     * 使用 CountDownLatch（闭锁），可以拦截一个或多个线程，使其在某个条件成熟后执行
     * 内部提供一个计数器，在构造时需要传入计数器的初始值
     * 还提供 countDown() 来操作计数器的值，每调用一次就会减一，直到计数器的值为 0，表示条件成熟
     * 所有应调 await() 而阻塞的线程都会被唤醒
     */
    private void userCountDownLatch() {
        CountDownLatch c1 = new CountDownLatch(0);
        CountDownLatch c2 = new CountDownLatch(1);
        CountDownLatch c3 = new CountDownLatch(1);

        Thread t1 = new Thread(new CountDownLatchWorker(c1, c2), "countDownLatch-1");
        Thread t2 = new Thread(new CountDownLatchWorker(c2, c3), "countDownLatch-2");
        Thread t3 = new Thread(new CountDownLatchWorker(c3, c3), "countDownLatch-3");

        t1.start();
        t2.start();
        t3.start();
    }

    static class CountDownLatchWorker implements Runnable {

        CountDownLatch cdl1;
        CountDownLatch cdl2;

        CountDownLatchWorker(CountDownLatch cdl1, CountDownLatch cdl2) {
            this.cdl1 = cdl1;
            this.cdl2 = cdl2;
        }

        @Override
        public void run() {
            try {
                cdl1.await(); // 前一个线程计数器为 0 才能执行
                System.out.println("thread static: " + Thread.currentThread().getName());
                cdl2.countDown(); // 本线程计数器 -1
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * FutureTask 是一个可以取消的异步计算，实现了 Future 的基本方法
     * 提供 start 和 cancel 操作，可以查询计算是否已经完成并且可以获取计算结果
     * get() 会阻塞在当计算没完成的时候
     * 一旦计算完成，那么计算就不能再次启动或取消
     *
     * 一个 FutureTask 可以用来包装一个 Callable 和一个 Runnable 对象
     * 因为 FutureTask 实现了 Runnable 方法
     * 所以一个 FutureTask 可以提交（submit）个一个 Executor 执行
     */
    private void userFutureTask() {
        FutureTask<Integer> futureTask1 = new FutureTask<>(new FutureTaskWorker(null));
        FutureTask<Integer> futureTask2 = new FutureTask<>(new FutureTaskWorker(futureTask1));
        FutureTask<Integer> futureTask3 = new FutureTask<>(new FutureTaskWorker(futureTask2));

        Thread t1 = new Thread(futureTask1, "futureTask-1");
        Thread t2 = new Thread(futureTask2, "futureTask-2");
        Thread t3 = new Thread(futureTask3, "futureTask-3");

        t1.start();
        t2.start();
        t3.start();
    }

    static class FutureTaskWorker implements Callable<Integer> {
        private final FutureTask<Integer> beforeFutureTask;

        FutureTaskWorker(FutureTask<Integer> beforeFutureTask) {
            this.beforeFutureTask = beforeFutureTask;
        }

        @Override
        public Integer call() throws Exception {
            if (beforeFutureTask != null) {
                try {
                    Integer result = beforeFutureTask.get();
                    System.out.println("thread static: " + Thread.currentThread().getName());
                    return result;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("thread static: " + Thread.currentThread().getName());
            }
            return 0;
        }
    }

    /**
     * 使用阻塞队列
     * 当阻塞队列插入数据时，如果队列满了，线程将会阻塞等待直到队列可以再次插入数据
     * 从阻塞队列中获取数据时，如果队列已空，线程将会阻塞等待直到队列非空
     */
    private void userBlockingQueue() {
        BlockingQueue<Thread> queue = new LinkedBlockingDeque<>();
        Thread t1 = new Thread(new BlockingQueueWorker(), "BlockingQueue-1");
        Thread t2 = new Thread(new BlockingQueueWorker(), "BlockingQueue-2");
        Thread t3 = new Thread(new BlockingQueueWorker(), "BlockingQueue-3");

        queue.offer(t1);
        queue.offer(t2);
        queue.offer(t3);

        int size = queue.size();
        for (int i = 0; i < size; i++) {
            Thread r = null;
            try {
                r = queue.take();
            } catch (InterruptedException e ) {
                e.printStackTrace();
            }
            if (r != null) {
                r.start();
                // 检查线程是否还活着
                while (r.isAlive());
            }
        }
    }

    static class BlockingQueueWorker implements Runnable {
        @Override
        public void run() {
//            try {
//                Thread.sleep(1000);
                System.out.println("thread static: " + Thread.currentThread().getName());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    }

    /**
     * newSingleThreadExecutor 返回一个包含单线程的 Executor，将多个任务交个它
     * 这个线程处理完一个任务就会接着处理下一个任务，如果线程出现异常，将会有一个新的线程来替代
     */
    private void userNewSingleThreadExecutor() {
        final Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread static: thread = " + Thread.currentThread().getName() + ", runnable = runnable-1");
            }
        }, "newSingleThreadExecutor-1");
        final Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread static: thread = " + Thread.currentThread().getName() + ", runnable = runnable-2");
                try {
                    t1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "newSingleThreadExecutor-2");
        final Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread static: thread = " + Thread.currentThread().getName() + ", runnable = runnable-3");
                try {
                    t2.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "newSingleThreadExecutor-3");

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.submit(t1);
        executorService.submit(t2);
        executorService.submit(t3);
        executorService.shutdown();
    }
}
