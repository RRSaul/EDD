package mx.unam.ciencias.edd;

import java.util.Comparator;

/**
 * Clase para ordenar y buscar arreglos genéricos.
 */
public class Arreglos {
    /* Constructor privado para evitar instanciación. */
    private Arreglos() {}

    /**
     * Ordena el arreglo recibido usando QickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordenar el arreglo.
     */
    public static <T> void
    quickSort(T[] arreglo, Comparator<T> comparador) {
        quickSort(arreglo, 0, arreglo.length - 1, comparador);
    }

    /**
     * Metodo auxiliar para quickSort
     * @param A El arreglo a ordenar.
     * @param p el pivote
     * @param r la longitud del arreglo -1
     * @param comp el comparador.
     * @param <T> el tipo generico.
     */
    private static <T> void quickSort(T[] A, int p, int r, Comparator<T> comp) {
        if (p < r) {
            int q = qsPartition(A, p, r, comp);
            quickSort(A, p, q - 1, comp);
            quickSort(A, q + 1, r, comp);
        }
    }

    /**
     * Metodo encargado de ordenar las particiones del arreglo
     * @param A El arreglo.
     * @param p El pivote.
     * @param r la longitud del arreglo -1.
     * @param comp el comparador
     * @param <T> el tipo generico.
     * @return
     */
    private static <T> int qsPartition(T[] A, int p, int r, Comparator<T> comp) {
        T pivot = A[p];
        int i = r + 1;
        for (int j = r; j > p; j--)
            if (comp.compare(A[j], pivot) >= 0) {
                i -= 1;
                swapElements(A, i, j);
            }
        swapElements(A, i - 1, p);
        return i - 1;
    }

    /**
     * Metodo encargado de intercambiar 2 elementos en un arreglo.
     * @param A El arreglo.
     * @param i Elemento a interambiar.
     * @param j Elemento a intercambiar.
     * @param <T> El tipo generico.
     */
    private static <T> void swapElements(T[] A, int i, int j) {
        T tmp = A[i];
        A[i] = A[j];
        A[j] = tmp;
    }

    /**
     * Ordena el arreglo recibido usando QickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void
    quickSort(T[] arreglo) {
        quickSort(arreglo, (a, b) -> a.compareTo(b));
    }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordernar el arreglo.
     */
    public static <T> void
    selectionSort(T[] arreglo, Comparator<T> comparador) {
        for (int i = 0; i < arreglo.length; i++) {
            int min = i;
            for (int j = i + 1; j < arreglo.length; j++) {
                if (comparador.compare(arreglo[j], arreglo[min]) < 0) {
                    min = j;
                }
            }
            swapElements(arreglo, i, min);
        }
    }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void
    selectionSort(T[] arreglo) {
        selectionSort(arreglo, (a, b) -> a.compareTo(b));
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo dónde buscar.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador para hacer la búsqueda.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T> int
    busquedaBinaria(T[] arreglo, T elemento, Comparator<T> comparador) {
        int izq = 0;
        int der = arreglo.length-1;

        while (izq < der) {
            int mitad = ((izq + der)/2);
            int comp = comparador.compare(elemento, arreglo[mitad]);
            if(comp == 0)
                return mitad;
            else if(comp > 0)
                izq = mitad + 1;
            else
                der = mitad - 1;
        }

        if(der == izq)
            if(comparador.compare(elemento, arreglo[izq]) == 0)
                return izq;
        return -1;
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     * @param elemento el elemento a buscar.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T extends Comparable<T>> int
    busquedaBinaria(T[] arreglo, T elemento) {
        return busquedaBinaria(arreglo, elemento, (a, b) -> a.compareTo(b));
    }
}
