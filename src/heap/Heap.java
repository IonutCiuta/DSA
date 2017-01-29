package heap;

import java.util.ArrayList;

/**
 * Created by John on 22.01.2017.
 */
public class Heap {
    enum Type {
        MINHEAP, MAXHEAP;
    }

    private ArrayList<Integer> data;
    private int size;
    private Type type;

    private Heap(Type type) {
        this.data = new ArrayList<>();
        this.size = 0;
        this.type = type;
    }

    public static Heap Minheap() {
        return new Heap(Type.MINHEAP);
    }

    public static Heap Maxheap() {
        return new Heap(Type.MAXHEAP);
    }

    public void insert(int value) {
        if(type.equals(Type.MINHEAP)) {
            insertInMinheap(value);
            return;
        }

        if(type.equals(Type.MAXHEAP)) {
            insertInMaxheap(value);
            return;
        }
    }

    private void insertInMinheap(int value) {
        data.add(value);
        int i = size;
        int parent = parent(i);

        while (i > 0 && data.get(parent) > data.get(i)) {
            switchValues(i, parent);
            i = parent;
            parent = parent(i);
        }

        size++;
    }

    private void insertInMaxheap(int value) {
        //todo
    }

    public void remove(int value) {
        if(type.equals(Type.MINHEAP)) {
            removeFromMinHeap(value);
            return;
        }

        if(type.equals(Type.MAXHEAP)) {
            removeFromMaxHeap(value);
            return;
        }
    }

    private void removeFromMinHeap(int value) {
        //Find index of value to be removed
        int i = data.indexOf(value);

        //Replace value with last element
        data.set(i, data.get(--size));

        //Minify heap
        int target = data.get(i);
        while(left(i) > 0 && (target > data.get(left(i)) || target > data.get(right(i)))) {
            if(target > data.get(left(i))) {
                switchValues(left(i), i);
                i = left(i);
                target = data.get(i);
                continue;
            }

            if (target > data.get(right(i))) {
                switchValues(right(i), i);
                i = right(i);
                target = data.get(i);
                continue;
            }
        }

        data.remove(size);
    }

    private void removeFromMaxHeap(int value) {

    }

    private void switchValues(int origin, int destination) {
        int aux = data.get(destination);
        data.set(destination, data.get(origin));
        data.set(origin, aux);
    }

    private int left(int i) {
        return i*2 + 1;
    }

    private int right(int i) {
        return i*2 + 2;
    }

    private int parent(int i) {
        return (i-1) / 2;
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
