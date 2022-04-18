package mx.unam.ciencias.edd.proyecto2;
import mx.unam.ciencias.edd.Lista;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


/**
 * Interfaz publica auxilear en procesar el contenido de los archivos.
 */

public class AuxilearDeArchivos {

    /**
     * Metodo encargado de devolver los elementos de la estrucura de datos desde el archivo.
     * @param direc el archivo a cargar.
     * @return Un arreglo de enteros, estos estan ordenados con forme aparecen el el archivo.
     */

    public static int[] elementosEnEstructuraDeArchivo(String direc,EnumEstructuras.Estructura estructura){

        String[] contenido = AuxilearDeArchivos.procesarContenido(direc);
        String[] contenidoLimpio = AuxilearDeArchivos.limpiarArchivo(contenido);
        Lista<Integer> elementosLista = new Lista<>();

        String estructuraString = estructura.name();
        Lista<Linea> listaContenido = new Lista<>();
        for (String linea: contenidoLimpio) {
            String[] lineaSeparada = linea.split("\\s");
            for (String elemento : lineaSeparada) {
                if(!elemento.equals("\\s") && !elemento.equals("") )
                    listaContenido.agregaFinal(new Linea(elemento.trim()));
            }
        }
        if(!listaContenido.getPrimero().toString().toLowerCase().equals(estructuraString.toLowerCase())){
            System.out.println("¡Parece que tu archivo contiene la estructura " + estructuraString + " pero no respeta el formato!\n" +
                    "¡Lo primero que debe estar presente en tu archivo es la estructura, corrigelo y vuelve a intentar!");
            System.exit(0);
        }

        listaContenido.eliminaPrimero();

        int contadorDeErrores = 0;

        for (Linea linea: listaContenido) {

                   try {
                    elementosLista.agregaFinal(Integer.valueOf(linea.toString()));
                       //El cath esta aqui para fortalecer de manera inclusiva, si hay un caratcer mal escrito,
                       //en lugar de terminar el programa simplemente se omite.
                    }catch (NumberFormatException i){
                       contadorDeErrores++;
                   }

        }
        if(contadorDeErrores>0){
            System.out.println("¡Parece que tu archivo contiene "+contadorDeErrores+" elemento/s mal escrito/s y se omitieron!\n" +
                    "¡Corrige este error si no quieres perder este/os elemento/s!");
        }


        int[] elementosInt = new int[elementosLista.getLongitud()];
        int contador = 0;
        for (Integer entero: elementosLista ) {
            elementosInt[contador] = entero;
            contador++;
        }
        return elementosInt;
    }
    /**
     * Metodo encargado de devolver los elementos de la estrucura de los argumentos en la entrada.
     * @param args los argumentos de entrada.
     * @return Un arreglo de enteros, estos estan ordenados con forme aparecen el el archivo.
     */
    public static int[] elementosEnEstructuraDeEntrada(String[] args){

      Lista<Integer> elementosLista= new Lista<>();
        int contadorContenido = 1;
        int contadorDeErrores = 0;
        while (contadorContenido<args.length){
            try {
            elementosLista.agregaFinal(Integer.valueOf(args[contadorContenido]));
            //El cath esta aqui para fortalecer de manera inclusiva, si hay un caracter mal escrito,
                //en lugar de terminar el programa simplemente se omite.
            }catch (NumberFormatException i){
               contadorDeErrores++;
            }
            contadorContenido++;
        }
        if(contadorDeErrores>0){
            System.out.println("¡Parece que tu entrada contiene "+contadorDeErrores+" elemento/s mal escrito/s y se omitieron!\n" +
                    "¡Corrige este error si no quieres perder este/os elemento/s!");
        }
        int[] elementosInt = new int[elementosLista.getLongitud()];
        int contador = 0;
        for (Integer entero: elementosLista ) {
            elementosInt[contador] = entero;
            contador++;
        }
        return elementosInt;


    }

    /**
     * Metodo encargado de limpiar el contenido del archivo de almoadillas(#), todo lo que se encuentre
     * despues de una sera ignorado.
     * @param contenido un arreglo de cadenas con el contenido del archivo.
     * @return
     */
    private static String[] limpiarArchivo(String[] contenido){
        int contador = 0;
        for(String linea: contenido){
            if(linea.contains("#")){
                contenido[contador] = linea.split("#")[0];

            }
            contador++;

        }
        return contenido;
    }

    /**
     * Metodo encargado de procesar el archivo dado y obtener su cotenido.
     * @param direc La direccion del archivo.
     * @return El contenido del archivo representado en un arreglo de cadenas, una cadena por cada linea.
     */

    private static String[] procesarContenido(String direc){
        String[] contenido;
        Lista<Linea> listaLineas = new Lista<>();
        try {

            String cadena;
            FileReader f = new FileReader(direc);
            BufferedReader b = new BufferedReader(f);

            while((cadena = b.readLine())!=null) {
                listaLineas.agregaFinal(new Linea(cadena.trim()));
            }
            b.close();
        }catch (IOException e){throw new IllegalArgumentException("El archivo a cargar no es valido.");  }

        contenido = new String[listaLineas.getLongitud()];
        int contador = 0;
        for (Linea linea: listaLineas) {
            contenido[contador] = linea.toString();
            contador++;
        }
        return contenido;
    }
}
