package com.mycompany.asdi;

public class QuitarPrimerPalabra {
    public static String quitar(String cadena) {
        int indiceEspacio = cadena.indexOf(" ");
        
        if (indiceEspacio != -1) {
            return cadena.substring(indiceEspacio + 1);
        } else {
            // Si no se encuentra un espacio en blanco, retorna una cadena vacía o null, según sea necesario
            return ""; 
        }
    }
}