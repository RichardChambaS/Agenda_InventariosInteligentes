package edu.unl.cc.ordenar;


import java.util.Comparator;

public class Seleccion<T> implements Ordenar_I<T> {

    @Override
    public void sort(T[] array, Comparator<T> cmp, MetricasOrden metrics) {

        for (int i = 0; i < array.length - 1; i++) {
            int minIndex = i;

            for (int j = i + 1; j < array.length; j++) {

                metrics.comparisons++;

                if (cmp.compare(array[j], array[minIndex]) < 0) {
                    minIndex = j;
                }
            }

            if (minIndex != i) {
                T temp = array[i];
                array[i] = array[minIndex];
                array[minIndex] = temp;
                metrics.swaps++;
            }
        }
    }
}
