package edu.unl.cc.ordenar;

import java.util.Comparator;

public interface Ordenar_I<T> {
    void ordenar(T[] arr, Comparator<T> cmp, MetricasOrden metrics);
}
