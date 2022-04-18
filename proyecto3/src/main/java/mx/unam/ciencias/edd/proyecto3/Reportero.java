package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.*;

import java.io.*;
import java.text.Normalizer;

/**
 * Clase encargada de analizar los documentos, digamos que genera los reportes
 * de cada documento, y del index tambien, y se los pasa a los escritores y dibujantes.
 */

public class Reportero{


    /**
     * Metodo encargado de generar el reporte de un archivo en forma de un diccionario.
     * @param direcArch la direccion del archivo.
     * @param nombreArch el nombre del archivo.
     * @return el diccionario con las palabras como llaves y las repeticiones como valores.
     */
    public static Diccionario<String,Integer> procesa( String direcArch,String nombreArch){
        File archivoLeer = new File(direcArch);
        if (!archivoLeer.exists()){
            //Se hace de esta manera para ser robusto de manera permisiva y no ser tan duro con el usuario.
            System.out.println("¡El archivo "+nombreArch+" no existe y será ignorado!");
            return new Diccionario<String, Integer>();
        }
        Diccionario<String,Integer> diccionario = new Diccionario<>();
        try {

            String linea;
            FileReader f = new FileReader(direcArch);
            BufferedReader b = new BufferedReader(f);
            while ((linea = b.readLine()) != null) {
                String[] palabras = linea.split("\\s");
                for (String cadena:palabras) {
                    cadena = Normalizer.normalize(cadena, Normalizer.Form.NFD);
                    cadena = cadena.replaceAll("[^\\p{ASCII}]", "");
                    cadena = cadena.replaceAll("\\p{Punct}", "");
                    cadena = cadena.toLowerCase();
                    if (!cadena.equals("")){
                        if (diccionario.contiene(cadena)) {
                            int cantidad = diccionario.get(cadena);
                            diccionario.agrega(cadena, cantidad + 1);
                        } else {
                            diccionario.agrega(cadena, 1);
                        }
                    }
                }
            }
            b.close();
        }catch (IOException e){
            //Se hace de esta manera para ser robusto de manera permisiva y no ser tan duro con el usuario.
            System.out.println("¡Ha habido un error de lectura con  "+nombreArch+", será ignorado!");
            return new Diccionario<String, Integer>();
        }
        if(diccionario.esVacia()){
            //Se hace de esta manera para ser robusto de manera permisiva y no ser tan duro con el usuario.
            System.out.println("¡"+nombreArch+" esta vacio, será ignorado!");
        }
        return diccionario;
    }


    /**
     * Crea la grafica con los documentos como vertices, si tienen una palbra de mas de
     * 7 de longitud en comun se conectan.
     * @param docs todos los documentos introducidos.
     * @return la grafica construida.
     */
    public static Grafica<Lista<Palabra>> crearGrafica(Lista<Lista<Palabra>> docs){

        Grafica<Lista<Palabra>> grafica = new Grafica<>();
        for (Lista<Palabra> lista: docs) {
            grafica.agrega(lista);
        }

        for (Lista<Palabra> lista: docs) {
            for (Lista<Palabra> listaInterna: docs) {
                if(!lista.equals(listaInterna) && !grafica.sonVecinos(lista,listaInterna)){
                    for (Palabra palabra:lista) {
                        if(palabra.getPalabra().length()>= 7 && listaInterna.contiene(palabra) && !grafica.sonVecinos(lista,listaInterna)){
                            grafica.conecta(lista,listaInterna);
                        }

                    }

                }

            }

        }
        return grafica;
    }



}
