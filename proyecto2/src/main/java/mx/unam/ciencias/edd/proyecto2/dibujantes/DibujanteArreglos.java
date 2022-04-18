package mx.unam.ciencias.edd.proyecto2.dibujantes;

/**
 * Clase publica encargada de dibujar arrelos, este es un extra para asi tener todas las
 * estructuas vistas, incluyendo esta la mas simple.
 */

public class DibujanteArreglos {
    /**
     * Metodo encargado de dibujar un arreglo dado.
     * @param arreglo el arreglo a dibujar.
     */

    public static void dibujarArreglo(int[] arreglo){

        StringBuilder arregloSVG = new StringBuilder("");
        int ancho = arreglo.length*20 + 20;
        int altur = 52;

        arregloSVG.append("<svg viewBox=\"0 0 "+ancho+" "+altur+"\" style=\"background: whitesmoke\" xmlns=\"http://www.w3.org/2000/svg\">\n");
        arregloSVG.append("  <text x=\"10\" y=\"15\"  fill=\"midnightblue\"  font-size=\"10\"   >A </text>\n" +
                "   <text x=\"17\" y=\"15\"  fill=\"darkslategray\"  font-size=\"10\"   >rreglo. </text>\n");
        int contador = 0;
        int esquinaSIanteriorx = 10;
       while (contador < arreglo.length){
           dCajaBrackeys(arregloSVG,esquinaSIanteriorx,20,
                   esquinaSIanteriorx+20, 32,arreglo[contador],"lavender","midnightblue");
           esquinaSIanteriorx =  esquinaSIanteriorx +20;
           contador++;
       }
       arregloSVG.append("</svg>");
        System.out.println(arregloSVG);
    }
    private static void dCajaBrackeys(StringBuilder builder,int esquinaISx, int esquinaISy,
                                     int esquinaDIx, int esquinaDIy, int contenido,String fillColor,String textColor){

        builder.append("<rect x =\"" + (esquinaISx+1) + "\"");
        builder.append(" y =\"" + esquinaISy + "\" ");
        builder.append("height =\"" + (esquinaDIy-esquinaISy) +"\" width = \""
                + ((esquinaDIx-esquinaISx)-1) +"\" style =\" fill: "+fillColor+"\"/>\n");

        builder.append("<path d=\"" +
                "M" + (esquinaISx+((esquinaDIx-esquinaISx)/3)) + "," + esquinaISy +
                " L" + (esquinaISx+1) + ","+ esquinaISy +
                " L" + (esquinaISx+1) + ","+ esquinaDIy +
                " L" + (esquinaISx+((esquinaDIx-esquinaISx)/3)) + ","+ esquinaDIy +
                " M" + (esquinaISx+(2*(esquinaDIx-esquinaISx)/3)) + ","+ esquinaDIy +
                " L" + (esquinaDIx-1) + ","+ esquinaDIy +
                " L" + (esquinaDIx-1) + ","+ esquinaISy +
                " L" + (esquinaISx+(2*(esquinaDIx-esquinaISx)/3)) + ","+ esquinaISy
                +
                " \"");
        builder.append("style=\"stroke:darkslategray; stroke-width:1; fill:none;\"></path>\n");

        builder.append("<text x=\"" + ((esquinaISx+(esquinaDIx-esquinaISx)/2)-0.5) + "\" y=\"" +(esquinaISy+(esquinaDIy-esquinaISy)/2) +
                "\" fill=\""+ textColor+"\" font-size=\"5\" style = \"text-anchor: middle\" dominant-baseline = \"middle\"\t>"
                + contenido +"</text>\n" );
    }


}
