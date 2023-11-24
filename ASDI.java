package com.mycompany.asdi;

import java.util.List;

public class ASDI implements Parser{
Pila miPila = new Pila();
    private boolean hayErrores = false;
    private String LINEA; //Aqui se guarda entrada

    public ASDI(List<Token> tokens,String linea){
        this.LINEA=linea;
    }

    @Override
    public boolean parse() {
        String primeraPalabra = ObtenerPrimerPalabra.obtener(LINEA);
        Q(primeraPalabra);
        //System.out.println(primeraPalabra+ "\n");
        //preanalisis = this.tokens.get(i);
        // = this.tokens.primeraPalabra;
        if(!hayErrores){
            System.out.println("Consulta correcta");
            return  true;
        }else {
            System.out.println("Se encontraron errores");
        }
        return false;
    }
    // Q
    private void Q(String palabra){
        //Consumimos un elemento de la pila,
        String Y=miPila.operacion(2,""); // Quitar un elemento (pop) y recibirlo
        //System.out.println(palabra+ "and"+ Y+ ".");
        //System.out.println(String.valueOf(TipoToken.SELECT)+ ".");
        //SELECT x Q, nos da: Q -> select D from T
        if((palabra.equals(String.valueOf(TipoToken.SELECT)))&&(Y.equals("Q"))){
            //System.out.println("check");
            // y agregamos su produccion corresponciente.
            miPila.operacion(1,"T"); // Add un elemento (push)
            miPila.operacion(1,"FROM"); // Add un elemento (push)
            miPila.operacion(1,"D"); // Add un elemento (push)
            miPila.operacion(1,"SELECT"); // Add un elemento (push)
            
            //Sabemos que palabra de entrada es SELECT y lo ultimo de pila es SELECT, match
            match(palabra, "SELECT");
        }else{
            hayErrores = true;
            System.out.println("Error sintactico.");
            return;
        }
    }
    // D 
    private void D(String palabra){
        
        //Consumimos un elemento de la pila,
        String Y=miPila.operacion(2,""); // Quitar un elemento (pop) y recibirlo
        //System.out.println(palabra+ "Dand"+ Y+ ".");
        //DISTINCT x D, nos da: D-> distinct P 
        if((palabra.equals(String.valueOf(TipoToken.DISTINCT)))&&(Y.equals("D"))){
            // y agregamos su produccion corresponciente.
            miPila.operacion(1,"P"); 
            miPila.operacion(1,"DISTINCT"); 
            match(palabra, "DISTINCT");
        }
        //ASTERISCO x D, nos da: D-> P
        else if ((palabra.equals(String.valueOf(TipoToken.ASTERISCO)))&&(Y.equals("D"))) {
            miPila.operacion(1,"P");
            P(palabra);
        }
        //IDENTIFICADOR x D, nos da: D-> P
        else if ((palabra.equals(String.valueOf(TipoToken.IDENTIFICADOR)))&&(Y.equals("D"))) {
            miPila.operacion(1,"P");
            P(palabra);
        }
        else{
            hayErrores = true;
            System.out.println("Error sintactico.");
            return;
        }
    }

    // P
    private void P(String palabra){
        String Y=miPila.operacion(2,""); // Quitar un elemento (pop) y recibirlo
        //System.out.println(palabra+ "Pand"+ Y+ ".");
        //ASTERISCO x P, nos da: P-> *
        if ((palabra.equals(String.valueOf(TipoToken.ASTERISCO)))&&(Y.equals("P"))) {
            miPila.operacion(1,"ASTERISCO");
            //Sabemos que palabra de entrada es ASTERISCO y lo ultimo de pila es ASTERISCO, match
            match(palabra,"ASTERISCO");
        }
        //IDENTIFICADOR x P, nos da: P-> A
        else if ((palabra.equals(String.valueOf(TipoToken.IDENTIFICADOR)))&&(Y.equals("P"))) {
            miPila.operacion(1,"A");
            A(palabra);
        }
        else{
            hayErrores = true;
            System.out.println("Error sintactico.");
            return;
        }
    }
    // A
    private void A(String palabra){
        String Y=miPila.operacion(2,""); // Quitar un elemento (pop) y recibirlo
        //System.out.println(palabra+ "Aand"+ Y+ ".");
        //IDENTIFICADOR x A, nos da: A-> A2 A1
        if ((palabra.equals(String.valueOf(TipoToken.IDENTIFICADOR)))&&(Y.equals("A"))) {
            miPila.operacion(1,"A1");
            miPila.operacion(1,"A2");
            A2(palabra);
        }
        else{
            hayErrores = true;
            System.out.println("Error sintactico.");
            return;
        }
    }
        
