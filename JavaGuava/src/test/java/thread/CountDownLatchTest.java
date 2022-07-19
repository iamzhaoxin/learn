package thread;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * CountDownLatch
 * 主线程 等 子线程 执行完成
 *
 * @author xzhao9
 * @since 2022-07-15 17:25
 **/
@Slf4j
public class CountDownLatchTest {
    @Test
    public void countdownLatchTest() {
        AtomicInteger threadIndex = new AtomicInteger(0);
        ExecutorService executorService = Executors.newFixedThreadPool(5, r -> new Thread(r, "thread-" + threadIndex.getAndIncrement()));
        int jobCount=5;
        List<Future<?>> futures= Lists.newArrayList();
        CountDownLatch countDownLatch=new CountDownLatch(jobCount);
        for(int i=0;i<jobCount;i++){
            int jobIndex=i;
            Future<?> future = executorService.submit(() -> {
                long start = System.currentTimeMillis();
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextLong(1000L));
                    log.info("job{} finished, cost {} ms", jobIndex, System.currentTimeMillis() - start);
                } catch (Exception e) {
                    log.warn(e.getMessage());
                } finally {
                    countDownLatch.countDown();
                }
            });
            futures.add(future);
        }
        try{
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.warn(e.getMessage());
        }

    }
}
