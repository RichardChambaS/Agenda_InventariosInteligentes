package edu.unl.cc.ordenar;

import java.util.Comparator;

public class Insercion<T> implements Ordenar_I<T> {

    @Override
    public void sort(T[] array, Comparator<T> cmp, MetricasOrden metrics) {

        for (int i = 1; i < array.length; i++) {

            T temp = array[i];
            int position = i;

            while (position > 0) {
                metrics.comparisons++;

                if (cmp.compare(array[position - 1], temp) > 0) {
                    array[position] = array[position - 1];
                    metrics.swaps++;
                    position--;
                } else {
                    break;
                }
            }
            array[position] = temp;
        }
    }
}
