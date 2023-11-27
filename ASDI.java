import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;

public class ASDI implements Parser{
    private int i = 0;
    private boolean hayErrores = false;
    private Token preanalisis;
    private final List<Token> tokens;
    Deque<Object> pila = new ArrayDeque<>();
    int longitud;


    public ASDI(List<Token> tokens){
        this.tokens = tokens;
        preanalisis = this.tokens.get(i);
        longitud = this.tokens.size();
        pila.push(TipoToken.EOF);
        pila.push("Q");
    }

    @Override
    public boolean parse() {
        HashMap<String, HashMap<TipoToken, List<Object>>> TablaAS = new HashMap<>();
        List<Object> listaQSelect = new ArrayList<>();
        listaQSelect.add("T");
        listaQSelect.add(TipoToken.FROM);
        listaQSelect.add("D");
        listaQSelect.add(TipoToken.SELECT);
        TablaAS.put("Q", new HashMap<TipoToken,List<Object>>(){{
            put(TipoToken.SELECT, listaQSelect);
        }});
        
        List<Object> listaD = new ArrayList<>();
        listaD.add("P");
        List<Object> listaDistinct = new ArrayList<>();
        listaDistinct.add("P");
        listaDistinct.add(TipoToken.DISTINCT);
        TablaAS.put("D", new HashMap<TipoToken,List<Object>>(){{
            put(TipoToken.DISTINCT, listaDistinct);
            put(TipoToken.IDENTIFICADOR, listaD);
            put(TipoToken.ASTERISCO, listaD);
        }});
      
        List<Object> listaPId = new ArrayList<>();
        listaPId.add("A");
        List<Object> listaPAsterisco = new ArrayList<>();
        listaPAsterisco.add(TipoToken.ASTERISCO);
        TablaAS.put("P", new HashMap<TipoToken,List<Object>>(){{
            put(TipoToken.IDENTIFICADOR, listaPId);
            put(TipoToken.ASTERISCO, listaPAsterisco);
        }});
        
        List<Object> listaA = new ArrayList<>();
        listaA.add("A1");
        listaA.add("A2");
        TablaAS.put("A", new HashMap<TipoToken,List<Object>>(){{
            put(TipoToken.IDENTIFICADOR, listaA);
        }});
        
        List<Object> listaEpsilon = new ArrayList<>();
        List<Object> listaA1 = new ArrayList<>();
        listaA1.add("A");
        listaA1.add(TipoToken.COMA);
        TablaAS.put("A1", new HashMap<TipoToken,List<Object>>(){{
            put(TipoToken.COMA, listaA1);
            put(TipoToken.FROM, listaEpsilon);
        }});
        
        List<Object> listaA2 = new ArrayList<>();
        listaA2.add("A3");
        listaA2.add(TipoToken.IDENTIFICADOR);
        TablaAS.put("A2", new HashMap<TipoToken,List<Object>>(){{
            put(TipoToken.IDENTIFICADOR, listaA2);
        }});
        List<Object> listaA3 = new ArrayList<>();
        listaA3.add(TipoToken.IDENTIFICADOR);
        listaA3.add(TipoToken.PUNTO);
        TablaAS.put("A3", new HashMap<TipoToken,List<Object>>(){{
            put(TipoToken.FROM, listaEpsilon);
            put(TipoToken.COMA, listaEpsilon);
            put(TipoToken.PUNTO, listaA3);
        }});
        List<Object> listaT = new ArrayList<>();
        listaT.add("T1");
        listaT.add("T2");
        TablaAS.put("T", new HashMap<TipoToken,List<Object>>(){{
            put(TipoToken.IDENTIFICADOR, listaT);
        }});
        List<Object> listaT1 = new ArrayList<>();
        listaT1.add("T");
        listaT1.add(TipoToken.COMA);
        TablaAS.put("T1", new HashMap<TipoToken,List<Object>>(){{
            put(TipoToken.COMA, listaT1);
            put(TipoToken.EOF, listaEpsilon);
        }});
        List<Object> listaT2 = new ArrayList<>();
        listaT2.add("T3");
        listaT2.add(TipoToken.IDENTIFICADOR);
        TablaAS.put("T2", new HashMap<TipoToken,List<Object>>(){{
            put(TipoToken.IDENTIFICADOR, listaT2);
        }});
        List<Object> listaT3 = new ArrayList<>();
        listaT3.add(TipoToken.IDENTIFICADOR);
        TablaAS.put("T3", new HashMap<TipoToken,List<Object>>(){{
            put(TipoToken.IDENTIFICADOR, listaT3);
            put(TipoToken.COMA, listaEpsilon);
            put(TipoToken.EOF, listaEpsilon);
        }});
        String fl = pila.peek().toString();
        TipoToken col = preanalisis.tipo;
        List<Object> celda;
        int j;
        
        while(true){
            //System.out.println("Pila: " + pila);
            //System.out.println("Preanalisis: " + preanalisis);
            
            
            if(pila.peek() == TipoToken.EOF)  break;
            celda = TablaAS.get(fl).get(col);
            if(celda == null){
                hayErrores = true;
                break;
            } 
            else if(celda.size() == 0){
                pila.pop();
                fl = pila.peek().toString();
            } 
            else if(celda.get(celda.size()-1) instanceof TipoToken){
                pila.pop();
                for(j = 0; j < celda.size(); j++){
                    pila.push(celda.get(j));
                }
                pila.pop();
                i++;
                preanalisis = tokens.get(i);
                col = tokens.get(i).tipo;
                fl = pila.peek().toString();
            }
            else if(celda.get(celda.size()-1) instanceof String){
                pila.pop();
                for(j = 0; j < celda.size(); j++)
                    pila.push(celda.get(j));
                fl = pila.peek().toString();
            }
            if(pila.peek() instanceof TipoToken){
                    if(pila.peek() == TipoToken.EOF) break;
                    pila.pop();
                    i++;
                    preanalisis = tokens.get(i);
                    fl = pila.peek().toString();
                    col = tokens.get(i).tipo;
            }
            /*if (hayErrores) {
                System.out.println("Error detectado");
                break;
            }*/
        }
        if(!hayErrores) System.out.println("El lexema es correcto");
        else Principal.error("El lexema es incorrecto");
        return hayErrores;
    }
}
