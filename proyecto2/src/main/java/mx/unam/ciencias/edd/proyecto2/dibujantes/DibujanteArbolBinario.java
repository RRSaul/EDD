package mx.unam.ciencias.edd.proyecto2.dibujantes;
import mx.unam.ciencias.edd.ArbolBinario;
import mx.unam.ciencias.edd.VerticeArbolBinario;

/**
 * Clase publica abstracta que extienden la mayoria de los dibujantes de arboles.
 */

public abstract class DibujanteArbolBinario {


    public static void dArbolBinario(ArbolBinario<Integer> arbol, String tipoArbolSinA, String coloInicial,String colorTexto){
        StringBuilder arbolSVG = new StringBuilder("");
        int escalaAltura = 15;
        int radio = 3;
        int ancho = (int) (20 + (Math.pow(2,arbol.altura()))* 10);
        int altur = ((30 + arbol.altura()* escalaAltura));
        VerticeArbolBinario<Integer> raiz = arbol.raiz();

        arbolSVG.append("<svg viewBox=\"0 0 " + ancho + " " + altur + "\" style=\"background: whitesmoke\" xmlns=\"http://www.w3.org/2000/svg\">\n");
        arbolSVG.append("  <text x=\"10\" y=\"10\"  fill=\""+coloInicial+"\"  font-size=\"5\"   >Á </text>\n" +
                "   <text x=\"14\" y=\"10\"  fill=\"darkslategray\" font-size=\"5\"   >"+tipoArbolSinA+"</text>\n");
        dVerticesRecursivo(arbolSVG,10,(ancho-10),20,escalaAltura,raiz,radio,colorTexto);
        arbolSVG.append("</svg>");
        System.out.println(arbolSVG);
    }

    /**
     * Metodo encargado de dibujar vertices de manera recursiva, todos los arboles usan o
     * se basan en este metodo para dibujar sus vertices.
     */
    private static void dVerticesRecursivo(StringBuilder builder, int limiteXI, int limiteXD, int altura,
                                           int escalaAltura, VerticeArbolBinario<Integer> verticeArbolBinario,int radio,String textColor){
        int centroPropio = (limiteXI+((limiteXD-limiteXI)/2));
        if(verticeArbolBinario.hayIzquierdo()){
            int centroIzquierdo = (limiteXI + ((centroPropio-limiteXI))/2);
            int alturaIzquierdo = (altura + escalaAltura);
            dLineas(builder,centroPropio,altura,centroIzquierdo,alturaIzquierdo);
            dVerticesRecursivo(builder,limiteXI,centroPropio,alturaIzquierdo,escalaAltura,
                    verticeArbolBinario.izquierdo(),radio,textColor);
        }
        if(verticeArbolBinario.hayDerecho()){
            int centroDerecho = (centroPropio+ ((limiteXD-centroPropio))/2);
            int alturaDerecho = (altura + escalaAltura);
            dLineas(builder,centroPropio,altura,centroDerecho,alturaDerecho);
            dVerticesRecursivo(builder,centroPropio,limiteXD,alturaDerecho,escalaAltura,
                    verticeArbolBinario.derecho(),radio,textColor);
        }
        dVertice(builder,centroPropio,altura, radio,verticeArbolBinario.get(),textColor);
    }

    /**
     *Metodo encargado de dibujar un solo vertice.
     */
    private static void dVertice(StringBuilder builder,int valorX, int valorY, int radio, int contenido,String textColor){
    builder.append(" <circle cx=\""+valorX+"\" cy=\""+valorY+"\" r=\""+radio+"\" stroke='darkslategray' stroke-width=\"0.5\"  fill='dimgray'/>");
    builder.append("<text x=\"" + valorX + "\" y=\"" +valorY +
                "\" fill=\""+ textColor+"\" font-size=\"3.5\" style = \"text-anchor: middle\" dominant-baseline = \"middle\"\t>"
                + contenido +"</text>\n" );
    }

    /**
     *Metodo encargado de dibujar una linea de un punto inicial a uno final.
     */
    private static void dLineas(StringBuilder builder,int valorXI, int valorYI, int valorXF, int valorYF){
    builder.append("<line x1=\""+ valorXI+"\" y1=\""+valorYI+"\"" +
            " x2=\""+valorXF+"\" y2=\""+valorYF+"\" stroke=\"darkslategray\" stroke-width=\"0.5\"></line>");
    }
}
