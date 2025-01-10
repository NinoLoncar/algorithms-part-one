import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int n = 0;

    public RandomizedQueue() {
        items = (Item[]) new Object[1];
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        StdOut.println("Size: " + queue.size());
        StdOut.println("Sample: " + queue.sample());
        StdOut.println("Iterating: ");
        for (Integer i : queue) {
            StdOut.println(i);
        }
        StdOut.println("Dequeue: ");
        StdOut.println(queue.dequeue());
        StdOut.println(queue.dequeue());
        StdOut.println(queue.dequeue());
        StdOut.println(queue.dequeue());
        StdOut.println("isEmpty: " + queue.isEmpty());
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void enqueue(Item item) {
        checkItem(item);
        items[n++] = item;
        if (items.length == n) {
            resize(items.length * 2);
        }
    }

    private void checkItem(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
    }

    private void checkEmptiness() {
        if (isEmpty())
            throw new NoSuchElementException();
    }

    public Item dequeue() {
        checkEmptiness();
        int rand = StdRandom.uniformInt(n);
        Item item = items[rand];
        items[rand] = items[--n];
        items[n] = null;
        if (n > 0 && n == items.length / 4) {
            resize(items.length / 2);
        }
        return item;
    }

    public Item sample() {
        checkEmptiness();
        int rand = StdRandom.uniformInt(n);
        return items[rand];
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }


    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++)
            copy[i] = items[i];
        items = copy;
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        int nextPos = 0;
        int[] order = new int[n];

        public RandomizedQueueIterator() {
            for (int i = 0; i < n; i++) {
                order[i] = i;
            }
            StdRandom.shuffle(order);
        }

        public boolean hasNext() {
            return nextPos < n;

        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return items[order[nextPos++]];
        }
    }
}
