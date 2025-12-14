package edu.unl.cc.servicio;

import edu.unl.cc.modelo.Pacientes;
import edu.unl.cc.busqueda.BusquedaLineal;
import edu.unl.cc.utilidades.CSVloader;
import edu.unl.cc.utilidades.SLL;

import java.util.List;

public class PacienteServicio {

    private SLL<Pacientes> listaPacientes = new SLL<>();

    public void cargarSiEstaVacio(String ruta) throws Exception {
        if (listaPacientes.isEmpty()) {
            Pacientes[] raw = CSVloader.cargar(ruta, Pacientes::fromCSV, Pacientes[]::new);
            for (Pacientes p : raw) listaPacientes.add(p);
        }
    }

    // Clase auxiliar para transportar respuesta (DTO)
    public static class ResultadoBusqueda {
        public int indice;
        public Pacientes dato;
        public ResultadoBusqueda(int i, Pacientes d) { this.indice = i; this.dato = d; }
    }

    // --------- BÚSQUEDAS ---------

    // Metodo que devuelve el objeto y el índice
    public ResultadoBusqueda buscar(String apellido, boolean esPrimera) {
        int idx = listaPacientes.buscarIndice(p -> p.getApellido().equalsIgnoreCase(apellido), esPrimera);
        if (idx != -1) {
            return new ResultadoBusqueda(idx, listaPacientes.get(idx));
        }
        return null;
    }

    public List<Pacientes> buscarPrioridadAlta() {
        return listaPacientes.findAll(p -> p.getPrioridad() == 1);
    }
}
