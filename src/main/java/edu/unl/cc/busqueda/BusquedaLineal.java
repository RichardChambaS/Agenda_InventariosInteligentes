package edu.unl.cc.busqueda;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class BusquedaLineal {
    public static <T> int primera(T[] arreglo, Predicate<T> pred) {
        for (int i = 0; i < arreglo.length; i++) {
            if (pred.test(arreglo[i])) {
                return i;
            }
        }
        return -1;
    }

    // Buscar la última coincidencia
    public static <T> int ultima(T[] arreglo, Predicate<T> pred) {
        for (int i = arreglo.length - 1; i >= 0; i--) {
            if (pred.test(arreglo[i])) {
                return i;
            }
        }
        return -1;
    }

    // Buscar todas las coincidencias
    public static <T> List<T> findAll(T[] arr, Predicate<T> pred) {
        List<T> r = new ArrayList<>();
        for (T elem : arr) {
            if (pred.test(elem)) {
                r.add(elem);
            }
        }
        return r;
    }

    // Búsqueda con centinela (solo exact match)
    public static <T> int centinela(T[] arr, T key) {
        if (arr.length == 0) return -1;

        T last = arr[arr.length - 1];
        arr[arr.length - 1] = key;

        int i = 0;
        while (!arr[i].equals(key)) {
            i++;
        }

        arr[arr.length - 1] = last;

        if (i < arr.length - 1 || last.equals(key)) {
            return i;
        }
        return -1;
    }
}
