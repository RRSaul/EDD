package mx.unam.ciencias.edd.proyecto2.dibujantes;

import mx.unam.ciencias.edd.Pila;

/**
 * Clase publica encarda de dibujar pilas.
 */

public class DibujantePila {
    /**
     * Metodo encargado de dibujar una Pila dada.
     * @param pila la pila a dibujar.
     * @param altura la longitud del arreglo de elementos que es tambien la altura de la pila.
     */

    public static void dibujarPila(Pila<Integer> pila, int altura){


        StringBuilder pilaSVG = new StringBuilder("");
        int ancho = 60;
        int altur = (12*altura +40);
        pilaSVG.append("<svg viewBox=\"0 0 "+ancho+" "+altur+"\" style=\"background: whitesmoke\" xmlns=\"http://www.w3.org/2000/svg\">\n");
        pilaSVG.append("  <text x=\"10\" y=\"15\"  fill=\"seagreen\"  font-size=\"10\"   >P </text>\n" +
                "   <text x=\"16\" y=\"15\"  fill=\"darkslategray\"  font-size=\"10\"   >ila. </text>\n");
        pilaSVG.append(" <path d=\"M 35,30 Q 37,24  50,25\" style=\"stroke:darkslategray; stroke-width:1; fill:none;\"></path>\n" +
                "    <path d=\"M 10,25 Q 23,24  25,30\" style=\"stroke:darkslategray; stroke-width:1; fill:none;\"></path>\n" +
                " <polygon points= \"50,25 50,23 52,25 50,27\" style=\"fill:darkslategray;stroke:darkslategray;stroke-width:1\"></polygon>\n");

        dCajaAASinElementos(pilaSVG,20,30,40,30+(12*altura),"lavender");
        int esquinaAnterior = 30;
        while (!pila.esVacia()){
            dElementoEnCaja(pilaSVG,20,esquinaAnterior,40,esquinaAnterior+12,pila.saca(),"seagreen");
            esquinaAnterior = (esquinaAnterior + 12);
        }
        pilaSVG.append("</svg>");
        System.out.println(pilaSVG);
    }





    private static void dCajaAASinElementos(StringBuilder builder,int esquinaISx, int esquinaISy,
                               int esquinaDIx, int esquinaDIy,String fillColor){

        builder.append("<polyline points= \"");
        builder.append(esquinaISx + "," + esquinaISy + " "+
                esquinaISx + "," + esquinaDIy + " " +
                esquinaDIx + "," + esquinaDIy + " "+
                esquinaDIx +"," + esquinaISy + "\"");
        builder.append(" style=\"fill:"+fillColor+";stroke:darkslategray;stroke-width:1\"></polyline>\n");
    }
    private static void dElementoEnCaja(StringBuilder builder,int esquinaISx, int esquinaISy,
                                       int esquinaDIx, int esquinaDIy, int contenido,String textColor){

        builder.append("<text x=\"" + (esquinaISx+(esquinaDIx-esquinaISx)/2) + "\" y=\"" +(esquinaISy+(esquinaDIy-esquinaISy)/2) +
                "\" fill=\""+ textColor+"\" font-size=\"5\" style = \"text-anchor: middle\" font-family=\"Helvetica\" dominant-baseline = \"middle\"\t>"
                + contenido +"</text>\n" );
    }

}
