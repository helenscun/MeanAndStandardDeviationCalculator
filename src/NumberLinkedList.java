import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A custom singly linked list that stores real numbers (doubles).
 *
 * Design decisions:
 *  - A tail reference is kept so add() is O(1); the list is never
 *    traversed just to append a value.
 *  - The size is tracked in a field so size() is O(1).
 *  - Implements Iterable so callers can use a for-each loop.
 *  - Only the operations this application needs are provided
 *    (append, size, isEmpty, iterate).
 */
public class NumberLinkedList implements Iterable<Double> {

    /** A single node in the list. */
    private static class Node {
        final double value;
        Node next;

        Node(double value) {
            this.value = value;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    /** Appends a value to the end of the list in constant time. */
    public void add(double value) {
        Node node = new Node(value);
        if (head == null) {
            head = node;
        } else {
            tail.next = node;
        }
        tail = node;
        size++;
    }

    /** @return number of values stored */
    public int size() {
        return size;
    }

    /** @return true if no values are stored */
    public boolean isEmpty() {
        return size == 0;
    }

    /** @return an iterator that walks the list from head to tail */
    @Override
    public Iterator<Double> iterator() {
        return new Iterator<Double>() {
            private Node current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Double next() {
                if (current == null) {
                    throw new NoSuchElementException();
                }
                double v = current.value;
                current = current.next;
                return v;
            }
        };
    }
}
