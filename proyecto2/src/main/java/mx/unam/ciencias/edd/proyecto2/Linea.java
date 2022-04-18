package mx.unam.ciencias.edd.proyecto2;

import java.text.Normalizer;

/**
 * Clase para Lineas, las lineas tienen una unica propiedad que es su contenido,
 * un String con un linea para su ordenamiento. Se a reusado esta clase del proyecto anterior,
 * aun que ahora no sea necesarario compararlos.
 */
public class Linea implements Comparable<Linea> {
    private String contenido;

    public Linea(String contenidoLinea) {
        this.contenido = contenidoLinea;
    }

    /**
     * Metodo en cargado de comparar dos lineas en base a su contenido, la comaparacion ignora espacios vacios, acentos, diéresis y eñes.
     * toman como vocales.
     * @param o la linea a comparar.
     * @return
     */
    @Override
    public int compareTo(Linea o) {
         String contenidonuevo = Normalizer.normalize(contenido,Normalizer.Form.NFD);
        String onueva = Normalizer.normalize(o.contenido,Normalizer.Form.NFD);
        contenidonuevo = contenidonuevo.replaceAll("[^\\p{ASCII}]", "");
        onueva = onueva.replaceAll("[^\\p{ASCII}]", "");
        contenidonuevo = contenidonuevo.toUpperCase();
        onueva = onueva.toUpperCase();
        return contenidonuevo.trim().compareTo(onueva.trim());
    }

    @Override
    public String toString() {
        return contenido;
    }
}
