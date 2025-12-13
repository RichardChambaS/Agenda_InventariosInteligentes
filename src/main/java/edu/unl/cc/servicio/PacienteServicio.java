package edu.unl.cc.servicio;

import edu.unl.cc.modelo.Pacientes;
import edu.unl.cc.busqueda.BusquedaLineal;
import edu.unl.cc.utilidades.CSVloader;

import java.util.List;

public class PacienteServicio {

    private Pacientes[] pacientes;

    public void cargarPacientes(String archivoCSV) throws Exception {
        pacientes = CSVloader.cargar(
                archivoCSV,
                (linea) -> {
                    String[] p = linea.split(";");
                    return new Pacientes(p[0], p[1], Integer.parseInt(p[2]));
                },
                Pacientes[]::new
        );
    }

    // --------- BÚSQUEDAS ---------

    public int buscarPrimeroPorApellido(String apellido) {
        return BusquedaLineal.primera(pacientes,
                p -> p.getApellido().equalsIgnoreCase(apellido));
    }

    public int buscarUltimoPorApellido(String apellido) {
        return BusquedaLineal.ultima(pacientes,
                p -> p.getApellido().equalsIgnoreCase(apellido));
    }

    public List<Pacientes> buscarPrioridadAlta() {
        return BusquedaLineal.findAll(pacientes,
                p -> p.getPrioridad() == 1);
    }

    public Pacientes[] getPacientes() {
        return pacientes;
    }
}
