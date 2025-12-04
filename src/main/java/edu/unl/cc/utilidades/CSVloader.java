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
            String resourceName,
            Function<String, T> parser,
            IntFunction<T[]> arrayCreator
    ) throws Exception {

        InputStream is = CSVloader.class.getResourceAsStream("/" + resourceName);

        if (is == null) {
            throw new Exception("No se encontró el recurso: " + resourceName);
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        List<T> list = new ArrayList<>();
        String line;

        // Saltar encabezado
        br.readLine();

        while ((line = br.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                list.add(parser.apply(line));
            }
        }

        return list.toArray(arrayCreator.apply(0));
    }
}
