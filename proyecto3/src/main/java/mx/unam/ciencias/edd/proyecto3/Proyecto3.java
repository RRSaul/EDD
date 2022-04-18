package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.Diccionario;
import mx.unam.ciencias.edd.Lista;

import java.io.File;
import java.util.Iterator;
import java.util.Scanner;

public class Proyecto3 {

    public static String directorio;
    public static void main(String[] args) {


        if(args.length<3){
            System.out.println("¡Introduzca los archivos y el directorio!");
            Scanner input = new Scanner(System.in);
            String entrada = input.nextLine();
            args = entrada.split("\\s");
        }

        int indice = 0;
        boolean direcBool = false;

        if(args.length <= 2){
            terminaGracia("¡No se ha detectado una entrada valida!\n" +
                    "Recuerda que el directerio se indica con la bandera -o seguida del directorio\n" +
                    "Ademas de almenos un archivo a analizar");


        }

        for (String argumento: args) {

            if(argumento.equals("-o")){
                direcBool = true;
                break;
            }
            indice++;
        }
        if(!direcBool || (indice+1>args.length)){
            terminaGracia("¡No se ha detectado el directorio!\n" +
                            "Recuerda que el directerio se indica con la bandera -o seguida del directorio");
        }
        directorio = args[indice + 1];
        File directorios = new File(directorio);
        if (!directorios.exists()) {
            if (directorios.mkdirs()) {
                System.out.println("¡Se ha creado un nuevo directorio como fue especificado!");
            }
        }
        int contadorArchivos = 0;
        Lista<Lista<Palabra>> listaDocs = new Lista<>();
        Lista<String> listaNomnres = new Lista<>();
        while (contadorArchivos < args.length){
            if(contadorArchivos != indice && contadorArchivos != (indice+1)){

                String[] arreglo = args[contadorArchivos].split("/");
                String nombre = arreglo[arreglo.length-1];
                Diccionario<String,Integer> diccionario = Reportero.procesa(args[contadorArchivos],nombre);

                Lista<Palabra> listaPalabra = new Lista<>();
                Iterator<String> iteratorLlave = diccionario.iteradorLlaves();
                for (Integer integer : diccionario) {
                    String palabra = iteratorLlave.next();
                    int repeticiones = integer;
                    listaPalabra.agregaFinal(new Palabra(palabra, repeticiones));
                }
                listaPalabra = Lista.mergeSort(listaPalabra);
                if(!listaPalabra.esVacia()) {
                    listaDocs.agregaFinal(listaPalabra);
                    listaNomnres.agregaFinal(nombre);
                }

                Escritor.escribirUArch(directorio,nombre,listaPalabra);
            }
            contadorArchivos++;
        }

    Escritor.escribirIndex(listaDocs,listaNomnres,directorio);
        System.out.println("¡Todo ha salido bien!\n" +
                "Los archivos se encuentran en el directorio proporcionado");
    }

    private static void terminaGracia(String mensaje){
        System.out.println(mensaje);
        System.exit(0);
    }
}
