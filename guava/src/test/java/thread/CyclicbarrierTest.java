package thread;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 所有子线程 等待 所有子线程 完成任务进入await状态 再都继续执行
 *
 * @author xzhao9
 * @since 2022-07-15 17:41
 **/
@Slf4j
public class CyclicbarrierTest {
    @Test
    public void cyclicbarrierTest() throws ExecutionException, InterruptedException {
        AtomicInteger threadIndex = new AtomicInteger(0);
        ExecutorService executorService = Executors.newFixedThreadPool(5, r -> new Thread(r, "thread-" + threadIndex.getAndIncrement()));
        int jobCount=5;
        List<Future<?>> futures= Lists.newArrayList();
        CyclicBarrier cyclicBarrier=new CyclicBarrier(jobCount);
        for(int i=0;i<jobCount;i++){
            int jobIndex=i;
            Future<?> future = executorService.submit(() -> {
                long start = System.currentTimeMillis();
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextLong(1000L));
                    log.info("job{} finished, cost {} ms", jobIndex, System.currentTimeMillis() - start);
                    cyclicBarrier.await();
                    log.info("job{} continue run, cost {} ms", jobIndex, System.currentTimeMillis() - start);
                } catch (InterruptedException e) {
                    log.warn(e.getMessage());
                } catch (BrokenBarrierException e){
                    log.info("BrokenBarrier, abort");
                }
            });
            futures.add(future);
        }
        for(Future<?> future:futures){
            future.get();
        }

    }
}
