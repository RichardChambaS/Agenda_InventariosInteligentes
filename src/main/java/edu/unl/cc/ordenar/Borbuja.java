package edu.unl.cc.ordenar;


import java.util.Comparator;

public class Borbuja<T> implements Ordenar_I<T> {

    @Override
    public void ordenar(T[] array, Comparator<T> cmp, MetricasOrden metrics) {

        for (int i = 0; i < array.length - 1; i++) {
            boolean swapped = false;

            for (int j = 0; j < array.length - 1 - i; j++) {

                metrics.comparaciones++;

                if (cmp.compare(array[j], array[j + 1]) > 0) {

                    T temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;

                    metrics.intercambios++;
                    swapped = true;
                }
            }

            if (!swapped) break;
        }
    }
}
