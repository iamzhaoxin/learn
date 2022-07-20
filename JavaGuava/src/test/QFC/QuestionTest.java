import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @Author: 赵鑫
 * @Date: 2022/7/20
 */
public class QuestionTest {

    //thread/ThreadTest.java:167

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

}
