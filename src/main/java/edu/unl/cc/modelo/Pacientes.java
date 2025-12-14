package edu.unl.cc.modelo;

public class Pacientes implements Comparable<Pacientes> {

    private String id;
    private String apellido;
    private int prioridad;

    public Pacientes(String id, String apellido, int prioridad) {
        this.id = id;
        this.apellido = apellido;
        this.prioridad = prioridad;
    }

    public String getId() {
        return id;
    }

    public String getApellido() {
        return apellido;
    }

    public int getPrioridad() {
        return prioridad;
    }

    @Override
    public int compareTo(Pacientes other) {
        // Primero ordenamos por prioridad
        int cmp = Integer.compare(this.prioridad, other.prioridad);

        if (cmp != 0)
            return cmp;

        // Si tienen la misma prioridad se ordenará por apellido
        return this.apellido.compareTo(other.apellido);
    }

    public static Pacientes fromCSV(String line) {
        String[] p = line.split(";");
        return new Pacientes(
                p[0],             // id
                p[1],             // apellido
                Integer.parseInt(p[2]) // prioridad
        );
    }

    private Pacientes siguiente;

    public Pacientes getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Pacientes siguiente) {
        this.siguiente = siguiente;
    }

    // Para búsquedas en SLL compara por ID
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Pacientes otro = (Pacientes) obj;
        return this.id.equals(otro.id);
    }
}