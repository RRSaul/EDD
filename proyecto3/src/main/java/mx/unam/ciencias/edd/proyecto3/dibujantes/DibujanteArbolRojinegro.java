package mx.unam.ciencias.edd.proyecto3.dibujantes;


import mx.unam.ciencias.edd.ArbolRojinegro;
import mx.unam.ciencias.edd.Color;
import mx.unam.ciencias.edd.VerticeArbolBinario;
import mx.unam.ciencias.edd.proyecto3.Palabra;

/**
 *Debido al uso de los metodos estaticos no se puede hacer @Overrite y hay que modificar
 * la clase directamente.
 * */

public class DibujanteArbolRojinegro  {

    /**
     * Metodo encarago de dibujar el Árbol Rojinegro dado.
     * @param arbolRojinegro el árbol rojinegro a dibujar.
     */
    public static void dArbolRojinegro(ArbolRojinegro<Palabra> arbolRojinegro, StringBuilder texto){

        dArbolBinarioRoji(arbolRojinegro,"rbol Rojinegro","tomato","black",texto);
    }




    private static void dArbolBinarioRoji(ArbolRojinegro<Palabra> arbol, String tipoArbolSinA, String coloInicial, String colorTexto,StringBuilder arbolSVG){
        int escalaAltura = 15;
        int radio = 3;
        int ancho = (int) (20 + (Math.pow(2,arbol.altura()))* 12);
        int altur = ((30 + arbol.altura()* escalaAltura));
        VerticeArbolBinario<Palabra> raiz = arbol.raiz();

        arbolSVG.append("<svg viewBox=\"0 0 " + ancho + " " + altur + "\" style=\"background: whitesmoke\" xmlns=\"http://www.w3.org/2000/svg\">\n");
        arbolSVG.append("  <text x=\"10\" y=\"10\"  fill=\""+coloInicial+"\"  font-size=\"5\"   >Á </text>\n" +
                "   <text x=\"14\" y=\"10\"  fill=\"darkslategray\" font-size=\"5\"   >"+tipoArbolSinA+"</text>\n");
        dVerticesRecursivo(arbol,arbolSVG,10,(ancho-10),20,escalaAltura,raiz,radio,colorTexto);
        arbolSVG.append("</svg>");

    }

    private static void dVerticesRecursivo(ArbolRojinegro<Palabra> arbolRojinegro, StringBuilder builder, int limiteXI, int limiteXD, int altura,
                                           int escalaAltura, VerticeArbolBinario<Palabra> verticeArbolBinario, int radio, String textColor){
        int centroPropio = (limiteXI+((limiteXD-limiteXI)/2));
        if(verticeArbolBinario.hayIzquierdo()){
            int centroIzquierdo = (limiteXI + ((centroPropio-limiteXI))/2);
            int alturaIzquierdo = (altura + escalaAltura);
            dLineas(builder,centroPropio,altura,centroIzquierdo,alturaIzquierdo);
            dVerticesRecursivo(arbolRojinegro,builder,limiteXI,centroPropio,alturaIzquierdo,escalaAltura,
                    verticeArbolBinario.izquierdo(),radio,textColor);
        }
        if(verticeArbolBinario.hayDerecho()){
            int centroDerecho = (centroPropio+ ((limiteXD-centroPropio))/2);
            int alturaDerecho = (altura + escalaAltura);
            dLineas(builder,centroPropio,altura,centroDerecho,alturaDerecho);
            dVerticesRecursivo(arbolRojinegro,builder,centroPropio,limiteXD,alturaDerecho,escalaAltura,
                    verticeArbolBinario.derecho(),radio,textColor);
        }
        dVertice(arbolRojinegro,builder,centroPropio,altura, radio,verticeArbolBinario.get().getPalabra(),textColor,verticeArbolBinario);
    }

    private static void dVertice(ArbolRojinegro<Palabra> arbolRojinegro, StringBuilder builder, int valorX, int valorY, int radio, String contenido, String textColor, VerticeArbolBinario vertice){
        @SuppressWarnings("unchecked") Color colorDeVertice = arbolRojinegro.getColor(vertice);
        String colorDeVGrafico;
        if(colorDeVertice == Color.NEGRO){
            colorDeVGrafico = "dimgray";
        }else {
            colorDeVGrafico = "tomato";
        }

        builder.append(" <circle cx=\""+valorX+"\" cy=\""+valorY+"\" r=\""+radio+"\" stroke='dimgray' stroke-width=\"0.5\"  fill='"+colorDeVGrafico+"'/>");
        builder.append("<text x=\"" + valorX + "\" y=\"" +valorY +
                "\" fill=\""+ textColor+"\" font-size=\"5\" style = \"text-anchor: middle\" dominant-baseline = \"middle\"\t>"
                + contenido +"</text>\n" );
    }

    private static void dLineas(StringBuilder builder,int valorXI, int valorYI, int valorXF, int valorYF){
        builder.append("<line x1=\""+ valorXI+"\" y1=\""+valorYI+"\"" +
                " x2=\""+valorXF+"\" y2=\""+valorYF+"\" stroke=\"dimgray\" stroke-width=\"0.5\"></line>");
    }
}
