package Question1;

/**
   A class that implements a queue collection using a
   singly-linked list as the underlying data structure
   @author Andrew Ensor
*/
import java.util.Collection;
import java.util.NoSuchElementException;

public class LinkedQueue<E> implements QueueADT<E>
{
   private int numElements;
   private Node<E> frontNode; // front of list is front of queue
   private Node<E> rearNode; // rear of list is rear of queue
   
   // default constructor that creates a new queue
   // that is initially empty
   public LinkedQueue()
   {  super();
      numElements = 0;
      frontNode = null;
      rearNode = null;
   }
   
   // constructor for creating a new queue which
   // initially holds the elements in the collection c
   public LinkedQueue(Collection<? extends E> c)
   {  this();
      for (E element : c)
         enqueue(element);
   }
   

   // Adds one element to the rear of this queue
   public void enqueue(E element)
   {  Node<E> newNode = new Node<E>(element);
      // add the new node to the end of the list
      if (rearNode==null)
      {  frontNode = newNode;
         rearNode = newNode;
      }
      else
      {  rearNode.next = newNode;
         rearNode = newNode;
      }
      numElements++;
   }
   
   // removes and returns the front element from this queue
   public E dequeue() throws NoSuchElementException
   {  if (frontNode != null)
      {  E frontElement = frontNode.element;
         frontNode = frontNode.next;
         numElements--;
         if (numElements == 0)
            rearNode = null;
         return frontElement;
      }
      else
         throw new NoSuchElementException();
   }
   
   // returns without removing the front element of this queue
   public E first() throws NoSuchElementException
   {  if (numElements > 0)
         return frontNode.element;
      else
         throw new NoSuchElementException();
   }
   
   // returns true if this queue contains no elements
   public boolean isEmpty()
   {  return (numElements==0);
   }
   
   // returns the number of elements in this queue
   public int size()
   {  return numElements;
   }
   
   // returns a string representation of the queue from front to rear
   public String toString()
   {  String output = "[";
      Node currentNode = frontNode;
      while (currentNode != null)
      {  output += currentNode.element;
         if (currentNode.next != null)
            output += ",";
         currentNode = currentNode.next;
      }
      output += "]";
      return output;
   }
      
   // inner class which represents a node in a singly-linked list
   private class Node<E>
   {  
      public E element;
      public Node<E> next;
      
      public Node(E element)
      {  this.element = element;
         next = null;
      }
   }
}
