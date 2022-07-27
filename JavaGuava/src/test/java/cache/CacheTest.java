package cache;

import com.google.common.cache.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * guava-cache
 *  优点：
 *      使用简单
 *      线程安全，内部实现类似于ConcurrentHashMap
 *      可以自动加载，定时更新缓存
 *      容量不够时LRU清理
 *      记录访问时间用于计算是否有效
 *      key/value支持多种引用类型
 *      统计缓存访问数据
 *      缓存被移除或失效时可以监听
 *  缺点：
 *      单个应用运行时的本地缓存，无法持久化
 *      单机，受机器内存限制，重启会导致缓存丢失
 *      应用分布式部署会出现缓存不一致
 *
 * @author xzhao9
 * @since 2022-07-14 19:30
 **/
public class CacheTest {
    public static void main(String[] args) throws ExecutionException {

        LoadingCache<String,Object> loadingCache= CacheBuilder
                .newBuilder()   //cache构造器，builder模式
                .maximumSize(1000)  //缓存最大容量
                .expireAfterAccess(30, TimeUnit.MINUTES)    //缓存有效期
                .refreshAfterWrite(10,TimeUnit.MINUTES)     //自动刷新缓存时间间隔
                .removalListener(new RemovalListener<String, Object>() {    //元素移除时的监听器
                    @Override
                    public void onRemoval(RemovalNotification<String, Object> notification) {
                        //......
                    }
                })
                .recordStats()  // 记录缓存使用情况
                .build(new CacheLoader<String, Object>() {  // 定义刷新缓存的方法
                    @Override
                    public Object load(String key) throws Exception {
                        return fetchFromDB(key);
                    }
                });
        System.out.println(loadingCache.get("aaa"));
        loadingCache.put("bbb",new Object());
        loadingCache.put("ccc","value2");
        System.out.println(loadingCache.get("bbb"));
        System.out.println(loadingCache.get("ccc"));
    }

    private static Object fetchFromDB(String key) {
        //DB
        return "value";
    }
}
