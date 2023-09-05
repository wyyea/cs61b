public class LinkedListDeque<T> implements Deque<T> {
    private static class node<T> {
        public T value;
        public node<T> next, prev;

        public node(T v, node<T> n, node<T> p) {
            value = v;
            next = n;
            prev = p;
        }
    }

    private node<T> sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new node<T>(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    @Override
    public void addFirst(T item) {
        size++;
        node<T> ptr = new node<T>(item, sentinel.next, sentinel);
        sentinel.next.prev = ptr;
        sentinel.next = ptr;
    }

    @Override
    public void addLast(T item) {
        size++;
        node<T> ptr = new node<T>(item, sentinel, sentinel.prev);
        sentinel.prev.next = ptr;
        sentinel.prev = ptr;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        node<T> ptr = sentinel.next;
        while (!ptr.equals(sentinel)) {
            System.out.print(ptr.value + " ");
            ptr = ptr.next;
        }
    }

    @Override
    public T removeFirst() {
        if (size <= 0)
            return null;
        size--;
        node<T> ptr = sentinel.next;
        sentinel.next = ptr.next;
        ptr.next.prev = sentinel;
        return ptr.value;
    }

    @Override
    public T removeLast() {
        if (size <= 0)
            return null;
        size--;
        node<T> ptr = sentinel.prev;
        sentinel.prev = ptr.prev;
        ptr.prev.next = sentinel;
        return ptr.value;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size)
            return null;
        node<T> ptr = sentinel.next;
        while (index-- > 0)
            ptr = ptr.next;
        return ptr.value;
    }

    private T getRecursiveHelper(node<T> ptr, int index) {
        if (index == 0)
            return ptr.value;
        return getRecursiveHelper(ptr.next, index - 1);
    }

    public T getRecursive(int index) {
        if (index < 0 || index >= size)
            return null;
        return getRecursiveHelper(sentinel.next, index);
    }
}
