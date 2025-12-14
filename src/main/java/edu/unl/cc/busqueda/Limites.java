package edu.unl.cc.busqueda;

import java.util.Comparator;

public class Limites {

    // lowerBound: primer índice donde clave aparece o debería aparecer (primer >= clave)
    public static <T> int lowerBound(T[] arr, T clave, Comparator<T> cmp) {
        int izquierda = 0;
        int derecha = arr.length;
        while (izquierda < derecha) {
            int medio = izquierda + (derecha - izquierda) / 2;
            if (cmp.compare(arr[medio], clave) < 0) {
                izquierda = medio + 1;
            } else {
                derecha = medio;
            }
        }
        return izquierda;
    }

    // upperBound: primer índice donde clave es mayor (último índice + 1 donde aparece)
    public static <T> int upperBound(T[] arr, T clave, Comparator<T> cmp) {
        int izquierda = 0;
        int derecha = arr.length;
        while (izquierda < derecha) {
            int medio = izquierda + (derecha - izquierda) / 2;
            if (cmp.compare(arr[medio], clave) <= 0) {
                izquierda = medio + 1;
            } else {
                derecha = medio;
            }
        }
        return izquierda;
    }

    // Métodos específicos para Citas
    public static int lowerBound(edu.unl.cc.modelo.Citas[] arr, edu.unl.cc.modelo.Citas clave) {
        return lowerBound(arr, clave, Comparator.naturalOrder());
    }

    public static int upperBound(edu.unl.cc.modelo.Citas[] arr, edu.unl.cc.modelo.Citas clave) {
        return upperBound(arr, clave, Comparator.naturalOrder());
    }

    // Métodos específicos para Inventario
    public static int lowerBound(edu.unl.cc.modelo.Inventario[] arr, edu.unl.cc.modelo.Inventario clave) {
        return lowerBound(arr, clave, Comparator.naturalOrder());
    }

    public static int upperBound(edu.unl.cc.modelo.Inventario[] arr, edu.unl.cc.modelo.Inventario clave) {
        return upperBound(arr, clave, Comparator.naturalOrder());
    }
}
