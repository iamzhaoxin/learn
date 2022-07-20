package thread;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import sun.nio.ch.ThreadPool;

import java.util.LinkedList;
import java.util.concurrent.*;

/**
 * .setDaemon
 * .join
 * <p>
 * 线程的中断标志,是每个线程内部的一个标志,是每个线程对象内部的一个成员变量
 * 标志为true代表线程收到了中断指令,false则代表线程没有收到中断指令
 * 调用thread.interrupt()方法来中断另外一个线程.一旦调用了thread.interrupt()方法,另外一个线程的中断标志位就会被设置为true
 * 线程通过使用Thread.interrupted()方法来检查是否线程被中断，会获取线程的中断标志位后清除线程的中断标志位,将其置为false
 * 或者调用thread.isInterrupted()方法来检查是否线程被中断，只会获取线程的标志位,而不会清除中断标志位
 * 当线程调用了一个可能抛出InterruptedException的方法的时候(比如join(),wait(),sleep()),
 * 在这些方法内部中,会不断的检查是否收到中断指令(中断标志位为true),一旦为true,则抛出一个InterruptedException异常.
 * 线程负责在catch()方法处理异常捕获的逻辑
 * 当线程没有执行上面的几个可能抛出InterruptedException的方法的时候,要想检查是否被中断,
 * 只能线程主动的调用Thread.interrupted()或者thread.isInterrupted去获取线程中断标志位的状态,
 * 如果为true则表示收到中断指令(上述几个方法内部实现了这种逻辑),至于是否要抛出InterruptedException则由本线程自己决定
 *
 * @author xzhao9
 * @since 2022-07-15 10:23
 **/
@Slf4j
public class ThreadTest {

    /**
     * setDaemon(true) 主线程结束时，子线程也结束
     */
    @Test
    public void daemonTest() {
        Thread thread = new Thread(() -> {
            log.info("running");
        });
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * .join() 让调用线程等待此线程生命周期结束
     */
    @Test
    public void joinTest() throws InterruptedException {
        Thread[] threads = new Thread[3];
        for (int i = 0; i < 3; i++) {
            int index = i;    // 无法直接在内部类里使用i，需要中间变量
            Thread thread = new Thread(() -> {
                log.info("{}", index);
            });
            threads[i] = thread;
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }
        log.info("threads are done");
    }

    /**
     * 在主线程中，通过引用线程将子线程的标志位置为true
     * 子线程执行比如join(),wait(),sleep()，阻塞状态下，会不断检查中断标志位，
     * 发现是true则抛出InterruptedException，然后将中断标志位置为false
     */
    @Test
    public void testInterruptedException() throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                System.out.println("catch InterruptedException");
                // thread.isInterrupted()方法来检查是否线程被中断，只会获取线程的标志位,而不会清除中断标志位
                /* TODO
                 *  log无法输出？
                 *  执行到logback-classic-1.2.11-sources.jar!/ch/qos/logback/classic/Logger.java:419
                 *  （执行LoggingEvent le = new LoggingEvent(localFQCN, this, level, msg, t, params);时）
                 *  与目标 VM 断开连接
                 */
                log.info("I was interrupted,current state: {}", Thread.currentThread().isInterrupted());
                System.out.println("after log");
            }
        });
        thread.start();
        Thread.sleep(1000L);
        thread.interrupt();
    }

    /**
     * 非阻塞状态下的线程，被调用interrupt方法后，中断标志位是true，但不会主动结束线程，需要主动检查并处理
     */
    @Test
    public void breadThreadTest() throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (true) {
                // Thread.interrupted()方法来检查是否线程被中断，会获取线程的中断标志位后清除线程的中断标志位,将其置为false
                if (Thread.interrupted()) {
                    break;
                } else {
                    log.info("running");
                }
            }
        });
        thread.start();
        Thread.sleep(1000L);
        thread.interrupt();
    }

    /**
     * exception.getCause()
     */
    @Test
    public void realCauseTest() {
        ExecutorService executorService = Executors.newSingleThreadExecutor(r -> new Thread(r, "executor"));
        Future<?> future = executorService.submit(() -> {
            try {
                throw new IllegalArgumentException("真正的原因");
            } catch (Exception e) {
                throw new IllegalStateException("虚假的原因", e);
            }
        });

        try {
            Object o = future.get();
        } catch (InterruptedException e) {
            log.info("got interrupted while waiting other threads to stop");
        } catch (ExecutionException e) {
            log.info("虚假的原因：{}", e.getMessage());
            log.info("可能真实的原因(unwrapped once): {}", e.getCause().getMessage());
            Throwable t = e;
            while (t.getCause() != null) {
                t = t.getCause();
            }
            log.info("一定真实的原因：{}", t.getMessage());
        }

    }

    /**
     * Future接口的cancel()方法
     *  - 任务已经完成 或 之前的已经被取消 或 由于其他原因不能被取消，
     *      那么这个方法将会返回false并且这个任务不会被取消。
     *  - 任务正在等待获取线程，取消任务
     *  - 如果这个任务已经正在运行
     *      - 参数为true，任务取消。
     *      - 参数为false，任务不会被取消。
     */
    @Test
    public void futureCancelTest() throws ExecutionException, InterruptedException {
        CompletableFuture<String> alive = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("alive");
            return "ok";
            // TODO .exceptionally方法不起作用？
        }).exceptionally(e -> {
            System.out.println("e: " + e);
            return "error";
        });

        alive.cancel(true);
        alive.get();
    }

    @Test
    public void futureCancelTest2(){
        CompletableFuture<String> alive = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("还活着");
            return "ok";
        });
        alive.exceptionally(e -> {
            System.out.println("Exception: " + e);
            return "error";
        });

        alive.cancel(true);
        try {
            alive.get();
        } catch (Exception e) {
            log.error("{}",e.getClass());
        }
    }

    /**
     * Executors预置了几种不同类型的ThreadPoolExecutor
     * newCachedThreadPool()
     * newFixedThreadPool(int)
     * newScheduledThreadPool(int)
     * SingleThreadExecutor()
     */
    @Test
    public void execute_Submit_Test() throws ExecutionException, InterruptedException {
        // ThreadPoolExecutor
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 10, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(20));
        Future<Integer> future = threadPoolExecutor.submit(() -> 1 + 2);
        log.info(String.valueOf(future.get()));
        threadPoolExecutor.execute(() -> log.info("log in ThreadPoolExecutor thread pool"));

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> log.info("log in executorService thread pool"));
        /*
            Runnable 接口的实现类 A 声明了一个有参构造函数 A(LinkedList result)
            创建 A 对象的时候传入了 result 对象，这样就能在类 A 的 run() 方法中对 result 进行各种操作了
            result 相当于主线程和子线程之间的桥梁，通过它主子线程可以共享数据
         */
        LinkedList<Integer> result = Lists.newLinkedList();
        class A implements Runnable {
            final LinkedList<Integer> result;

            public A(LinkedList<Integer> result) {
                this.result = result;
            }

            @Override
            public void run() {
                result.add(1);
                log.info("class A add 1 to result");
            }
        }
        Future<LinkedList<Integer>> submitResult = executorService.submit(new A(result), result);
        LinkedList<Integer> resultFromFuture = submitResult.get();
        log.info("传入的result和返回的Future里的result是同一个对象：{}", result == resultFromFuture);
    }

}
