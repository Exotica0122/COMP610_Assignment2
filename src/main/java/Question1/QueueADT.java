package Question1;

/**
   An interface that defines the abstract data type
   for a queue collection of elements with type E
*/
import java.util.NoSuchElementException;

public interface QueueADT<E>
{
   // adds one element to the rear of this queue
   public void enqueue(E element);
   // removes and returns the front element of the queue
   public E dequeue() throws NoSuchElementException;
   // returns without removing the front element of this queue
   public E first() throws NoSuchElementException;
   // returns true if this queue contains no elements
   public boolean isEmpty();
   // returns the number of elements in this queue
   public int size();
}
