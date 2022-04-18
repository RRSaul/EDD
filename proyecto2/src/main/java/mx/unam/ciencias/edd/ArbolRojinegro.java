package mx.unam.ciencias.edd;

/**
 * Clase para árboles rojinegros. Un árbol rojinegro cumple las siguientes
 * propiedades:
 *
 * <ol>
 *  <li>Todos los vértices son NEGROS o ROJOS.</li>
 *  <li>La raíz es NEGRA.</li>
 *  <li>Todas las hojas (<code>null</code>) son NEGRAS (al igual que la raíz).</li>
 *  <li>Un vértice ROJO siempre tiene dos hijos NEGROS.</li>
 *  <li>Todo camino de un vértice a alguna de sus hojas descendientes tiene el
 *      mismo número de vértices NEGROS.</li>
 * </ol>
 *
 * Los árboles rojinegros se autobalancean.
 */
public class ArbolRojinegro<T extends Comparable<T>>
    extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class VerticeRojinegro extends Vertice {

        /** El color del vértice. */
        public Color color;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeRojinegro(T elemento) {
            super(elemento);
            color = Color.NINGUNO;
        }

        /**
         * Regresa una representación en cadena del vértice rojinegro.
         * @return una representación en cadena del vértice rojinegro.
         */
        public String toString() {
            if(color == Color.ROJO){
                return "R{" + elemento.toString() + "}";
            }else
                return "N{" + elemento.toString() + "}";
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeRojinegro}, su elemento es igual al elemento de
         *         éste vértice, los descendientes de ambos son recursivamente
         *         iguales, y los colores son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked")
            VerticeRojinegro vertice = (VerticeRojinegro)objeto;
            return (color == vertice.color && super.equals(objeto));
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinarioOrdenado}.
     */
    public ArbolRojinegro() {
        super();
    }

    /**
     * Construye un árbol rojinegro a partir de una colección. El árbol
     * rojinegro tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        rojinegro.
     */
    public ArbolRojinegro(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link
     * VerticeRojinegro}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice rojinegro con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
        return new VerticeRojinegro(elemento);
    }

    /**
     * Regresa el color del vértice rojinegro.
     * @param vertice el vértice del que queremos el color.
     * @return el color del vértice rojinegro.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         VerticeRojinegro}.
     */
    public Color getColor(VerticeArbolBinario<T> vertice) {
        return ((VerticeRojinegro)(vertice)).color;
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol recoloreando
     * vértices y girando el árbol como sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        super.agrega(elemento);
        VerticeRojinegro v = (VerticeRojinegro)getUltimoVerticeAgregado();
        v.color = Color.ROJO;
        rebalanceaR(v);
    }

    /**
     * Metodo axiliar de rebalanceo de un arbol rojinegro.
     * @param vertice el vertice a rebalancear;
     */
    private void rebalanceaR(VerticeRojinegro vertice){
        VerticeRojinegro p,a,t,u;
        if(!vertice.hayPadre()){
            vertice.color = Color.NEGRO;
            return;
        }
        p = (VerticeRojinegro)vertice.padre;
        if(esNegro(p)){
            return;
        }else {
            a = (VerticeRojinegro)p.padre;
            if(esHijoIzquierdo(p)) {
                t = (VerticeRojinegro)a.derecho;
            }else{
                t = (VerticeRojinegro)a.izquierdo;
            }
        }
        if(esRojo(t)){
            t.color = Color.NEGRO;
            p.color = Color.NEGRO;
            a.color = Color.ROJO;
            rebalanceaR(a);
            return;
        }
        if(esHijoIzquierdo(vertice) != esHijoIzquierdo(p)){
            if(esHijoIzquierdo(p)) {
                super.giraIzquierda(p);
            }else
                super.giraDerecha(p);
            u = p;
            p = vertice;
            vertice = u;
        }
        p.color = Color.NEGRO;
        a.color = Color.ROJO;
        if(esHijoIzquierdo(vertice))
            super.giraDerecha(a);
        else
            super.giraIzquierda(a);
    }
    private boolean esRojo(VerticeRojinegro vertice){
        return (vertice != null && vertice.color == Color.ROJO);
    }
    private boolean esNegro(VerticeRojinegro vertice){
        return (vertice == null ||vertice != null && vertice.color == Color.NEGRO);
    }


    /**
     * Metodo que regresa i un vertice es hijo izquierdo.
     * @param vertice
     * @return true si es hijo izquierdo.
     */
    private boolean esHijoIzquierdo(Vertice vertice){
        return vertice.hayPadre() && vertice.padre.izquierdo == vertice;
    }
    /**
     * Metodo que regresa i un vertice es hijo derecho.
     * @param vertice
     * @return true si es hijo derecho.
     */
    private boolean esHijoDerecho(Vertice vertice){
        return vertice.hayPadre() && vertice.padre.derecho == vertice;
    }

    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y recolorea y gira el árbol como sea necesario para
     * rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
        VerticeRojinegro u, h;
        VerticeRojinegro v = (VerticeRojinegro) (super.busca(elemento));
        if (v == null)
            return;
        elementos--;
        if (v.hayIzquierdo() && v.hayDerecho())
            v = (VerticeRojinegro) (intercambiaEliminable(v));
        if (!v.hayIzquierdo() && !v.hayDerecho()) {
            u = (VerticeRojinegro) (nuevoVertice(null));
            u.color = Color.NEGRO;
            u.padre = v;
            v.izquierdo = u;
        }
        if (v.hayIzquierdo()) {
            h = (VerticeRojinegro)v.izquierdo;
        } else
            h = (VerticeRojinegro)v.derecho;
        eliminaVertice(v);
        if (esRojo(h)) {
            h.color = Color.NEGRO;
            return;
        }
        if (esNegro(h) && esNegro(v))
            rebalancearElimina(h);
        eliminaVertice(h);
    }

    /**
     * Metodo auxiliar que rebalancea despues de eliminar.
     * @param vertice el vertice a rebalancear.
     */
    private void rebalancearElimina(VerticeRojinegro vertice){
        VerticeRojinegro p,h,hi,hd;
        if(!vertice.hayPadre())
            return;
        p = (VerticeRojinegro) vertice.padre;
        if(esHijoIzquierdo(vertice)){
            h = (VerticeRojinegro)p.derecho;
        }else
            h = (VerticeRojinegro)p.izquierdo;
        if(esRojo(h)){
            p.color = Color.ROJO;
            h.color = Color.NEGRO;
            if(esHijoIzquierdo(vertice)){
                super.giraIzquierda(p);
                h = (VerticeRojinegro) vertice.padre.derecho;
            }else {
                super.giraDerecha(p);
                h = (VerticeRojinegro) vertice.padre.izquierdo;
            }
        }
        hi = (VerticeRojinegro)(h.izquierdo);
        hd = (VerticeRojinegro)(h.derecho);
        if(esNegro(p) && esNegro(h) && esNegro(hi) && esNegro(hd)){
            h.color = Color.ROJO;
            rebalancearElimina(p);
            return;
        }else
        if(esNegro(h) && esNegro(hi) && esNegro(hd) && esRojo(p)){
            h.color = Color.ROJO;
            p.color = Color.NEGRO;
            return;
        }
        if((esHijoIzquierdo(vertice) && esRojo(hi) && esNegro(hd)) ||
                (esHijoDerecho(vertice) && esNegro(hi) && esRojo(hd))){
            h.color = Color.ROJO;
            if(esRojo(hi))
                hi.color = Color.NEGRO;
            else
                hd.color = Color.NEGRO;
            if(esHijoIzquierdo(vertice)){
                super.giraDerecha(h);
                h = (VerticeRojinegro)(vertice.padre.derecho);
            }else{
                super.giraIzquierda(h);
                h = (VerticeRojinegro)(vertice.padre.izquierdo);
            }
        }
        hi = (VerticeRojinegro)(h.izquierdo);
        hd = (VerticeRojinegro)(h.derecho);
        h.color = p.color;
        p.color = Color.NEGRO;
        if(esHijoIzquierdo(vertice)){
            hd.color = Color.NEGRO;
            super.giraIzquierda(p);
        }else{
            hi.color = Color.NEGRO;
            super.giraDerecha(p);
        }
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la izquierda por los usuarios de la
     * clase, porque se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                "pueden girar a la izquierda " +
                "por el usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la derecha por los usuarios de la
     * clase, porque se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                "pueden girar a la derecha " +
                "por el usuario.");
    }
}
