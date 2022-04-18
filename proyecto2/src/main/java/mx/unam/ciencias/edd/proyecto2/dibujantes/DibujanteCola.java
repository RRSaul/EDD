package mx.unam.ciencias.edd.proyecto2.dibujantes;

import mx.unam.ciencias.edd.Cola;
/**
 * Clase publica encargada de dibujar colas.
 */

public class DibujanteCola {

    /**
     * Metodo encargado de dibujar una cola dada.
     * @param cola la cola a dibujar.
     * @param longitud la longitud del arreglo de elementos, que seria tambien la longitud
     *                 de la cola.
     */

    public static void dibujarCola(Cola<Integer> cola,int longitud) {

        StringBuilder colaSVG = new StringBuilder("");
        int ancho = longitud *20 + 40;
        int altur = 52;
        colaSVG.append("<svg viewBox=\"0 0 "+ancho+" "+altur+"\" style=\"background: whitesmoke\" xmlns=\"http://www.w3.org/2000/svg\">\n");
        colaSVG.append("  <text x=\"10\" y=\"15\"  fill=\"indianred\"  font-size=\"10\"   >C </text>\n" +
                "   <text x=\"17\" y=\"15\"  fill=\"darkslategray\" font-size=\"10\"   >ola. </text>\n");
        dFlechaDI(colaSVG,5,20,15,32);
        dCajaAIDSinElemento(colaSVG,15,20,
                20*(longitud +1),32,"lavender");
        int esquinaAnterior = 15;
        while (!cola.esVacia()){
            dElementoEnCaja(colaSVG,esquinaAnterior,20,
                    (esquinaAnterior+20),32,cola.saca(),"indianred");
            esquinaAnterior = (esquinaAnterior + 20);
        }
        dFlechaDI(colaSVG,(esquinaAnterior+5),20,esquinaAnterior+15,32);
        colaSVG.append("</svg>");
        System.out.println(colaSVG);



    }
    private static void dCajaAIDSinElemento(StringBuilder builder,int esquinaISx, int esquinaISy,
                                           int esquinaDIx, int esquinaDIy,String fillColor){

        builder.append("<rect x =\"" + esquinaISx + "\"");
        builder.append(" y =\"" + esquinaISy + "\" ");
        builder.append("height =\"" + (esquinaDIy-esquinaISy) +"\" width = \""
                + (esquinaDIx-esquinaISx) +"\" style =\" fill: "+fillColor+"\"/>\n");

        builder.append("<path d=\"M" + esquinaISx + "," + esquinaISy + " L" +
                esquinaDIx + ","+ esquinaISy +" M" + esquinaISx + ","+ esquinaDIy
                +" L" + esquinaDIx + ","+ esquinaDIy + " \"");
        builder.append("style=\"stroke:darkslategray; stroke-width:1; fill:none;\"></path>\n");
    }
    private static void dElementoEnCaja(StringBuilder builder,int esquinaISx, int esquinaISy,
                                       int esquinaDIx, int esquinaDIy, int contenido,String textColor){

        builder.append("<text x=\"" + (esquinaISx+(esquinaDIx-esquinaISx)/2) + "\" y=\"" +(esquinaISy+(esquinaDIy-esquinaISy)/2) +
                "\" fill=\""+ textColor+"\" font-size=\"5\" style = \"text-anchor: middle\" font-family=\"Helvetica\" dominant-baseline = \"middle\"\t>"
                + contenido +"</text>\n" );
    }
    private static void dFlechaDI(StringBuilder builder,int esquinaISx, int esquinaISy,
                                 int esquinaDIx, int esquinaDIy) {

        builder.append("<polygon points= \"");
        int esquinaDeFlecha = (esquinaISx + (((esquinaDIx - esquinaISx) / 3) + 2));
        builder.append((esquinaDIx-2) + "," + (esquinaISy+((esquinaDIy-esquinaISy)/2)) + " "
                + esquinaDeFlecha + "," + (esquinaISy+((esquinaDIy-esquinaISy)/2)) + " "
                + esquinaDeFlecha + "," + (esquinaISy+((esquinaDIy-esquinaISy)/3)) + " "
                + (esquinaISx+2) + "," + (esquinaISy+((esquinaDIy-esquinaISy)/2)) + " "
                + esquinaDeFlecha + "," + (esquinaISy+(2*(esquinaDIy-esquinaISy)/3)) + " "
                + esquinaDeFlecha + "," + (esquinaISy+((esquinaDIy-esquinaISy)/2)) + " "
                +(esquinaDIx-2) + "," + (esquinaISy+((esquinaDIy-esquinaISy)/2)) +"\"");
        builder.append(" style=\"fill:darkslategray;stroke:darkslategray;stroke-width:0.5\"></polygon>\n");
    }
}
