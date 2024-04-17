package net.javaci.training.socketThread._06_threadProblems.producerConsumer;

import java.util.LinkedList;

public class Buffer {
    private LinkedList<Integer> buffer;
    private int capacity;

    public Buffer(int capacity) {
        this.capacity = capacity;
        buffer = new LinkedList<>();
    }

    public synchronized void produce(int item) throws InterruptedException {
        while (buffer.size() == capacity) {
            wait();
        }
        System.out.println("Üretici: " + item);
        buffer.add(item);
        notifyAll();
    }

    public synchronized int consume() throws InterruptedException {
        while (buffer.size() == 0) {
            wait();
        }
        int item = buffer.remove();
        System.out.println("Tüketici: " + item);
        notifyAll();
        return item;
    }
}