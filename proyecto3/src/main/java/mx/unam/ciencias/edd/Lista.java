package mx.unam.ciencias.edd;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>Clase genérica para listas doblemente ligadas.</p>
 *
 * <p>Las listas nos permiten agregar elementos al inicio o final de la lista,
 * eliminar elementos de la lista, comprobar si un elemento está o no en la
 * lista, y otras operaciones básicas.</p>
 *
 * <p>Las listas no aceptan a <code>null</code> como elemento.</p>
 *
 * @param <T> El tipo de los elementos de la lista.
 */
public class Lista<T> implements Coleccion<T> {

    /* Clase interna privada para nodos. */
    private class Nodo {
        /* El elemento del nodo. */
        public T elemento;
        /* El nodo anterior. */
        public Nodo anterior;
        /* El nodo siguiente. */
        public Nodo siguiente;

        /* Construye un nodo con un elemento. */
        public Nodo(T elemento) {
            this.elemento = elemento;
        }
    }

    /* Clase interna privada para iteradores. */
    private class Iterador implements IteradorLista<T> {
        /* El nodo anterior. */
        public Nodo anterior;
        /* El nodo siguiente. */
        public Nodo siguiente;

        /* Construye un nuevo iterador. */
        public Iterador() {
            anterior = null;
            siguiente = cabeza;
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            return siguiente != null;
        }

        /* Nos da el elemento siguiente. */
        @Override public T next() {
            if(siguiente == null)
                throw new NoSuchElementException("El siguiente del iterador " +
                        "es vacío.");
            anterior = siguiente;
            siguiente = siguiente.siguiente;
            return anterior.elemento;
        }

        /* Nos dice si hay un elemento anterior. */
        @Override public boolean hasPrevious() {
            return anterior != null;
        }

        /* Nos da el elemento anterior. */
        @Override public T previous() {
            if(anterior == null)
                throw new NoSuchElementException("El anterior del iterador " +
                        "es vacío.");
            siguiente = anterior;
            anterior = anterior.anterior;
            return siguiente.elemento;
        }

        /* Mueve el iterador al inicio de la lista. */
        @Override public void start() {
            anterior = null;
            siguiente = cabeza;
        }

        /* Mueve el iterador al final de la lista. */
        @Override public void end() {
            siguiente = null;
            anterior = rabo;
        }
    }

    /* Primer elemento de la lista. */
    private Nodo cabeza;
    /* Último elemento de la lista. */
    private Nodo rabo;
    /* Número de elementos en la lista. */
    private int longitud;

    /**
     * Regresa la longitud de la lista. El método es idéntico a {@link
     * #getElementos}.
     * @return la longitud de la lista, el número de elementos que contiene.
     */
    public int getLongitud() {
        return  longitud;
    }

    /**
     * Regresa el número elementos en la lista. El método es idéntico a {@link
     * #getLongitud}.
     * @return el número elementos en la lista.
     */
    @Override public int getElementos() {
        return  longitud;
    }

