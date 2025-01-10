import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first = null;
    private Node last = null;
    private int size = 0;

    public Deque() {
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(1);
        deque.addLast(3);
        StdOut.println("Size: " + deque.size);
        StdOut.println("Iterating: ");
        for (Integer i : deque) {
            StdOut.println(i);
        }
        StdOut.println("First:" + deque.removeFirst());
        StdOut.println("Last: " + deque.removeLast());
        StdOut.println("isEmpty: " + deque.isEmpty());
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        checkItem(item);
        if (isEmpty()) {
            first = new Node();
            last = first;
        } else {
            Node oldFirst = first;
            first = new Node();
            oldFirst.previous = first;
            first.next = oldFirst;
        }
        first.item = item;
        size++;
    }

    public void addLast(Item item) {
        checkItem(item);
        if (isEmpty()) {
            last = new Node();
            first = last;
        } else {
            Node oldLast = last;
            last = new Node();
            oldLast.next = last;
            last.previous = oldLast;
        }
        last.item = item;
        size++;
    }

    public Item removeFirst() {
        checkEmptiness();
        Item item = first.item;
        if (size == 1) {
            first = null;
            last = null;
        } else {
            first = first.next;
            first.previous = null;
        }
        size--;
        return item;
    }

    public Item removeLast() {
        checkEmptiness();
        Item item = last.item;
        if (size == 1) {
            first = null;
            last = null;
        } else {
            last = last.previous;
            last.next = null;
        }
        size--;
        return item;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private void checkItem(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
    }

    private void checkEmptiness() {
        if (isEmpty())
            throw new NoSuchElementException();
    }

    private class Node {
        Item item;
        Node next, previous;
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
}
