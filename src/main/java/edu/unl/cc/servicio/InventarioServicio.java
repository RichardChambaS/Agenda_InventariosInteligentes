package edu.unl.cc.servicio;

import edu.unl.cc.busqueda.BusquedaBinaria;
import edu.unl.cc.busqueda.Limites;
import edu.unl.cc.busqueda.BusquedaLineal;
import edu.unl.cc.modelo.Inventario;
import edu.unl.cc.ordenar.*;
import edu.unl.cc.utilidades.CSVloader;

import java.util.Comparator;

public class InventarioServicio {

    private Inventario[] items;

    // Comparador por stock convertido a entero
    private final Comparator<Inventario> cmpStock =
            (a, b) -> Integer.compare(
                    Integer.parseInt(a.getStock()),
                    Integer.parseInt(b.getStock())
            );

    public void cargarInventario(String archivoCSV) throws Exception {
        items = CSVloader.cargar(
                archivoCSV,
                Inventario::fromCSV,
                Inventario[]::new
        );
    }

    public MetricasOrden ordenarBurbuja() {
        MetricasOrden m = new MetricasOrden();
        new Borbuja<Inventario>().ordenar(items, cmpStock, m);
        return m;
    }

    public MetricasOrden ordenarInsercion() {
        MetricasOrden m = new MetricasOrden();
        new Insercion<Inventario>().ordenar(items, cmpStock, m);
        return m;
    }

    public MetricasOrden ordenarSeleccion() {
        MetricasOrden m = new MetricasOrden();
        new Seleccion<Inventario>().ordenar(items, cmpStock, m);
        return m;
    }

    public int buscarExactoPorStock(int stock) {
        Inventario clave = new Inventario("", "", String.valueOf(stock));
        return BusquedaBinaria.buscar(items, clave, cmpStock);
    }

    public Inventario[] buscarRangoStock(int min, int max) {
        Inventario a = new Inventario("", "", String.valueOf(min));
        Inventario b = new Inventario("", "", String.valueOf(max));

        int lb = Limites.lowerBound(items, a, cmpStock);
        int ub = Limites.upperBound(items, b, cmpStock);

        if (lb >= ub) return new Inventario[0];

        Inventario[] r = new Inventario[ub - lb];
        System.arraycopy(items, lb, r, 0, r.length);

        return r;
    }

    public Inventario[] getItems() { return items; }
}
