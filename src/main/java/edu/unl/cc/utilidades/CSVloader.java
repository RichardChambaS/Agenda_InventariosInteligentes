package edu.unl.cc.utilidades;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;

public class CSVloader {

    public static <T> T[] cargar(
            String nombreRecurso, //Ruta donde se ubica
            Function<String, T> parser, //Convierte una lúnea en un objeto
            IntFunction<T[]> creadorArreglo
    ) throws Exception {

        File file = new File(nombreRecurso);

        if (!file.exists()) {
            // Intenta buscar agregando 'src/' por si acaso
            file = new File("src/resources/" + nombreRecurso);
        }

        if (!file.exists()) {
            throw new FileNotFoundException("ERROR: No encuentro el archivo en: " + file.getAbsolutePath() +
                    "\nASEGÚRATE de crear la carpeta 'resources' en la raíz del proyecto y poner los CSV ahí.");
        }

        BufferedReader br = new BufferedReader(new FileReader(file));
        List<T> list = new ArrayList<>();
        String line;

        // Saltar encabezado
        br.readLine();

        while ((line = br.readLine()) != null) {
            if (!line.trim().isEmpty()) {

                T obj = parser.apply(line);
                if (obj != null) {
                    list.add(obj);
                }

            }
        }

        return list.toArray(creadorArreglo.apply(0));
    }
}
