package thread.listenableFuture;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

/**
 * void addListener(Runnable listener, Executor executor);
 * 其中executor是回调方法的执行器(通常是线程池)。
 * 需要注意的是不加Executor的情况，只适用于轻型的回调方法，如果回调方法很耗时占资源，会造成线程阻塞
 * 因为默认的DirectExecutor有可能在主线程中执行回调
 *
 * @author xzhao9
 * @since 2022-07-21 10:16
 **/
@Slf4j
public class ListenableFutureTest {

    /**
     * 添加监听后，可以添加callback
     * BUT 添加callback后，不能添加监听
     */
    @Test
    public void addCallback_addListener_Test() throws ExecutionException, InterruptedException {
        ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

        Callable<String> callable = () -> {
            Thread.sleep(2000L);
            log.info("running");
            return "finished";
        };

        ListenableFuture<String> listenableFuture = listeningExecutorService.submit(callable);

        FutureCallback<String> futureCallback = new FutureCallback<String>() {
            @Override
            public void onSuccess(String result) {
                log.info("future finished success");
            }

            @Override
            public void onFailure(Throwable t) {
                log.info("future fail");
            }
        };

        listenableFuture.addListener(() -> log.info("listener"), listeningExecutorService);
        // 添加回调
        Futures.addCallback(listenableFuture, futureCallback, MoreExecutors.directExecutor());
//        listenableFuture.addListener(() -> log.info("listener"), listeningExecutorService);

        log.info(listenableFuture.toString());
        log.trace(listenableFuture.get());

        listeningExecutorService.shutdown();
    }

    /**
     * 主线程同时有addListener()和.get()时
     * ListenableFuture第一次submit的任务作为get()的结果，然后主线程不阻塞，主线程结束。
     * 如果这时addListener()里的任务还没执行完
     *  - 如果自定义了线程工厂，设为守护进程，执行任务的子线程会被JVM结束
     *  - 默认是用户进程
     *  - 在@Test方法中，用户进程也会被结束
     */
    @Test
    public void listenableFuture_AddListenerAfterGet_Test(){
        ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
        ListenableFuture<String> future = listeningExecutorService.submit(() -> {
            log.info("listenableFuture running");
            return "listenableFuture result";
        });
        future.addListener(()->{
            try {
                log.debug("future.addListener is daemon: {}",Thread.currentThread().isDaemon());
                Thread.sleep(1000);
                log.info("listenableFuture still running");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },Executors.newCachedThreadPool());

        try {
            log.trace("listenableFuture result: {}", future.get());
            Thread.sleep(100);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * 同上，get()只会阻塞到原始任务结束，addCallback是独立线程
     */
    @Test
    public void listenableFuture_AddCallbackAfterGet_Test(){
        ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
        ListenableFuture<String> future = listeningExecutorService.submit(() -> {
            log.info("listenableFuture running");
            return "listenableFuture result";
        });
        Futures.addCallback(future, new FutureCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    Thread.sleep(1000);
                    log.info("addCallback still running");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                throw new RuntimeException(t);
            }
        },Executors.newCachedThreadPool());

        try {
            log.trace("listenableFuture result: {}", future.get());
            Thread.sleep(1500);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * allAsList 任何一个future执行失败，都会执行callback的onFailure方法
     * 如果只想获得正常返回的结果，可以用successfulAsList方法，会将失败或取消的future结果用null替代，而不进入onFailure方法
     */
    @Test
    public void allAsListTest(){
        ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
        ListenableFuture<String> listenableFuture = listeningExecutorService.submit(()->"future result");
        ListenableFuture<String> listenableFuture2 = listeningExecutorService.submit(()->"future result");
        ListenableFuture<String> listenableFuture3 = listeningExecutorService.submit(()->"future result");
        ListenableFuture<String> listenableFuture4 = listeningExecutorService.submit(()->"future result");

        ListenableFuture<List<String>> futures = Futures.allAsList(listenableFuture, listenableFuture2, listenableFuture3, listenableFuture4);
        Futures.addCallback(futures, new FutureCallback<List<String>>() {
            @Override
            public void onSuccess(List<String> result) {
                log.info("success");
            }

            @Override
            public void onFailure(Throwable t) {
                log.info("failed");
            }
        },listeningExecutorService);
    }

    @Test
    public void transformTest() throws ExecutionException, InterruptedException {
        ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
        ListenableFuture<String> listenableFuture = listeningExecutorService.submit(()->"future result");
        ListenableFuture<Integer> transformFuture = Futures.transform(listenableFuture, result -> result.length(), listeningExecutorService);
        log.info(String.valueOf(transformFuture.get()));
    }


}