    // A1
    private void A1(String palabra){
        String Y=miPila.operacion(2,""); // Quitar un elemento (pop) y recibirlo
        //System.out.println(palabra+ "A2and"+ Y+ ".");
        //FROM x A1, nos da: A1-> E
        if ((palabra.equals(String.valueOf(TipoToken.FROM)))&&(Y.equals("A1"))) {
            
            // Sacamos el nuevo Top de pila, lo guardamos y volvemos
            //a meter para que no se pierda...
            Y=miPila.operacion(2,""); //SACAMOS y actualizamos
            miPila.operacion(1,Y); //metemos 

            //AQUI mandamos a match porque no sabemos que hay debajo de pila actual, y a donde
            //nos llevara... match lo resolvera.
            match(palabra,Y);
        }
        //COMA x A1, nos da: P-> , A
        else if ((palabra.equals(String.valueOf(TipoToken.COMA)))&&(Y.equals("A1"))) {
            miPila.operacion(1,"A");
            miPila.operacion(1,"COMA");
            //Sabemos que palabra de entrada es COMA y lo ultimo de pila es COMA, match
            match(palabra,"COMA");
        }
        else{
            hayErrores = true;
            System.out.println("Error sintactico.");
            return;
        }
    }
    // A2
    private void A2(String palabra){
        String Y=miPila.operacion(2,""); // Quitar un elemento (pop) y recibirlo
        //System.out.println(palabra+ "A1and"+ Y+ ".");
        //IDENTIFICADOR x A2, nos da: A2-> id A3
        if ((palabra.equals(String.valueOf(TipoToken.IDENTIFICADOR)))&&(Y.equals("A2"))) {
            miPila.operacion(1,"A3");
            miPila.operacion(1,"IDENTIFICADOR");
            //Sabemos que palabra de entrada es IDENTIFICADOR y lo ultimo de pila es IDENTIFICADOR, match
            match(palabra,"IDENTIFICADOR");
        }
        else{
            hayErrores = true;
            System.out.println("Error sintactico.");
            return;
        }
    }
    // A3
    private void A3(String palabra){
        String Y=miPila.operacion(2,""); // Quitar un elemento (pop) y recibirlo
        //System.out.println(palabra+ "A3and"+ Y+ ".");
        //FROM x A3, nos da: A3-> E
        if ((palabra.equals(String.valueOf(TipoToken.FROM)))&&(Y.equals("A3"))) {
            
            // Sacamos el nuevo Top de pila, lo guardamos y volvemos
            //a meter para que no se pierda...
            Y=miPila.operacion(2,""); //SACAMOS y actualizamos
            miPila.operacion(1,Y); //metemos 

            //AQUI mandamos a match porque no sabemos que hay debajo de pila actual, y a donde
            //nos llevara... match lo resolvera.
            match(palabra,Y);
        } 
        //COMA x A3, nos da: A3-> E
        else if ((palabra.equals(String.valueOf(TipoToken.COMA)))&&(Y.equals("A3"))) {
            
            // Sacamos el nuevo Top de pila, lo guardamos y volvemos
            //a meter para que no se pierda...
            Y=miPila.operacion(2,""); //SACAMOS y actualizamos
            miPila.operacion(1,Y); //metemos 

            //AQUI mandamos a match porque no sabemos que hay debajo de pila actual, y a donde
            //nos llevara... match lo resolvera.
            match(palabra,Y);
        }
        //PUNTO x A3, nos da: A3-> . id
        else if ((palabra.equals(String.valueOf(TipoToken.PUNTO)))&&(Y.equals("A3"))) {
            miPila.operacion(1,"IDENTIFICADOR");
            miPila.operacion(1,"PUNTO");
            //Sabemos que palabra de entrada es PUNTO y lo ultimo de pila es PUNTO, match
            match(palabra,"PUNTO");
        }
        else{
            hayErrores = true;
            System.out.println("Error sintactico.");
            return;
        }
    }
    // T 
    private void T(String palabra){
        String Y=miPila.operacion(2,""); // Quitar un elemento (pop) y recibirlo
        //System.out.println(palabra+ "Tand"+ Y+ ".");
        //IDENTIFICADOR x T, nos da: T-> T2 T1
        if ((palabra.equals(String.valueOf(TipoToken.IDENTIFICADOR)))&&(Y.equals("T"))) {
            miPila.operacion(1,"T1");
            miPila.operacion(1,"T2");
            T2(palabra);
        }
        else{
            hayErrores = true;
            System.out.println("Error sintactico.");
            return;
        }
    } 
    // T1
    private void T1(String palabra){
        String Y=miPila.operacion(2,""); // Quitar un elemento (pop) y recibirlo
        //System.out.println(palabra+ "T1and"+ Y+ ".");
        //COMA x T1, nos da: T1-> , T
        if ((palabra.equals(String.valueOf(TipoToken.COMA)))&&(Y.equals("T1"))) {
            miPila.operacion(1,"T");
            miPila.operacion(1,"COMA");
            //Sabemos que palabra de entrada es PUNTO y lo ultimo de pila es PUNTO, match
            match(palabra,"COMA");
        }
        //EOF x T1, nos da: T1-> E
        else if((palabra.equals(String.valueOf(TipoToken.EOF)))&&(Y.equals("T1"))){
            // Sacamos el nuevo Top de pila, lo guardamos y volvemos
            //a meter para que no se pierda...
            Y=miPila.operacion(2,""); //SACAMOS y actualizamos
            miPila.operacion(1,Y); //metemos 

            //AQUI mandamos a match porque no sabemos que hay debajo de pila actual, y a donde
            //nos llevara... match lo resolvera.
            match(palabra,Y);
        }
        else{
            hayErrores = true;
            System.out.println("Error sintactico.");
            return;
        }
    }

