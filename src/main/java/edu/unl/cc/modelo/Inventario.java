package edu.unl.cc.modelo;

public class Inventario implements Comparable<Inventario> {

    private String id;
    private String insumo;
    private String stock;

    public Inventario(String id, String insumo, String stock) {
        this.id = id;
        this.insumo = insumo;
        this.stock = stock;
    }

    public String getId() { return id; }
    public String getInsumo() { return insumo; }
    public String getStock() { return stock; }

    @Override
    public int compareTo(Inventario other) {
        return Integer.compare(Integer.parseInt(this.stock),
                Integer.parseInt(other.stock));
    }

    public static Inventario fromCSV(String line) {
        String[] p = line.split(";");
        return new Inventario(
                p[0].trim(), // id
                p[1].trim(), // insumo
                p[2].trim()  // stock
        );
    }

    @Override
    public String toString() {
        String nombreCorto = (insumo.length() > 35) ? insumo.substring(0, 32) + "..." : insumo;
        return String.format("| %-10s | %-32s | Stock: %5s |", id, nombreCorto, stock);
    }

    // En Inventario.java
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Inventario other = (Inventario) obj;
        // Asumiendo que buscamos por stock o id, ajusta según lo que busques
        return this.stock.equals(other.stock);
    }
}

