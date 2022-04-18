package mx.unam.ciencias.edd.proyecto3.dibujantes;

import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.proyecto3.Palabra;

import java.util.Iterator;

/**
 * Clase publica auxilear al momento de dibujar con svg y trabajar con los documentos.
 */

public class AuxilearGeneral {
    /**
     * Metodo encargado de dar un color en base a un indice, apartir de un punto los colores son iguales.
     * @param numero el indice de color.
     * @return el color.
     */
   public static String colorDeBase(int numero){

        switch (numero){
            case 1:
                return "midnightblue";
            case 2:
                return "indigo";
            case 3:
                return "royalBlue";
            case 4:
                return "mediumslateblue";
            case 5:
                return "darkcyan";
            case 6:
                return "seagreen";
            case 7:
                return "darkkhaki";
            case 8:
                return "sienna";
            case 9:
                return "brown";
            case 10:
                return "indianred";

            default:
                return "darkslategray";

        }
    }
    /**
     * Metodo encargado de dar un color en base a un indice, apartir de un punto los colores son iguales.
     * @param numero el indice de color.
     * @return el color.
     */
    public static String colorDeBaseReducido(int numero){

        switch (numero){
            case 1:
                return "midnightblue";
            case 2:
                return "indigo";
            case 3:
                return "royalBlue";
            case 4:
                return "mediumslateblue";
            case 5:
                return "darkcyan";
            case 6:
                return "seagreen";
            case 7:
                return "darkkhaki";
            case 8:
                return "sienna";
            case 9:
                return "brown";

            default:
                return "darkslategray";

        }
    }

    /**
     * Metodo encargado de reducir la lista a una logitud dada, el resto se ignoran.
     * @param palabras las palabras del documento.
     * @param nuevaLong la nueva longitud.
     * @return la lista con la nueva longitud.
     */
     public static Lista<Palabra> cortaLista(Lista<Palabra> palabras, int nuevaLong){

        if(nuevaLong>= palabras.getLongitud()){return palabras;}
        Iterator<Palabra> iterator = palabras.iterator();
        Lista<Palabra> palabrasCorta = new Lista<>();

        int contador = 0;
        while (contador<nuevaLong){
            palabrasCorta.agregaFinal(iterator.next());
            contador++;
        }

        return palabrasCorta;

    }

    /**
     * Metodo encargado de reducir la lista a una logitud dada, el resto se suman en OTRAS.
     * @param palabras las palabras del documento.
     * @param nuevaLong la nueva longitud.
     * @return la lista con la nueva longitud +1.
     */
    public static Lista<Palabra> cortaListaConOtros(Lista<Palabra> palabras, int nuevaLong){

        if(nuevaLong-1>= palabras.getLongitud()){return palabras;}
        Iterator<Palabra> iterator = palabras.iterator();
        Lista<Palabra> palabrasCorta = new Lista<>();

        int contador = 0;

        while (contador<nuevaLong){
            palabrasCorta.agregaFinal(iterator.next());
            contador++;
        }
        int sumaResto = 0;
        while (iterator.hasNext()){

            sumaResto+= iterator.next().getRepeticiones();
        }
        palabrasCorta.agregaFinal(new Palabra("OTRAS",sumaResto));
        return palabrasCorta;

    }

    /**
     * Metodo encargado de dar la cantidad de palabras totales en el documento.
     * @param palabras las palabras del cocumento.
     * @return la cantidad de palabras totales.
     */
    public static int pabrasTotales(Lista<Palabra> palabras){

        Iterator<Palabra> iterator = palabras.iterator();
        int total = 0;
        while (iterator.hasNext()){
            total += iterator.next().getRepeticiones();

        }
        return total;
    }

    /**
     * Metodo encargado de dar la cantidad de repeticiones maximas de las palarbas en un documento.
     * @param palabras las palabras en el doc.
     * @return la cantidad maxima.
     */

    static int cantidadMaxima(Lista<Palabra> palabras){

        int cantidadMaxima = 0;
        for (Palabra palabra:palabras) {
            cantidadMaxima = Math.max(cantidadMaxima,palabra.getRepeticiones());

        }
        return cantidadMaxima;
    }
}
