package edu.unl.cc.app;

import edu.unl.cc.ordenar.MetricasOrden;
import edu.unl.cc.ordenar.Recomendation;
import edu.unl.cc.servicio.AgendaServicio;
import edu.unl.cc.servicio.InventarioServicio;
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

    public static void compararAlgoritmosAgenda(AgendaServicio servicio, String archivo) {
        System.out.println("\n>>> ANALISIS PARA: " + archivo);

        // Intentamos medir. Si falla la carga, medir devuelve null o vacio.
        MetricasOrden mBurbuja = medir(archivo, servicio, () -> servicio.ordenarBurbuja());
        MetricasOrden mSeleccion = medir(archivo, servicio, () -> servicio.ordenarSeleccion());
        MetricasOrden mInsercion = medir(archivo, servicio, () -> servicio.ordenarInsercion());

        if (mBurbuja != null) {
            imprimirTabla(mBurbuja, mSeleccion, mInsercion);
            Recomendation.generar(servicio.getCitas(), mBurbuja, mInsercion, mSeleccion);
        } else {
            System.out.println("[ERROR] No se pudo generar la tabla porque el archivo no cargo.");
        }
    }

    private static void compararAlgoritmosInventario(InventarioServicio servicio, String archivo) {
        System.out.println("\n>>> ANALISIS PARA: " + archivo);

        MetricasOrden mBurbuja = medirInv(archivo, servicio, () -> servicio.ordenarBurbuja());
        MetricasOrden mSeleccion = medirInv(archivo, servicio, () -> servicio.ordenarSeleccion());
        MetricasOrden mInsercion = medirInv(archivo, servicio, () -> servicio.ordenarInsercion());

        if (mBurbuja != null) {
            imprimirTabla(mBurbuja, mSeleccion, mInsercion);
        }
    }

    // --- Utilitarios de Medición ---

    private static MetricasOrden medir(String archivo, AgendaServicio s, Supplier<MetricasOrden> alg) {
        try {
            // Quitamos "resources/" para probar carga directa
            s.cargarCitas(archivo);

            // Verificacion de seguridad
            if (s.getCitas() == null || s.getCitas().length == 0) {
                System.out.println("[ALERTA] El arreglo esta vacio despues de cargar " + archivo);
                return null;
            }

        } catch (Exception e) {
            System.out.println("[ERROR GRAVE] Fallo al cargar " + archivo + ": " + e.getMessage());
            return null;
        }

        long t1 = System.nanoTime();
        MetricasOrden m = alg.get();
        m.tiempoNs = System.nanoTime() - t1;
        return m;
    }

    private static MetricasOrden medirInv(String archivo, InventarioServicio s, Supplier<MetricasOrden> alg) {
        try {
            s.cargarInventario(archivo);
            if (s.getItems() == null || s.getItems().length == 0) return null; // Validacion
        } catch (Exception e) {
            System.out.println("[ERROR GRAVE] Fallo al cargar inventario: " + e.getMessage());
            return null;
        }

        long t1 = System.nanoTime();
        MetricasOrden m = alg.get();
        m.tiempoNs = System.nanoTime() - t1;
        return m;
    }

    // Si m no es null, se imprime
    private static void imprimirTabla(MetricasOrden b, MetricasOrden s, MetricasOrden i) {
        if (b == null || s == null || i == null) return;

        System.out.printf("%-12s %-15s %-15s %-15s\n", "Algoritmo", "Comparaciones", "Intercambios", "Tiempo (ns)");
        System.out.println("-----------------------------------------------------------");
        System.out.printf("%-12s %-15d %-15d %-15d\n", "Burbuja", b.comparaciones, b.intercambios, b.tiempoNs);
        System.out.printf("%-12s %-15d %-15d %-15d\n", "Seleccion", s.comparaciones, s.intercambios, s.tiempoNs);
        System.out.printf("%-12s %-15d %-15d %-15d\n", "Insercion", i.comparaciones, i.intercambios, i.tiempoNs);
    }
}