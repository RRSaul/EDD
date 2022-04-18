package mx.unam.ciencias.edd.proyecto2.dibujantes;

import mx.unam.ciencias.edd.ArbolBinarioCompleto;

public class DibujanteArbolBinarioCompleto extends DibujanteArbolBinario{

    /**
     * Metodo encargado de dibujar un Árbol Binario Completo dado.
     * @param arbolBinarioCompleto El árbol binario completo a dibujar.
     */
    public static void dArbolCompleto(ArbolBinarioCompleto<Integer> arbolBinarioCompleto) {
      dArbolBinario(arbolBinarioCompleto,"rbol Binario Completo.","mediumvioletred","white");
    }

}
