import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 赵鑫
 * @Date: 2022/7/20
 */
public class CompletableFutureDelayerTest {
    public static void main(String[] args) {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000L);
                System.out.println("我还活着");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "finished";
        });
        CompletableFuture<String> futureDelay = CompletableFutureDelayer.timeout(future, 1500, TimeUnit.MILLISECONDS);
        try {
            System.out.println(futureDelay.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
