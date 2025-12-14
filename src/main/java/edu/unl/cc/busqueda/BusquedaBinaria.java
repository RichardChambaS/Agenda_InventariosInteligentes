package edu.unl.cc.busqueda;

import java.util.Comparator;

public class BusquedaBinaria {

    // Búsqueda binaria iterativa genérica. Requiere que el arreglo esté ordenado según el comparador.
    public static <T> int busquedaBinaria(T[] arr, T clave, Comparator<T> cmp) {
        int izquierda = 0;
        int derecha = arr.length - 1;
        while (izquierda <= derecha) {
            int medio = izquierda + (derecha - izquierda) / 2;
            int comparacion = cmp.compare(arr[medio], clave);
            if (comparacion == 0) {
                return medio; // Encontrado
            } else if (comparacion < 0) {
                izquierda = medio + 1;
            } else {
                derecha = medio - 1;
            }
        }
        return -1; // No encontrado
    }

    // Método específico para Citas, usando compareTo natural (por fechaHora)
    public static int busquedaBinaria(edu.unl.cc.modelo.Citas[] arr, edu.unl.cc.modelo.Citas clave) {
        return busquedaBinaria(arr, clave, Comparator.naturalOrder());
    }

    // Método específico para Inventario, usando compareTo natural (por stock)
    public static int busquedaBinaria(edu.unl.cc.modelo.Inventario[] arr, edu.unl.cc.modelo.Inventario clave) {
        return busquedaBinaria(arr, clave, Comparator.naturalOrder());
    }
}
