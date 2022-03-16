package Question2;

import java.util.NoSuchElementException;

public class PriorityQueue implements QueueADT<HuffmanNode> {

    private final ArrayHeap<HuffmanNode> heap;

    public PriorityQueue() {
        this.heap = new ArrayHeap<>();
    }

    @Override
    public void enqueue(HuffmanNode element) {
        this.heap.add(element);
    }

    @Override
    public HuffmanNode dequeue() throws NoSuchElementException {
        return this.heap.removeMin();
    }

    @Override
    public HuffmanNode first() throws NoSuchElementException {
        return this.heap.getMin();
    }

    @Override
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    @Override
    public int size() {
        return heap.size();
    }

    public String toString() {
        return heap.toString();
    }

}
