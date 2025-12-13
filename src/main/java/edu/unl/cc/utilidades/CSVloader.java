package edu.unl.cc.utilidades;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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
        //Algo a recalcar es que busca por los resoruces
        InputStream is = CSVloader.class.getResourceAsStream("/" + nombreRecurso);

        if (is == null) {
            throw new Exception("No se encontró el recurso: " + nombreRecurso);
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(is));

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
