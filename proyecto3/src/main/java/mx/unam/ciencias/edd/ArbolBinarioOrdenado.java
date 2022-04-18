package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>Clase para árboles binarios ordenados. Los árboles son genéricos, pero
 * acotados a la interfaz {@link Comparable}.</p>
 *
 * <p>Un árbol instancia de esta clase siempre cumple que:</p>
 * <ul>
 *   <li>Cualquier elemento en el árbol es mayor o igual que todos sus
 *       descendientes por la izquierda.</li>
 *   <li>Cualquier elemento en el árbol es menor o igual que todos sus
 *       descendientes por la derecha.</li>
 * </ul>
 */
public class ArbolBinarioOrdenado<T extends Comparable<T>>
        extends ArbolBinario<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Pila para recorrer los vértices en DFS in-order. */
        private Pila<Vertice> pila;

        /* Inicializa al iterador. */
        public Iterador() {
            pila = new Pila<>();
            Vertice r = raiz;
            while(r != null){
                pila.mete(r);
                r = r.izquierdo;
            }
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            return !pila.esVacia();
        }

        /* Regresa el siguiente elemento en orden DFS in-order. */
        @Override public T next() {
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            Vertice v = pila.saca();
            if(v.hayDerecho()){
                Vertice t = v.derecho;
                while(t != null){
                    pila.mete(t);
                    t = t.izquierdo;
                }
            }
            return v.elemento;
        }
    }

    /**
     * El vértice del último elemento agegado. Este vértice sólo se puede
     * garantizar que existe <em>inmediatamente</em> después de haber agregado
     * un elemento al árbol. Si cualquier operación distinta a agregar sobre el
     * árbol se ejecuta después de haber agregado un elemento, el estado de esta
     * variable es indefinido.
     */
    protected Vertice ultimoAgregado;

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinario}.
     */
    public ArbolBinarioOrdenado() { super(); }

    /**
     * Construye un árbol binario ordenado a partir de una colección. El árbol
     * binario ordenado tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario ordenado.
     */
    public ArbolBinarioOrdenado(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Agrega un nuevo elemento al árbol. El árbol conserva su orden in-order.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        if(elemento == null)
            throw new IllegalArgumentException();
        Vertice v = nuevoVertice(elemento);
        if(esVacia()){
            ultimoAgregado = v;
            raiz = v;
            elementos = 1;
            return;
        }
        agregaR(raiz, elemento);
    }

    private void agregaR(Vertice v, T e){
        if (e.compareTo(v.elemento) > 0 && !v.hayDerecho()){
            Vertice t = nuevoVertice(e);
            v.derecho = t;
            t.padre = v;
            elementos++;
            ultimoAgregado = t;
            return;
        }
        if (e.compareTo(v.elemento) <= 0 && !v.hayIzquierdo()){
            Vertice t = nuevoVertice(e);
            v.izquierdo = t;
            t.padre = v;
            ultimoAgregado = t;
            elementos++;
            return;
        }
        if (e.compareTo(v.elemento) > 0)
            agregaR(v.derecho, e);
        if (e.compareTo(v.elemento) <= 0)
            agregaR(v.izquierdo, e);
    }

    /**
     * Elimina un elemento. Si el elemento no está en el árbol, no hace nada; si
     * está varias veces, elimina el primero que encuentre (in-order). El árbol
     * conserva su orden in-order.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        Vertice v = vertice(busca(elemento));
        if (v == null)
            return;
        if (v == raiz && elementos == 1){
            raiz = null;
            elementos = 0;
            return;
        }
        if (!v.hayIzquierdo() && !v.hayDerecho()){
            if(v.padre.izquierdo == v){
                v.padre.izquierdo = null;
                elementos--;
                return;
            }
            if(v.padre.derecho == v){
                v.padre.derecho = null;
                elementos--;
                return;
            }
        }
        if ((v.hayIzquierdo() && !v.hayDerecho()) ||
                (!v.hayIzquierdo() && v.hayDerecho())){
            eliminaVertice(v);
            elementos--;
            return;
        }
        if(v.hayIzquierdo() && v.hayDerecho()){
            eliminaVertice(intercambiaEliminable(v));
            elementos--;
        }
    }

    /**
     * Intercambia el elemento de un vértice con dos hijos distintos de
     * <code>null</code> con el elemento de un descendiente que tenga a lo más
     * un hijo.
     * @param vertice un vértice con dos hijos distintos de <code>null</code>.
     * @return el vértice descendiente con el que vértice recibido se
     *         intercambió. El vértice regresado tiene a lo más un hijo distinto
     *         de <code>null</code>.
     */
    protected Vertice intercambiaEliminable(Vertice vertice) {
        if (!vertice.hayIzquierdo() || !vertice.hayDerecho())
            return null;
        Vertice v = maximoEnSubArbol(vertice.izquierdo);
        T e = vertice.elemento;
        vertice.elemento = v.elemento;
        v.elemento = e;
        return v;
    }
    private Vertice maximoEnSubArbol(Vertice v){
        return v.derecho == null ? v : maximoEnSubArbol(v.derecho);
    }

    /**
     * Elimina un vértice que a lo más tiene un hijo distinto de
     * <code>null</code> subiendo ese hijo (si existe).
     * @param vertice el vértice a eliminar; debe tener a lo más un hijo
     *                distinto de <code>null</code>.
     */
    protected void eliminaVertice(Vertice vertice) {
        Vertice p = vertice.padre;
        Vertice u = null;
        if (vertice.hayDerecho() && vertice.hayIzquierdo())
            return;
        if (vertice.hayDerecho())
            u = vertice.derecho;
        if (vertice.hayIzquierdo())
            u = vertice.izquierdo;
        if (vertice.hayPadre()){
            if (vertice.padre.izquierdo == vertice)
                vertice.padre.izquierdo = u;
            if (vertice.padre.derecho == vertice)
                vertice.padre.derecho = u;
        }
        if (!vertice.hayPadre())
            raiz = u;
        if (u != null)
            u.padre = p;
    }

    /**
     * Busca un elemento en el árbol recorriéndolo in-order. Si lo encuentra,
     * regresa el vértice que lo contiene; si no, regresa <code>null</code>.
     * @param elemento el elemento a buscar.
     * @return un vértice que contiene al elemento buscado si lo
     *         encuentra; <code>null</code> en otro caso.
     */
    @Override public VerticeArbolBinario<T> busca(T elemento) {
        if (esVacia())
            return null;
        return buscaR(raiz,elemento);

    }
    private Vertice buscaR(Vertice v, T elemento){
        if(v.elemento.equals(elemento))
            return v;
        if(v.elemento.compareTo(elemento) > 0 && v.hayIzquierdo())
            return buscaR(v.izquierdo, elemento);
        if (v.elemento.compareTo(elemento) < 0 && v.hayDerecho())
            return buscaR(v.derecho, elemento);
        return null;
    }

    /**
     * Regresa el vértice que contiene el último elemento agregado al
     * árbol. Este método sólo se puede garantizar que funcione
     * <em>inmediatamente</em> después de haber invocado al método {@link
     * agrega}. Si cualquier operación distinta a agregar sobre el árbol se
     * ejecuta después de haber agregado un elemento, el comportamiento de este
     * método es indefinido.
     * @return el vértice que contiene el último elemento agregado al árbol, si
     *         el método es invocado inmediatamente después de agregar un
     *         elemento al árbol.
     */
    public VerticeArbolBinario<T> getUltimoVerticeAgregado() {
        return ultimoAgregado;
    }

    /**
     * Gira el árbol a la derecha sobre el vértice recibido. Si el vértice no
     * tiene hijo izquierdo, el método no hace nada.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraDerecha(VerticeArbolBinario<T> vertice) {
        Vertice v = vertice(vertice), p, sA, i;
        boolean izq;
        if (!v.hayIzquierdo())
            return;
        if (v == raiz){
            raiz = v.izquierdo;
            p = v.padre;
            sA = v.izquierdo.derecho;
            i = v.izquierdo;
            v.padre = i;
            v.padre.derecho = v;
            v.izquierdo = sA;
            v.padre.padre = p;
            if (v.hayIzquierdo())
                v.izquierdo.padre = v;
            return;
        }
        p = v.padre;
        sA = v.izquierdo.derecho;
        i = v.izquierdo;
        izq = v == v.padre.izquierdo;
        v.padre = i;
        v.padre.derecho = v;
        v.izquierdo = sA;
        v.padre.padre = p;
        if (izq)
            p.izquierdo = v.padre;
        else
            p.derecho = v.padre;
        if (v.hayIzquierdo())
            v.izquierdo.padre = v;
    }

    /**
     * Gira el árbol a la izquierda sobre el vértice recibido. Si el vértice no
     * tiene hijo derecho, el método no hace nada.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        Vertice v = vertice(vertice), p, sA, d;
        boolean izq;
        if (!v.hayDerecho())
            return;
        if (v == raiz){
            raiz = v.derecho;
            p = v.padre;
            sA = v.derecho.izquierdo;
            d = v.derecho;
            v.padre = d;
            v.padre.izquierdo = v;
            v.derecho = sA;
            v.padre.padre = p;
            if (v.derecho != null)
                v.derecho.padre = v;
            return;
        }
        izq = v == v.padre.izquierdo;
        p = v.padre;
        sA = v.derecho.izquierdo;
        d = v.derecho;
        v.padre = d;
        v.padre.izquierdo = v;
        v.derecho = sA;
        v.padre.padre = p;
        if (v.hayDerecho())
            v.derecho.padre = v;
        if (izq)
            p.izquierdo = v.padre;
        else
            p.derecho = v.padre;
    }
    /**
     * Realiza un recorrido DFS <em>pre-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsPreOrder(AccionVerticeArbolBinario<T> accion) {
        if (esVacia())
            return;
        dfsPreOrderR(accion, raiz);
    }

    private void dfsPreOrderR(AccionVerticeArbolBinario<T> a, Vertice v){
        a.actua(v);
        if(v.hayIzquierdo())
            dfsPreOrderR(a, v.izquierdo);
        if(v.hayDerecho())
            dfsPreOrderR(a, v.derecho);
    }

    /**
     * Realiza un recorrido DFS <em>in-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsInOrder(AccionVerticeArbolBinario<T> accion) {
        if(esVacia())
            return;
        dfsInOrderR(accion, raiz);
    }

    private void dfsInOrderR(AccionVerticeArbolBinario<T> a, Vertice v){
        if(v.hayIzquierdo())
            dfsInOrderR(a, v.izquierdo);
        a.actua(v);
        if(v.hayDerecho())
            dfsInOrderR(a, v.derecho);
    }

    /**
     * Realiza un recorrido DFS <em>post-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsPostOrder(AccionVerticeArbolBinario<T> accion) {
        if (esVacia())
            return;
        dfsPostOrderR(accion, raiz);
    }
    private void dfsPostOrderR(AccionVerticeArbolBinario<T> a, Vertice v){
        if(v.hayIzquierdo())
            dfsPostOrderR(a, v.izquierdo);
        if(v.hayDerecho())
            dfsPostOrderR(a, v.derecho);
        a.actua(v);
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}
