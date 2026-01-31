package com.air.advantage.service.communication;

import java.util.ArrayList;
import java.util.List;

public class BoundedMessageQueue<T> {
    private final T[] elements;
    private int head = 0;
    private int tail = 0;
    private int size = 0;
    private final int capacity;

    @SuppressWarnings("unchecked")
    public BoundedMessageQueue(int capacity) {
        this.capacity = capacity;
        this.elements = (T[]) new Object[capacity];
    }

    public synchronized void clear() {
        for (int i = 0; i < size; i++) {
            elements[(head + i) % capacity] = null;
        }
        head = 0;
        tail = 0;
        size = 0;
    }

    public synchronized void push(T item) {
        // Do not add duplicates: use equals() to compare existing elements
        for (int i = 0; i < size; i++) {
            int idx = (head + i) % capacity;
            T existing = elements[idx];
            if (existing == null && item == null) {
                return;
            }
            if (existing != null && existing.equals(item)) {
                return;
            }
        }

        if (size == capacity) {
            // Queue is full, remove oldest element
            head = (head + 1) % capacity;
            size--;
        }

        elements[tail] = item;
        tail = (tail + 1) % capacity;
        size++;
    }

    public synchronized T pop() {
        if (size == 0) {
            return null;
        }
        
        T item = elements[head];
        elements[head] = null;
        head = (head + 1) % capacity;
        size--;
        return item;
    }

    public synchronized int size() {
        return size;
    }

    public synchronized boolean isEmpty() {
        return size == 0;
    }

    public synchronized List<T> getAll() {
        List<T> result = new ArrayList<>(size);
        int index = head;
        for (int i = 0; i < size; i++) {
            result.add(elements[index]);
            index = (index + 1) % capacity;
        }
        return result;
    }
}