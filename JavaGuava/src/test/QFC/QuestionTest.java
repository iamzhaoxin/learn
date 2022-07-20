import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * //thread/ThreadTest.java:167
 * .exceptionally不支持链式编程？
 *
 * @Author: 赵鑫
 * @Date: 2022/7/20
 */
@Slf4j
public class QuestionTest {

    /**
     * output:
     * [main] ERROR QuestionTest - class java.util.concurrent.CancellationException
     */
    @Test
    public void futureCancelTest() {
        CompletableFuture<String> alive = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            log.info("还活着");
            return "ok";
            // .exceptionally不起作用
        }).exceptionally(e -> {
            log.debug("处理exception");
            return "error";
        });

        alive.cancel(true);
        try {
            alive.get();
        } catch (Exception e) {
            log.error("get error: {}",e.getClass());
        }
    }

    /**
     * output:
     * Exception: java.util.concurrent.CancellationException
     * [main] ERROR QuestionTest - class java.util.concurrent.CancellationException
     */
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
        // 这样单独写才可以起作用
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

}
