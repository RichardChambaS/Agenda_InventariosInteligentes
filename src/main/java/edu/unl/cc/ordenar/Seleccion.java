package edu.unl.cc.ordenar;


import java.util.Comparator;

public class Seleccion<T> implements Ordenar_I<T> {

    @Override
    public void ordenar(T[] arreglo, Comparator<T> cmp, MetricasOrden metricas) {

        for (int i = 0; i < arreglo.length - 1; i++) {
            int minIndex = i;

            for (int j = i + 1; j < arreglo.length; j++) {

                metricas.comparaciones++;

                if (cmp.compare(arreglo[j], arreglo[minIndex]) < 0) {
                    minIndex = j;
                }
            }

            if (minIndex != i) {
                T temp = arreglo[i];
                arreglo[i] = arreglo[minIndex];
                arreglo[minIndex] = temp;
                metricas.intercambios++;
            }
        }
    }
}
