package mx.unam.ciencias.edd.proyecto2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Clase publica encargada de identificar la estructura en el archivo dado o en la entrada estandar.
 */

public class IdentificadorDeEstructuras {

    /**
     * Metodo par saber si la entrada dada al programa se trata de un archivo o una entrada manual.
     * @param args los argumentos de la entrada.
     * @return true si trata de un archivo, false si es una entrada manual.
     */

    public static boolean esArchivo(String[] args){

        String entradaArch = args[0];

        StringBuilder entrada = new StringBuilder();
        for (String arg: args){
            entrada.append(arg);
        }
        File archivo = new File(String.valueOf(entradaArch));
        if(entradaArch.equals(entrada.toString()) && !archivo.exists()){

            System.out.println("¡El archivo que intentas graficar no existe!\n" +
                                "Si estas ingresando una estructura manualmente ¡esta vacia! ");
            System.exit(0);
        }

        return entradaArch.equals(entrada.toString()) && archivo.exists();
    }

    /**
     * Metodo encargado de identificar de que estructura se trata la entrada.
     * @param args los argumentos de entrada.
     * @return un EnumEstructuras.Estructura de la primera estructura encontrada.
     */
    public static EnumEstructuras.Estructura identificadorEnEntrada(String[] args){
        StringBuilder entradaSB = new StringBuilder();
        for (String arg: args){
            entradaSB.append(arg);
        }
        //Al usar el paso a minusculas se refuerza a errores de escritura que SOLO sea por minusculas o mayusculas.
        String entrada = entradaSB.toString().toLowerCase();
        if(entrada.contains("lista"))
            return EnumEstructuras.Estructura.Lista;
        if(entrada.contains("arreglo") && !entrada.contains("monticuloarreglo"))
            return EnumEstructuras.Estructura.Arreglo;
        if(entrada.contains("cola"))
            return EnumEstructuras.Estructura.Cola;
        if(entrada.contains("pila"))
            return EnumEstructuras.Estructura.Pila;
        if(entrada.contains("arbolbinariocompleto"))
            return EnumEstructuras.Estructura.ArbolBinarioCompleto;
        if(entrada.contains("arbolbinarioordenado"))
            return EnumEstructuras.Estructura.ArbolBinarioOrdenado;
        if(entrada.contains("arbolrojinegro"))
            return EnumEstructuras.Estructura.ArbolRojinegro;
        if(entrada.contains("arbolavl"))
            return EnumEstructuras.Estructura.ArbolAVL;
        if(entrada.contains("grafica"))
            return EnumEstructuras.Estructura.Grafica;
        if(entrada.contains("monticulominimo"))
            return EnumEstructuras.Estructura.MonticuloMinimo;
        if(entrada.contains("monticuloarreglo")) {
            return EnumEstructuras.Estructura.MonticuloArreglo;
        }
        System.out.println("¡Parece que la entrada no es una esctructura valida!\n" +
                "¡Revisa que este bien escrito!");
        System.exit(0);
        //No es posible llegar a este punto.
        throw new IllegalArgumentException();






    }

    /**
     * Metodo encargado de identificar de que estructura se trata el archivo.
     * @param args los argumentos de entrada.
     * @return un EnumEstructuras.Estructura de la primera estructura encontrada.
     */

    public static EnumEstructuras.Estructura identificaEnArchivo(String[] args){
        StringBuilder contenido = new StringBuilder(" ");
        String entradaArch = args[0];
        try {
            FileReader f = new FileReader(entradaArch);
            BufferedReader b = new BufferedReader(f);

            String cadena;
            while ((cadena = b.readLine()) != null) {
                contenido.append(cadena).append("\n");
            }
            b.close();
        } catch (IOException e){

            System.out.println("¡Parece que el archivo esta dañado!\n" +
                               "¡Intenta con un archivo distinto o con una entrada manual!");
            System.exit(0);
        }
        //Al usar el paso a minusculas se refuerza a errores de escritura que SOLO sea por minusculas o mayusculas.
        String contenidoMius = contenido.toString().toLowerCase();
        if(contenidoMius.contains("lista"))
            return EnumEstructuras.Estructura.Lista;
        if(contenidoMius.contains("arreglo") && !contenidoMius.contains("monticuloarreglo"))
            return EnumEstructuras.Estructura.Arreglo;
        if(contenidoMius.contains("cola"))
            return EnumEstructuras.Estructura.Cola;
        if(contenidoMius.contains("pila"))
            return EnumEstructuras.Estructura.Pila;
        if(contenidoMius.contains("arbolbinariocompleto"))
            return EnumEstructuras.Estructura.ArbolBinarioCompleto;
        if(contenidoMius.contains("arbolbinarioordenado"))
            return EnumEstructuras.Estructura.ArbolBinarioOrdenado;
        if(contenidoMius.contains("arbolrojinegro"))
            return EnumEstructuras.Estructura.ArbolRojinegro;
        if(contenidoMius.contains("arbolavl"))
            return EnumEstructuras.Estructura.ArbolAVL;
        if(contenidoMius.contains("grafica"))
            return EnumEstructuras.Estructura.Grafica;
        if(contenidoMius.contains("monticulominimo"))
            return EnumEstructuras.Estructura.MonticuloMinimo;
        if(contenidoMius.contains("monticuloarreglo"))
            return EnumEstructuras.Estructura.MonticuloArreglo;
        System.out.println("¡Parece que el archivo no contiene una estructura reconocible!\n" +
                "¡Revisa que este bien escrito!");
        System.exit(0);
        //No es posible llegar a este punto.
        throw new IllegalArgumentException();
    }



}
