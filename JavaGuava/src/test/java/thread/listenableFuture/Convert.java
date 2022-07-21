package thread.listenableFuture;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

/**
 * @author xzhao9
 * @since 2022-07-21 11:45
 **/
@Slf4j
public class Convert {

    static ListeningExecutorService listeningExecutorService = MoreExecutors
            .listeningDecorator(Executors.newCachedThreadPool(r -> {
                Thread t=new Thread(r);
                t.setDaemon(true);
                t.setName("listenableFutureThread");
                return t;
            }));

    public static <T> ListenableFuture<T> toListenableFuture(CompletableFuture<T> completableFuture){
        return listeningExecutorService.submit(() -> {
            try {
                return completableFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static <T> CompletableFuture<T> toCompletableFuture(ListenableFuture<T> listenableFuture){
        CompletableFuture<T> completableFuture = new CompletableFuture<>();
        //在listenableFuture所在的子线程执行complete，completableFuture也将基于子线程执行
        listenableFuture.addListener(()->{
            try {
                completableFuture.thenRun(()->log.info("converting to CompletableFuture"));
                completableFuture.complete(listenableFuture.get());
            } catch (Exception e) {
                completableFuture.completeExceptionally(e);
            }
        }, listeningExecutorService);

        // 如果不用addListener，会在主线程执行complete方法，所以completeFuture后续也基于主线程执行
/*        try {
            completableFuture.thenRun(()->log.info("running in thread: {}",Thread.currentThread().getName()));
            completableFuture.complete(listenableFuture.get());
        } catch (Exception e) {
            completableFuture.completeExceptionally(e);
        }*/

        return completableFuture;
    }

    public static <T> CompletableFuture<T> toCompletableFuture2(ListenableFuture<T> listenableFuture){
        CompletableFuture<T> completableFuture = CompletableFuture.supplyAsync(()->{
            try {
                log.info("getting result of listenableFuture");
                return listenableFuture.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        return completableFuture;
    }

}
