package mx.unam.ciencias.edd.proyecto3.dibujantes;

import mx.unam.ciencias.edd.ArbolAVL;
import mx.unam.ciencias.edd.VerticeArbolBinario;
import mx.unam.ciencias.edd.proyecto3.Palabra;

/**
 * Debido al uso de los metodos estaticos no se puede hacer @Overrite y hay que modificar
 * la clase directamente.
 */

public class DibujanteArbolBinarioAVL  {

    /**
     * Metodo encargado de dibujar un ÁrbolAVL dado.
     * @param arbolAVL El árbol AVL a dibujar.
     */
    public static void dArbolAVL(ArbolAVL<Palabra> arbolAVL,StringBuilder texto){

            dArbolBinarioAVL(arbolAVL,"rbol AVL","darkmagenta","black",texto);
    }

    private static void dArbolBinarioAVL(ArbolAVL<Palabra> arbol, String tipoArbolSinA, String coloInicial, String colorTexto, StringBuilder arbolAVL){
        int escalaAltura = 15;
        int radio = 3;
        int ancho = (int) (20 + (Math.pow(2,arbol.altura()))* 12);
        int altur = ((30 + arbol.altura()* escalaAltura));
        VerticeArbolBinario<Palabra> raiz = arbol.raiz();

        arbolAVL.append("<svg viewBox=\"0 0 " + ancho + " " + altur + "\" style=\"background: whitesmoke\" xmlns=\"http://www.w3.org/2000/svg\">\n");
        arbolAVL.append("  <text x=\"10\" y=\"10\"  fill=\""+coloInicial+"\"  font-size=\"5\"   >Á </text>\n" +
                "   <text x=\"14\" y=\"10\"  fill=\"darkslategray\" font-size=\"5\"   >"+tipoArbolSinA+"</text>\n");
        dVerticesRecursivo(arbol,arbolAVL,10,(ancho-10),20,escalaAltura,raiz,radio,colorTexto);
        arbolAVL.append("</svg>");
    }

    private static void dVerticesRecursivo(ArbolAVL<Palabra> arbolAVL, StringBuilder builder, int limiteXI, int limiteXD, int altura,
                                           int escalaAltura, VerticeArbolBinario<Palabra> verticeArbolBinario, int radio, String textColor){
        int centroPropio = (limiteXI+((limiteXD-limiteXI)/2));
        if(verticeArbolBinario.hayIzquierdo()){
            int centroIzquierdo = (limiteXI + ((centroPropio-limiteXI))/2);
            int alturaIzquierdo = (altura + escalaAltura);
            dLineas(builder,centroPropio,altura,centroIzquierdo,alturaIzquierdo);
            dVerticesRecursivo(arbolAVL,builder,limiteXI,centroPropio,alturaIzquierdo,escalaAltura,
                    verticeArbolBinario.izquierdo(),radio,textColor);
        }
        if(verticeArbolBinario.hayDerecho()){
            int centroDerecho = (centroPropio+ ((limiteXD-centroPropio))/2);
            int alturaDerecho = (altura + escalaAltura);
            dLineas(builder,centroPropio,altura,centroDerecho,alturaDerecho);
            dVerticesRecursivo(arbolAVL,builder,centroPropio,limiteXD,alturaDerecho,escalaAltura,
                    verticeArbolBinario.derecho(),radio,textColor);
        }
        dVertice(builder,centroPropio,altura, radio,verticeArbolBinario.get().getPalabra(),textColor,verticeArbolBinario);
    }

    private static void dVertice( StringBuilder builder, int valorX, int valorY, int radio,
                                 String contenido, String textColor, VerticeArbolBinario verticeArbolBinario){
        int alturaIzquierdo = -1;
        int alturaDerecho = -1;
        if(verticeArbolBinario.hayIzquierdo()){
            alturaIzquierdo = verticeArbolBinario.izquierdo().altura();
        }
        if(verticeArbolBinario.hayDerecho()){

            verticeArbolBinario.derecho().altura();
        }

        double posicionY = valorY - radio - 1.5;
        int posicionX;
        if(!verticeArbolBinario.hayPadre()|| ( verticeArbolBinario.padre().hayIzquierdo() && verticeArbolBinario.padre().izquierdo().equals(verticeArbolBinario))){

            posicionX = valorX - radio -1;
        }else {
            posicionX = valorX +radio + 1;
        }
        float fontSize = 3.5f;
        if(verticeArbolBinario.altura()==0 || (alturaIzquierdo- alturaDerecho) ==0){
            fontSize = 2f;
        }


        builder.append(" <circle cx=\""+valorX+"\" cy=\""+valorY+"\" r=\""+radio+"\" stroke='lightcoral' stroke-width=\"0.5\"  fill='lavenderblush'/>");
        builder.append("<text x=\"" + valorX + "\" y=\"" +valorY +
                "\" fill=\""+ textColor+"\" font-size=\"3.5\" style = \"text-anchor: middle\" dominant-baseline = \"middle\"\t>"
                + contenido +"</text>\n" );
        builder.append("<text x=\"" + posicionX + "\" y=\"" +posicionY +
                "\" fill=\"darkslategray\" font-size=\""+fontSize+"\" style = \"text-anchor: middle\" dominant-baseline = \"middle\"\t>"
                + "("+ verticeArbolBinario.altura() + "/"+ ( alturaIzquierdo- alturaDerecho) + ")" +"</text>\n" );


    }

    private static void dLineas(StringBuilder builder,int valorXI, int valorYI, int valorXF, int valorYF){
        builder.append("<line x1=\""+ valorXI+"\" y1=\""+valorYI+"\"" +
                " x2=\""+valorXF+"\" y2=\""+valorYF+"\" stroke=\"lightcoral\" stroke-width=\"0.5\"></line>");
    }
}

