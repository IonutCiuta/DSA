package heap;

import java.util.Arrays;

/**
 * Created by John on 29.01.2017.
 */
public class MinHeap {
    private static final int DEFAULT_CAPACITY = 10;
    private int[] heap;
    private int size;
    private int capacity;

    public MinHeap(int capacity) {
        this.heap = new int[capacity];
        this.capacity = capacity;
        this.size = 0;
    }

    public MinHeap() {
        this(DEFAULT_CAPACITY);
    }

    private int leftChildIndex(int parent) { return parent * 2 + 1; }
    private int rightChildIndex(int parent) { return parent * 2 + 2; }
    private int parentIndex(int child) { return (child - 1) / 2; }

    private boolean hasLeftChild(int parent) { return leftChildIndex(parent) < size; }
    private boolean hasRighChild(int parent) { return rightChildIndex(parent) < size; }
    private boolean hasParent(int child) { return child > 0 && parentIndex(child) >= 0; }

    private int getLeftChild(int parent) { return heap[leftChildIndex(parent)]; }
    private int getRightChild(int parent) { return heap[rightChildIndex(parent)]; }
    private int getParent(int child) {return heap[parentIndex(child)]; }

    private void swap(int destination, int source) {
        int aux = heap[destination];
        heap[destination] = heap[source];
        heap[source] = aux;
    }

    private void ensureCapacity() {
        if(size == capacity) {
            capacity *= 2;
            heap = Arrays.copyOf(heap, capacity);
        }
    }

    private void heapifyDown() {
        int index = 0;

        while(hasLeftChild(index)) {
            int smallerChildIndex = leftChildIndex(index);
            if(hasRighChild(index) && (getRightChild(index) < getLeftChild(index))) {
                smallerChildIndex = rightChildIndex(index);
            }

            if(heap[index] > heap[smallerChildIndex]) {
                swap(smallerChildIndex, index);
                index = smallerChildIndex;
            } else {
                break;
            }
        }
    }

    private void heapifyUp() {
        int index = size - 1;

        while(hasParent(index)) {
            int parentIndex = parentIndex(index);
            if(getParent(index) > heap[index]) {
                swap(parentIndex, index);
                index = parentIndex;
            } else {
                break;
            }
        }
    }

    public int peek() {
        if(size == 0) throw new IllegalStateException();
        return heap[0];
    }

    public int poll() {
        int result = peek();
        size -= 1;
        swap(0, size);
        heap[size] = 0;
        heapifyDown();
        return result;
    }

    public void insert(int item) {
        ensureCapacity();
        heap[size] = item;
        size++;
        heapifyUp();
    }

    public void heapSort() {
        int[] result = new int[capacity];
        int next = 0;
        while (size > 0) {
            result[next++] = poll();
        }
        heap = result;
        size = next;
    }

    @Override
    public String toString() {
        return Arrays.toString(heap);
    }
}
