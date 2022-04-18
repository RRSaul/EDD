package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.proyecto3.dibujantes.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

/**
 * Clase ecargada de escribir cada documento en el direcctorio dado.
 */

public class Escritor {


    /**
     * Metodo encargado de escribir el reporte correspondiente a cada archivo dado.
     * @param directorio el directorio donde se guardara.
     * @param nombreArchivo el nombre del archivo.
     * @param listaPalabras las palabras en dicho archivo.
     */
    public static void escribirUArch(String directorio, String nombreArchivo, Lista<Palabra> listaPalabras) {
        if (listaPalabras.esVacia()) {
            return;
        }
        String nombreEnDirectorio = nombreArchivo + "-reporte";
        StringBuilder texto = new StringBuilder("<!DOCTYPE html>\n" +
                "<html lang=\"es-MX\">\n" +
                "  <head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "<body bgcolor=\"whitesmoke\" text=\"whitesmoke\" leftmargin=\"50px\"");
        texto.append("<title>" + nombreEnDirectorio + "</title>\n" +
                "  </head>\n" );

        DibujanteConteo.dibujaConteo(nombreArchivo,texto,listaPalabras);
        DibujanteGrafico.dibujaGrafico(texto,listaPalabras);
        DibujanteArboles.dibujarArboles(texto,listaPalabras);
        texto.append(
                "</body>\n" +
                        "</html>");
        try {
            FileWriter f = new FileWriter(directorio + "/" + nombreEnDirectorio + ".html");
            BufferedWriter b = new BufferedWriter(f);
            b.write(texto.toString());
            b.close();
        }catch (IOException e){
            System.out.println("¡Ha habido un error al escribir tu reporte!");

        }
    }

    /**
     * Metodo encargado de escribir el archivo index.html en el directorio con la info de todos los documentos.
     * @param docs  Una lista con todos los documentos.
     * @param listaNombres Una lista con los nombres de lo documentos.
     * @param directorio el directorio donde irá el index.
     */
    public static void escribirIndex(Lista<Lista<Palabra>> docs,Lista<String> listaNombres, String directorio){

        StringBuilder texto = new StringBuilder();
        texto.append("<!DOCTYPE html>\n" +
                "<html lang=\"es-MX\">\n" +
                "  <head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "<body bgcolor=\"whitesmoke\" text=\"black\" leftmargin=\"50px\"</title>\n" +
                "  </head>");

        Iterator<String> nombre = listaNombres.iterator();

        for (Lista<Palabra> palabras: docs) {


            String nombreDoc = nombre.next();
            String nombreEnDirectorio = nombreDoc + "-reporte";
                DibujanteConteo.dibujaMiniConteo(nombreDoc, texto, AuxilearGeneral.cortaListaConOtros(palabras, 9));
                texto.append("<a href=\""+directorio+"/"+nombreEnDirectorio+ ".html\">"+nombreEnDirectorio+ "</a>");

        }

        DibujanteGrafica.dibujarGrafica(Reportero.crearGrafica(docs),texto,listaNombres);

        texto.append("</body>\n" +
                "</html>");
        try {
            FileWriter f = new FileWriter(directorio + "/index.html");
            BufferedWriter b = new BufferedWriter(f);



            b.write(texto.toString());
            b.close();
        }catch (IOException e){
            System.out.println("¡Ha habido un error al escribir tu index!");
            System.exit(0);
        }

    }
}
