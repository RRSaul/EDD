package mx.unam.ciencias.edd;

/**
 * Clase para métodos estáticos con dispersores de bytes.
 */
public class Dispersores {

    /* Constructor privado para evitar instanciación. */
    private Dispersores() {}

    /**
     * Función de dispersión XOR.
     * @param llave la llave a dispersar.
     * @return la dispersión de XOR de la llave.
     */
    public static int dispersaXOR(byte[] llave) {
        byte[] aux;
        if(llave.length % 4 != 0){
            int sobra = 4 - (llave.length % 4);
            aux = new byte[llave.length + sobra];
            for(int i = 0; i < aux.length; i++) {
                if(i >= llave.length){aux[i] = (byte) 0; }
                else{aux[i] =llave[i];}

            }
        }else{
            aux = llave;
        }

        int r = 0;
        int contadorCuatro = 0;

        while (contadorCuatro < aux.length){

            int n = ((aux[contadorCuatro] & 0xFF) << 24 |
                    (aux[contadorCuatro+1] & 0xFF) << 16 |
                    (aux[contadorCuatro+2] & 0xFF) << 8 |
                    (aux[contadorCuatro+3] & 0xFF));
            r ^= n;
            contadorCuatro += 4;
        }

        return r;
    }

    /**
     * Función de dispersión de Bob Jenkins.
     * @param llave la llave a dispersar.
     * @return la dispersión de Bob Jenkins de la llave.
     */
    public static int dispersaBJ(byte[] llave) {
        int a = 0x9e3779b9, b = 0x9e3779b9, c = 0xffffffff;
        int contadorDoce = 0, disponibleDoce = llave.length, longitud = llave.length;
        while(disponibleDoce >= 12) {
            a += ((llave[contadorDoce] & 0xFF) |
                    ((llave[contadorDoce + 1] & 0xFF) << 8) |
                    ((llave[contadorDoce + 2] & 0xFF) << 16) |
                    ((llave[contadorDoce + 3] & 0xFF) << 24));

            b += ((llave[contadorDoce + 4] & 0xFF) |
                    ((llave[contadorDoce + 5] & 0xFF) << 8) |
                    ((llave[contadorDoce + 6] & 0xFF) << 16) |
                    ((llave[contadorDoce + 7] & 0xFF) << 24));

            c += ((llave[contadorDoce + 8] & 0xFF) |
                    ((llave[contadorDoce + 9] & 0xFF) << 8) |
                    ((llave[contadorDoce + 10] & 0xFF) << 16) |
                    ((llave[contadorDoce + 11] & 0xFF) << 24));

            int[] r = mezcla(a, b, c);
            a = r[0];
            b = r[1];
            c = r[2];
            contadorDoce += 12;
            disponibleDoce -= 12;
        }
        c += longitud;
        switch (disponibleDoce) {
            case 11: c += ((llave[contadorDoce + 10] & 0xFF) << 24);
            case 10: c += ((llave[contadorDoce + 9] & 0xFF) << 16);
            case 9: c += ((llave[contadorDoce + 8] & 0xFF) << 8);
            case 8: b += ((llave[contadorDoce + 7] & 0xFF) << 24);
            case 7: b += ((llave[contadorDoce + 6] & 0xFF) << 16);
            case 6: b += ((llave[contadorDoce + 5] & 0xFF) << 8);
            case 5: b += (llave[contadorDoce + 4] & 0xFF);
            case 4: a += ((llave[contadorDoce + 3] & 0xFF) << 24);
            case 3: a += ((llave[contadorDoce + 2] & 0xFF) << 16);
            case 2: a += ((llave[contadorDoce + 1] & 0xFF) << 8);
            case 1: a += (llave[contadorDoce] & 0xFF);
        }

        int[] salida = mezcla(a, b, c);
        return salida[2];
    }

    /**
     * Metodo auxilear enxargado de mezclar en la funcion de disperción BJ
     * @param a
     * @param b
     * @param c
     * @return la mezcla hecha.
     */
    private static int[] mezcla(int a, int b, int c) {
        int[] salida = new int[3];
        a -= b; a -= c; a ^= (c >>> 13);
        b -= c; b -= a; b ^= (a << 8);
        c -= a; c -= b; c ^= (b >>> 13);
        a -= b; a -= c; a ^= (c >>> 12);
        b -= c; b -= a; b ^= (a << 16);
        c -= a; c -= b; c ^= (b >>> 5);
        a -= b; a -= c; a ^= (c >>> 3);
        b -= c; b -= a; b ^= (a << 10);
        c -= a; c -= b; c ^= (b >>> 15);
        salida[0] = a; salida[1] = b; salida[2] = c;
        return salida;
    }

    /**
     * Función de dispersión Daniel J. Bernstein.
     * @param llave la llave a dispersar.
     * @return la dispersión de Daniel Bernstein de la llave.
     */
    public static int dispersaDJB(byte[] llave) {
        int h = 5381;
        for(int i = 0; i < llave.length; i++) {
            h *= 33;
            h += llave[i] & 0xFF;
        }
        return h;
    }
}
