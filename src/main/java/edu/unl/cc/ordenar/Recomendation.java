package edu.unl.cc.ordenar;



public class Recomendation {

    //Dice recomendaciones cual es mejor usar
    public static void generar (Object[] datos, MetricasOrden burbuja, MetricasOrden insercion, MetricasOrden seleccion) {

        int n = datos.length;

        System.out.println("\n-----------------------------------------------------");
        System.out.println("                MATRIZ DE RECOMENDACIÓN");
        System.out.println("-----------------------------------------------------");


        // ----------  Datos casi ordenados ----------
        if (esCasiOrdenado(burbuja.comparaciones, insercion.comparaciones)) {
            System.out.println("** Datos parecen casi ordenados → Inserción es la mejor opción.");
        }

        // ---------- Minimizar swaps ----------
        if (seleccion.intercambios < burbuja.intercambios && seleccion.intercambios < insercion.intercambios) {
            System.out.println("** Si necesitas minimizar intercambios → Selección.");
        }

        // ---------- Minimizar comparaciones ----------
        if (insercion.comparaciones < burbuja.comparaciones &&
                insercion.comparaciones < seleccion.comparaciones) {

            System.out.println("** Inserción realizó menos comparaciones → eficiente para este dataset.");
        }

        // ---------- Dataset grande ----------
        if (n > 500) {
            System.out.println("** n es grande → Ninguno es ideal (usar Merge/Quick), pero Selección es más predecible.");
        }

        // ---------- Totalmente inverso ----------
        if (burbuja.intercambios > (n * n) / 4) {
            System.out.println("** Parece inverso → Burbuja rinde mal; Selección puede ser más estable.");
        }

        System.out.println("-----------------------------------------------------\n");
    }

    // Detección simple de datos casi ordenados
    private static boolean esCasiOrdenado(long compBurbuja, long compInsercion) {
        // Si InsertionSort hace varias comparaciones menos que el BubbleSort
        return compInsercion < (compBurbuja / 3);
    }
}
