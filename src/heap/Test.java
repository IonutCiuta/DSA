package heap;

/**
 * Created by John on 22.01.2017.
 */
public class Test {
    public static void main(String[] args) {
        //Heap heap = Heap.Minheap();
        MinHeap heap = new MinHeap();
        heap.insert(10);
        System.out.println(heap);

        heap.insert(11);
        System.out.println(heap);

        heap.insert(20);
        System.out.println(heap);

        heap.insert(100);
        System.out.println(heap);

        heap.insert(9);
        System.out.println(heap);

        heap.insert(8);
        System.out.println(heap);

        heap.insert(7);
        System.out.println(heap);

        heap.poll();
        System.out.println(heap);

        heap.heapSort();
        System.out.println(heap);
    }
}
