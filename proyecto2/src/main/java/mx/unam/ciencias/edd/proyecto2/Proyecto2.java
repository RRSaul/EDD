package mx.unam.ciencias.edd.proyecto2;
import mx.unam.ciencias.edd.*;
import mx.unam.ciencias.edd.proyecto2.dibujantes.*;

/**
 * Clase publica y principal del Proyecto 2, Estructuras de datos en SVG.
 */


public class Proyecto2 {
    //La estructura actual identificada.
    private static EnumEstructuras.Estructura estructuraActual;
    //Los elementos en la estructura dada.
    private static int[] elementosEnEstructura;


    /**
     * Metodo publico main encargado de recibir los arguentos y manejar las accines a seguir.
     * @param args los argumentos de la entrada.
     */
    public static void main(String[] args) {
        //El StringBuilder para analizar la entrada.
        StringBuilder argumentoEntrada = new StringBuilder();
        for (String arg: args){
            argumentoEntrada.append(arg);
        }
        if(IdentificadorDeEstructuras.esArchivo(args)){
            estructuraActual = IdentificadorDeEstructuras.identificaEnArchivo(args);
             elementosEnEstructura = AuxilearDeArchivos.elementosEnEstructuraDeArchivo(argumentoEntrada.toString(),estructuraActual);



            constuctoresYDibujantes();
        }else {


            estructuraActual = IdentificadorDeEstructuras.identificadorEnEntrada(args);
            elementosEnEstructura = AuxilearDeArchivos.elementosEnEstructuraDeEntrada(args);
            constuctoresYDibujantes();
        }
    }

    /**
     * Metodo encargado de manejar con un switch la construccion y dibujo de las estructuras.
     */
    private static void constuctoresYDibujantes(){

        switch (estructuraActual){

            case Lista:
                Lista<Integer> lista = ConstructorDeEstructuras.construirLista(elementosEnEstructura);
                DibujanteLista.dibujarLista(lista);
                break;

            case Arreglo:
                int[] arreglo = ConstructorDeEstructuras.contruirArreglo(elementosEnEstructura);
                DibujanteArreglos.dibujarArreglo(arreglo);

                break;

            case Cola:
                Cola<Integer> cola = ConstructorDeEstructuras.construirCola(elementosEnEstructura);
                DibujanteCola.dibujarCola(cola,elementosEnEstructura.length);

                break;

            case Pila:
                Pila<Integer> pila = ConstructorDeEstructuras.construirPila(elementosEnEstructura);
                DibujantePila.dibujarPila(pila,elementosEnEstructura.length);
                break;

            case ArbolBinarioCompleto:
                ArbolBinarioCompleto<Integer> arbolBCompleto = ConstructorDeEstructuras.
                        contruirArbolBinaroCompleto(elementosEnEstructura);
                DibujanteArbolBinarioCompleto.dArbolCompleto(arbolBCompleto);
                break;

            case ArbolBinarioOrdenado:
                ArbolBinarioOrdenado<Integer> arbolBinarioOrdenado = ConstructorDeEstructuras.
                        contruirArbolBinaroOrdenado(elementosEnEstructura);
                DibujanteArbolBinarioOrdenado.dArbolOrdenado(arbolBinarioOrdenado);
                break;

            case ArbolRojinegro:
                ArbolRojinegro<Integer> arbolRojinegro = ConstructorDeEstructuras.contruirArbolRojinegro(elementosEnEstructura);
                DibujanteArbolRojinegro.dArbolRojinegro(arbolRojinegro);
                break;

            case ArbolAVL:
                ArbolAVL<Integer> arbolAVL = ConstructorDeEstructuras.contruirArbolAVL(elementosEnEstructura);
                DibujanteArbolBinarioAVL.dArbolAVL(arbolAVL);
                break;

            case Grafica:
                Grafica <Integer> grafica = ConstructorDeEstructuras.contruirGrafica(elementosEnEstructura);
                DibujanteGrafica.dibujarGrafica(grafica);
                break;

            case MonticuloMinimo:
                MonticuloMinimo<ValorIndexable<Integer>> monticuloMinimo= ConstructorDeEstructuras.contruirMonticuloMinimo(elementosEnEstructura);
                DibujanteMonticuloMinimo.dMonticuloMinimo(monticuloMinimo);
                break;

            case MonticuloArreglo:
                MonticuloArreglo<ValorIndexable<Integer>> monticuloArreglo = ConstructorDeEstructuras.contruirMonticuloArreglo(elementosEnEstructura);

                DibujanteMonticuloArreglo.dibujarArregloMonticulo(monticuloArreglo);
                break;

            default:
                // No deberia llegar a este punto.
                throw new IllegalArgumentException("No se pudo reconocer una estructuraActual valida.");
        }
    }




}
