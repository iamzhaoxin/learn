package utilities;

/**
 * 当使用String上锁（synchronized）时，可能因为new String导致锁住的不是同一个对象
 * 使用String类的intern方法，或guava的Interner类的intern方法，可以保证指向同一个对象
 * 区别：
 *  - String.intern常量池有限，存储在hashtable中，数据多了之后，碰撞厉害，而且容易加重full gc负担
 *  - Interner内部基于ConcurrentHashMap实现，而且可以设置引用类型，不会加重full gc负担，
 *       但有一个问题就是如果gc回收了存储在Interner里面的String，那么pool.intern(lock)可能也会返回不同的引用
 *       总之，还是建议使用Interner，效率和内存使用率更高
 *  - String.intern只能用在String对象，而guava Interner可以任何非空的object
 *
 * @author xzhao9
 * @since 2022-07-13 19:52
 **/
public class InternerTest {

}
