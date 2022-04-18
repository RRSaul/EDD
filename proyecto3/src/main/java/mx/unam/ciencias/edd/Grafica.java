package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para gráficas. Una gráfica es un conjunto de vértices y aristas, tales
 * que las aristas son un subconjunto del producto cruz de los vértices.
 */
public class Grafica<T> implements Coleccion<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Iterador auxiliar. */
        private Iterator<Vertice> iterador;

        /* Construye un nuevo iterador, auxiliándose de la lista de vértices. */
        public Iterador() {
            iterador = vertices.iterator();
        }

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
            return iterador.hasNext();
        }

        /* Regresa el siguiente elemento. */
        @Override public T next() {
            return iterador.next().elemento;
        }
    }

    /* Clase interna privada para vértices. */
    private class Vertice implements VerticeGrafica<T>,
            ComparableIndexable<Vertice> {

        /* El elemento del vértice. */
        public T elemento;
        /* El color del vértice. */
        public Color color;
        /* La distancia del vértice. */
        public double distancia;
        /* El índice del vértice. */
        public int indice;
        /* La lista de vecinos del vértice. */
        public Lista<Vecino> vecinos;

        /* Crea un nuevo vértice a partir de un elemento. */
        public Vertice(T elemento) {
            this.elemento = elemento;
            this.color = Color.NINGUNO;
            vecinos = new Lista<>();
        }

        /* Regresa el elemento del vértice. */
        @Override public T get() {
            return elemento;
        }

        /* Regresa el grado del vértice. */
        @Override public int getGrado() {
            return vecinos.getElementos();
        }

        /* Regresa el color del vértice. */
        @Override public Color getColor() {
            return color;
        }

        /* Regresa un iterable para los vecinos. */
        @Override public Iterable<? extends VerticeGrafica<T>> vecinos() {
            return vecinos;
        }

        /* Define el índice del vértice. */
        @Override public void setIndice(int indice) {
            this.indice = indice;
        }

        /* Regresa el índice del vértice. */
        @Override public int getIndice() {
            return indice;
        }

        /* Compara dos vértices por distancia. */
        @Override public int compareTo(Vertice vertice) {
            return doubleCompare(this.distancia,vertice.distancia);
        }
    }

    /* Clase interna privada para vértices vecinos. */
    private class Vecino implements VerticeGrafica<T> {

        /* El vértice vecino. */
        public Vertice vecino;
        /* El peso de la arista conectando al vértice con su vértice vecino. */
        public double peso;

        /* Construye un nuevo vecino con el vértice recibido como vecino y el
         * peso especificado. */
        public Vecino(Vertice vecino, double peso) {
            this.vecino = vecino;
            this.peso = peso;
        }

        /* Regresa el elemento del vecino. */
        @Override public T get() {
            return this.vecino.elemento;
        }

        /* Regresa el grado del vecino. */
        @Override public int getGrado() {
            return this.vecino.getGrado();
        }

        /* Regresa el color del vecino. */
        @Override public Color getColor() {
            return this.vecino.getColor();
        }

        /* Regresa un iterable para los vecinos del vecino. */
        @Override public Iterable<? extends VerticeGrafica<T>> vecinos() {
            return this.vecino.vecinos();
        }
    }

    /* Interface para poder usar lambdas al buscar el elemento que sigue al
     * reconstruir un camino. */
    @FunctionalInterface
    private interface BuscadorCamino {
        /* Regresa true si el vértice se sigue del vecino. */
        public boolean seSiguen(Grafica.Vertice v, Grafica.Vecino a);
    }

    /* Vértices. */
    private Lista<Vertice> vertices;
    /* Número de aristas. */
    private int aristas;

    /**
     * Constructor único.
     */
    public Grafica() {
        this.vertices = new Lista<Vertice>();
    }

    /**
     * Metodo encargado de comparar doubles segun las especificaciones del libro.
     * @param a el primer double a comparar
     * @param b el segundo doble a comparar
     * @return -1, 0, 1 dependiendo del resultado.
     */
    private int doubleCompare(double a, double b){
        if(a != -1 && (b == -1 || a < b)) { return -1; }
        if( b!= -1 && (a == -1 || a > b)) { return 1; }
        return 0;
    }

    /**
     * Metodo encargado de sumar doubles seggun las especificaciones del libro.
     * @param a el primer double a sumar
     * @param b el segundo double a sumar
     * @return la suma de ambos doubles, si hay un infinito en la suma el resultado es infinito.
     */
    private double doubleAd(double a, double b){
        return (a==-1 || b==-1) ? -1 : a + b;


    }

    /**
     * Regresa el número de elementos en la gráfica. El número de elementos es
     * igual al número de vértices.
     * @return el número de elementos en la gráfica.
     */
    @Override public int getElementos() {
        return vertices.getElementos();
    }

    /**
     * Regresa el número de aristas.
     * @return el número de aristas.
     */
    public int getAristas() {
        return aristas;
    }

    /**
     * Agrega un nuevo elemento a la gráfica.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si el elemento ya había sido agregado a
     *         la gráfica.
     */
    @Override public void agrega(T elemento) {
        if(elemento == null || contiene(elemento))
            throw new IllegalArgumentException();
        Vertice v = new Vertice(elemento);
        vertices.agrega(v);
    }

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica. El peso de la arista que conecte a los elementos será 1.
     * @param a el primer elemento a conectar.
     * @param b el segundo elemento a conectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, o si a es
     *         igual a b.
     */
    public void conecta(T a, T b) {
        conecta(a,b,1);
    }
    /**
     * Metodo auxiliar para buscar vertices.
     * @param elemento el elemento del vertice a buscar.
     * @return El vertice con el elemento, null en caso de que no este.
     */
    private Vertice buscaVertice(T elemento){
        for(Vertice v: vertices)
            if(v.elemento.equals(elemento))
                return v;
        return null;
    }

    /**
     * Metodo auxiliar para buscar vecino de a
     * @param a, vertice con elemento a
     * @param b, vertice vecino con elemento b
     * @return El Vecino encontrado, null en otro caso
     */
    private Vecino getVecino(Vertice a, Vertice b) {
        for(Vecino vecino: a.vecinos) {
            if(vecino.vecino.equals(b)) { return vecino; }
        }
        return null;
    }


    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica.
     * @param a el primer elemento a conectar.
     * @param b el segundo elemento a conectar.
     * @param peso el peso de la nueva vecino.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, si a es
     *         igual a b, o si el peso es no positivo.
     */
    public void conecta(T a, T b, double peso) {
        if(peso <= 0)
            throw new IllegalArgumentException();
        Vertice va = buscaVertice(a);
        Vertice vb = buscaVertice(b);
        if(va == null || vb == null)
            throw new NoSuchElementException();
        if (sonVecinos(a, b) || a.equals(b))
            throw new IllegalArgumentException();
        aristas ++;
        va.vecinos.agrega(new Vecino(vb,peso));
        vb.vecinos.agrega(new Vecino(va,peso));

    }
    /**
     * Desconecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica y estar conectados entre ellos.
     * @param a el primer elemento a desconectar.
     * @param b el segundo elemento a desconectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados.
     */
    public void desconecta(T a, T b) {
        Vertice v = this.buscaVertice(a);
        Vertice u = this.buscaVertice(b);
        if (v == null || u == null) { throw new NoSuchElementException(); }
        if (!this.sonVecinos(a, b)) { throw new IllegalArgumentException(); }

        this.aristas --;
        u.vecinos.elimina(this.getVecino(u, v));
        v.vecinos.elimina(this.getVecino(v, u));
    }

    /**
     * Nos dice si el elemento está contenido en la gráfica.
     * @return <code>true</code> si el elemento está contenido en la gráfica,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        return buscaVertice(elemento) != null;
    }

    /**
     * Elimina un elemento de la gráfica. El elemento tiene que estar contenido
     * en la gráfica.
     * @param elemento el elemento a eliminar.
     * @throws NoSuchElementException si el elemento no está contenido en la
     *         gráfica.
     */
    @Override public void elimina(T elemento) {
        Vertice v = buscaVertice(elemento);
        if (v == null)
            throw new NoSuchElementException();

        for (Vecino u: v.vecinos) {
            this.desconecta(v.elemento, u.vecino.elemento);
        }
        this.vertices.elimina(v);
    }

    /**
     * Nos dice si dos elementos de la gráfica están conectados. Los elementos
     * deben estar en la gráfica.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return <code>true</code> si a y b son vecinos, <code>false</code> en otro caso.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     */
    public boolean sonVecinos(T a, T b) {
        Vertice v = this.buscaVertice(a);
        Vertice u = this.buscaVertice(b);
        if (v == null || u == null) { throw new NoSuchElementException(); }
        if(this.getVecino(v, u) == null)
            return false;
        return this.getVecino(u, v) != null;
    }

    /**
     * Regresa el peso de la arista que comparten los vértices que contienen a
     * los elementos recibidos.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return el peso de la arista que comparten los vértices que contienen a
     *         los elementos recibidos.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados.
     */
    public double getPeso(T a, T b) {
        Vertice v = this.buscaVertice(a);
        Vertice u = this.buscaVertice(b);
        if (v == null || u == null)
            throw new NoSuchElementException();

        if (!this.sonVecinos(a, b))
            throw new IllegalArgumentException();

        return this.getVecino(v, u).peso;
    }

    /**
     * Define el peso de la arista que comparten los vértices que contienen a
     * los elementos recibidos.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @param peso el nuevo peso de la arista que comparten los vértices que
     *        contienen a los elementos recibidos.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados, o si peso
     *         es menor o igual que cero.
     */
    public void setPeso(T a, T b, double peso) {
        Vertice v = this.buscaVertice(a);
        Vertice u = this.buscaVertice(b);
        if (v == null || u == null)
            throw new NoSuchElementException();

        if (!this.sonVecinos(a, b) || peso <= 0)
            throw new IllegalArgumentException();

        this.getVecino(v, u).peso = peso;
        this.getVecino(u, v).peso = peso;
    }

    /**
     * Regresa el vértice correspondiente el elemento recibido.
     * @param elemento el elemento del que queremos el vértice.
     * @throws NoSuchElementException si elemento no es elemento de la gráfica.
     * @return el vértice correspondiente el elemento recibido.
     */
    public VerticeGrafica<T> vertice(T elemento) {
        Vertice vertice = this.buscaVertice(elemento);
        if (vertice == null)
            throw new NoSuchElementException();
        return vertice;
    }

    /**
     * Define el color del vértice recibido.
     * @param vertice el vértice al que queremos definirle el color.
     * @param color el nuevo color del vértice.
     * @throws IllegalArgumentException si el vértice no es válido.
     */
    public void setColor(VerticeGrafica<T> vertice, Color color) {
        if (vertice == null || ((vertice.getClass() != Vertice.class &&
                vertice.getClass() != Vecino.class))) {
            throw new IllegalArgumentException("El Vértice dado es inválido");
        }

        if (vertice.getClass() == Vecino.class) {

            Vecino v = (Vecino) vertice;
            v.vecino.color = color;
            return;
        }
        Vertice v = (Vertice) vertice;
        v.color = color;
    }


    /**
     * Nos dice si la gráfica es conexa.
     * @return <code>true</code> si la gráfica es conexa, <code>false</code> en
     *         otro caso.
     */
    public boolean esConexa() {
        Vertice v = vertices.getPrimero();
        for(Vertice n : vertices)
            n.color = Color.ROJO;
        Cola<Vertice> c = new Cola<>();
        setColor(v,Color.ROJO);
        c.mete(v);
        while(!c.esVacia()){
            Vertice m = c.saca();
            for(Vecino x : m.vecinos)
                if(x.vecino.color == Color.ROJO){
                    setColor(x.vecino,Color.NEGRO);
                    c.mete(x.vecino);
                }
        }
        for(Vertice n : vertices)
            if(n.color == Color.ROJO)
                return false;
        return true;
    }
    /**
     * Realiza la acción recibida en cada uno de los vértices de la gráfica, en
     * el orden en que fueron agregados.
     * @param accion la acción a realizar.
     */
    public void paraCadaVertice(AccionVerticeGrafica<T> accion) {
        for(Vertice v : vertices)
            accion.actua(v);
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por BFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void bfs(T elemento, AccionVerticeGrafica<T> accion) {
        if(!contiene(elemento))
            throw new NoSuchElementException();
        Cola<Vertice> c = new Cola<>();
        recorre(elemento, accion, c);

    }

    /**
     * Metodo auxiar encargado de recorrer la grafica en base a una instacia de mete saca.
     * @param elemento el elemento a empezar
     * @param accion la accion a hacer
     * @param ms la instacia de metesaca
     */
    private void recorre(T elemento,
                         AccionVerticeGrafica<T> accion,
                         MeteSaca<Vertice> ms) {
        Vertice v = buscaVertice(elemento), e;
        for(Vertice ve: vertices)
            ve.color = Color.ROJO;
        ms.mete(v);
        setColor(v,Color.NEGRO);
        while(!ms.esVacia()) {
            e = ms.saca();
            accion.actua(e);
            for (Vecino j: e.vecinos)
                if(j.vecino.color == Color.ROJO) {
                    ms.mete(j.vecino);
                    setColor(j.vecino,Color.NEGRO);
                }
        }
        for(Vertice x : vertices)
            x.color = Color.NINGUNO;
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por DFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void dfs(T elemento, AccionVerticeGrafica<T> accion) {
        if(!contiene(elemento))
            throw new NoSuchElementException();
        Pila<Vertice> c = new Pila<>();
        recorre(elemento, accion, c);
    }

    /**
     * Nos dice si la gráfica es vacía.
     * @return <code>true</code> si la gráfica es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
        return vertices.esVacia();
    }

    /**
     * Limpia la gráfica de vértices y aristas, dejándola vacía.
     */
    @Override public void limpia() {
        vertices.limpia();
        aristas = 0;
    }

    /**
     * Regresa una representación en cadena de la gráfica.
     * @return una representación en cadena de la gráfica.
     */
    @Override public String toString() {
        StringBuilder s = new StringBuilder("{");
        Lista<Vertice> l = new Lista<>();
        for(Vertice v : vertices)
            s.append(v.elemento.toString()).append(", ");
        s.append("}, {");
        for (Vertice k : vertices){
            for (Vertice j : vertices)
                if (sonVecinos(k.elemento, j.elemento) && !l.contiene(j))
                    s.append("(").append(k.elemento.toString()).append(", ").append(j.elemento.toString()).append("), ");
            l.agrega(k);
        }
        return s + "}";
    }

    /**
     * Nos dice si la gráfica es igual al objeto recibido.
     * @param objeto el objeto con el que hay que comparar.
     * @return <code>true</code> si la gráfica es igual al objeto recibido;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") Grafica<T> grafica = (Grafica<T>) objeto;
        if ((vertices.getLongitud() != grafica.vertices.getLongitud()) || (aristas != grafica.aristas) || vertices.equals(grafica.vertices))
            return false;
        for (Vertice v : vertices)
            for (Vertice v2 : vertices)
                if (v.elemento != v2.elemento && sonVecinos(v.elemento, v2.elemento) && !grafica.sonVecinos(v.elemento, v2.elemento))
                    return false;
        return true;
    }

    /**
     * Regresa un iterador para iterar la gráfica. La gráfica se itera en el
     * orden en que fueron agregados sus elementos.
     * @return un iterador para iterar la gráfica.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Calcula una trayectoria de distancia mínima entre dos vértices.
     * @param origen el vértice de origen.
     * @param destino el vértice de destino.
     * @return Una lista con vértices de la gráfica, tal que forman una
     *         trayectoria de distancia mínima entre los vértices <code>a</code> y
     *         <code>b</code>. Si los elementos se encuentran en componentes conexos
     *         distintos, el algoritmo regresa una lista vacía.
     * @throws NoSuchElementException si alguno de los dos elementos no está en
     *         la gráfica.
     */
    public Lista<VerticeGrafica<T>> trayectoriaMinima(T origen, T destino) {
        Vertice s = buscaVertice(origen), t = buscaVertice(destino);
        if (s == null || t == null)
            throw new NoSuchElementException();

        Lista<VerticeGrafica<T>> listaRecorrido = new Lista<>();

        if(origen.equals(destino)) {
            listaRecorrido.agrega(s);
            return listaRecorrido;
        }

        for(Vertice v: this.vertices) {
            v.distancia = -1;
        }

        s.distancia = 0;
        Cola<Vertice> cola = new Cola<>();
        cola.mete(s);
        while(!cola.esVacia()) {
            Vertice vertice = cola.saca();
            for(Vecino vecino: vertice.vecinos) {
                if(vecino.vecino.distancia == -1) {
                    vecino.vecino.distancia = vertice.distancia + 1;
                    cola.mete(vecino.vecino);
                }
            }
        }
        if(t.distancia == -1)
            return listaRecorrido;

        Vertice u = t;
        listaRecorrido.agrega(t);

        while(!u.elemento.equals(s.elemento)) {
            for(Vecino v: u.vecinos) {
                if(u.distancia == v.vecino.distancia + 1) {
                    listaRecorrido.agrega(v.vecino);
                    u = v.vecino;
                }
            }
        }

        return listaRecorrido.reversa();
    }

    /**
     * Calcula la ruta de peso mínimo entre el elemento de origen y el elemento
     * de destino.
     * @param origen el vértice origen.
     * @param destino el vértice destino.
     * @return una trayectoria de peso mínimo entre el vértice <code>origen</code> y
     *         el vértice <code>destino</code>. Si los vértices están en componentes
     *         conexas distintas, regresa una lista vacía.
     * @throws NoSuchElementException si alguno de los dos elementos no está en
     *         la gráfica.
     */
    public Lista<VerticeGrafica<T>> dijkstra(T origen, T destino) {
        Vertice s = this.buscaVertice(origen), t = this.buscaVertice(destino);
        if (s == null || t == null)
            throw new NoSuchElementException();

        Lista<VerticeGrafica<T>> listaRecorrido = new Lista<>();

        if(origen.equals(destino)) {
            listaRecorrido.agrega(s);
            return listaRecorrido;
        }

        for(Vertice v: this.vertices)
            v.distancia = -1;

        s.distancia = 0;

        MonticuloMinimo<Vertice> monticulo = new MonticuloMinimo<>(this.vertices);

        while(!monticulo.esVacia()) {
            Vertice vertice = monticulo.elimina();
            for(Vecino vecino: vertice.vecinos) {
                if(this.doubleCompare(vecino.vecino.distancia, this.doubleAd(vertice.distancia, vecino.peso)) > 0) {
                    vecino.vecino.distancia = this.doubleAd(vertice.distancia, vecino.peso);
                    monticulo.reordena(vecino.vecino);
                }
            }

        }

        if(t.distancia == -1) { return listaRecorrido; }

        Vertice u = t;
        listaRecorrido.agrega(t);

        while(!u.elemento.equals(s.elemento)) {
            for(Vecino v: u.vecinos) {
                if(u.distancia == this.doubleAd(v.vecino.distancia, 1)) {
                    listaRecorrido.agrega(v.vecino);
                    u = v.vecino;
                }
            }
        }

        return listaRecorrido.reversa();
    }
}
