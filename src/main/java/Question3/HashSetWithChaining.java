package Question3;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class HashSetWithChaining<E> implements Set<E> {

    private Node<E>[] hashTable;
    private int numElements;
    private final float LOAD_FACTER;
    private final int initialCap;

    public HashSetWithChaining(int capacity, float loadFactor) {
        this.hashTable = new Node[capacity];
        this.LOAD_FACTER = loadFactor;
        this.initialCap = capacity;
    }

    public HashSetWithChaining(int capacity) {
        this.hashTable = new Node[capacity];
        this.LOAD_FACTER = 0.75f;
        this.initialCap = capacity;
    }

    @Override
    public boolean add(E e) {
        if(contains(e)) return false;
        if((float)numElements/(float)hashTable.length > LOAD_FACTER) expandCapacity();

        Node<E> node = new Node(e);
        int index = Math.abs(e.hashCode()) % hashTable.length;
        if(hashTable[index] == null) {
            hashTable[index] = node;
            numElements++;
            return true;
        }

        Node<E> current = hashTable[index];
        while(current.next != null) {
            if(current.element == node.element) return false;
            current = current.next;
        }
        current.next = node;
        numElements++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if(isEmpty() || o == null) return false;
        Node<E> removeNode = new Node(o);
        int index = Math.abs(o.hashCode()) % hashTable.length;
        if(hashTable[index] == null) return false;

        Node<E> current = hashTable[index];

        if(current.element == removeNode.element) {
            hashTable[index] = current.next;
            numElements--;
            return true;
        }

        Node<E> previous = current;

        while(current.next != null) {
            current = current.next;
            if(current.element == removeNode.element) {
                previous.next = current.next;
                numElements--;
                return true;
            }
            previous = current;
        }

        return false;
    }

    @Override
    public int size() { return numElements; }

    @Override
    public boolean isEmpty() { return numElements == 0; }

    @Override
    public boolean contains(Object o) {
        if(isEmpty() || o == null) return false;
        Node<E> removeNode = new Node(o);
        int index = Math.abs(o.hashCode()) % hashTable.length;
        if(hashTable[index] == null) return false;

        Node<E> current = hashTable[index];

        if(current.element == removeNode.element) { return true; }

        while(current.next != null) {
            current = current.next;
            if(current.element == removeNode.element) return true;
        }

        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return (Iterator<E>) Arrays.stream(hashTable).iterator();
    }

    @Override
    public Object[] toArray() {
        return Arrays.stream(hashTable).toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        int counter = 0;
        for(Node<E> element: hashTable) {
            if(element != null) {
                Node<E> current = element;
                a[counter] = (T) current.element;
                counter++;
                while(current.next != null) {
                    a[counter] = (T) current.element;
                    counter++;
                    current = current.next;
                }
            }
        }
        return a;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for(Object item : c) if(!contains(item)) return false;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean addedAll = true;
        for(E item : c) if(!add(item)) addedAll = false;
        return addedAll;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean retainedAll = true;
        for(Object item: c) if(!contains(item)) retainedAll = false;
        if(retainedAll == true) {
            this.clear();
            for(Object item: c) this.add((E) item);
        }
        return retainedAll;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean removedAll = true;
        for(Object item : c) {
            if(!remove(item)) removedAll = false;
        }
        return removedAll;
    }

    @Override
    public void clear() {
        this.hashTable =  new Node[initialCap];
        this.numElements = 0;
    }

    public void expandCapacity() {
        Node<E>[] oldTable = hashTable;
        hashTable = new Node[oldTable.length*2];
        this.numElements = 0;
        for(Node<E> element: oldTable) {
            if(element != null) {
                Node<E> current = element;
                this.add(current.element);
                while(current.next != null) {
                    add(current.next.element);
                    current = current.next;
                }
            }
        }
    }

    public String toString() {
        String s = "";
        for(int i=0; i<hashTable.length; i++) {
            Node<E> current = hashTable[i];
            s+=i+1 + ". ";
            if(current != null) {
                s+= current.element;
                while(current.next != null) {
                    s += " " + current.next.element;
                    current = current.next;
                }
            }
            s+="\n";
        }

        return s;
    }

    protected class Node<E>
    {
        public E element;
        public Node<E> next;

        public Node(E element)
        {  this.element = element;
            next = null;
        }
    }

    public static void main(String[] args) {
        Set<String> hashSetWithChaining = new HashSetWithChaining<>(10);
//        hashSetWithChaining.add("Peter");
//        hashSetWithChaining.add("Peter");
//        hashSetWithChaining.add("Dreezy");
//        hashSetWithChaining.add("Cyrus");
//        hashSetWithChaining.add("Paul");
//        hashSetWithChaining.add("Les");
//        hashSetWithChaining.add("Oliver");
//        hashSetWithChaining.add("Pat");
//
//        hashSetWithChaining.add("Polo");
//        hashSetWithChaining.add("Cole");
//
//        hashSetWithChaining.add("Shou");
//        hashSetWithChaining.add("Kent");
//        hashSetWithChaining.add("Shawn");
//        hashSetWithChaining.add("Ben");
//        hashSetWithChaining.add("Hunter");
//        hashSetWithChaining.add("Mario");
//        hashSetWithChaining.add("Luigi");

//        String[] list = hashSetWithChaining.toArray(new String[hashSetWithChaining.size()]);
//        for(String string : list) System.out.println(string);

        System.out.println(hashSetWithChaining);
        System.out.println(hashSetWithChaining.size());
        System.out.println("--------------------");

        String[] removal = new String[5];
        removal[0] = "Shou";
        removal[1] = "Kent";
        removal[2] = "Pat";
        removal[3] = "Oliver";
        removal[4] = "Peter";


        System.out.println(hashSetWithChaining.addAll(Arrays.asList(removal)));
        System.out.println(hashSetWithChaining);
        System.out.println(hashSetWithChaining.size());
    }
}