    /**
     * Nos dice si la lista es vacía.
     * @return <code>true</code> si la lista es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
        return(cabeza == null);
    }

    /**
     * Agrega un elemento a la lista. Si la lista no tiene elementos, el
     * elemento a agregar será el primero y último. El método es idéntico a
     * {@link #agregaFinal}.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void agrega(T elemento) {
        if(elemento == null)
            throw new IllegalArgumentException("El elemento presentado es nullo.");

        Nodo nodo = new Nodo(elemento);
        longitud++;

        if(esVacia())
            cabeza = rabo = nodo;
        else {
            rabo.siguiente = nodo;
            nodo.anterior = rabo;
            rabo = nodo;
        }
    }

    /**
     * Agrega un elemento al final de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaFinal(T elemento) {
        if(elemento == null)
            throw new IllegalArgumentException("El elemento presentado es nullo.");

        Nodo nodo = new Nodo(elemento);
        longitud++;

        if(esVacia())
            cabeza = rabo = nodo;
        else {
            rabo.siguiente = nodo;
            nodo.anterior = rabo;
            rabo = nodo;
        }
    }

    /**
     * Agrega un elemento al inicio de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaInicio(T elemento) {
        if(elemento == null)
            throw new IllegalArgumentException("El elemento presentado es nullo.");

        Nodo nodo = new Nodo(elemento);
        longitud++;

        if(esVacia())
            cabeza = rabo = nodo;
        else {
            cabeza.anterior = nodo;
            nodo.siguiente = cabeza;
            cabeza = nodo;
        }
    }

    /**
     * Inserta un elemento en un índice explícito.
     *
     * Si el índice es menor o igual que cero, el elemento se agrega al inicio
     * de la lista. Si el índice es mayor o igual que el número de elementos en
     * la lista, el elemento se agrega al fina de la misma. En otro caso,
     * después de mandar llamar el método, el elemento tendrá el índice que se
     * especifica en la lista.
     * @param i el índice dónde insertar el elemento. Si es menor que 0 el
     *          elemento se agrega al inicio de la lista, y si es mayor o igual
     *          que el número de elementos en la lista se agrega al final.
     * @param elemento el elemento a insertar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void inserta(int i, T elemento) {
        if (elemento == null)
            throw new IllegalArgumentException("El elemento es null.");

        if (i <= 0)
            agregaInicio(elemento);
        else if (i >= longitud)
            agregaFinal(elemento);
        else {
            longitud++;
            Nodo auxiliar = getNodos(cabeza, i, 1);
            Nodo nodo = new Nodo(elemento);
            nodo.anterior = auxiliar;
            nodo.siguiente = auxiliar.siguiente;
            nodo.siguiente.anterior = nodo;
            nodo.anterior.siguiente = nodo;
        }
    }

    /**
     * Elimina un elemento de la lista. Si el elemento no está contenido en la
     * lista, el método no la modifica.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        if (elemento != null) {
            Nodo nodo = this.elBuscadormagicorecursivo(cabeza,elemento);
            if (nodo == null)
                return;
            else if (cabeza == rabo) {
                cabeza = rabo = null;
            } else if (cabeza == nodo) {
                cabeza = cabeza.siguiente;
                cabeza.anterior = null;
            } else if (rabo == nodo) {
                rabo = rabo.anterior;
                rabo.siguiente = null;
            } else {
                nodo.siguiente.anterior = nodo.anterior;
                nodo.anterior.siguiente = nodo.siguiente;
            }
            longitud--;
        }
    }
    /**
     * Elimina el primer elemento de la lista y lo regresa.
     * @return el primer elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaPrimero() {
        if(esVacia())
            throw new NoSuchElementException("La lista es vacía.");
        T s = cabeza.elemento;
        eliminaNodo(cabeza);
        return s;
    }

    /**
     * Elimina el último elemento de la lista y lo regresa.
     * @return el último elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaUltimo() {
        if(esVacia())
            throw new NoSuchElementException("La lista es vacía.");
        T s = rabo.elemento;
        eliminaNodo(rabo);
        return s;
    }
    /**
     * Metodo que elimina el nodo dado.
     * @param nodo nodo por eliminar.
     */
    private void eliminaNodo(Nodo nodo) {
        longitud--;

        if(cabeza == rabo) {
            cabeza = rabo = null;
        } else if(nodo == cabeza) {
            cabeza = cabeza.siguiente;
            cabeza.anterior = null;
        } else if(nodo == rabo) {
            rabo = rabo.anterior;
            rabo.siguiente = null;
        } else {
            nodo.anterior.siguiente = nodo.siguiente;
            nodo.siguiente.anterior = nodo.anterior;
        }
    }

    /**
     * Nos dice si un elemento está en la lista.
     * @param elemento el elemento que queremos saber si está en la lista.
     * @return <code>true</code> si <code>elemento</code> está en la lista,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        return this.elBuscadormagicorecursivo(cabeza, elemento) != null;
    }
    /**
     * Regres el nodo de la lista que contiene al elemento dado, si no existe regresa null.
     * @param scan el nodo actulizable que recorre la lista.
     * @param elemento el elemento que buscamos.
     * @return el nodo del elemento que queremos.
     */
    private Nodo elBuscadormagicorecursivo(Nodo scan, T elemento) {
        if (scan == null || scan.elemento.equals(elemento))
            return scan;
        return elBuscadormagicorecursivo(scan.siguiente, elemento);
    }

