package edu.unl.cc.busqueda;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class BusquedaLineal {

    // Devuelve el índice de la primera ocurrencia de clave, o -1 si no se encuentra
    public static <T> int primeraOcurrencia(T[] arr, T clave) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != null && arr[i].equals(clave)) {
                return i;
            }
        }
        return -1;
    }

    // Devuelve el índice de la última ocurrencia de clave, o -1 si no se encuentra
    public static <T> int ultimaOcurrencia(T[] arr, T clave) {
        for (int i = arr.length - 1; i >= 0; i--) {
            if (arr[i] != null && arr[i].equals(clave)) {
                return i;
            }
        }
        return -1;
    }

    // Devuelve una lista de índices donde el predicado es verdadero
    public static <T> List<Integer> findAll(T[] arr, Predicate<T> pred) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != null && pred.test(arr[i])) {
                indices.add(i);
            }
        }
        return indices;
    }

    // Búsqueda lineal con centinela coloca la clave al final para evitar chequeo de limite
    public static <T> int busquedaConCentinela(T[] arr, T clave) {
        if (arr.length == 0) return -1;
        // Guardar el último elemento
        T ultimo = arr[arr.length - 1];
        // Colocar centinela
        arr[arr.length - 1] = clave;
        int i = 0;
        // Buscar hasta encontrar la clave
        while (!arr[i].equals(clave)) {
            i++;
        }
        // Restaurar el último elemento
        arr[arr.length - 1] = ultimo;
        // Verificar si se encontró realmente o fue el centinela
        if (i < arr.length - 1 || ultimo.equals(clave)) {
            return i;
        }
        return -1;
    }
}
