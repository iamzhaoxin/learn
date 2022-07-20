import com.sun.istack.internal.NotNull;

import java.util.concurrent.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * @Author: 赵鑫
 * @Date: 2022/7/20
 */
public class CompletableFutureDelayer {

    static final class DaemonThreadFactory implements ThreadFactory {
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setDaemon(true);
            t.setName("CompletableFutureDelayScheduler");
            return t;
        }
    }
    static final ScheduledThreadPoolExecutor delayer;
    static {
        delayer = new ScheduledThreadPoolExecutor(1, new DaemonThreadFactory());
        delayer.setRemoveOnCancelPolicy(true);
    }

    public static <T> CompletableFuture<T> timeout(CompletableFuture<T> future, long delay, TimeUnit unit) {
        final CompletableFuture<T> timer = new CompletableFuture<>();
        Runnable runnable=()->timer.completeExceptionally(new TimeoutException());
        // 计时器开始计时，定时任务：runnable。runnable执行结束后抛出TimeoutException异常
        ScheduledFuture<?> scheduledFuture = delayer.schedule(runnable, delay, unit);

        future.applyToEither(timer, Function.identity()).exceptionally(throwable -> {
            System.out.println(throwable);
            CompletableFuture<T> futureReturn = new CompletableFuture<>();
            futureReturn.completeExceptionally(throwable);
            try {
                return futureReturn.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        return future;
    }

}
