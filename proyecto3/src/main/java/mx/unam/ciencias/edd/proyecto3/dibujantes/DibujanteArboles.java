package mx.unam.ciencias.edd.proyecto3.dibujantes;

import mx.unam.ciencias.edd.ArbolAVL;
import mx.unam.ciencias.edd.ArbolRojinegro;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.proyecto3.Palabra;

/**
 * Clase encargada de dibujar los 2 arboles necesarios.
 */
public class DibujanteArboles {
    /**
     *
     *Metodo encargado de dibujar los 2 arboles por documento.
     */

    public static void dibujarArboles(StringBuilder builder, Lista<Palabra> palabras){

        palabras = AuxilearGeneral.cortaLista(palabras,15);

        ArbolRojinegro<Palabra> arbolRojinegro = new ArbolRojinegro<>(palabras);
        ArbolAVL<Palabra> arbolAVL = new ArbolAVL<>(palabras);

        DibujanteArbolRojinegro.dArbolRojinegro(arbolRojinegro,builder);
        DibujanteArbolBinarioAVL.dArbolAVL(arbolAVL,builder);


    }

}
