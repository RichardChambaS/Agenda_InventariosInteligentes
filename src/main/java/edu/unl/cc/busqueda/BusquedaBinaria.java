package edu.unl.cc.busqueda;

import java.util.Comparator;

public class BusquedaBinaria {

    public static <T> int buscar(T[] arr, T key, Comparator<T> cmp) {
        int low = 0;
        int high = arr.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            int c = cmp.compare(arr[mid], key);

            if (c == 0) {
                return mid;
            } else if (c < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }
}
