package edu.unl.cc.modelo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Citas implements Comparable<Citas> {

    private String id;
    private String apellido;
    private Date fechaHora;

    public Citas(String id, String apellido, Date fechaHora) {
        this.id = id;
        this.apellido = apellido;
        this.fechaHora = fechaHora;
    }

    public String getId() { return id; }
    public String getApellido() { return apellido; }
    public Date getFechaHora() { return fechaHora; }

    @Override
    public int compareTo(Citas other) {
        // Orden natural: por fechaHora
        return this.fechaHora.compareTo(other.fechaHora);
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return id + ";" + apellido + ";" + sdf.format(fechaHora);
    }




    public static Citas fromCSV(String line) {
        try {
            String[] p = line.split(";");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

            return new Citas(
                    p[0],               // id
                    p[1],               // apellido
                    sdf.parse(p[2])     // fechaHora
            );
        } catch (Exception e) {
            throw new RuntimeException("Error al parsear Cita: " + line, e);
        }
    }

}
