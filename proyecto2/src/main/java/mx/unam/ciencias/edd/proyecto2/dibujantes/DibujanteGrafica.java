package mx.unam.ciencias.edd.proyecto2.dibujantes;

import mx.unam.ciencias.edd.Color;
import mx.unam.ciencias.edd.Grafica;
import mx.unam.ciencias.edd.VerticeGrafica;

import java.util.Iterator;

/**
 * Clase publica encargada de dibujar graficas.
 */

public class DibujanteGrafica {

    /**
     * Metodo encargado de dibujar una grafica dada.
     * @param grafica la grafica a dibujar.
     */


    public static void dibujarGrafica(Grafica <Integer> grafica){
        StringBuilder graficaSVG = new StringBuilder("");

        int ancho = grafica.getElementos()*10 + 20;
        int altur = (30 +grafica.getElementos()*10 );
        int radio = (grafica.getElementos()*5

        );
        int centroXDeGrafica = (radio + 10);
        int centroYDeGrafica = (radio+20);
        double aumentoDeGrado =  Math.toRadians(360/grafica.getElementos());
        graficaSVG.append("<svg viewBox=\"0 0 "+ancho+" "+altur+"\" style=\"background: whitesmoke\" xmlns=\"http://www.w3.org/2000/svg\">\n");
        graficaSVG.append("  <text x=\"8\" y=\"15\"  fill=\"maroon\"  font-size=\"8\"   >G </text>\n" +
                "   <text x=\"14\" y=\"15\"  fill=\"darkslategray\"  font-size=\"8\"   >rafica. </text>\n");
        StringBuilder builderVertices = new StringBuilder();
        Iterator<Integer> iterator = grafica.iterator();
        double anguloActual = 0;

        while (iterator.hasNext()){

            int  elementoEnVertice = iterator.next();
            VerticeGrafica <Integer>vertice = grafica.vertice(elementoEnVertice);
            Iterator<Integer> iteratorInterno = grafica.iterator();
            double anguloActualInterno = 0;
            double centroXVerticeActual = centroXDeGrafica + Math.cos(anguloActual) * radio;

            double centroYVerticeActual = centroYDeGrafica + Math.sin(anguloActual) * radio;
            while (iteratorInterno.hasNext()){
                int elementoInterno = iteratorInterno.next();
                if(grafica.sonVecinos(elementoEnVertice,elementoInterno) && grafica.vertice(elementoInterno).getColor()== Color.NINGUNO){
                    dLineas(graficaSVG, centroXVerticeActual, centroYVerticeActual,
                            (centroXDeGrafica + Math.cos(anguloActualInterno) * radio),(centroYDeGrafica + Math.sin(anguloActualInterno) * radio));

                }
                anguloActualInterno = anguloActualInterno + aumentoDeGrado;
            }
            dVertice(builderVertices, centroXVerticeActual,
                    centroYVerticeActual,4,elementoEnVertice,"white");
            anguloActual = anguloActual+aumentoDeGrado;
            grafica.setColor(vertice,Color.NEGRO);
        }

        graficaSVG.append(builderVertices);
        graficaSVG.append("</svg>");
        System.out.println(graficaSVG);

    }

    private static void dVertice(StringBuilder builder,double valorX, double valorY, int radio, int contenido,String textColor){
        builder.append(" <circle cx=\""+valorX+"\" cy=\""+valorY+"\" r=\""+radio+"\" stroke='darkslategray' stroke-width=\"0.5\"  fill='dimgray'/>");
        builder.append("<text x=\"" + valorX + "\" y=\"" +valorY +
                "\" fill=\""+ textColor+"\" font-size=\"3.5\" style = \"text-anchor: middle\" dominant-baseline = \"middle\"\t>"
                + contenido +"</text>\n" );
    }
    private static void dLineas(StringBuilder builder,double valorXI, double valorYI, double valorXF, double valorYF){
        builder.append("<line x1=\""+ valorXI+"\" y1=\""+valorYI+"\"" +
                " x2=\""+valorXF+"\" y2=\""+valorYF+"\" stroke=\"darkslategray\" stroke-width=\"0.5\"></line>");
    }
}
