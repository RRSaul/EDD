package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>Clase para árboles binarios completos.</p>
 *
 * <p>Un árbol binario completo agrega y elimina elementos de tal forma que el
 * árbol siempre es lo más cercano posible a estar lleno.</p>
 */
public class ArbolBinarioCompleto<T> extends ArbolBinario<T> {


    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Cola para recorrer los vértices en BFS. */
        private Cola<Vertice> cola;

        /* Inicializa al iterador. */
        public Iterador() {
            cola = new Cola<Vertice>();
            if (esVacia())
                return;
            cola.mete(raiz);
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override
        public boolean hasNext() {
            return !cola.esVacia();
        }

        /* Regresa el siguiente elemento en orden BFS. */
        @Override
        public T next() {
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            Vertice vertice = cola.saca();
            if (vertice.hayIzquierdo())
                cola.mete(vertice.izquierdo);
            if (vertice.hayDerecho())
                cola.mete(vertice.derecho);
            return vertice.elemento;

        }

    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinario}.
     */
    public ArbolBinarioCompleto() { super(); }

    /**
     * Construye un árbol binario completo a partir de una colección. El árbol
     * binario completo tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario completo.
     */
    public ArbolBinarioCompleto(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Agrega un elemento al árbol binario completo. El nuevo elemento se coloca
     * a la derecha del último nivel, o a la izquierda de un nuevo nivel.
     * @param elemento el elemento a agregar al árbol.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void agrega(T elemento) {
        if(elemento == null)
            throw new IllegalArgumentException();
        if(esVacia()) {
            raiz = nuevoVertice(elemento);
            elementos = 1;
            return;
        }
        Vertice aux = nuevoVertice(elemento);
        Cola<Vertice> cola = new Cola<Vertice>();
        cola.mete(raiz);
        while (!cola.esVacia()) {
            Vertice vertice = cola.saca();
            if (vertice.hayIzquierdo()) {
                cola.mete(vertice.izquierdo);
            }else {
                vertice.izquierdo = aux;
                aux.padre = vertice;
                elementos ++;
                break;
            }
            if (vertice.hayDerecho()) {
                cola.mete(vertice.derecho);
            }else {
                vertice.derecho = aux;
                aux.padre = vertice;
                elementos++;
                break;
            }
        }
    }

    /**
     * Elimina un elemento del árbol. El elemento a eliminar cambia lugares con
     * el último elemento del árbol al recorrerlo por BFS, y entonces es
     * eliminado.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        Vertice v = vertice(busca(elemento));
        if(esVacia() || elemento == null || v == null)
            return;
        elementos--;
        if (elementos==0) {
            raiz = null;
            return;
        }
        Vertice ultimo = ultimoVertice();
        if(ultimo.padre.izquierdo == ultimo){
            v.elemento = ultimo.elemento;
            ultimo.padre.izquierdo = null;
            ultimo.padre = null;
            return;
        }
        if(ultimo.padre.derecho == ultimo){
            v.elemento = ultimo.elemento;
            ultimo.padre.derecho = null;
            ultimo.padre = null;
        }


    }


    /**
     * Regresa la altura del árbol. La altura de un árbol binario completo
     * siempre es ⌊log<sub>2</sub><em>n</em>⌋.
     * @return la altura del árbol.
     */
    @Override public int altura() {
        return super.altura();
    }

    /**
     * Realiza un recorrido BFS en el árbol, ejecutando la acción recibida en
     * cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void bfs(AccionVerticeArbolBinario<T> accion) {if(esVacia())
        return;
        Cola<Vertice> c = new Cola<>();
        c.mete(raiz);
        while(!c.esVacia()){
            Vertice v = c.saca();
            accion.actua(v);
            if(v.izquierdo != null)
                c.mete(v.izquierdo);
            if(v.derecho != null)
                c.mete(v.derecho);
        }
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden BFS.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    private Vertice ultimoVertice(){
        Cola<Vertice> c = new Cola<>();
        Vertice v = raiz;
        c.mete(raiz);
        while(!c.esVacia()){
            v = c.saca();
            if(v.hayIzquierdo())
                c.mete(v.izquierdo);
            if(v.hayDerecho())
                c.mete(v.derecho);
        }
        return v;
    }
}
