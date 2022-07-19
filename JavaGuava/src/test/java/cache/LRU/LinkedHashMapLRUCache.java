package cache.LRU;

import com.google.common.base.Preconditions;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 另一种方法是LinkedList<K>配合HashMap<K,V>实现LRU
 *
 * 如果LinkedHashMaoLRUCache直接继承LinkedHashMap，会暴露多余的方法，所以不用继承关系，用组合关系
 *
 * not the thread-safe class
 *
 * @Author: 赵鑫
 * @Date: 2022/7/14
 */
public class LinkedHashMapLRUCache<K, V> implements LRUCache<K, V> {

    private static class InternalLRUCache<K, V> extends LinkedHashMap<K, V> {
        private final int limit;

        public InternalLRUCache(int limit) {
            super(16, 0.75f, true);
            this.limit = limit;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            return size()>limit;
        }
    }

    private final InternalLRUCache<K, V> internalLRUCache;
    private final int limit;

    public LinkedHashMapLRUCache(int limit) {
        Preconditions.checkArgument(limit > 0, "limit need big than zero");
        this.internalLRUCache = new InternalLRUCache<>(limit);
        this.limit = limit;

    }

    @Override
    public void put(K key, V value) {
        this.internalLRUCache.put(key, value);
    }

    @Override
    public V get(K key) {
        return this.internalLRUCache.get(key);
    }

    @Override
    public void remove(K key) {
        this.internalLRUCache.remove(key);
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void clear() {
        this.internalLRUCache.clear();
    }

    @Override
    public int limit() {
        return limit;
    }

    @Override
    public String toString() {
        return internalLRUCache.toString();
    }
}
