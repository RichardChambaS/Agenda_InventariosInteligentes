package edu.unl.cc.ordenar;

import java.util.Comparator;

public interface Ordenar_I<T> {
    void sort(T[] arr, Comparator<T> cmp, MetricasOrden metrics);
}
