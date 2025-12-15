package edu.unl.cc.servicio;

import edu.unl.cc.busqueda.BusquedaBinaria;
import edu.unl.cc.busqueda.Limites;
import edu.unl.cc.busqueda.BusquedaLineal;
import edu.unl.cc.modelo.Inventario;
import edu.unl.cc.ordenar.*;
import edu.unl.cc.utilidades.CSVloader;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

    public List<Inventario> buscarPorRango(int minStock, int maxStock) {
        // 1. Validación básica
        if (items == null || items.length == 0) {
            return new ArrayList<>();
        }

        // 2. ORDENAR: Es OBLIGATORIO para búsqueda binaria.
        // Usamos Inserción porque si ya está ordenado, es muy rápido.
        ordenarInsercion();

        // 3. Crear objetos "Dummy" para comparar
        // Solo nos importa el stock, el id y nombre pueden ser vacíos
        Inventario dummyMin = new Inventario("", "", String.valueOf(minStock));
        Inventario dummyMax = new Inventario("", "", String.valueOf(maxStock));

        // 4. Calcular los índices con Búsqueda Binaria (Tus clases Limites)
        // lowerBound: Primer elemento >= minStock
        int inicio = Limites.lowerBound(items, dummyMin, cmpStock);

        // upperBound: Primer elemento > maxStock (exclusivo)
        int fin = Limites.upperBound(items, dummyMax, cmpStock);

        // 5. Recolectar los elementos en ese rango
        List<Inventario> resultados = new ArrayList<>();

        // El bucle va desde 'inicio' hasta 'fin' (sin incluir fin)
        for (int i = inicio; i < fin && i < items.length; i++) {
            resultados.add(items[i]);
        }

        return resultados;
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

    public int buscarCentinela(String stockBuscado) {
        // Necesitas crear un objeto dummy para comparar
        Inventario key = new Inventario("", "", stockBuscado);
        return BusquedaLineal.centinela(items, key);
    }

    public Inventario[] getItems() { return items; }
}
