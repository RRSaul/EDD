package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.*;

/**
 * Clase pubica para construir estructuras en base a un arreglo de elementos.
 */

public class ConstructorDeEstructuras {

    /**
     * Metodo encargado de construir un arreglo, esta implementado por completez.
     * @param arregloDeElementos el arreglo de enteros de elementos.
     * @return Un arreglo construido.
     */
 public static int[] contruirArreglo(int[] arregloDeElementos){
     return arregloDeElementos;
 }
    /**
     * Metodo encargado de construir una lista en base a un arreglo de elementos.
     * @param arregloDeElementos el arreglo de enteros de elementos.
     * @return Una lista construida.
     */
 public static Lista<Integer> construirLista(int[] arregloDeElementos){

     Lista<Integer> listaEstructura = new Lista<>();
     for (int elemento:arregloDeElementos){
         listaEstructura.agregaFinal(elemento);
     }
     return listaEstructura;
 }

    /**
     * Metodo encargado de construir una cola.
     * @param arregloDeElementos el arreglo de enteros de elementos.
     * @return Una cola construida.
     */
 public static Cola<Integer> construirCola(int[] arregloDeElementos){
     Cola<Integer> colaEstructura = new Cola<>();
     for (int elemento:arregloDeElementos) {
         colaEstructura.mete(elemento);
     }
     return colaEstructura;
    }
    /**
     * Metodo encargado de construir una pila.
     * @param arregloDeElementos el arreglo de enteros de elementos.
     * @return Un pila construida.
     */
 public static Pila<Integer> construirPila(int[] arregloDeElementos){
     Pila<Integer> pilaEstructura = new Pila<>();
     for (int elemento:arregloDeElementos) {
         pilaEstructura.mete(elemento);
     }
     return pilaEstructura;
    }

    /**
     * Metodo encargado de construir un arbol binario completo.
     * @param arregloDeElementos el arreglo de enteros de elementos.
     * @return Un arbol binario completo construido.
     */
    public static ArbolBinarioCompleto<Integer> contruirArbolBinaroCompleto(int[] arregloDeElementos){
        ArbolBinarioCompleto<Integer> arbolBinarioCompletoEstructura = new ArbolBinarioCompleto<>();
        for (int elemento: arregloDeElementos) {
            arbolBinarioCompletoEstructura.agrega(elemento);
        }
        return arbolBinarioCompletoEstructura;

    }

    /**
     * Metodo encargado de construir un arbol binario ordenado.
     * @param arregloDeElementos el arreglo de enteros de elementos.
     * @return Un arbol binario ordenado construido.
     */
    public static ArbolBinarioOrdenado<Integer> contruirArbolBinaroOrdenado(int[] arregloDeElementos){

        ArbolBinarioOrdenado<Integer> arbolBinarioOrdenadoEstructura = new ArbolBinarioOrdenado<>();
        for (int elemento: arregloDeElementos) {
            arbolBinarioOrdenadoEstructura.agrega(elemento);
        }
        return arbolBinarioOrdenadoEstructura;
    }
    /**
     * Metodo encargado de construir un arbol rojinegreo.
     * @param arregloDeElementos el arreglo de enteros de elementos.
     * @return Un arbol rojinegro construido.
     */
    public static ArbolRojinegro<Integer> contruirArbolRojinegro(int[] arregloDeElementos){

        ArbolRojinegro<Integer> arbolRojinegroEstructura = new ArbolRojinegro<>();
        for (int elemento: arregloDeElementos) {
            arbolRojinegroEstructura.agrega(elemento);
        }
        return arbolRojinegroEstructura;
    }
    /**
     * Metodo encargado de construir un arbol AVL.
     * @param arregloDeElementos el arreglo de enteros de elementos.
     * @return Un arbol AVL construido.
     */
    public static ArbolAVL<Integer> contruirArbolAVL(int[] arregloDeElementos){

        ArbolAVL<Integer> arbolAVLEstructura = new ArbolAVL<>();
        for (int elemento: arregloDeElementos) {
            arbolAVLEstructura.agrega(elemento);
        }
        return arbolAVLEstructura;
    }
    /**
     * Metodo encargado de construir una grafica.
     * @param arregloDeElementos el arreglo de enteros de elementos.
     * @return Una grafica construida.
     */
    public static Grafica<Integer> contruirGrafica(int[] arregloDeElementos) {

        if ((arregloDeElementos.length % 2) != 0) {
            for (int entero:arregloDeElementos) {
                System.out.println(entero);

            }
            System.out.println("¡No se pudo construir la grafica!\n" +
                    "Asegurese que el numero de elementos en la entrada o el archivo sea par,\n" +
                    "ya que cada par de elementos es una arista.");
            System.exit(0);
        }
        Grafica<Integer> graficaEstructura = new Grafica<>();
        Lista<Integer> elementosUnicos = new Lista<>();
        int contadorDeEle = 0;
        while (contadorDeEle < arregloDeElementos.length){

            if(!elementosUnicos.contiene(arregloDeElementos[contadorDeEle])){
                elementosUnicos.agregaFinal(arregloDeElementos[contadorDeEle]);
            }
            contadorDeEle++;
        }
        for (int elemento : elementosUnicos) {
            graficaEstructura.agrega(elemento);
        }
        int contador = 0;
        while (contador < arregloDeElementos.length) {
            if(arregloDeElementos[contador] != arregloDeElementos[contador+1]) {
                graficaEstructura.conecta(arregloDeElementos[contador],
                        arregloDeElementos[contador + 1]);

                }
            contador = contador + 2;
        }
        return graficaEstructura;
    }

    /**
     * Metodo encargado de construir un monticulo minimo.
     * @param arregloDeElementos el arreglo de enteros de elementos.
     * @return Un monticulo minimo construido.
     */
    public static MonticuloMinimo<ValorIndexable<Integer>> contruirMonticuloMinimo(int[] arregloDeElementos){

        Lista<ValorIndexable<Integer>> listaM = new Lista<ValorIndexable<Integer>>();
        for (int elemento: arregloDeElementos) {

            listaM.agregaFinal(new ValorIndexable<>(elemento,elemento));

        }

        return new MonticuloMinimo<>(listaM);
    }

    /**
     * Metodo encargado de construir un monticulo arreglo.
     * @param arregloDeElementos el arreglo de enteros de elementos.
     * @return Un monticulo arreglo construido.
     */
    public static MonticuloArreglo<ValorIndexable<Integer>> contruirMonticuloArreglo(int[] arregloDeElementos){

        return new MonticuloArreglo<>(contruirMonticuloMinimo(arregloDeElementos));
    }



}
