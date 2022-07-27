package thread.completableFuture;

import com.sun.istack.internal.NotNull;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * @Author: 赵鑫
 * @Date: 2022/7/20
 */
@Slf4j
public class CompletableFutureDelayer {

    static final ScheduledThreadPoolExecutor delayer;   // delayer：线程数=1的定时任务线程池

    static final class DaemonThreadFactory implements ThreadFactory {
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            /*
                使用线程池，默认线程工厂创建的都是用户线程，核心线程会一直存在
                    - 在程序结束时，要用shutdown关闭线程池，否则线程池资源一直不释放，JVM不退出
                    - 或者用setDaemon(true)设为守护进程。主线程结束时，守护进程就会结束
             */
            t.setDaemon(true);
            t.setName("CompletableFutureDelayScheduler");
            return t;
        }
    }

    static {
        delayer = new ScheduledThreadPoolExecutor(1, new DaemonThreadFactory());
        delayer.setRemoveOnCancelPolicy(true);
    }

    public static <T> CompletableFuture<T> timeout(CompletableFuture<T> future, long delay, TimeUnit unit) {
        final CompletableFuture<T> timer = new CompletableFuture<>();
        Runnable runnable = () -> timer.completeExceptionally(new TimeoutException());
        // 开始定时执行runnable, runnable执行结束后抛出TimeoutException异常
        delayer.schedule(runnable, delay, unit);

        return future.applyToEither(timer, Function.identity()).exceptionally(throwable -> {
            Throwable t = throwable;
            while (t.getCause() != null) {
                t = t.getCause();
            }
            if(t instanceof TimeoutException){
                log.info("这里执行超时处理");
            }
            return null;
        });
    }

}
