package edu.unl.cc.utilidades;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class SLL<T> {

    // Nodo interno
    private class Node {
        T data;
        Node next;
        Node(T data) { this.data = data; this.next = null; }
    }

    private Node head;
    private Node tail;
    private int size;

    public void add(T data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }


    // Búsqueda Lineal: Todas las coincidencias (findAll)
    public List<T> findAll(Predicate<T> pred) {
        List<T> results = new ArrayList<>();
        Node current = head;
        while (current != null) {
            if (pred.test(current.data)) {
                results.add(current.data);
            }
            current = current.next;
        }
        return results;
    }

    public int buscarIndice(Predicate<T> condicion, boolean buscarPrimero) {
        Node current = head;
        int index = 0;
        int lastIndex = -1;

        while (current != null) {
            if (condicion.test(current.data)) {
                if (buscarPrimero) return index; // Retorna inmediato
                lastIndex = index; // Guarda y sigue buscando
            }
            current = current.next;
            index++;
        }
        return lastIndex;
    }


    public T get(int index) {
        if (index < 0 || index >= size) return null;
        Node current = head;
        for (int i = 0; i < index; i++) current = current.next;
        return current.data;
    }

    public boolean isEmpty() { return head == null; }
    public int size() { return size; }
}