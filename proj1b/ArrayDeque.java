public class ArrayDeque<T> implements Deque<T> {
    // the list is [first, last)
    private int first, last, size;
    private T[] items;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        first = last = 0;
        size = 0;
    }

    private void resize(int newsize) {
        T[] array = (T[]) new Object[newsize];
        if (first < last) {
            System.arraycopy(items, first, array, 0, size);
        } else {
            // [first, length-1]
            System.arraycopy(items, first, array, 0, items.length - first);
            // [0, size - (length-first) - 1]
            System.arraycopy(items, 0, array, items.length - first, size - items.length + first);
        }
        first = 0;
        last = size;
        items = array;
    }

    @Override
    public void addFirst(T item) {
        if (size == items.length)
            resize(2 * size);
        size++;
        first = (first + items.length - 1) % items.length;
        items[first] = item;
    }

    @Override
    public void addLast(T item) {
        if (size == items.length)
            resize(2 * size);
        size++;
        items[last] = item;
        last = (last + 1) % items.length;
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
        if (size == 0)
            return;
        if (first < last) {
            for (int i = first; i < last; i++)
                System.out.print(items[i] + " ");
        } else {
            for (int i = first; i < items.length; i++)
                System.out.print(items[i] + " ");
            int t = size - items.length + first;
            for (int i = 0; i < t; i++)
                System.out.print(items[i] + " ");
        }
    }

    @Override
    public T removeFirst() {
        if (size <= 0)
            return null;
        size--;
        T ret = items[first];
        items[first] = null;
        first = (first + 1) % items.length;
        if (items.length >= 16 && (double) size / items.length < 0.25)
            resize(items.length / 2);
        return ret;
    }

    @Override
    public T removeLast() {
        if (size <= 0)
            return null;
        size--;
        last = (last + items.length - 1) % items.length;
        T ret = items[last];
        items[last] = null;
        if (items.length >= 16 && (double) size / items.length < 0.25)
            resize(items.length / 2);
        return ret;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size)
            return null;
        index = (first + index) % items.length;
        return items[index];
    }
}
