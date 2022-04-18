package mx.unam.ciencias.edd.proyecto2.dibujantes;
import mx.unam.ciencias.edd.Lista;
import java.util.Iterator;

/**
 * Clase publica encargada de dibujar listas.
 */

public class DibujanteLista {
    /**
     * Metodo encargado de dibujar una lista dada.
     * @param lista la lista a dibujar.
     */
    public static void dibujarLista(Lista<Integer> lista) {

        StringBuilder listaSVG = new StringBuilder("");
        int ancho = lista.getLongitud() * 25 + 10;
        int altur = 52;
        listaSVG.append("<svg viewBox=\"0 0 " + ancho + " " + altur + "\" style=\"background: whitesmoke\" xmlns=\"http://www.w3.org/2000/svg\">\n");
        listaSVG.append("  <text x=\"10\" y=\"15\"  fill=\"goldenrod\"  font-size=\"10\"   >L </text>\n" +
                "   <text x=\"17\" y=\"15\"  fill=\"darkslategray\" font-size=\"10\"   >ista. </text>\n");
        Iterator<Integer> iterator =  lista.iterator();
        int contador = 0;
        int esquinaSIanteriorx = 10;
        while (contador<(lista.getLongitud()-1)){

            dCajaConBiFlecha(listaSVG,esquinaSIanteriorx,20,(esquinaSIanteriorx+25),32,iterator.next(),"lavender","sienna");
            esquinaSIanteriorx = (esquinaSIanteriorx + 25);
            contador++;
        }
        dCajaCerrada(listaSVG,esquinaSIanteriorx,20,(esquinaSIanteriorx+(3*(25)/5)),32,iterator.next(),"lavender","sienna");
        listaSVG.append("</svg>");
        System.out.println(listaSVG);
    }





        private static void dCajaConBiFlecha(StringBuilder builder,int esquinaISx, int esquinaISy,
                                         int esquinaDIx, int esquinaDIy, int contenido,String fillColor,String textColor) {

        int finDeCaja= (esquinaISx +(3*(esquinaDIx - esquinaISx) / 5));
        dCajaCerrada(builder,esquinaISx,esquinaISy,finDeCaja,esquinaDIy,contenido,fillColor,textColor);
        dFlechaDD(builder,finDeCaja,esquinaISy,esquinaDIx,esquinaDIy);
    }

    private static void dFlechaDD(StringBuilder builder,int esquinaISx, int esquinaISy,
                                  int esquinaDIx, int esquinaDIy) {

        builder.append("<polygon points= \"");
        int esquinaDeFlechaI = (esquinaISx + ((esquinaDIx - esquinaISx) / 3) + 1);
        int esquinaDeFlechaD = (esquinaDIx - ((esquinaDIx - esquinaISx) / 3) - 1);
        int alturaSuperiorFlecha = (esquinaISy+((esquinaDIy-esquinaISy)/3 )+1);
        int puntoInferiorFlecha = (esquinaISy+(2*(esquinaDIy-esquinaISy)/3)-1);

        int alturaArribaBorde = (alturaSuperiorFlecha+1);
        int alturaAbajoBorde = (puntoInferiorFlecha-1);
        builder.append(esquinaDeFlechaD + "," + alturaArribaBorde+ " "
                + esquinaDeFlechaI + "," + alturaArribaBorde + " "
                + esquinaDeFlechaI + "," + alturaSuperiorFlecha + " "
                + (esquinaISx+2) + "," + (esquinaISy+((esquinaDIy-esquinaISy)/2)) + " "
                + esquinaDeFlechaI + "," + puntoInferiorFlecha + " "
                + esquinaDeFlechaI + "," + alturaAbajoBorde + " "
                + esquinaDeFlechaD + "," + alturaAbajoBorde + " "
                + esquinaDeFlechaD + "," + puntoInferiorFlecha + " "
                +(esquinaDIx-2) + "," + (esquinaISy+((esquinaDIy-esquinaISy)/2))+" "
                + esquinaDeFlechaD + "," + alturaSuperiorFlecha +  "\"");
        builder.append(" style=\"fill:darkslategray;stroke:darkslategray;stroke-width:0.3\"></polygon>\n");
    }


    public static void dCajaCerrada(StringBuilder builder,int esquinaISx, int esquinaISy,
                                    int esquinaDIx, int esquinaDIy, int contenido,String fillColor,String textColor){

        builder.append("<polygon points= \"");
        builder.append(esquinaISx + "," + esquinaISy + " "
                + esquinaDIx +"," + esquinaISy + " "
                + esquinaDIx + "," + esquinaDIy + " "
                + esquinaISx + "," + esquinaDIy +"\"");
        builder.append(" style=\"fill:"+fillColor+";stroke:darkslategray;stroke-width:1\"></polygon>\n");
        builder.append("<text x=\"" + (esquinaISx+(esquinaDIx-esquinaISx)/2) + "\" y=\"" + (esquinaISy+(esquinaDIy-esquinaISy)/2) +
                "\" fill=\""+ textColor+"\" font-size=\"5\" style = \"text-anchor: middle\" font-family=\"Helvetica\" dominant-baseline = \"middle\"\t>"+contenido +"</text>\n" );
    }
}
