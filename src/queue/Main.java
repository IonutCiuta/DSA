package queue;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by John on 14.01.2017.
 */
public class Main {
    public static void main(String[] args) {
        Queue<Integer> queue = new Queue<>();
        queue.push(1);
        queue.push(2);
        queue.push(30);
        queue.push(75);
        System.out.println(queue);

        queue.pop();
        System.out.println(queue);

        queue.push(100);
        queue.push(499);
        System.out.println(queue);

        queue.remove(3);
        System.out.println(queue);
    }
}

class Queue<E> {
    private LinkedList<E> storage;

    public Queue() {
        this.storage = new LinkedList<>();
    }

    public void push(E i) {
        this.storage.addFirst(i);
    }

    public E pop() {
        return this.storage.remove(storage.size() - 1);
    }

    public void remove(Integer i) {
        this.storage.remove(i-1);
    }

    @Override
    public String toString() {
        return storage.toString();
    }
}
