import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        Item item;
        Node prev;
        Node next;

        Node(Item item) {
            this.item = item;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    // construct an empty deque
    public Deque() {
        head = new Node(null);
        tail = new Node(null);
        head.next = tail; // dummy
        tail.prev = head; // dummy
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot add an null element");
        }
        Node front = new Node(item);
        front.next = head.next;
        front.prev = head;
        head.next.prev = front;
        head.next = front;
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot add a null element");
        }
        Node last = new Node(item);
        last.prev = tail.prev;
        tail.prev.next = last;
        last.next = tail;
        tail.prev = last;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException("Deque is empty");
        }
        Node node = head.next;
        head.next = node.next;
        node.next.prev = head;
        size--;
        return node.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (size == 0) {
            throw new NoSuchElementException("Deque is empty");
        }
        Node node = tail.prev;
        tail.prev = node.prev;
        node.prev.next = tail;
        size--;
        return node.item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {

        private Node curr = head;

        @Override
        public boolean hasNext() {
            return curr.next != tail;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more items");
            }
            curr = curr.next;
            return curr.item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove unsupported");
        }
    }

    @Override
    public String toString() {
        // String content = "";
        StringBuilder content = new StringBuilder();
        for (Item item : this) {
            // content += "," + item;
            content.append("," + item);
        }
        if (!content.toString().isEmpty()) {
            // content = content.substring(1);
            return "[" + content.substring(1) + "]";
        }
        return "[" + content.toString() + "]";
    }

    // unit testing (required)
    public static void main(String[] args) {
        StdOut.println("Start testing");

        // Test 1 public operations
        Deque<Integer> dq = new Deque<>();
        StdOut.println("Test 1A passed? " + dq.isEmpty());
        StdOut.println("Test 1B passed? " + dq.toString().equals("[]"));
        dq.addLast(1);
        dq.addLast(2);
        StdOut.println("Test 1C passed? " + dq.toString().equals("[1,2]"));
        StdOut.println("Test 1D passed? " + (dq.size() == 2));
        StdOut.println("Test 1E passed? " + (dq.iterator().next() == 1));
        dq.addFirst(0);
        StdOut.println("Test 1F passed? " + (dq.toString().equals("[0,1,2]")));
        dq.removeFirst();
        StdOut.println("Test 1G passed? " + (dq.toString().equals("[1,2]")));
        dq.removeLast();
        StdOut.println("Test 1H passed? " + (dq.toString().equals("[1]")));
        dq.removeFirst();
        StdOut.println("Test 1I passed? " + (dq.toString().equals("[]")));
        StdOut.println("Test 1J passed? " + dq.isEmpty());
        StdOut.println("Test 1K passed? " + (!dq.iterator().hasNext()));

        // Test 2: Exceptions
        Deque<Integer> dq2 = new Deque<>();
        try {
            dq2.removeFirst();
        } catch (NoSuchElementException e) {
            // boolean result = e instanceof NoSuchElementException;
            StdOut.println("Test 2A passed? " + true);
        }

        try {
            dq2.removeLast();
        } catch (NoSuchElementException e) {
            // boolean result = e instanceof NoSuchElementException;
            StdOut.println("Test 2B passed? " + true);
        }

        try {
            dq2.addFirst(null);
        } catch (IllegalArgumentException e) {
            // boolean result = e instanceof IllegalArgumentException;
            StdOut.println("Test 2C passed? " + true);
        }

        try {
            dq2.addLast(null);
        } catch (IllegalArgumentException e) {
            // boolean result = e instanceof IllegalArgumentException;
            StdOut.println("Test 2D passed? " + true);
        }

        try {
            dq2.iterator().remove();
        } catch (UnsupportedOperationException e) {
            // boolean result = e instanceof UnsupportedOperationException;
            boolean result = true;
            StdOut.println("Test 2E passed? " + result);
        }

        try {
            dq2.iterator().next();
        } catch (NoSuchElementException e) {
            // boolean result = e instanceof NoSuchElementException;
            StdOut.println("Test 2F passed? " + true);
        }

        // Test 3: types
        Deque<String> dq3 = new Deque<>();
        dq3.addFirst("Hello world");
        StdOut.println("Test 3A passed? " + dq3.toString().equals("[Hello world]"));
        Deque<Double> dq4 = new Deque<>();
        dq4.addFirst(3.1416);
        StdOut.println("Test 3A passed? " + dq4.toString().equals("[3.1416]"));

        StdOut.println("All tests finished");
    }

}
