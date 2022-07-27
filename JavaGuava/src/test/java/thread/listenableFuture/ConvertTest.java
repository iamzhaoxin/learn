package thread.listenableFuture;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @author xzhao9
 * @since 2022-07-21 11:57
 **/
@Slf4j
public class ConvertTest {

    static ThreadFactory THREADFACTORY = r -> {
        Thread t=new Thread(r);
        t.setDaemon(true);
        t.setName("listenableFutureThread");
        return t;
    };

    public static void main(String[] args) {
        ConvertTest convertTest = new ConvertTest();
        convertTest.listenableFutureToCompletableFuture();
        log.info("======");
        convertTest.listenableFutureToCompletableFuture2();
        log.info("======");
        convertTest.completableFutureToListenableFuture();
    }

    private void completableFutureToListenableFuture() {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                log.info("completableFuture running");
                Thread.sleep(2000);
                log.info("completableFuture finished");
                return "result of completableFuture";
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        ListenableFuture<String> listenableFuture = Convert.toListenableFuture(completableFuture);

        CountDownLatch latch = new CountDownLatch(1);
        listenableFuture.addListener(() -> {
            try {
                Thread.sleep(1000);
                log.info("listenableFuture running");
                latch.countDown();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            // 设为守护线程，主线程退出后，如果不等待，会导致addListener线程被jvm结束
        }, Executors.newCachedThreadPool(THREADFACTORY));

        try {
            log.info("listenableFuture result: {}", listenableFuture.get());
            //等待addListener线程
            latch.await();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private void listenableFutureToCompletableFuture() {
        ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool(THREADFACTORY));
        ListenableFuture<String> listenableFuture = listeningExecutorService.submit(() -> {
            try {
                log.info("listenableFuture running");
                Thread.sleep(2000);
                log.info("listenableFuture finished");
                return "result of listenableFuture";
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        CompletableFuture<String> completableFuture = Convert.toCompletableFuture(listenableFuture);
//        CompletableFuture<String> completableFuture = Convert.toCompletableFuture2(listenableFuture);

        completableFuture.thenRun(() -> {
            log.info("completableFuture running");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        try {
            log.info(String.valueOf(completableFuture.get()));
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private void listenableFutureToCompletableFuture2() {
        ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool(THREADFACTORY));
        ListenableFuture<String> listenableFuture = listeningExecutorService.submit(() -> {
            try {
                log.info("listenableFuture running");
                Thread.sleep(2000);
                log.info("listenableFuture finished");
                return "result of listenableFuture";
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

//        CompletableFuture<String> completableFuture = Convert.toCompletableFuture(listenableFuture);
        CompletableFuture<String> completableFuture = Convert.toCompletableFuture2(listenableFuture);

        completableFuture.thenRun(() -> {
            log.info("completableFuture running");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        try {
            log.info(String.valueOf(completableFuture.get()));
        } catch (Exception e) {
            log.error("", e);
        }
    }

}
