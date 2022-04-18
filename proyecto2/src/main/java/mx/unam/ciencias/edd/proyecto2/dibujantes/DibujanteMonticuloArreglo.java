package mx.unam.ciencias.edd.proyecto2.dibujantes;

import mx.unam.ciencias.edd.MonticuloArreglo;
import mx.unam.ciencias.edd.ValorIndexable;

/**
 * Clase publica ecargada de dibujar Monticulos Arreglo. Como sabemos los Monticulos Minimos
 * son arboles y arreglos a la vez, esta es la encagada del arreglo.
 */
public class DibujanteMonticuloArreglo {

    /**
     * Metodo encargado de dibujar un Monticulo Arreglo dado.
     * @param monticuloMinimo el moticulo arreglo a dibujar.
     */

    public static void dibujarArregloMonticulo(MonticuloArreglo<ValorIndexable<Integer>> monticuloMinimo ){


        int[] arreglo =  new int[monticuloMinimo.getElementos()];
        int contadoragrega = 0;

        while (contadoragrega <arreglo.length ){

            arreglo[contadoragrega] = monticuloMinimo.get(contadoragrega).getElemento();


            contadoragrega++;
        }

        StringBuilder arregloSVG = new StringBuilder("");

        int ancho = arreglo.length*20 + 20;
        int altur = (int) (40 + (Math.pow(1.3,arreglo.length)*2.2));


        arregloSVG.append("<svg viewBox=\"0 0 "+ancho+" "+altur+"\" style=\"background: whitesmoke\" xmlns=\"http://www.w3.org/2000/svg\">\n");
        arregloSVG.append("  <text x=\"10\" y=\"15\"  fill=\"maroon\"  font-size=\"10\"   >M </text>\n" +
                "   <text x=\"18\" y=\"15\"  fill=\"darkslategray\"  font-size=\"10\"   >onticulo Arreglo. </text>\n");
        int contador = 0;
        int esquinaSIanteriorx = 10;
        int inicioDeCuadro = ((altur)/2 - 2 );
        int finDeCuadro = (inicioDeCuadro + 12);
        while (contador < arreglo.length){
            dCajaBrackeys(arregloSVG,esquinaSIanteriorx,inicioDeCuadro,
                    esquinaSIanteriorx+20, finDeCuadro,arreglo[contador],"lavender","maroon");

            if((contador*2 + 1)< arreglo.length){

                int centroPartida = (esquinaSIanteriorx+10);
                int centroFinal = (2*(esquinaSIanteriorx+10));
                dCurva(arregloSVG,centroPartida, inicioDeCuadro,centroFinal,
                        inicioDeCuadro,(centroPartida+((centroFinal-centroPartida)/2)),inicioDeCuadro-((centroFinal-centroPartida)/4));
            }
            if((contador*2 + 2) < arreglo.length){

                int centroPartida = (esquinaSIanteriorx+10);
                int centroFinal = (2*(esquinaSIanteriorx+10) + 20);
                dCurva(arregloSVG,centroPartida, finDeCuadro,centroFinal,
                        finDeCuadro,(centroPartida+((centroFinal-centroPartida)/2)),finDeCuadro+((centroFinal-centroPartida)/4));
            }
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

    private static void dCurva(StringBuilder builder,int valorXI, int valorYI,
                                      int valorXF, int valorYF, int valorXCurva, int valorYCurva){
        builder.append("<path d=\"M "+ valorXI+","+valorYI+" Q "+ valorXCurva+","+valorYCurva+"  " + valorXF+"," + valorYF+"\"" +
                " style=\"stroke:darkslategray; stroke-width:0.6; fill:none;\" stroke-dasharray=\"3 , 1\"\"></path>\n");


    }








    }
