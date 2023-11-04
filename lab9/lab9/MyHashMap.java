package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * A hash table-backed Map implementation. Provides amortized constant time access to elements via
 * get(), remove(), and put() in the best case.
 *
 * @author Your name here
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

  private static final int DEFAULT_SIZE = 16;
  private static final double MAX_LF = 0.75;

  private ArrayMap<K, V>[] buckets;
  private int size;

  private int loadFactor() {
    return size / buckets.length;
  }

  public MyHashMap() {
    buckets = new ArrayMap[DEFAULT_SIZE];
    this.clear();
  }

  /* Removes all of the mappings from this map. */
  @Override
  public void clear() {
    this.size = 0;
    for (int i = 0; i < this.buckets.length; i += 1) {
      this.buckets[i] = new ArrayMap<>();
    }
  }

  /**
   * Computes the hash function of the given key. Consists of computing the hashcode, followed by
   * modding by the number of buckets. To handle negative numbers properly, uses floorMod instead of
   * %.
   */
  private int hash(K key) {
    if (key == null) {
      return 0;
    }

    int numBuckets = buckets.length;
    return Math.floorMod(key.hashCode(), numBuckets);
  }

  private void resize(int capacity) {
    ArrayMap<K, V>[] oldBuckets = buckets;
    buckets = new ArrayMap[capacity];
    this.clear();
    for (ArrayMap<K, V> m : oldBuckets) {
      for (K key : m) {
        if (buckets[hash(key)].size() == 0) {
          size++;
        }
        buckets[hash(key)].putAtLast(key, m.get(key));
      }
    }
  }

  /* Returns the value to which the specified key is mapped, or null if this
   * map contains no mapping for the key.
   */
  @Override
  public V get(K key) {
    //        throw new UnsupportedOperationException();
    return buckets[hash(key)].get(key);
  }

  /* Associates the specified value with the specified key in this map. */
  @Override
  public void put(K key, V value) {
    if (buckets[hash(key)].size() == 0) {
      size++;
    }
    buckets[hash(key)].put(key, value);
    if (loadFactor() > MAX_LF) {
      resize(2 * buckets.length);
    }
  }

  /* Returns the number of key-value mappings in this map. */
  @Override
  public int size() {
    //    throw new UnsupportedOperationException();
    int totalSize = 0;
    for (ArrayMap<K, V> m : buckets) {
      totalSize += m.size();
    }
    return totalSize;
  }

  //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

  /* Returns a Set view of the keys contained in this map. */
  @Override
  public Set<K> keySet() {
    HashSet<K> hs = new HashSet<>();
    for (ArrayMap<K, V> m : buckets) {
      hs.addAll(m.keySet());
    }
    return hs;
  }

  /* Removes the mapping for the specified key from this map if exists.
   * Not required for this lab. If you don't implement this, throw an
   * UnsupportedOperationException. */
  @Override
  public V remove(K key) {
    //    throw new UnsupportedOperationException();
    return buckets[hash(key)].remove(key);
  }

  /* Removes the entry for the specified key only if it is currently mapped to
   * the specified value. Not required for this lab. If you don't implement this,
   * throw an UnsupportedOperationException.*/
  @Override
  public V remove(K key, V value) {
    //    throw new UnsupportedOperationException();
    return buckets[hash(key)].remove(key, value);
  }

  @Override
  public Iterator<K> iterator() {
    //    throw new UnsupportedOperationException();
    return keySet().iterator();
  }
}
