public class LinkedListDeque<T> {
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

    public void addFirst(T item) {
        size++;
        node<T> ptr = new node<T>(item, sentinel.next, sentinel);
        sentinel.next.prev = ptr;
        sentinel.next = ptr;
    }

    public void addLast(T item) {
        size++;
        node<T> ptr = new node<T>(item, sentinel, sentinel.prev);
        sentinel.prev.next = ptr;
        sentinel.prev = ptr;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        node<T> ptr = sentinel.next;
        while (!ptr.equals(sentinel)) {
            System.out.print(ptr.value + " ");
            ptr = ptr.next;
        }
    }

    public T removeFirst() {
        assert (size > 0);
        size--;
        node<T> ptr = sentinel.next;
        sentinel.next = ptr.next;
        ptr.next.prev = sentinel;
        return ptr.value;
    }

    public T removeLast() {
        assert (size > 0);
        size--;
        node<T> ptr = sentinel.prev;
        sentinel.prev = ptr.prev;
        ptr.prev.next = sentinel;
        return ptr.value;
    }

    public T get(int index) {
        assert (0 <= index && index < size);
        node<T> ptr = sentinel.next;
        while (index-- > 0)
            ptr = ptr.next;
        return ptr.value;
    }

    public T getRecursiveHelper(node<T> ptr, int index) {
        if (index == 0)
            return ptr.value;
        return getRecursiveHelper(ptr.next, index - 1);
    }

    public T getRecursive(int index) {
        assert (0 <= index && index < size);
        return getRecursiveHelper(sentinel.next, index);
    }
}
