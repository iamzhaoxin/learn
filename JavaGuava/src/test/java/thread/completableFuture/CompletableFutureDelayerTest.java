package thread.completableFuture;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 赵鑫
 * @Date: 2022/7/20
 */
@Slf4j
public class CompletableFutureDelayerTest {
    public static void main(String[] args) {
        // CompletableFuture异步任务 默认采用的线程是ForkJoinPool创建的守护线程
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000L);
                log.info("我还活着");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "finished";
        });
        CompletableFuture<String> futureDelay = CompletableFutureDelayer.timeout(future, 1500, TimeUnit.MILLISECONDS);
        try {
            if(futureDelay.get()!=null){
                log.info(futureDelay.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 当主线程结束时，所有守护线程都结束（所以线程池ScheduledThreadPoolExecutor要自定义线程工厂，将线程池里的子线程设为非默认的守护线程
    }
}
