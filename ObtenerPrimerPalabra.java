package com.mycompany.asdi;

public class ObtenerPrimerPalabra {
    public static String obtener(String linea) {
        // Verificar si la cadena está vacía o es nula
        if (linea == null || linea.isEmpty()) {
            System.exit(1); // Finalizar el programa con un código de salida 1 (error)
        }

        // Buscar el índice del primer espacio en blanco
        int indiceEspacio = linea.indexOf(" ");

        // Verificar si se encontró un espacio en blanco
        if (indiceEspacio != -1) {
            // Si se encuentra un espacio en blanco, extraer la primer palabra
            return linea.substring(0, indiceEspacio);
        } else {
            // Si no se encuentra ningún espacio en blanco, la cadena es la única palabra
            return linea;
        }
    }
}
