package thread;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * 可以把Semaphore看作一个包含多个许可（permit）的集合
 * Semaphore可用于追踪可用资源的个数。
 *
 * @author xzhao9
 * @since 2022-07-15 16:22
 **/
@Slf4j
public class SemaphoreTest {
    @Test
    public void semaphoreTest() throws ExecutionException, InterruptedException {

        Semaphore semaphore=new Semaphore(3);
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        ArrayList<Future<?>> futures = Lists.newArrayList();
        for (int i = 0; i < 5; i++) {
            int index = i;
            // child thread
            Future<?> future = executorService.submit(() -> {
                boolean acquired=false;
                long start=System.currentTimeMillis();
                try{
                    if(semaphore.tryAcquire(1000,TimeUnit.MILLISECONDS)){
                        acquired=true;
                        Thread.sleep(1000L);
                        log.info("index: {} acquired permit,cost {} ms",index,System.currentTimeMillis()-start);
                    }else{
                        log.info("index: {} failed to acquire permit,cost {} ms",index,System.currentTimeMillis()-start);
                    }
                }catch (InterruptedException e){
                    log.info("interrupted while waiting for permit");
                }finally {
                    if(acquired){
                        semaphore.release();
                    }
                }
            });
            futures.add(future);
        }
        for(Future<?> future:futures){
            future.get();
        }
    }
}
