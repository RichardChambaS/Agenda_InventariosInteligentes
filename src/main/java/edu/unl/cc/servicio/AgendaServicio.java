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

    private final Comparator<Citas> cmpFecha =
            (a, b) -> a.getFechaHora().compareTo(b.getFechaHora());

    public void cargarCitas(String archivoCSV) throws Exception {

        citas = CSVloader.cargar(
                archivoCSV,
                (linea) -> {
                    try {
                        String[] p = linea.split(";");
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

                        return new Citas(
                                p[0].trim(),
                                p[1].trim(),
                                sdf.parse(p[2].trim())
                        );
                    } catch (Exception e) {
                        System.out.println("Línea inválida en Citas (IGNORADA): " + linea);
                        return null;
                    }
                },
                Citas[]::new
        );
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
        return m;
    }

    public MetricasOrden ordenarSeleccion() {
        MetricasOrden m = new MetricasOrden();
        new Seleccion<Citas>().ordenar(citas, cmpFecha, m);
        return m;
    }

    // ---------------- BUSCAR ----------------

    public int buscarExacta(Citas key) {
        return BusquedaBinaria.buscar(citas, key, cmpFecha);
    }

    public int buscarPrimeraPorApellido(String apellido) {
        return BusquedaLineal.primera(citas, c -> c.getApellido().equalsIgnoreCase(apellido));
    }

    public int buscarUltimaPorApellido(String apellido) {
        return BusquedaLineal.ultima(citas, c -> c.getApellido().equalsIgnoreCase(apellido));
    }

    public Citas[] buscarPorRangoFechas(Citas inicio, Citas fin) {
        int lb = Limites.lowerBound(citas, inicio, cmpFecha);
        int ub = Limites.upperBound(citas, fin, cmpFecha);

        if (lb >= ub) return new Citas[0];

        Citas[] r = new Citas[ub - lb];
        System.arraycopy(citas, lb, r, 0, r.length);
        return r;
    }

    public void setCitas(Citas[] citas) {
        this.citas = citas;
    }

    public Citas[] getCitas() { return citas; }
}


