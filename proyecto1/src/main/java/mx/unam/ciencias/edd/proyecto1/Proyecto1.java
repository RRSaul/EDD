package mx.unam.ciencias.edd.proyecto1;

import java.io.*;
import java.util.Iterator;

public class Proyecto1 {
    private static Lista<Linea> lineas = new Lista <Linea>();
    private static String[] argumento;

    public static void main(String[] args) throws IOException {
        argumento = args;
        StringBuilder entrada = new StringBuilder(" ");
        if(args != null) {

            for (String arg:args) {
                entrada.append(arg).append(" ");

            }
                archivo(entrada.toString(),argumento);
        }
        else throw new IllegalArgumentException();
        }

    /**
     * Metodo que maneja los 4 casos posibles de la entrada.
     * @param entrada la reprentacion en cadena de args.
     * @param argumentos El arreglo de argumentos
     * @throws IOException si hay algun error.
     */
    public static void archivo(String entrada,String[] argumentos) throws IOException {
        if(entrada.contains(" -r ") && entrada.contains(" -o ")){
            guardaR();
        }else if (entrada.contains("-o")){
            guarda();
        }else if(entrada.contains("-r")){
            impR();
        }else {
            imp(argumentos);
        }
    }

    /**
     * Metodo encargado de ordenar las lineas del archivo he imprimirlas en consola.
     * @param argumento es el archivo a ordendar.
     * @throws IOException si hay algún error.
     */
    public static void imp(String[] argumento) throws IOException {

        for (String arg:argumento) {

            leerArch(arg);
        }
        ordenarLineas();
        System.out.println(lineas);

    }

    /**
     * Metodo encargado de ordenar en orden inverso las lineas del archivo he imprimirlas en consola.
     * @throws IOException si hay algún error.
     */
    public static void impR()throws IOException{
        limpiaBanderas();
       for (String arg:argumento) {
        if(!arg.equals(""))
        leerArch(arg);
    }
        ordenarLineas();
        lineas = lineas.reversa();
        System.out.println(lineas);

    }

    /**
     * Metodo encargado de guardar el archivo ordenado en el indentificador dado.
     * @throws IOException Si hay algun error.
     */
    public static void guarda()throws IOException{
        String indetificador =  getIdentificador();
        limpiaBanderas();
       for (String arg:argumento) {
            if(!arg.equals(""))
            leerArch(arg);
        }
       ordenarLineas();
        guardarenArcc(indetificador);
    }

    /**
     * Metodo encargado de guardar el archivo ordenado en reversa en el indentificador dado.
     * @throws IOException Si hay algun error.
     */
    public static void guardaR()throws IOException{
        String indetificador =  getIdentificador();
        limpiaBanderas();
        for (String arg:argumento) {
            if(!arg.equals(""))
            leerArch(arg);
        }
        ordenarLineas();
        lineas = lineas.reversa();
        guardarenArcc(indetificador);


    }
    /**
     * Metodo encargado de leer el archivo dado y cargarlo a la lista del proyecto.
     */
    private static void leerArch(String direc) throws IOException {
        String cadena;
        FileReader f = new FileReader(direc);
        BufferedReader b = new BufferedReader(f);

        while((cadena = b.readLine())!=null) {
            lineas.agregaFinal(new Linea(cadena));
        }
        b.close();


    }

    /**
     * Metodo encargado de ordenar la lista del proyecto.
     */
    private static void ordenarLineas(){
        lineas = Lista.mergeSort(lineas);


    }
    /**
     * Metodo encarado de guardar la lista del proyecto en la direccion dada.
     * @param direc es la direccion a guardar.
     *
     */
    private static void guardarenArcc(String direc) throws IOException {
        Iterator<Linea> iterator = lineas.iterator();
        FileWriter f = new FileWriter(direc);
        BufferedWriter b = new BufferedWriter(f);
        while(iterator.hasNext()) {
            b.write(String.valueOf(iterator.next()) + "\n");
        }
        b.close();

    }

    /**
     *Metodo encargago de encontrar el identificador en la presensia de la bandera -o y devolverlo.
     * @return el identificador donde guardar el archivo ordenado.
     */
    private static String getIdentificador(){
        String identificador = "";
        int index = 0;
        for (String arg:argumento) {
            if(arg.contains("-o")){
                break;
            }
            index++;
        }
        identificador = argumento[index+1];
        argumento[index+1] = "";
        return identificador;
    }

    /**
     * Metodo encargado de limpiar las banderas en la entrada de argumentos y asi evitar FileNotFoundExepcions,
     * ademas de reforzar en contra del uso de multiples banderas.
     */
    private static void limpiaBanderas(){
        int index = 0;
        for (String arg:argumento) {
            if (arg.equals("-r") || arg.equals("-o"))
                argumento[index] = "";
            index++;
        }
    }



}
