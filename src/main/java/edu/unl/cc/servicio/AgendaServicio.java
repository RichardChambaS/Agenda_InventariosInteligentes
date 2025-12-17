package edu.unl.cc.servicio;

import edu.unl.cc.busqueda.BusquedaBinaria;
import edu.unl.cc.busqueda.BusquedaLineal;
import edu.unl.cc.busqueda.Limites;
import edu.unl.cc.modelo.Citas;
import edu.unl.cc.ordenar.Borbuja;
import edu.unl.cc.ordenar.Insercion;
import edu.unl.cc.ordenar.MetricasOrden;
import edu.unl.cc.ordenar.Seleccion;
import edu.unl.cc.utilidades.CSVloader;

import java.text.SimpleDateFormat;
import java.util.Comparator;

public class AgendaServicio {

    private Citas[] citas;
    private boolean estaOrdenado = false;

    private final Comparator<Citas> cmpFecha = (a, b) -> a.getFechaHora().compareTo(b.getFechaHora());

    public void cargarCitas(String archivo) throws Exception {
        citas = CSVloader.cargar(archivo, linea -> {
            try {
                // 1. Separar por punto y coma (o coma, según tu CSV)
                String[] partes = linea.split(";");

                // Validación básica de columnas
                if (partes.length < 3) return null;

                // 2. Parsear la fecha.
                // OJO: Revisa si tu CSV usa "2024-01-01 10:00" (espacio) o "2024-01-01T10:00" (T)
                // Usualmente en estos ejercicios es con 'T' o espacio. Pondré el formato estándar.
                String fechaStr = partes[2].trim();
                SimpleDateFormat sdf;

                if (fechaStr.contains("T")) {
                    sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                } else {
                    sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                }

                // 3. Crear el objeto Citas (ID, Apellido, Fecha)
                return new Citas(
                        partes[0].trim(), // ID
                        partes[1].trim(), // Apellido
                        sdf.parse(fechaStr) // Fecha convertida
                );
            } catch (Exception e) {
                // Si la línea falla, retornamos null y el CSVloader la ignora
                 System.out.println("Error parseando linea: " + linea); // Descomentar para debug
                return null;
            }
        }, Citas[]::new);

        estaOrdenado = false; // Al cargar de nuevo, se pierde el orden
    }


    // --- MÉTODOS DE VALIDACIÓN ---
    public boolean tieneDatos() {
        return citas != null && citas.length > 0;
    }


    // ---------------- ORDENAR ----------------

    public MetricasOrden ordenarBurbuja() {
        MetricasOrden m = new MetricasOrden();
        new Borbuja<Citas>().ordenar(citas, cmpFecha, m);
        return m;
    }

    public MetricasOrden ordenarInsercion() {
        MetricasOrden m = new MetricasOrden();
        new Insercion<Citas>().ordenar(citas, cmpFecha, m);
        estaOrdenado = true;
        return m;
    }

    public MetricasOrden ordenarSeleccion() {
        MetricasOrden m = new MetricasOrden();
        new Seleccion<Citas>().ordenar(citas, cmpFecha, m);
        return m;
    }

    // ---------------- BUSCAR ----------------

    public int buscarExacta(Citas key) {
        if (!tieneDatos()) return -1;
        if (!estaOrdenado) ordenarInsercion(); // Garantizar orden
        return BusquedaBinaria.buscar(citas, key, cmpFecha);
    }

    public Citas[] buscarPorRangoFechas(Citas inicio, Citas fin) {
        if (!tieneDatos()) return new Citas[0];
        if (!estaOrdenado) ordenarInsercion();

        int lb = Limites.lowerBound(citas, inicio, cmpFecha);
        int ub = Limites.upperBound(citas, fin, cmpFecha);

        if (ub > citas.length) ub = citas.length; // Protección
        int cantidad = ub - lb;

        if (cantidad <= 0) return new Citas[0];

        Citas[] resultado = new Citas[cantidad];
        System.arraycopy(citas, lb, resultado, 0, cantidad);
        return resultado;
    }

    public void setCitas(Citas[] citas) {
        this.citas = citas;
    }
    // En AgendaServicio.java
    public Citas[] getCitas() {
        return this.citas;
    }
}


