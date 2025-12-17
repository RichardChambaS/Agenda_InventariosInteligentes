package edu.unl.cc.ordenar;

//Esta clase solo es para que los valores vayan subiendo
public class MetricasOrden {
    public long comparaciones = 0; //Cada vez que usamos ComparteTo() se aumenta uno
    public long intercambios = 0;
    public long tiempoNs = 0;


    @Override
    public String toString() {
        return "comparisons=" + comparaciones +
                ", swaps=" + intercambios +
                ", time=" + tiempoNs + "ns";
    }
}
