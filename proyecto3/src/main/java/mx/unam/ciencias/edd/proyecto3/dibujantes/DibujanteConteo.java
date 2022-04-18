package mx.unam.ciencias.edd.proyecto3.dibujantes;

import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.proyecto3.Palabra;

/**
 * Clase encargada de dibujar los conteos de palabras en un documento.
 */

public class DibujanteConteo {

    public static void dibujaConteo(String nombreArchivo,StringBuilder texto, Lista<Palabra> palabras){
        int anchoCelda = 50;
        int altoCelda = 25;

        int ancho =520;
        int altur = 55 + altoCelda*(int)Math.ceil((double) palabras.getLongitud()/10);



        texto.append("<svg viewBox=\"0 0 "+ancho+" "+altur+"\" style=\"background: whitesmoke\" xmlns=\"http://www.w3.org/2000/svg\">\n");
        texto.append("  <text x=\""+(ancho/2)+"\" y=\"20\"  fill=\"darkslateblue\" font-family=\"monospace\" font-size=\"18\" style = \"text-anchor: middle\" dominant-baseline = \"middle\" > Reporte del archivo "+nombreArchivo+"</text>\n");
        int contador = 0;
        int celdaX = 10;
        int celdaY = 25;
        int contadorIndice = 1;
        for (Palabra palabra:palabras) {
            if(contador%10 == 0){
                celdaY += altoCelda;
                contador = 0;
            }
            dibujaCelda(texto,(celdaX+((contador)*anchoCelda)),celdaY,anchoCelda,altoCelda,palabra.getPalabra(),palabra.getRepeticiones(), AuxilearGeneral.colorDeBase(contadorIndice));
            contador++;
            contadorIndice++;
        }

        texto.append("</svg>");




    }
    public static void dibujaMiniConteo(String nombreArchivo,StringBuilder texto, Lista<Palabra> palabras){
        int anchoCelda = 50;
        int altoCelda = 25;

        int ancho =520;
        int altur = 50 + altoCelda*(int)Math.ceil((double) palabras.getLongitud()/10);



        texto.append("<svg viewBox=\"0 0 "+ancho+" "+altur+"\" style=\"background: whitesmoke\" xmlns=\"http://www.w3.org/2000/svg\">\n");
        texto.append("  <text x=\""+10+"\" y=\"20\"  fill=\"darkslateblue\" font-family=\"monospace\" font-size=\"15\" style = \"text-anchor: start\" dominant-baseline = \"middle\" > Reporte del archivo "+nombreArchivo+"</text>\n");
        int contador = 0;
        int celdaX = 10;
        int celdaY = 15;
        int contadorIndice = 1;
        for (Palabra palabra:palabras) {
            if(contador%10 == 0){
                celdaY += altoCelda;
                contador = 0;
            }
            dibujaCelda(texto,(celdaX+((contador)*anchoCelda)),celdaY,anchoCelda,altoCelda,palabra.getPalabra(),palabra.getRepeticiones(), AuxilearGeneral.colorDeBaseReducido(contadorIndice));
            contador++;
            contadorIndice++;
        }

        texto.append("</svg>");
    }
    private static void dibujaCelda(StringBuilder texto, int xInicial, int yInicial,
                                    int ancho, int altura, String palabra, int repeticion,String color){
        xInicial += 1;
        yInicial += 1;
        ancho-=1;
        altura-=1;

        texto.append("<rect x=\""+xInicial+"\" y=\""+yInicial+"\" width=\""+ancho+"\" height=\""+altura+"\" rx=\"10\" ry=\"10\"\n" +
                "        fill=\""+ color+"\" />\n" +
                "   <rect x=\""+(xInicial + 5)+"\" y=\""+(yInicial+10)+"\" width=\""+(ancho-10)+"\" height=\""+(altura-13)+"\" rx=\"7\" ry=\"7\"\n" +
                "        fill=\"white\" />");
        int xTexto = (xInicial + (ancho/2));
        int ySuperior = (yInicial+5);
        int yInferior = (yInicial + 17);
        texto.append("<text x=\""+xTexto+"\" y=\""+ySuperior+"\" fill=\"white\" font-family=\"Arial, Helvetica, sans-serif\" font-size=\"9\" style = \"text-anchor: middle\" dominant-baseline = \"middle\">"+palabra+"</text>\n" +
                "   <text x=\""+xTexto+"\" y=\""+yInferior+"\" fill=\"black\" font-family=\"Arial, Helvetica, sans-serif\" font-size=\"9\" style = \"text-anchor: middle\" dominant-baseline = \"middle\">"+repeticion+"</text>");



    }


}
