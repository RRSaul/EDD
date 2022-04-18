package mx.unam.ciencias.edd.proyecto3;

/**
 * Clase auxiliar para el conteo de palabras, debido a que los diccionarios no se
 * pueden ordenar hay que usar una estructura que se pueda, en este se usa una lista y
 * esta es de palabras, una vez una palabra se agrega a una lista no se puede modificar.
 */
public class Palabra implements Comparable<Palabra> {

    //La palabra en cuestión.
    private String palabra;
    //La cantidad de repeticiones de la palabra.
    private int repeticiones;

    public Palabra(String palabra,int repeticiones){
        this.palabra = palabra;
        this.repeticiones = repeticiones;
    }

    /**
     * Ya que usaremos listas para poder ordenar hay que sobrecargar a nuestra conveniencia
     * el metodo compareTo, este compara las repeticiones.
     * @param o la otra palabra a comparar.
     * @return -1, 0, 1.
     */
    @Override
    public int compareTo(Palabra o) {
        return Integer.compare(o.repeticiones,this.repeticiones);
    }
    /**
     * Metodo encargado de regresar la palabra en cuestión.
     * @return la palabra.
     */
    public String getPalabra(){
        return this.palabra;
    }

    /**
     * Metodo encargado de regresar las repeticiones de la palabra.
     * @return las repeticiones.
     */
    public int getRepeticiones(){
        return this.repeticiones;
    }

    /**
     * Para poder saber si un documento tiene alguna pabra en común con otro documento hay
     * que sobrecargar el metodo equals.
     * @param o la otra palabra a comparar.
     * @return si el string palabra es igual.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Palabra)) return false;
        Palabra palabra1 = (Palabra) o;
        return palabra.equals(palabra1.palabra);
    }

}
