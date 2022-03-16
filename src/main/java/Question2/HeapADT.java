package Question2; /**
   An interface that defines the abstract data type
   for an ascending heap collection of elements with type E
*/
import java.util.Comparator;

public interface HeapADT<E>
{
   // adds one element to the heap in correct position to maintain heap
   // and returns true if element successfully added
   public boolean add(E element);
   // removes and returns smallest element in heap and maintain heaps
   public E removeMin();
   // returns without removing the smallest element in heap
   public E getMin();
   // returns the Comparator used to order collection, or null if
   // collection is sorted according to elements' natural order
   public Comparator<? super E> comparator();
   // returns true if this heap contains no elements
   public boolean isEmpty();
   // returns the number of elements in this heap
   public int size();
}
