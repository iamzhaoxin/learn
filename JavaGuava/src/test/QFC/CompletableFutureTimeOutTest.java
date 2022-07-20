import com.google.common.collect.Maps;
import com.sun.applet2.preloader.CancelException;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.HashMap;
import java.util.concurrent.*;
import java.util.function.Function;

/**
 * 以CompletableFuture为例，如何保证方法在使用该类的异步API的基础上（不包括使用同步get等方法），对于超时能够做出有效的响应和处理。
 *
 * @author xzhao9
 * @since 2022-07-19 20:32
 **/
@Slf4j
public class CompletableFutureTimeOutTest {

    /**
     * 使用CompletableFutureDelay.completeOnTimeout
     * 为异步API附加一个计时器，将计时器加入ScheduledThreadPoolExecutor
     * 计时器到时间后，使用一个线程抛出异常，随即释放线程。（所以只需要一个线程，因为每个计时器只在抛出异常时，持有线程一瞬间）
     * applyToEither().exceptionally()得到正常结果 或 超时处理
     */
    @Test
    public void testDelay() throws ExecutionException, InterruptedException, CancelException {
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
        CompletableFutureDelay.cancelTimeout(future);
        future_delay.get();
    }

    // todo 代理模式 增强CompletableFuture


}

@Slf4j
class CompletableFutureDelay {

    static final ScheduledThreadPoolExecutor schedule;
    static final HashMap<CompletableFuture<?>, Runnable> futureTimerMap;

    static {
        schedule = new ScheduledThreadPoolExecutor(1);
        // 提交的任务在运行之前被取消时，执行被抑制，默认false：取消的任务不会从工作队列中删除，直至其延迟过去
        schedule.setRemoveOnCancelPolicy(true);
        futureTimerMap = Maps.newHashMap();
    }

    /**
     * @param function 超时后要执行的方法
     * @param future   要执行的任务
     * @param timeout  超时时间
     * @param unit     实践单位
     * @param <T>      任务返回值类型
     * @return 任务执行结果 或 超时方法function的返回值
     */
    public static <T> CompletableFuture<T> completeOnTimeout(Function<Throwable, T> function,
                                                             CompletableFuture<T> future, long timeout, TimeUnit unit) {
        // timer 空任务
        final CompletableFuture<T> timer = new CompletableFuture<>();
        // timerTask 执行timer完成后，抛出TimeoutException
        Runnable timerTask = () -> timer.completeExceptionally(new TimeoutException());
        // timeout时间后，执行timerTask（即timeout时间后抛出TimeoutException），TimeoutException是timer的执行结果
        schedule.schedule(timerTask, timeout, unit);
        // 将future和对应的timerTask加入HashMap
        futureTimerMap.put(future, timerTask);
        // 完成任一任务后，将future从Map中移除。如果future先执行完成，则返回正常result
        return future.applyToEither(timer, result -> {
            futureTimerMap.remove(future);
            return result;
            // 如果是timer先完成任务(抛出异常触发exceptionally)，执行超时处理：exceptionally(throwable->function.apply(throwable))
        }).exceptionally(function);
    }

    // todo 如何捕捉CancellationException而不是输出，log.info不显示
    public static <T> void cancelTimeout(CompletableFuture<T> future) {
        try {
            log.debug("1");
            System.out.println("1");
            if (future.cancel(true)) {
                log.debug("2");
                System.out.println("2");
                Runnable timerTask = futureTimerMap.get(future);
                schedule.remove(timerTask);
                futureTimerMap.remove(future);
            } else {
                throw new CancelException("cancel future failed");
            }
        } catch (CancelException e) {
            log.info("cancel future success");
        }
    }

}