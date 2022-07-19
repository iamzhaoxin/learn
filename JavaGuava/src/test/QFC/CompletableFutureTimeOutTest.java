import org.junit.Test;

import java.util.concurrent.*;
import java.util.function.Function;

/**
 * 以CompletableFuture为例，如何保证方法在使用该类的异步API的基础上（不包括使用同步get等方法），对于超时能够做出有效的响应和处理。
 *
 * @author xzhao9
 * @since 2022-07-19 20:32
 **/
public class CompletableFutureTimeOutTest {

    /**
     * 使用CompletableFutureDelay.completeOnTimeout
     * 为异步API附加一个计时器，将计时器加入ScheduledThreadPoolExecutor
     * 计时器到时间后，使用一个线程抛出异常，随即释放线程。（所以只需要一个线程，因为每个计时器只在抛出异常时，持有线程一瞬间）
     * applyToEither().exceptionally()得到正常结果 或 超时处理
     */
    @Test
    public void testDelay() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "finished";
        });
        CompletableFuture<String> future_delay = CompletableFutureDelay.completeOnTimeout((throwable -> {
            System.out.println(throwable.getMessage());
            return null;
        }), future, 3, TimeUnit.SECONDS);
        future_delay.get();
    }

    // todo 代理模式 增强CompletableFuture


}

class CompletableFutureDelay {
    static final ScheduledThreadPoolExecutor schedule = new ScheduledThreadPoolExecutor(1);
    public static <T> CompletableFuture<T> completeOnTimeout(Function<Throwable, T> function,
                                                             CompletableFuture<T> future, long timeout, TimeUnit unit) {
        final CompletableFuture<T> timer = new CompletableFuture<>();
        // timeout时间后，timer开始执行，并瞬间执行结束，然后执行completeExceptionally(抛出异常)，作为timer的结果
        schedule.schedule(() -> timer.completeExceptionally(new TimeoutException()), timeout, unit);
        // 如果先完成任务future，则不执行exceptionally(),否则先完成定时器timer，抛出异常，执行exceptionally(throwable->function.apply(throwable))
        return future.applyToEither(timer, resultFuture -> resultFuture).exceptionally(function);
    }
}

/*

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

    //TODO get方法返回的异常 如何捕捉处理？
    @Test
    public void futureCancelTest2() throws ExecutionException, InterruptedException {
        CompletableFuture<String> alive = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("alive");
            return "ok";
        });
        alive.exceptionally(e -> {
            System.out.println("e: " + e);
            return "error";
        });

        alive.cancel(true);
        alive.get();
    }

 */
