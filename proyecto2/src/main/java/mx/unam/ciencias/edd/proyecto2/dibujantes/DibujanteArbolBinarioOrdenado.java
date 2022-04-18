package mx.unam.ciencias.edd.proyecto2.dibujantes;

import mx.unam.ciencias.edd.ArbolBinarioOrdenado;

public class DibujanteArbolBinarioOrdenado extends DibujanteArbolBinario {

    /**
     * Metodo encargado de dibujar un Árbol Binario Ordenado dado.
     * @param arbolBinarioOrdenado El árbol binario ordenado a dibujar.
     */

    public static void dArbolOrdenado(ArbolBinarioOrdenado<Integer> arbolBinarioOrdenado){
        dArbolBinario(arbolBinarioOrdenado,"rbol Binario Ordenado"
        ,"darkcyan","white");
    }
}