    /**
     * Regresa la reversa de la lista.
     * @return una nueva lista que es la reversa la que manda llamar el método.
     */
    public Lista<T> reversa() {
        Lista<T> lista = new Lista<>();
        Nodo nodo = rabo;

        while(nodo != null) {
            lista.agregaFinal(nodo.elemento);
            nodo = nodo.anterior;
        }

        return lista;
    }
    /**
     * Regresa una copia de la lista. La copia tiene los mismos elementos que la
     * lista que manda llamar el método, en el mismo orden.
     * @return una copiad de la lista.
     */
    public Lista<T> copia() {
        Lista<T> lista = new Lista<>();
        Nodo nodo = cabeza;

        while(nodo != null) {
            lista.agregaFinal(nodo.elemento);
            nodo = nodo.siguiente;
        }

        return lista;
    }

    /**
     * Limpia la lista de elementos, dejándola vacía.
     */
    @Override public void limpia() {
        cabeza = rabo = null;
        longitud = 0;
    }

    /**
     * Regresa el primer elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getPrimero() {
        if(esVacia())
            throw new NoSuchElementException("La lista es vacía.");
        return cabeza.elemento;
    }
    /**
     * Regresa el último elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getUltimo() {
        if (esVacia())
            throw new NoSuchElementException("La lista es vacía.");
        return rabo.elemento;
    }

    /**
     *Recorre la lista en hasta llegar al nodo con el indice i.
     * @param nodo el nodo desde donde iniciamos a recorrer la lista.
     * @param i el indice del nodo que queremos.
     * @param j contador actulizable para el metodo.
     * @return el nodo con indice i.
     */
    private Nodo getNodos(Nodo nodo, int i, int j) {
        if (i == j)
            return nodo;
        return getNodos(nodo.siguiente, i, ++j);
    }

    /**
     * Regresa el <em>i</em>-ésimo elemento de la lista.
     * @param i el índice del elemento que queremos.
     * @return el <em>i</em>-ésimo elemento de la lista.
     * @throws ExcepcionIndiceInvalido si <em>i</em> es menor que cero o mayor o
     *         igual que el número de elementos en la lista.
     */
    public T get(int i) {
        if(i < 0 || i >= longitud)
            throw new ExcepcionIndiceInvalido("El índice es inválido.");;
        Nodo nodo = getNodos(cabeza, i, 0);
        return nodo.elemento;
    }

    /**
     * Regresa el índice del elemento recibido en la lista.
     * @param elemento el elemento del que se busca el índice.
     * @return el índice del elemento recibido en la lista, o -1 si el elemento
     *         no está contenido en la lista.
     */
    public int indiceDe(T elemento) {
        Nodo nodo = cabeza;
        for(int i = 0; nodo != null; i++) {
            if(nodo.elemento.equals(elemento))
                return i;
            nodo = nodo.siguiente;
        }

        return -1;
    }
    /**
     * Regresa una representación en cadena de la lista.
     * @return una representación en cadena de la lista.
     */
    @Override public String toString() {
        StringBuilder s = new StringBuilder("[");
        Nodo nodo = cabeza;

        while(nodo != null) {
            s.append(nodo.elemento);
            nodo = nodo.siguiente;

            if(nodo != null)
                s.append(", ");
        }

        s.append("]");
        return s.toString();
    }

    /**
     * Nos dice si la lista es igual al objeto recibido.
     * @param objeto el objeto con el que hay que comparar.
     * @return <code>true</code> si la lista es igual al objeto recibido;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {

        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") Lista<T> lista = (Lista<T>)objeto;
        if(lista.getLongitud() != longitud)
            return false;
        Nodo n = lista.cabeza;
        Nodo m = cabeza;
        while(n != null) {
            if(n.elemento.equals(m.elemento)) {
                n = n.siguiente;
                m = m.siguiente;
            } else
                return false;
        }

        return true;
    }

    /**
     * Regresa un iterador para recorrer la lista en una dirección.
     * @return un iterador para recorrer la lista en una dirección.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Regresa un iterador para recorrer la lista en ambas direcciones.
     * @return un iterador para recorrer la lista en ambas direcciones.
     */
    public IteradorLista<T> iteradorLista() {
        return new Iterador();
    }

