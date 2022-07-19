package thread;

import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.index.qual.SameLen;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Supplier;

/**
 * @author xzhao9
 * @since 2022-07-19 16:31
 **/
@Slf4j
public class CompletableFutureTest {

    /**
     * CompletableFuture.supplyAsync
     * 创建有返回值的异步任务
     */
    @Test
    public void supplyAsyncTest() throws ExecutionException, InterruptedException {
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(new Supplier<Double>() {
            @Override
            public Double get() {
                try {
                    Thread.sleep(2000L);
                    log.info("finished sleep");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return 12.5;
            }
        });
        Stopwatch stopwatch = Stopwatch.createStarted();
        log.info("run result: {}", cf.get());
        stopwatch.stop();
        log.info("use time: {}", stopwatch);
    }

    /**
     * CompletableFuture.runAsync()
     * 创建无返回值的异步任务(get()的返回值为null)
     */
    @Test
    public void runAsyncTest() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> log.info("running"));
        Stopwatch stopwatch = Stopwatch.createStarted();
        log.info("run result: {}", cf.get());
        stopwatch.stop();
        log.info("use time: {}", stopwatch);
    }

    /**
     * supplyAsync和runAsync都有一个重载方法，制定执行异步任务的Executor
     * 如果不指定，默认使用ForkJoinPool.commonPool()
     * - ForkJoin：如果待执行任务体量太大，就进行fork()拆分成小块任务，子任务的执行结果合并join()成父任务的结果
     * - ForkJoinTask一定运行在一个ForkJoinPool中，如果没有显式提交到ForkJoinPool，会用一个common池（全进程共享）来执行任务
     * 如果机器单核，默认用ThreadPerTaskExecutor，每次调用execute()都会创建一个线程
     */
    @Test
    public void supplyAsync_WithPoolTest() throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        CompletableFuture<Integer> cf = CompletableFuture.supplyAsync(() -> {
            log.info("running");
            return 1 + 2;
        }, forkJoinPool);
        Stopwatch stopwatch = Stopwatch.createStarted();
        log.info("run result: {}", cf.get());
        stopwatch.stop();
        log.info("use time: {}", stopwatch);
    }

    @Test
    public void runAsync_WithPoolTest() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> log.info("running"), executorService);
        CompletableFuture<Void> cf2 = CompletableFuture.runAsync(() -> log.info("running"), executorService);
        CompletableFuture<Void> cf3 = CompletableFuture.runAsync(() -> log.info("running"), executorService);
        Stopwatch stopwatch = Stopwatch.createStarted();
        log.info("run result: {}", cf.get());
        stopwatch.stop();
        log.info("use time: {}", stopwatch);
    }

    /**
     * thenApply: 有参，有返回值
     * <p>
     * thenApply 由执行cf的线程立即执行cf2
     * thenApplyAsync 将cf2提交到线程池异步执行，实际执行cf2的可能是其他线程
     * <p>
     * 多个方法：
     * - 都有两个实现：默认使用ForkJoin.commonPool()和制定Executor实现
     * - 不带Async结尾：由触发任务的线程执行任务
     * - 待Async：由触发任务的线程，将任务提交到线程池，触发任务和执行任务的线程不一定是同一个
     */
    @Test
    public void thenApplyTest() throws ExecutionException, InterruptedException {
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(() -> 1.2 + 2);
        // cf的返回值作为参数，传入到thenApply方法中
        CompletableFuture<String> cf2 = cf.thenApply(result -> "then" + result);
        Stopwatch stopwatch = Stopwatch.createStarted();
        log.info("run result: {}", cf2.get());
        stopwatch.stop();
        log.info("use time: {}", stopwatch);
    }

    /**
     * thenAccept 有参，无返回值
     * thenRun  无参，无返回值(CompletableFuture<Void>的get()返回null)
     * 链式
     */
    @Test
    public void thenAccept_thenRun_Test() throws ExecutionException, InterruptedException {
        CompletableFuture<Double> cf1 = CompletableFuture.supplyAsync(() -> {
            log.info("cf running");
            return 1.2 + 2;
        });
        CompletableFuture<Void> cf2 = cf1.thenApply(resultFrom_cf1 -> "thenApply" + resultFrom_cf1)
                .thenAccept(result -> log.info("thenAccept: {}", result))
                .thenRun(() -> log.info("then run finished"));
        log.info("cf1 result: {}", cf1.get());
        log.info("cf2 result: {}", cf2.get());
    }

    /**
     * exceptionally
     * 指定某个任务执行异常时，执行的回调方法，将抛出的异常作为参数传递到回调方法中
     * 如果任务正常执行，返回值就是正常的值
     */
    @Test
    public void exceptionallyTest() throws ExecutionException, InterruptedException {
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(() -> {
            log.info("cf running");
            return 1 + 1.2;
        });
        CompletableFuture<Double> cf2 = cf.exceptionally(e -> {
            // log打印异常时，不要用占位符，把异常放在最后
            log.error("error stack trace", e);
            return (double) -1;
        });
        CompletableFuture<Void> cf3 = cf.thenApply(result -> "accept" + result)
                .thenAccept(r -> log.info("{}", r));
        log.info("cf result: {}", cf.get());
        log.info("cf2 result: {}", cf2.get());
        log.info("cf3 result: {}", cf3.get());

        CompletableFuture.supplyAsync(() -> 1 + 2.2)
                .exceptionally(e -> {
                    log.error("", e);
                    return (double) -1;
                }).thenAccept(r -> log.info("end"));
    }

    /**
     * whenComplete
     * 当任务执行完成后 执行的回调方法,传入两个参数：执行结果，执行期间抛出的异常
     * - 如果正常执行，则异常为null，不能手动return，但返回值是传入的result
     * - 执行异常，则返回值的get方法抛出异常
     */
    @Test
    public void whenCompleteTest() throws ExecutionException, InterruptedException {
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(() -> {
            log.info("cf running");
            return 1 + 1.2;
        });
        CompletableFuture<Double> cf_complete = cf.whenComplete((result, throwable) -> {
            if (throwable != null) {
                log.error("error stack trace", throwable);
            } else {
                log.info("cf run success,result: {}", result);
            }
        });
        log.info("whenComplete result: {}", cf_complete.get());
    }

    /**
     * handle
     * 和whenComplete的区别：
     * - 手动return
     */
    @Test
    public void handleTest() throws ExecutionException, InterruptedException {
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(() -> {
            log.info("cf running");
            return 1 + 1.2;
        });
        CompletableFuture<String> cf_handle = cf.handle((result, throwable) -> {
            if (throwable != null) {
                log.error("error stack trace", throwable);
                return "run error";
            } else {
                log.info("cf run success,result: {}", result);
                return "run success";
            }
        });
        log.info("cf_handle result: {}", cf_handle.get());
    }

    // 组合处理

    /**
     * 将两个CompletableFuture组合，只有两个都正常执行完了，才会执行某任务
     * - thenCombine 将两个任务的执行结果作为方法入参，方法有返回值
     * - thenAcceptBoth 将两个result入参，无返回值
     * - runAfterBoth 没有入参，没有返回值
     * 两个任务只要有一个执行异常，则将异常作为任务的执行结果
     */
    @Test
    public void Both_Test() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> cf = CompletableFuture.supplyAsync(() -> 1 + 2);
        CompletableFuture<Double> cf2 = CompletableFuture.supplyAsync(() -> 3 + 2.1);
        // 入参，有返回值
        CompletableFuture<Double> cf_combine = cf.thenCombine(cf2, (a, b) -> {
            log.info("a: {},b: {}", a, b);
            return a + b;
        });
        // 入参，无返回值
        CompletableFuture<Void> cf_acceptBoth = cf.thenAcceptBoth(cf2, (a, b) -> {
            log.info("a: {},b: {}", a, b);
        });
        // 两个任务都执行完成后执行，无入参，无返回值
        CompletableFuture<Void> cf_runAfterBoth = cf.runAfterBoth(cf2, () -> {
            log.info("runAfterBoth");
        });
        log.info("cf_combine: {}", cf_combine.get());
        log.info("cf_acceptBoth: {}", cf_acceptBoth.get());
        log.info("cf_runAfterBoth: {}", cf_runAfterBoth.get());
    }

    /**
     * 将两个CompletableFuture组合，只要有一个任务先执行完了，就会执行某任务
     * - applyToEither: 入参，有返回值
     * - acceptEither: 入参，无返回值
     * - runAfterEither: 无参，无返回值
     * 如果先执行完的任务 执行异常，即将异常信息作为执行结果
     */
    @Test
    public void Either_Test() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> cf1 = CompletableFuture.supplyAsync(() -> 1 + 2);
        CompletableFuture<Integer> cf2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000L);
                throw new IllegalArgumentException("exception");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        CompletableFuture<Void> cf_either = cf1.runAfterEither(cf2, () -> log.info("finished"));
        log.info("{}", cf_either.get());
    }

    /**
     * thenCompose
     * 在某个任务执行完后，将执行结果作为方法入参，然后执行指定方法
     */
    @Test
    public void thenCompose() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> cf1 = CompletableFuture.supplyAsync(() -> 1 + 2);
        CompletableFuture<Double> cf_thenCompose = cf1.thenCompose(result -> {
            log.info("thenCompose的参数：{}", result);
            return CompletableFuture.supplyAsync(() -> 5 + 2.3);
        });
        log.info("{}", cf_thenCompose.get());
    }

    /**
     * allOf 多个任务都执行完成后才执行
     * - 只要有一个任务异常，则执行后返回值的get方法都会抛出异常
     * - 都正常执行，则get返回null
     */
    @Test
    public void allOf_Test() throws ExecutionException, InterruptedException {
        CompletableFuture<Double> cf1 = CompletableFuture.supplyAsync(() -> 1 + 2.1);
        CompletableFuture<Double> cf2 = CompletableFuture.supplyAsync(() -> 1 + 5.2);
        CompletableFuture<Double> cf3 = CompletableFuture.supplyAsync(() -> 1 + 2.4);
        CompletableFuture<Void> cf_allOf = CompletableFuture.allOf(cf1, cf2, cf3);
        log.info("{}", cf_allOf.get()); //null
        CompletableFuture<Void> cf_allOf_thenComplete = CompletableFuture.allOf(cf1, cf2, cf3)
                .whenComplete((a, b) -> log.info("a={},b={}", a, b));   //a=null,b=null
    }

}
