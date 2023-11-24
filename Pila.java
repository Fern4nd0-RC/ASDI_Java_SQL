package com.mycompany.asdi;

import java.util.Stack;

import java.util.Stack;

public class Pila {
    private Stack<String> pila;

    public Pila() {
        //Se inicializa la pila
        pila = new Stack<>();
        pila.push("EOF");
        pila.push("Q");
        //pila.push("T");
        //pila.push("FROM");
        //pila.push("DISTINCT");
        //pila.push("SELECT");

    }   

    public String operacion(int accion, String Nuevo_elemento) {
        switch (accion) {
            case 0:
                pop();
                return "";
            case 1:
                push(Nuevo_elemento);
                return "";
            case 2:
                // Realizar una acción personalizada
                String elementoExtraido = pila.pop();
                //System.out.println("Acción personalizada realizada");
                return elementoExtraido;
            default:
                System.out.println("Acción no reconocida");
                return "";
        }
    }

    private void pop() {
        if (!pila.isEmpty()) {
            String elemento = pila.pop();
            //System.out.println("Pop realizado. Valor extraído: " + elemento);
        } else {
            System.out.println("La pila está vacía. No se puede hacer pop.");
        }
    }

    private void push(String Nuevo_elemento) {
        pila.push(Nuevo_elemento);
        //System.out.println("Push realizado. Nuevo valor agregado: " + Nuevo_elemento);
    }
}

