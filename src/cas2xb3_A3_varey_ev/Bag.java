package cas2xb3_A3_varey_ev;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Bag<Item> implements Iterable<Item> {
    private Node<Item> one;    // beginning of bag
    private int number;               // number of elements in bag

    // helper linked list class
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    public Bag() {
        one = null;
        number = 0;
    }

    public boolean isEmpty() {
        return one == null;
    }

    public int size() {
        return number;
    }

    public void add(Item item) {
        Node<Item> oldfirst = one;
        one = new Node<Item>();
        one.item = item;
        one.next = oldfirst;
        number++;
    }

    public Iterator<Item> iterator()  {
        return new ListIterator<Item>(one);  
    }

    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public ListIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }


}