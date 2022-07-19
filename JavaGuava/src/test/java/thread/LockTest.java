package thread;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * TODO 不熟
 * ReentrantLock 可重入锁
 * ReadWriteLock 读写锁（可同时读）
 *
 * @author xzhao9
 * @since 2022-07-15 14:33
 **/
@Slf4j
public class LockTest {
    @Test
    public void reentrantLockTest() throws ExecutionException, InterruptedException {
        Lock lock = new ReentrantLock();
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        ArrayList<Future<?>> futures = Lists.newArrayList();
        for (int i = 0; i < 3; i++) {
            int index = i;
            // child thread
            Future<?> future = executorService.submit(() -> {
                boolean locked = false;
                long start = System.currentTimeMillis();
                log.info("index: {} start at: {}", index,start);
                try {
                    if (lock.tryLock(500L, TimeUnit.MILLISECONDS)) {
                        locked=true;
                        Thread.sleep(1000L);
                        log.info("index: {} got lock, cost {} ms", index, System.currentTimeMillis() - start);
                    }
                } catch (InterruptedException e) {
                    log.warn("interrupt");
                } finally {
                    if (locked) {
                        lock.unlock();
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
