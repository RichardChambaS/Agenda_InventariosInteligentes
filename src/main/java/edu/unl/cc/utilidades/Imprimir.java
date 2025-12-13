package edu.unl.cc.utilidades;

public class Imprimir {

    public static <T> void imprimirArreglo(T[] arr) {
        for (T e : arr) {
            System.out.println(e);
        }
    }

}
