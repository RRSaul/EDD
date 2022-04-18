package mx.unam.ciencias.edd.proyecto3.dibujantes;

import mx.unam.ciencias.edd.Color;
import mx.unam.ciencias.edd.Grafica;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.VerticeGrafica;
import mx.unam.ciencias.edd.proyecto3.Palabra;

import java.util.Iterator;
/**
 * Clase encargado de dibujar una grafica de con cada documento como vertice.
 */
public class DibujanteGrafica {


public static void dibujarGrafica(Grafica<Lista<Palabra>> grafica,StringBuilder graficaSVG,Lista<String> nombres){


    int ancho = grafica.getElementos()*10 + 20;
    int altur = (30 +grafica.getElementos()*10 );
    int radio = (grafica.getElementos()*5

    );
    int centroXDeGrafica = (radio + 10);
    int centroYDeGrafica = (radio+20);
    double aumentoDeGrado =  Math.toRadians(360/(double)grafica.getElementos());
    graficaSVG.append("<svg viewBox=\"0 0 "+ancho+" "+altur+"\" style=\"background: whitesmoke\" xmlns=\"http://www.w3.org/2000/svg\">\n");
    graficaSVG.append("  <text x=\"8\" y=\"15\"  fill=\"lightcoral\"  font-size=\"8\"   >G </text>\n" +
            "   <text x=\"14\" y=\"15\"  fill=\"black\"  font-size=\"8\"   >rafica. </text>\n");
    StringBuilder builderVertices = new StringBuilder();
    Iterator<Lista<Palabra>> iterator = grafica.iterator();
    Iterator<String> iteNombres = nombres.iterator();

    double anguloActual = 0;

    while (iterator.hasNext()){
        Lista<Palabra> palabrasActual = iterator.next();

        String  elementoEnVertice = iteNombres.next();

        VerticeGrafica<Lista<Palabra>> vertice = grafica.vertice(palabrasActual);
        Iterator<Lista<Palabra>> iteratorInterno = grafica.iterator();
        double anguloActualInterno = 0;
        double centroXVerticeActual = centroXDeGrafica + Math.cos(anguloActual) * radio;

        double centroYVerticeActual = centroYDeGrafica + Math.sin(anguloActual) * radio;
        while (iteratorInterno.hasNext()){
            Lista<Palabra> elementoInterno = iteratorInterno.next();
            if(grafica.sonVecinos(palabrasActual,elementoInterno) && grafica.vertice(elementoInterno).getColor()== Color.NINGUNO){
                dLineas(graficaSVG, centroXVerticeActual, centroYVerticeActual,
                        (centroXDeGrafica + Math.cos(anguloActualInterno) * radio),(centroYDeGrafica + Math.sin(anguloActualInterno) * radio));

            }
            anguloActualInterno = anguloActualInterno + aumentoDeGrado;
        }
        dVertice(builderVertices, centroXVerticeActual,
                centroYVerticeActual,4,elementoEnVertice,"black");
        anguloActual = anguloActual+aumentoDeGrado;
        grafica.setColor(vertice,Color.NEGRO);
    }

    graficaSVG.append(builderVertices);
    graficaSVG.append("</svg>");

}

    private static void dVertice(StringBuilder builder,double valorX, double valorY, int radio, String contenido,String textColor){
        builder.append(" <circle cx=\""+valorX+"\" cy=\""+valorY+"\" r=\""+radio+"\" stroke='lightcoral' stroke-width=\"0.5\"  fill='lavenderblush'/>");
        builder.append("<text x=\"" + valorX + "\" y=\"" +valorY +
                "\" fill=\""+ textColor+"\" font-size=\"3.5\" style = \"text-anchor: middle\" dominant-baseline = \"middle\"\t>"
                + contenido +"</text>\n" );
    }
    private static void dLineas(StringBuilder builder,double valorXI, double valorYI, double valorXF, double valorYF){
        builder.append("<line x1=\""+ valorXI+"\" y1=\""+valorYI+"\"" +
                " x2=\""+valorXF+"\" y2=\""+valorYF+"\" stroke=\"lightcoral\" stroke-width=\"0.5\"></line>");
    }
}
