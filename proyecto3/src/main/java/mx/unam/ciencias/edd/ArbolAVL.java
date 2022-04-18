package mx.unam.ciencias.edd;

/**
 * <p>Clase para árboles AVL.</p>
 *
 * <p>Un árbol AVL cumple que para cada uno de sus vértices, la diferencia entre
 * la áltura de sus subárboles izquierdo y derecho está entre -1 y 1.</p>
 */
public class ArbolAVL<T extends Comparable<T>>
        extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class VerticeAVL extends Vertice {

        /** La altura del vértice. */
        public int altura;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeAVL(T elemento) {
            super(elemento);
        }

        /**
         * Regresa la altura del vértice.
         * @return la altura del vértice.
         */
        @Override public int altura() {
            return this.altura;
        }

        /**
         * Regresa una representación en cadena del vértice AVL.
         * @return una representación en cadena del vértice AVL.
         */
        @Override public String toString() {
            return elemento + " " + altura + "/" + getBalance(this);
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeAVL}, su elemento es igual al elemento de éste
         *         vértice, los descendientes de ambos son recursivamente
         *         iguales, y las alturas son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked") VerticeAVL vertice = (VerticeAVL)objeto;

            return (altura == vertice.altura && super.equals(objeto));
        }

    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinarioOrdenado}.
     */
    public ArbolAVL() { super(); }

    /**
     * Construye un árbol AVL a partir de una colección. El árbol AVL tiene los
     * mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol AVL.
     */
    public ArbolAVL(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link VerticeAVL}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
        return new VerticeAVL(elemento);
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol girándolo como
     * sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        super.agrega(elemento);
        rebalanceaR((VerticeAVL)ultimoAgregado.padre);

    }

    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y gira el árbol como sea necesario para rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
        VerticeAVL v = (VerticeAVL) super.busca(elemento);
        if(v == null)
            return;
        if(v.hayIzquierdo() && v.hayDerecho())
            v = (VerticeAVL) (intercambiaEliminable(v));
        if(!v.hayIzquierdo() && !v.hayDerecho())
            eliminaHoja(v);
        else masDeUnHijo(v);
        rebalanceaR((VerticeAVL)v.padre);


    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la derecha por los usuarios de la clase, porque
     * se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
                "girar a la izquierda por el " +
                "usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la izquierda por los usuarios de la clase, porque
     * se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
                "girar a la derecha por el " +
                "usuario.");
    }

    private void rebalanceaR(VerticeAVL v){
        VerticeAVL p,q;
        if(v == null)
            return;
        recalculaAltura(v);
        int vBalnce = getBalance(v);

        if(vBalnce == -2){
            q = (VerticeAVL) v.derecho;

            if(getBalance(q) == 1){
                super.giraDerecha(q);
                recalculaAltura(q);
                recalculaAltura((VerticeAVL) (q.padre));
            }
            super.giraIzquierda(v);
            recalculaAltura(v);
        }
        if(vBalnce == 2){
            p = (VerticeAVL) v.izquierdo;
            if(getBalance(p) == -1){

                super.giraIzquierda(p);
                recalculaAltura(p);
                recalculaAltura((VerticeAVL) (p.padre));
            }
            super.giraDerecha(v);
            recalculaAltura(v);
        }
        rebalanceaR((VerticeAVL) (v.padre));


    }

    /**
     * Metodo auxiliar de elilima que actua cuando se tiene a lo mas un hijo en el vertice.
     * @param v
     */
    private void masDeUnHijo(VerticeAVL v){
        if(!v.hayIzquierdo())
            eliminaCasoIzquierdo(v);
        else
            eliminaCasoDerecho(v);
    }


    private void recalculaAltura(VerticeAVL v){
        if(v == null)
            return;
        v.altura = Math.max(getAltura((VerticeAVL) v.izquierdo), getAltura((VerticeAVL) v.derecho)) + 1;

    }

    /**
     * Metodo que regresa la altura con el caso incluyente de null.
     * @param v el vertice.
     * @return la altura,-1 en caso de ser null.
     */
    private int getAltura(VerticeAVL v){
        if(v == null)
            return -1 ;
        return v.altura();
    }
    private void eliminaCasoIzquierdo(VerticeAVL v){
        if(raiz == v){
            raiz = raiz.derecho;
            v.derecho.padre = null;
        }else{
            v.derecho.padre = v.padre;
            if(esHijoIzquierdo(v))
                v.padre.izquierdo = v.derecho;
            else
                v.padre.derecho = v.derecho;
        }
        elementos--;
    }

    private void eliminaCasoDerecho(VerticeAVL v){
        if(raiz == v){
            raiz = raiz.izquierdo;
            v.izquierdo.padre = null;
        }else{
            v.izquierdo.padre = v.padre;
            if(esHijoIzquierdo(v))
                v.padre.izquierdo = v.izquierdo;
            else
                v.padre.derecho = v.izquierdo;
        }
        elementos--;
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

    private int getBalance(VerticeAVL v) {
        if (v == null)
            return 0;
        return getAltura((VerticeAVL)v.izquierdo) - getAltura((VerticeAVL)v.derecho);
    }
    private void eliminaHoja(Vertice v){
        if(raiz == v){
            raiz = null;
            ultimoAgregado = null;
        }else if(esHijoIzquierdo(v))
            v.padre.izquierdo = null;
        else
            v.padre.derecho = null;
        elementos--;
    }


}