    // T2
    private void T2(String palabra){
        String Y=miPila.operacion(2,""); // Quitar un elemento (pop) y recibirlo
        //System.out.println(palabra+ "T2and"+ Y+ ".");
        //IDENTIFICADOR x T2, nos da: T2-> id T3
        if ((palabra.equals(String.valueOf(TipoToken.IDENTIFICADOR)))&&(Y.equals("T2"))) {
            miPila.operacion(1,"T3");
            miPila.operacion(1,"IDENTIFICADOR");
            //Sabemos que palabra de entrada es PUNTO y lo ultimo de pila es PUNTO, match
            match(palabra,"IDENTIFICADOR");
        }
        else{
            hayErrores = true;
            System.out.println("Error sintactico.");
            return;
        }
    }
    // T3 
    private void T3(String palabra){
        String Y=miPila.operacion(2,""); // Quitar un elemento (pop) y recibirlo
        //System.out.println(palabra+ "T3and"+ Y+ ".");
        //IDENTIFICADOR x T3, nos da: T3-> id
        if ((palabra.equals(String.valueOf(TipoToken.IDENTIFICADOR)))&&(Y.equals("T3"))) {
            miPila.operacion(1,"IDENTIFICADOR");
            //Sabemos que palabra de entrada es PUNTO y lo ultimo de pila es PUNTO, match
            match(palabra,"IDENTIFICADOR");
        }
        //COMA x T3, nos da: T3-> E
        else if((palabra.equals(String.valueOf(TipoToken.COMA)))&&(Y.equals("T3"))){
            // Sacamos el nuevo Top de pila, lo guardamos y volvemos
            //a meter para que no se pierda...
            Y=miPila.operacion(2,""); //SACAMOS y actualizamos
            miPila.operacion(1,Y); //metemos 

            //AQUI mandamos a match porque no sabemos que hay debajo de pila actual, y a donde
            //nos llevara... match lo resolvera.
            match(palabra,Y);
        }
        //EOF x T3, nos da: T3-> E
        else if((palabra.equals(String.valueOf(TipoToken.EOF)))&&(Y.equals("T3"))){
            // Sacamos el nuevo Top de pila, lo guardamos y volvemos
            //a meter para que no se pierda...
            Y=miPila.operacion(2,""); //SACAMOS y actualizamos
            miPila.operacion(1,Y); //metemos 

            //AQUI mandamos a match porque no sabemos que hay debajo de pila actual, y a donde
            //nos llevara... match lo resolvera.
            match(palabra,Y);
        }
        else{
            hayErrores = true;
            System.out.println("Error sintactico.");
            return;
        }
    }
    private void match(String palabra,String PILA){
        //System.out.println(palabra+ "=="+ PILA);
        if((palabra.equals("EOF"))&&(PILA.equals("EOF"))){
            miPila.operacion(0,""); // Quitamos elemento de tope pila (pop)
            this.LINEA=QuitarPrimerPalabra.quitar(LINEA);//Quitamos primer palabra de ENTRADA actual

            //La pila y la entrada quedan vacias. Validacion-->

        }
        else if(palabra.equals(PILA)){
            //Sabemos que lo primero de la Entrada y de la Pila son iguales. Se eliminan
            this.LINEA=QuitarPrimerPalabra.quitar(LINEA);//Quitamos primer palabra de ENTRADA actual
            miPila.operacion(0,""); // Quitamos elemento de tope pila (pop)
            palabra = ObtenerPrimerPalabra.obtener(LINEA);
            // Sacamos el nuevo Top de pila, lo guardamos y volvemos
            //a meter para que no se pierda...
            PILA=miPila.operacion(2,""); //SACAMOS y actualizamos
            miPila.operacion(1,PILA); //metemos 

            match(palabra, PILA);
        }
        else{
            if (PILA.equals("Q")){
                Q(palabra);
            }
            else if(PILA.equals("D")){
                D(palabra);
            }
            else if(PILA.equals("P")){
                P(palabra);
            }
            else if(PILA.equals("A1")){
                A1(palabra);
            }
            else if(PILA.equals("A2")){
                A2(palabra);
            }
            else if(PILA.equals("A3")){
                A3(palabra);
            }
            else if(PILA.equals("T")){
                T(palabra);
            }
            else if(PILA.equals("T1")){
                T1(palabra);
            }
            else if(PILA.equals("T2")){
                T2(palabra);
            }
            else if(PILA.equals("T3")){
                T3(palabra);
            }else{
                hayErrores = true;
                System.out.println("Se esperaba un estado.");
            }
        }

    }
}
