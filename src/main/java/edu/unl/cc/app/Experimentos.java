package edu.unl.cc.app;

import edu.unl.cc.ordenar.MetricasOrden;
import edu.unl.cc.ordenar.Recomendation;
import edu.unl.cc.servicio.AgendaServicio;
import edu.unl.cc.servicio.InventarioServicio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class Experimentos {

    public static void ejecutarTodo(AgendaServicio dummyAgenda, InventarioServicio dummyInv) {
        System.out.println("\n=================================================");
        System.out.println("   MODO AUTOMATICO COMPLETO");
        System.out.println("=================================================");

        compararAlgoritmosAgenda(dummyAgenda, "citas_100.csv");
        compararAlgoritmosAgenda(dummyAgenda, "citas_100_casi_ordenadas.csv");
        compararAlgoritmosInventario(dummyInv, "inventario_500_inverso.csv");
    }

    /**
     * Comparativa para la AGENDA (Citas)
     */
    public static void compararAlgoritmosAgenda(AgendaServicio servicio, String archivo) {
        System.out.println("\n>>> ANÁLISIS PARA AGENDA: " + archivo);

        // Definimos cómo recargar los datos para la Agenda
        Runnable cargador = () -> {
            try {
                servicio.cargarCitas(archivo);
            } catch (Exception e) {
                throw new RuntimeException("Error cargando citas: " + e.getMessage());
            }
        };

        // Medimos los 3 algoritmos usando la lógica de la mediana
        MetricasOrden mBurbuja = medirPromedio("Burbuja", cargador, servicio::ordenarBurbuja);
        MetricasOrden mSeleccion = medirPromedio("Selección", cargador, servicio::ordenarSeleccion);
        MetricasOrden mInsercion = medirPromedio("Inserción", cargador, servicio::ordenarInsercion);

        imprimirTabla(mBurbuja, mSeleccion, mInsercion);

        // Pasamos datos ficticios al generador de recomendaciones (solo necesita el tamaño y métricas)
        // Usamos un array object vacío del tamaño aproximado solo para cumplir la firma,
        // o si Recomendation usa datos reales, asegurarse de que el servicio tenga datos cargados al final.
        try { servicio.cargarCitas(archivo); } catch (Exception e){} // Recargar una última vez para analisis
        Recomendation.generar(servicio.getCitas(), mBurbuja, mInsercion, mSeleccion);
    }

    /**
     * Comparativa para el INVENTARIO
     */
    public static void compararAlgoritmosInventario(InventarioServicio servicio, String archivo) {
        System.out.println("\n>>> ANÁLISIS PARA INVENTARIO: " + archivo);

        // Definimos cómo recargar los datos para el Inventario
        Runnable cargador = () -> {
            try {
                servicio.cargarInventario(archivo);
            } catch (Exception e) {
                throw new RuntimeException("Error cargando inventario: " + e.getMessage());
            }
        };

        MetricasOrden mBurbuja = medirPromedio("Burbuja", cargador, servicio::ordenarBurbuja);
        MetricasOrden mSeleccion = medirPromedio("Selección", cargador, servicio::ordenarSeleccion);
        MetricasOrden mInsercion = medirPromedio("Inserción", cargador, servicio::ordenarInsercion);

        imprimirTabla(mBurbuja, mSeleccion, mInsercion);

        try { servicio.cargarInventario(archivo); } catch (Exception e){}
        Recomendation.generar(servicio.getItems(), mBurbuja, mInsercion, mSeleccion);
    }


    // --- Utilitarios de Medición ---

    /**
     * MÉTODOLOGÍA:
     * 1. Ejecuta 11 veces.
     * 2. Descarta la primera (Warm-up de la JVM).
     * 3. Recarga los datos originales antes de cada ejecución.
     * 4. Calcula la mediana de los tiempos.
     */
    private static MetricasOrden medirPromedio(String nombreAlgoritmo, Runnable recargarDatos, Supplier<MetricasOrden> algoritmo) {
        List<Long> tiempos = new ArrayList<>();
        MetricasOrden ultimasMetricas = null;


        for (int i = 0; i < 11; i++) {
            // 1. Limpiar memoria y Recargar datos desordenados
            recargarDatos.run();
            System.gc(); // Sugerencia al Garbage Collector para estabilizar

            // 2. Ejecutar y medir
            long inicio = System.nanoTime();
            MetricasOrden m = algoritmo.get();
            long fin = System.nanoTime();

            // 3. Guardar tiempo (ignoramos la corrida 0 por calentamiento)
            if (i > 0) {
                tiempos.add(fin - inicio);
                ultimasMetricas = m; // Guardamos las comparaciones/swaps (son constantes en mismos datos)
            }
        }

        if (ultimasMetricas == null) return new MetricasOrden();

        // 4. Calcular Mediana
        Collections.sort(tiempos);
        long mediana = tiempos.get(tiempos.size() / 2);

        // Asignamos la mediana al objeto de métricas
        ultimasMetricas.tiempoNs = mediana;

        return ultimasMetricas;
    }


    private static void imprimirTabla(MetricasOrden b, MetricasOrden s, MetricasOrden i) {
        if (b == null || s == null || i == null) {
            System.out.println("Error: No se pudieron obtener métricas.");
            return;
        }

        System.out.printf("%-12s %-15s %-15s %-15s\n", "Algoritmo", "Comparaciones", "Intercambios", "Tiempo (ns)");
        System.out.println("-----------------------------------------------------------");
        System.out.printf("%-12s %-15d %-15d %-15d\n", "Burbuja", b.comparaciones, b.intercambios, b.tiempoNs);
        System.out.printf("%-12s %-15d %-15d %-15d\n", "Seleccion", s.comparaciones, s.intercambios, s.tiempoNs);
        System.out.printf("%-12s %-15d %-15d %-15d\n", "Insercion", i.comparaciones, i.intercambios, i.tiempoNs);
    }
}