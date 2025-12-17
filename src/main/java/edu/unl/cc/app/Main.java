package edu.unl.cc.app;

import edu.unl.cc.modelo.Citas;
import edu.unl.cc.modelo.Inventario;
import edu.unl.cc.modelo.Pacientes;
import edu.unl.cc.servicio.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);

    private static final AgendaServicio agenda = new AgendaServicio();
    private static final InventarioServicio inventario = new InventarioServicio();
    private static final PacienteServicio pacientes = new PacienteServicio();

    public static void main(String[] args) {
        boolean ejecutando = true;

        while (ejecutando) {
            System.out.println("\n==============================================");
            System.out.println("      SISTEMA VETERINARIO - MENU PRINCIPAL");
            System.out.println("==============================================");
            System.out.println("1. Gestionar Agenda (Datos Aleatorios - 100)");
            System.out.println("2. Gestionar Agenda (Casi Ordenados - 100)");
            System.out.println("3. Gestionar Inventario (Por Stock)");
            System.out.println("4. Gestionar Pacientes (Busquedas)");
            System.out.println("5. Ejecutar Experimentos Globales (Automatico)");
            System.out.println("0. Salir");
            System.out.println("==============================================");
            System.out.print("Seleccione una opcion: ");

            int opcion = leerEntero();

            try {
                switch (opcion) {
                    case 1:
                        gestionarAgenda("citas_100.csv", "ALEATORIA");
                        break;
                    case 2:
                        gestionarAgenda("citas_100_casi_ordenadas.csv", "CASI ORDENADA");
                        break;
                    case 3:
                        gestionarInventario();
                        break;
                    case 4:
                        gestionarPacientes();
                        break;
                    case 5:
                        Experimentos.ejecutarTodo(new AgendaServicio(), new InventarioServicio());
                        break;
                    case 0:
                        ejecutando = false;
                        System.out.println("Saliendo del sistema...");
                        break;
                    default:
                        System.out.println(" Opcion no valida.");
                }
            } catch (Exception e) {
                System.out.println("[ERROR] " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    // ================================================================
    // OPCION 1 Y 2: AGENDA
    // ================================================================
    private static void gestionarAgenda(String archivo, String tipo) throws Exception {
        System.out.println("\n--- PROCESANDO AGENDA: " + tipo + " ---");

        // 1. Mostrar comparativa (Experimentos)
        System.out.println(">> Analisis de algoritmos...");
        Experimentos.compararAlgoritmosAgenda(agenda, archivo);

        // 2. Dejar ordenado para busquedas
        System.out.println(">> Ordenando por Insercion...");
        agenda.cargarCitas(archivo); // Recargamos para asegurar
        agenda.ordenarInsercion();

        boolean enSubmenu = true;
        while (enSubmenu) {
            System.out.println("\n   >> SUBMENU AGENDA (" + tipo + ")");
            System.out.println("   1. Buscar Cita Exacta (Binaria)");
            System.out.println("   2. Buscar Citas por Rango (Fechas)");
            System.out.println("   3. Ver lista de citas (Imprimir)");
            System.out.println("   0. Volver al Menu Principal");
            System.out.print("   Opcion: ");

            int subOp = leerEntero();
            switch (subOp) {
                case 1:
                    buscarCitaExacta();
                    break;
                case 2:
                    buscarCitaRango();
                    break;
                case 3:
                    imprimirListaDirecta(agenda.getCitas());
                    break;
                case 0:
                    enSubmenu = false;
                    break;
                default:
                    System.out.println("   [ERROR] Opcion invalida.");
            }
        }
    }

    private static void buscarCitaExacta() {
        System.out.print("   Ingrese fecha exacta (yyyy-MM-dd HH:mm): ");
        try {
            String fechaStr = sc.nextLine();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Citas clave = new Citas("", "", sdf.parse(fechaStr));

            int indice = agenda.buscarExacta(clave);

            if (indice != -1) {
                System.out.println("   [ENCONTRADO] " + agenda.getCitas()[indice]);
                System.out.println("   [INFO] Indice encontrado en ["+ (indice+1) +"]");
            } else {
                System.out.println("   [INFO] No se encontro cita en esa fecha.");
            }
        } catch (Exception e) {
            System.out.println("   [ERROR] Formato de fecha incorrecto o datos no cargados.");
        }
    }

    private static void buscarCitaRango() {
        System.out.println("   --- Busqueda por Rango ---");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            System.out.print("   Fecha Inicio: ");
            Citas inicio = new Citas("", "", sdf.parse(sc.nextLine()));
            System.out.print("   Fecha Fin:    ");
            Citas fin = new Citas("", "", sdf.parse(sc.nextLine()));

            Citas[] resultados = agenda.buscarPorRangoFechas(inicio, fin);

            System.out.println("   [RESULTADO] Se encontraron " + resultados.length + " citas.");
            imprimirListaDirecta(resultados); // Imprimir resultados del rango

        } catch (Exception e) {
            System.out.println("   [ERROR] Error en las fechas.");
        }
    }

    // ================================================================
    //  OPCION 3: INVENTARIO
    // ================================================================
    private static void gestionarInventario() throws Exception {
        System.out.println("\n--- GESTION DE INVENTARIO ---");
        System.out.println(">> Cargando y ordenando por Stock...");
        // 1. Mostrar comparativa (Experimentos)
        Experimentos.compararAlgoritmosInventario(inventario, "inventario_500_inverso.csv");
        System.out.println("\n[INFO] Aplicando ordenamiento final (Inserción) para habilitar búsquedas...");
        inventario.ordenarInsercion();
        if (inventario.getItems() == null || inventario.getItems().length == 0) {
            System.out.println("[ERROR] No hay datos en el inventario. Revise el CSV.");
            return;
        }

        System.out.println(">>> Inventario listo y ordenado (" + inventario.getItems().length + " productos).");

        boolean enSubmenu = true;
        while (enSubmenu) {
            System.out.println("\n >> SUBMENU INVENTARIO");
            System.out.println("   1. Consultar Producto por Rango de Stock (Busqueda Binaria)");
            System.out.println("   2. Ver todo el inventario (Lista Completa)");
            System.out.println("   3. Buscar por Stock exacto (Estrategia Centinela)");
            System.out.println("   0. Volver al menu principal");
            System.out.print("   Opcion: ");

            int subOp = leerEntero();
            switch (subOp) {
                case 1:
                    System.out.println("\n--- Búsqueda por Rango (Bounds) ---");
                    System.out.print("Ingrese Stock MÍNIMO: ");
                    int min = leerEntero();
                    System.out.print("Ingrese Stock MÁXIMO: ");
                    int max = leerEntero();

                    List<Inventario> resultados = inventario.buscarPorRango(min, max);

                    if (resultados.isEmpty()) {
                        System.out.println("No se encontraron productos en el rango [" + min + " - " + max + "].");
                    } else {
                        System.out.println("--- Productos encontrados: " + resultados.size() + " ---");
                        for (Inventario inv : resultados) {
                            System.out.println(inv);
                        }
                    }
                    break;

                case 2:
                    imprimirListaDirecta(inventario.getItems());
                    break;
                case 3:
                    System.out.print("   Ingrese cantidad de stock a buscar: ");
                    String stockBusq = sc.nextLine();

                    // Verificamos que haya datos cargados
                    if (inventario.getItems() == null) {
                        try { inventario.cargarInventario("inventario_500_inverso.csv"); }
                        catch(Exception e) { System.out.println("Error cargando: " + e.getMessage()); }
                    }

                    int idx = inventario.buscarCentinela(stockBusq);

                    if (idx != -1) {
                        System.out.println("¡Encontrado en índice " + idx + "!");
                        var item = inventario.getItems()[idx ];
                        System.out.println("\n   [ENCONTRADO]");
                        System.out.println("   ---------------------------");
                        System.out.println("   ID:       " + item.getId());
                        System.out.println("   Producto: " + item.getInsumo());
                        System.out.println("   Stock:    " + item.getStock());
                        System.out.println("   ---------------------------");
                    } else {
                        System.out.println("   [INFO] No hay ningun producto con stock exacto de " + stockBusq);
                    }
                    break;

                case 0:
                    enSubmenu = false;
                    break;
                default:
                    System.out.println("   Opcion no valida.");
            }
        }
    }

    // ================================================================
    // OPCION 4: PACIENTES
    // ================================================================
    private static void gestionarPacientes() throws Exception {
        System.out.println("\n--- GESTION DE PACIENTES ---");
        try {
            pacientes.cargarSiEstaVacio("pacientes_500.csv");
        } catch (Exception e) {
            System.out.println("[ERROR] Falla al cargar pacientes.csv. Verifique que el archivo exista.");
            return;
        }

        boolean enSubmenu = true;
        while (enSubmenu) {
            System.out.println("\n   >> SUBMENU PACIENTES");
            System.out.println("   1. Buscar por Apellido");
            System.out.println("   2. Listar por Alta Prioridad (1) ");
            System.out.println("   3. Ver lista completa de pacientes");
            System.out.println("   0. Volver");
            System.out.print("   Opcion: ");

            int subOp = leerEntero();
            switch (subOp) {
                case 1:
                    System.out.print("   Ingrese Apellido: ");
                    String apellido = sc.nextLine();

                    var primero = pacientes.buscar(apellido, true);

                    if (primero != null) {
                        System.out.println("   [PRIMERA COINCIDENCIA] " + primero.dato);

                        var ultimo = pacientes.buscar(apellido, false);
                        if (ultimo != null && ultimo.indice != primero.indice) {
                            System.out.println("   [ULTIMA COINCIDENCIA]  " + ultimo.dato);
                        }
                    } else {
                        System.out.println("   [INFO] No encontrado.");
                    }
                    break;

                case 2:
                    System.out.println("   Buscando prioridad 1...");
                    List<?> lista = pacientes.buscarPrioridadAlta();
                    if (lista.isEmpty()) System.out.println("   Ninguno encontrado.");
                    else for(Object o : lista) System.out.println("   -> " + o);
                    break;

                case 3:
                    System.out.println("   Mostrando pacientes ordenados por prioridad...");
                    imprimirListaDirecta(pacientes.getPacientesOrdenados());
                    break;

                case 0:
                    enSubmenu = false;
                    break;

                default:
                    System.out.println("   Opcion no valida.");
            }
        }
    }



    // ================================================================
    // UTILIDADES
    // ================================================================
    private static int leerEntero() {
        try { return Integer.parseInt(sc.nextLine()); } catch (Exception e) { return -1; }
    }

    private static <T> void imprimirListaDirecta(T[] arreglo) {
        if (arreglo == null || arreglo.length == 0) {
            System.out.println("   [AVISO] La lista esta vacia o no se cargo correctamente.");
            return;
        }
        System.out.println("--- INICIO DE LISTA (" + arreglo.length + " registros) ---");
        for (T t : arreglo) {
            System.out.println(t);
        }
        System.out.println("--- FIN DE LISTA ---");
    }
}