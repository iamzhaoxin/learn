import jdk.nashorn.internal.codegen.CompilerConstants;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;

/**
 * @author xzhao9
 * @since 2022-07-20 14:05
 **/
@Slf4j
public class CancelTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Double> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("我还活着");
            return 1.1 + 2;
        });

        future.exceptionally(throwable -> {
            log.error(throwable.getMessage(), throwable);
            return null;
        });
        log.info(future.isCompletedExceptionally() + "");

        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        CompletableFuture<Double> timer = new CompletableFuture<>();
        // 计时器
        executor.schedule(()->timer.completeExceptionally(new TimeoutException("limit time: ")),1, TimeUnit.SECONDS);

        CompletableFuture<Object> result = CompletableFuture.anyOf(future, timer).exceptionally(t -> {
            System.out.println(t.getMessage());
            return null;
        });

        System.out.println(future.isDone());
        System.out.println(timer.isDone());
        System.out.println(result.isDone());

    }

}
