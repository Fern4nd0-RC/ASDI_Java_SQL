/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.asdi;

import java.util.Stack;

/**
 *
 * @author fromerod1800
 */
public class Pila {

    // Supongamos que tienes una pila interna (por ejemplo, de String)
    private Stack<String> stack = new Stack<>();

    // Método operacion modificado para retornar un String
    public String operacion(int tipoOperacion, String elemento) {
        switch (tipoOperacion) {
            case 1: // Supongamos que 1 es para agregar (push)
                stack.push(elemento);
                return "Elemento agregado: " + elemento;
            case 2: // Supongamos que 2 es para quitar (pop)
                if (!stack.isEmpty()) {
                    return "Elemento quitado: " + stack.pop();
                } else {
                    return "La pila está vacía";
                }
            // Puedes agregar más casos para diferentes operaciones
            default:
                throw new UnsupportedOperationException("Operación no soportada");
        }
    }
}
