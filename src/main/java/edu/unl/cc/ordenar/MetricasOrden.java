package edu.unl.cc.ordenar;

//Esta clase solo es para que los valores vayan subiendo
public class MetricasOrden {
    public long comparisons = 0; //Cada vez que usamos ComparteTo() se aumenta uno
    public long swaps = 0;
    public long timeNs = 0;


    @Override
    public String toString() {
        return "comparisons=" + comparisons +
                ", swaps=" + swaps +
                ", time=" + timeNs + "ns";
    }
}