    /**
     * Regresa una copia de la lista, pero ordenada. Para poder hacer el
     * ordenamiento, el método necesita una instancia de {@link Comparator} para
     * poder comparar los elementos de la lista.
     * @param comparador el comparador que la lista usará para hacer el
     *                   ordenamiento.
     * @return una copia de la lista, pero ordenada.
     */
    public Lista<T> mergeSort(Comparator<T> comparador) {
        if (this.getLongitud() < 2) {
            return this.copia();
        }
        Lista<T> leftHalf = new Lista<>();
        Lista<T> rigthHalf = new Lista<>();

        int half = this.getLongitud() / 2;
        int elementsCount = 0;

        Nodo nodo = this.cabeza;
        while (nodo != null) {
            if (elementsCount < half) { leftHalf.agregaFinal(nodo.elemento); }
            else { rigthHalf.agregaFinal(nodo.elemento); }
            elementsCount += 1;
            nodo = nodo.siguiente;
        }

        leftHalf = leftHalf.mergeSort(comparador);
        rigthHalf = rigthHalf.mergeSort(comparador);

        Lista<T> newList = mix(leftHalf, rigthHalf, comparador);
        return newList;
    }

    /**
     * Dadas dos listas ordenadas, l y r, regresa una nueva lista ordenada
     * resultado de unir ambas.
     * @param l lista ordenada (con al menos un elemento).
     * @param r lista ordenada (con al menos un elemento).
     * @param comparador el comparador que la lista usará para hacer el ordenamiento.
     * @return una nueva lista ordenada.
     */
    private Lista <T> mix(Lista<T> l, Lista<T> r, Comparator<T> comparador) {
        Lista<T> newList = new Lista<>();
        Nodo nodoA = l.cabeza;
        Nodo nodoB = r.cabeza;
        while(nodoA != null && nodoB != null) {
            if(comparador.compare(nodoA.elemento, nodoB.elemento) <= 0) {
                newList.agregaFinal(nodoA.elemento);
                nodoA = nodoA.siguiente;
            } else {
                newList.agregaFinal(nodoB.elemento);
                nodoB = nodoB.siguiente;
            }
        }
        if (nodoA == null) {
            while(nodoB != null) {
                newList.agregaFinal(nodoB.elemento);
                nodoB = nodoB.siguiente;
            }
        } else {
            while(nodoA != null) {
                newList.agregaFinal(nodoA.elemento);
                nodoA = nodoA.siguiente;
            }
        }
        return newList;
    }

    /**
     * Regresa una copia de la lista recibida, pero ordenada. La lista recibida
     * tiene que contener nada más elementos que implementan la interfaz {@link
     * Comparable}.
     * @param <T> tipo del que puede ser la lista.
     * @param lista la lista que se ordenará.
     * @return una copia de la lista recibida, pero ordenada.
     */
    public static <T extends Comparable<T>>
    Lista<T> mergeSort(Lista<T> lista) {
        return lista.mergeSort((a, b) -> a.compareTo(b));
    }

    /**
     * Busca un elemento en la lista ordenada, usando el comparador recibido. El
     * método supone que la lista está ordenada usando el mismo comparador.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador con el que la lista está ordenada.
     * @return <code>true</code> si el elemento está contenido en la lista,
     *         <code>false</code> en otro caso.
     */
    public boolean busquedaLineal(T elemento, Comparator<T> comparador) {
        for(Iterator<T> i = iterator(); i.hasNext();) {
            T e = i.next();
            if (e.equals(elemento)) {
                return true;
            }
        }
        return false;
    }



    /**
     * Busca un elemento en una lista ordenada. La lista recibida tiene que
     * contener nada más elementos que implementan la interfaz {@link
     * Comparable}, y se da por hecho que está ordenada.
     * @param <T> tipo del que puede ser la lista.
     * @param lista la lista donde se buscará.
     * @param elemento el elemento a buscar.
     * @return <code>true</code> si el elemento está contenido en la lista,
     *         <code>false</code> en otro caso.
     */
    public static <T extends Comparable<T>>
    boolean busquedaLineal(Lista<T> lista, T elemento) {
        return lista.busquedaLineal(elemento, (a, b) -> a.compareTo(b));
    }
}
