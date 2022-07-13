package cache.LRU;

/**
 * @Author: 赵鑫
 * @Date: 2022/7/4
 */
public interface LRUCache<K, V> {

    void put(K key,V value);

    V get(K key);

    void remove(K key);

    int size();

    void clear();

    int limit();
}
