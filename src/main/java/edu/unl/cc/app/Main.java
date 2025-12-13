package edu.unl.cc.app;

import edu.unl.cc.modelo.*;
import edu.unl.cc.ordenar.*;
import edu.unl.cc.servicio.*;

import java.util.Scanner;

import static edu.unl.cc.utilidades.Imprimir.imprimirArreglo;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        AgendaServicio agenda = new AgendaServicio();
        InventarioServicio inventario = new InventarioServicio();
        PacienteServicio pacientes = new PacienteServicio();

        boolean running = true;

        try {
            while (running) {

                System.out.println("\n=========== MENÚ PRINCIPAL ===========");
                System.out.println("Elija qué archivo desea ordenar, comparar y buscar");
                System.out.println("1. citas_100.csv");
                System.out.println("2. citas_100_casi_ordenadas.csv");
                System.out.println("3. inventario_500_inverso.csv");
                System.out.println("4. pacientes_500.csv");
                System.out.println("0. Salir");
                System.out.print("Seleccione: ");

                int op = sc.nextInt();
                sc.nextLine();

                switch (op) {

                    // -------------------------------------------------
                    // CITAS ALEATORIAS
                    // -------------------------------------------------
                    case 1 -> ejecutarAgenda(
                            agenda,
                            "citas_100.csv",
                            "CITAS ALEATORIAS",
                            sc
                    );

                    // -------------------------------------------------
                    // CITAS CASI ORDENADAS
                    // -------------------------------------------------
                    case 2 -> ejecutarAgenda(
                            agenda,
                            "citas_100_casi_ordenadas.csv",
                            "CITAS CASI ORDENADAS",
                            sc

                    );

                    // -------------------------------------------------
                    // INVENTARIO
                    // -------------------------------------------------
                    case 3 -> {
                        inventario.cargarInventario("inventario_500_inverso.csv");

                        System.out.println("\n>>> INVENTARIO (ordenado por stock) <<<");

                        MetricasOrden mb = medir(() -> inventario.ordenarBurbuja());
                        MetricasOrden mi = medir(() -> inventario.ordenarInsercion());
                        MetricasOrden ms = medir(() -> inventario.ordenarSeleccion());

                        imprimirTablaMetricas("INVENTARIO", mb, mi, ms);
                        Recomendation.generar(inventario.getItems(), mb, mi, ms);

                        System.out.print("¿Imprimir inventario ordenado? (s/n): ");
                        if (sc.nextLine().equalsIgnoreCase("s")) {
                            imprimirArreglo(inventario.getItems());
                        }

                        System.out.print("Buscar stock exacto: ");
                        int st = sc.nextInt();
                        sc.nextLine();
                        System.out.println("Resultado: " + inventario.buscarExactoPorStock(st));
                    }

                    // -------------------------------------------------
                    // PACIENTES
                    // -------------------------------------------------
                    case 4 -> {
                        pacientes.cargarPacientes("pacientes_500.csv");

                        System.out.println("\n>>> PACIENTES <<<");

                        System.out.print("Apellido a buscar: ");
                        String ap = sc.nextLine();

                        System.out.println("Primero: " + pacientes.buscarPrimeroPorApellido(ap));
                        System.out.println("Último:  " + pacientes.buscarUltimoPorApellido(ap));
                        System.out.println("Prioridad alta: " +
                                pacientes.buscarPrioridadAlta().size());
                    }

                    case 0 -> running = false;

                    default -> System.out.println("Opción no válida.");
                }
            }

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("\nPrograma finalizado.");
    }

    // =========================================================
    // MÉTODOS AUXILIARES
    // =========================================================

    private static void ejecutarAgenda(
            AgendaServicio agenda,
            String archivo,
            String titulo,
            Scanner sc
    ) throws Exception {

        agenda.cargarCitas(archivo);

        System.out.println("\n>>> " + titulo + " <<<");

        Citas[] original = agenda.getCitas();

    // Burbuja
        agenda.setCitas(original.clone());
        MetricasOrden mb = medir(() -> agenda.ordenarBurbuja());

    // Inserción
        agenda.setCitas(original.clone());
        MetricasOrden mi = medir(() -> agenda.ordenarInsercion());

    // Selección
        agenda.setCitas(original.clone());
        MetricasOrden ms = medir(() -> agenda.ordenarSeleccion());


        imprimirTablaMetricas(titulo, mb, mi, ms);
        Recomendation.generar(agenda.getCitas(), mb, mi, ms);

        System.out.print("¿Imprimir citas ordenadas? (s/n): ");
        if (sc.nextLine().equalsIgnoreCase("s")) {
            imprimirArreglo(agenda.getCitas());
        }

        System.out.print("Buscar por apellido: ");
        String ap = sc.nextLine();

        System.out.println("Primera coincidencia: " +
                agenda.buscarPrimeraPorApellido(ap));
        System.out.println("Última coincidencia:  " +
                agenda.buscarUltimaPorApellido(ap));
    }

    private static MetricasOrden medir(SortAction action) {
        long t = System.nanoTime();
        MetricasOrden m = action.run();
        m.tiempoNs = System.nanoTime() - t;
        return m;
    }

    @FunctionalInterface
    private interface SortAction {
        MetricasOrden run();
    }

    private static void imprimirTablaMetricas(
            String titulo,
            MetricasOrden b,
            MetricasOrden i,
            MetricasOrden s
    ) {
        System.out.println("\n========= RESULTADOS " + titulo + " =========");
        System.out.printf("%-12s %-15s %-15s %-15s\n",
                "Algoritmo", "Comparaciones", "Intercambios", "Tiempo(ns)");
        System.out.println("--------------------------------------------------------");

        System.out.printf("%-12s %-15d %-15d %-15d\n",
                "Burbuja", b.comparaciones, b.intercambios, b.tiempoNs);
        System.out.printf("%-12s %-15d %-15d %-15d\n",
                "Inserción", i.comparaciones, i.intercambios, i.tiempoNs);
        System.out.printf("%-12s %-15d %-15d %-15d\n",
                "Selección", s.comparaciones, s.intercambios, s.tiempoNs);
    }
}
