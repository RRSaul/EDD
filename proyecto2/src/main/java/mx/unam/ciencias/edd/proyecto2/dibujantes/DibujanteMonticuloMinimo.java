package mx.unam.ciencias.edd.proyecto2.dibujantes;

import mx.unam.ciencias.edd.ArbolBinarioCompleto;
import mx.unam.ciencias.edd.MonticuloMinimo;
import mx.unam.ciencias.edd.ValorIndexable;
/**
 * Clase publica ecargada de dibujar Monticulos Arreglo. Como sabemos los Monticulos Minimos
 * son arboles y arreglos a la vez, esta es la encagada del árbol extendiendo a Dibujante Arbol Binario
 * y usando un Arbol Binario Completo.
 */

public class DibujanteMonticuloMinimo extends DibujanteArbolBinario {

    /**
     * Metodo encargado de dibujar un Monticulo Minimo dado.
     * @param monticuloMinimo el monticulo minimo a dibujar.
     */

    public static void dMonticuloMinimo(MonticuloMinimo<ValorIndexable<Integer>> monticuloMinimo){

        ArbolBinarioCompleto<Integer> abMM = new ArbolBinarioCompleto<>();
        for (ValorIndexable<Integer> elementoI:
             monticuloMinimo) {
            abMM.agrega(elementoI.getElemento());
        }
    dArbolBinario(abMM,"rbol/ Monticulo Minimo.","darkolivegreen","white");
    }
}
