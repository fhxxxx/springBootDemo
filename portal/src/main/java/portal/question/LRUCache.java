package portal.question;


import java.util.*;

/**
 * LinkedHashMap天然支持 LRU缓存策略
 */
public class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private final int capacity;


    public LRUCache(int initialCapacity, int capacity) {
        super(initialCapacity, 0.75f, true);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        return size() > capacity;
    }
}

/**
 * 基于linkedList和hashMap实现LRU
 * 主要思路：本体就是一个HashMap,只是需要额外的数据结构来记录插入和使用的时间先后，那就用链表linkedList实现
 */
class NormalLRUCache<K, V> {

    private final int capacity;
    private final Map<K, V> cache;
    private final LinkedList<K> keyList;

    public NormalLRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>(capacity);
        this.keyList = new LinkedList<>();
    }

    //put 向缓存里存入一个key-value
    public synchronized void put(K key, V value) {
        //如果缓存内有这个key了，则先移除
        if (cache.containsKey(key)) {
            cache.remove(key);
        }
        //如果大于了容量则弹出最老的数据
        while (cache.size() >= capacity){
            K firstKey = keyList.removeFirst();
            cache.remove(firstKey);
        }
        //将新的key-value存入缓存中，并将该key添加到keyList的末尾，表示最近被访问
        cache.put(key, value);
        keyList.addLast(key);
    }

    //get操作:根据key获取value
    public synchronized V get(K key){
        //先更新链表里的先后顺序，访问的数据放到最后的位置
        if (cache.containsKey(key)) {
            keyList.remove(key);
            keyList.addLast(key);
            return cache.get(key);
        }
        return null;
    }
}
