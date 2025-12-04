package edu.unl.cc.modelo;

public class Inventario implements Comparable<Inventario> {

    private String id;
    private String stock;
    private String insumo;

    public Inventario(String id, String stock, String insumo) {
        this.id = id;
        this.stock = stock;
        this.insumo = insumo;
    }

    public String getId() { return id; }
    public String getStock() { return stock; }
    public String getInsumo() { return insumo; }

    @Override
    public int compareTo(Inventario other) {
        // Convertimos stock a int para comparar correctamente
        int s1 = Integer.parseInt(this.stock);
        int s2 = Integer.parseInt(other.stock);
        return Integer.compare(s1, s2);
    }

    public static Inventario fromCSV(String line) {
        String[] p = line.split(";");
        return new Inventario(
                p[0],     // id
                p[1],      // insumo (segunda columna)
                p[2]    // stock (tercera columna)

        );
    }


    @Override
    public String toString() {
        return id + ";" + insumo + ";" + stock;
    }
}
