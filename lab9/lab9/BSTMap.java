package lab9;

import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

  private class Node {
    /* (K, V) pair stored in this Node. */
    public K key;
    public V value;

    /* Children of this Node. */
    public Node left;
    public Node right;

    public Node(K k, V v) {
      key = k;
      value = v;
    }
  }

  private Node root; /* Root node of the tree. */
  private int size; /* The number of key-value pairs in the tree */

  /* Creates an empty BSTMap. */
  public BSTMap() {
    this.clear();
  }

  /* Removes all of the mappings from this map. */
  @Override
  public void clear() {
    root = null;
    size = 0;
  }

  /**
   * Returns the value mapped to by KEY in the subtree rooted in P. or null if this map contains no
   * mapping for the key.
   */
  private V getHelper(K key, Node p) {
    //        throw new UnsupportedOperationException();
    while (p != null) {
      if (key.compareTo(p.key) == 0) {
        return p.value;
      } else if (key.compareTo(p.key) > 0) {
        p = p.right;
      } else {
        p = p.left;
      }
    }
    return null;
  }

  /**
   * Returns the value to which the specified key is mapped, or null if this map contains no mapping
   * for the key.
   */
  @Override
  public V get(K key) {
    //        throw new UnsupportedOperationException();
    return getHelper(key, root);
  }

  /**
   * Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping. Or if p is null,
   * it returns a one node BSTMap containing (KEY, VALUE).
   */
  private Node putHelper(K key, V value, Node p) {
    //        throw new UnsupportedOperationException();
    if (p == null) {
      size++;
      return new Node(key, value);
    }
    Node ret = p;
    while (key.compareTo(p.key) != 0) {
      if (key.compareTo(p.key) > 0) {
        if (p.right == null) {
          p.right = new Node(key, value);
          size++;
        }
        p = p.right;
      } else {
        if (p.left == null) {
          p.left = new Node(key, value);
          size++;
        }
        p = p.left;
      }
    }
    p.value = value;
    return ret;
  }

  /** Inserts the key KEY If it is already present, updates value to be VALUE. */
  @Override
  public void put(K key, V value) {
    //        throw new UnsupportedOperationException();
    root = putHelper(key, value, root);
  }

  /* Returns the number of key-value mappings in this map. */
  @Override
  public int size() {
    //        throw new UnsupportedOperationException();
    return size;
  }

  //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////
  public Set<K> keySet(Node p) {
    if (p == null) {
      return new HashSet<>();
    }
    Set<K> set = new HashSet<>();
    set.add(p.key);
    set.addAll(keySet(p.left));
    set.addAll(keySet(p.right));
    return set;
  }

  /* Returns a Set view of the keys contained in this map. */
  @Override
  public Set<K> keySet() {
    //    throw new UnsupportedOperationException();
    return keySet(root);
  }

  /** get the predecessor or successor of p, move it to the place of p */
  public Node getMid(Node p) {
    if (p.left == null && p.right == null) {
      return null;
    } else if (p.left == null) {
      return p.right;
    } else if (p.right == null) {
      return p.left;
    }
    Node mid = p;
    Node cur_mid = p.right;
    while (mid.left != null) {
      cur_mid = mid;
      mid = mid.left;
    }
    if (cur_mid.left == mid) {
      cur_mid.left = mid.right;
    } else {
      cur_mid.right = mid.right;
    }
    mid.left = p.left;
    mid.right = p.right;
    return mid;
  }

  /** Removes KEY from the tree if present returns VALUE removed, null on failed removal. */
  @Override
  public V remove(K key) {
    Node p = root;
    Node parent = null;
    while (p != null) {
      if (key.compareTo(p.key) == 0) {
        break;
      }
      parent = p;
      p = key.compareTo(p.key) > 0 ? p.right : p.left;
    }
    if (p == null) { // key not found
      return null;
    }
    Node mid = getMid(p);
    if (parent == null) { // key at root
      root = mid;
    } else if (parent.left == p) {
      parent.left = mid;
    } else {
      parent.right = mid;
    }
    return p.value;
  }

  /**
   * Removes the key-value entry for the specified key only if it is currently mapped to the
   * specified value. Returns the VALUE removed, null on failed removal.
   */
  @Override
  public V remove(K key, V value) {
    //    throw new UnsupportedOperationException();
    Node p = root;
    Node parent = null;
    while (p != null) {
      if (key.compareTo(p.key) == 0) {
        break;
      }
      parent = p;
      p = key.compareTo(p.key) > 0 ? p.right : p.left;
    }
    if (p == null || p.value != value) { // key not found
      return null;
    }
    Node mid = getMid(p);
    if (parent == null) { // key at root
      root = mid;
    } else if (parent.left == p) {
      parent.left = mid;
    } else {
      parent.right = mid;
    }
    return p.value;
  }

  @Override
  public Iterator<K> iterator() {
    //    throw new UnsupportedOperationException();
    return keySet().iterator();
  }
}
