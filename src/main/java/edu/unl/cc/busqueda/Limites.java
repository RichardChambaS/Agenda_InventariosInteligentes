package edu.unl.cc.busqueda;

import java.util.Comparator;

public class Limites {
    // Primer índice donde arr[i] >= key

    public static <T> int lowerBound(T[] arreglo, T key, Comparator<T> cmp) {
        int low = 0;
        int high = arreglo.length;

        while (low < high) {
            int mid = low + (high - low) / 2;

            if (cmp.compare(arreglo[mid], key) < 0) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }

    // Primer índice donde arr[i] > key
    public static <T> int upperBound(T[] arreglo, T key, Comparator<T> cmp) {
        int low = 0;
        int high = arreglo.length;

        while (low < high) {
            int mid = low + (high - low) / 2;

            if (cmp.compare(arreglo[mid], key) <= 0) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }
}
