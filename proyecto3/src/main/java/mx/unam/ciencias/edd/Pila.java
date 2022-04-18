package mx.unam.ciencias.edd;

/**
 * Clase para pilas genéricas.
 */
public class Pila<T> extends MeteSaca<T> {

    /**
     * Regresa una representación en cadena de la pila.
     * @return una representación en cadena de la pila.
     */
    @Override public String toString() {
        if(esVacia()) return "";

        StringBuilder rep = new StringBuilder();

        Nodo aux = cabeza;
        while(aux != null) {
            rep.append(aux.elemento.toString()).append("\n");
            aux = aux.siguiente;
        }
        return rep.toString();

    }

    /**
     * Agrega un elemento al tope de la pila.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void mete(T elemento) {
        if (elemento == null)
            throw new IllegalArgumentException();
        Nodo aux = new Nodo(elemento);
        if (esVacia()) {
            cabeza = rabo = aux;
        } else {
            aux.siguiente = cabeza;
            cabeza = aux;
        }
    }
}
