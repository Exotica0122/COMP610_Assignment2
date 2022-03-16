package Question2; /**
   A class that implements a heap collection using an array data
   structure, where a node at index i has its left child stored
   at index 2i+1 and its right child stored at index 2i+2,
   so its parent is at index (i-1)/2
   @author Andrew Ensor
*/
import java.util.Collection;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class ArrayHeap<E> implements HeapADT<E>
{
   private final int INITIAL_CAPACITY = 15; // initial heap height is 3
   private int numElements;
   private E[] elements;
   private Comparator<? super E> comparator;
   
   public ArrayHeap()
   {  this(null);
   }
   
   public ArrayHeap(Comparator<? super E> comparator)
   {  this.comparator = comparator;
      numElements = 0;
      elements = (E[])(new Object[INITIAL_CAPACITY]); // unchecked
   }
   
   public ArrayHeap(Collection<? extends E> c,
      Comparator<? super E> comparator)
   {  this(comparator);
      for (E element : c)
         add(element);
   }
   
   // adds one element to the heap in correct position to maintain heap
   public boolean add(E element)
   {  if (numElements >= elements.length)
         expandCapacity();
      elements[numElements++] = element;
      if (numElements>1)
         restoreHeapAfterAdd();      
      return true;
   }
   
   // helper method which increase the current size of the array to
   // handle another level of the heap
   private void expandCapacity()
   {  E[] largerArray 
         = (E[])(new Object[elements.length*2+1]); // unchecked
      // copy the elements array to the largerArray
      for (int i=0; i<numElements; i++)
         largerArray[i] = elements[i];
      elements = largerArray;
   }
   
   // helper method that restores the heap ordering after an element
   // has been added to index numElements-1, moving the element upward
   // toward the root until order is restored
   private void restoreHeapAfterAdd()
   {  int currentIndex = numElements-1; // start with added element
      int parentIndex = (currentIndex-1)/2;
      while (currentIndex>0 && compare(elements[currentIndex],
         elements[parentIndex])<0)
      {  // swap this node with its parent node
         E temp = elements[currentIndex];
         elements[currentIndex] = elements[parentIndex];
         elements[parentIndex] = temp;
         // move up to the parent node
         currentIndex = parentIndex;
         parentIndex = (currentIndex-1)/2;
      }
   }
   
   // removes and returns smallest element in heap and maintain heap
   // or throws a NoSuchElementException if heap is empty
   public E removeMin() throws NoSuchElementException
   {  if (numElements==0)
         throw new NoSuchElementException();
      E minElement = elements[0];
      // replace the root of heap with its last element
      elements[0] = elements[--numElements];
      elements[numElements] = null;
      restoreHeapAfterRemove();
      return minElement;
   }
   
   // helper method that restores the heap ordering after an element
   // has been removed, moving the root element downward until order
   // is restored
   private void restoreHeapAfterRemove()
   {  int currentIndex = 0; // start with root element
      int smallerChild = getSmallerChildIndex(currentIndex);
      while (smallerChild>=0 && compare(elements[currentIndex],
         elements[smallerChild])>0)
      {  // swap this node with its smaller child node
         E temp = elements[currentIndex];
         elements[currentIndex] = elements[smallerChild];
         elements[smallerChild] = temp;
         // move down to the smaller child node
         currentIndex = smallerChild;
         smallerChild = getSmallerChildIndex(currentIndex);
      }
   }
   
   // helper method which returns index of smaller child of element
   // at specified index, or returns -1 if no children
   private int getSmallerChildIndex(int parentIndex)
   {  int smallerChild = -1;
      int leftChild = 2*parentIndex+1;
      int rightChild = 2*parentIndex+2;
      if (leftChild<numElements)
      {  // the node has a left child
         if (rightChild<numElements)
         {  // the node also has a right child
            if (compare(elements[leftChild], elements[rightChild])<0)
               smallerChild = leftChild;
            else
               smallerChild = rightChild;
         }
         else // only a left child
            smallerChild = leftChild;
      }
      return smallerChild;
   }
   
   // returns without removing the smallest element in heap
   // or throws a NoSuchElementException if heap is empty
   public E getMin() throws NoSuchElementException
   {  if (numElements>0)
         return elements[0];
      else
         throw new NoSuchElementException();
   }
   
   // returns the Comparator used to order collection, or null if
   // collection is sorted according to elements' natural order
   public Comparator<? super E> comparator()
   {  return comparator;
   }

   // returns true if this heap contains no elements
   public boolean isEmpty()
   {  return numElements==0;
   }
   
   // returns the number of elements in this heap
   public int size()
   {  return numElements;
   }
   
   // returns a string representation of the elements in the heap
   public String toString()
   {  String output = "[";
      for (int i=0; i<numElements; i++)
      {  output += ""+elements[i];
         if (i+1<numElements)
            output += ",";
      }
      output += "]";
      return output;
   }

   // performs a comparison of the two elements, using the comparator
   // if not null, otherwise using the compareTo method
   private int compare(E element1, E element2)
   {  if (comparator!=null)
         return comparator.compare(element1, element2);
      else if (element1!=null && element1 instanceof Comparable)
         return ((Comparable)element1).compareTo(element2); //unchecked
      else if (element2!=null && element2 instanceof Comparable)
         return -((Comparable)element2).compareTo(element1);//unchecked
      else return 0;
   }
   
   public static void main(String[] args)
   {  HeapADT<String> heap = new ArrayHeap<String>();
      heap.add("cow");
      heap.add("cat");
      heap.add("dog");
      heap.add("bat");
      heap.add("ant");
      System.out.println("Elements sorted using heap sort");
      while (!heap.isEmpty())
      {  System.out.print(heap);
         System.out.println(" (smallest = " + heap.removeMin() + ")");
      }
   }
}
