package mx.unam.ciencias.edd.proyecto3.dibujantes;

import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.proyecto3.Palabra;

/**
 * Clase encargada de dibujar los graficos de Pastel y de barras.
 */

public class DibujanteGrafico {
    public static void dibujaGrafico(StringBuilder texto, Lista<Palabra> palabras){
        StringBuilder pastel = new StringBuilder();
        StringBuilder barrasBuilder = new StringBuilder();
        int ancho = 110;
        int altura = ancho/2;
        //Informacion para pastel
        int centroY = (altura/2);
        int centroXPastel = (ancho/4);
        int radio = centroXPastel-4;
        double aumentoDeGrado =  Math.toRadians(360/(double) AuxilearGeneral.pabrasTotales(palabras));
        double anguloActual= 0;
        int contadorColor = 1;

        //Iformacion para barras.


        Lista<Palabra> listaCorta = AuxilearGeneral.cortaLista(palabras,10);
        int baseBarraY = altura -4;
        int topeBarraY = 8;
        int topeIzquierdoX = (ancho/2) +4;
        int topeDerechoX = ancho -2;
        int anchoBarra = (topeDerechoX-topeIzquierdoX)/listaCorta.getLongitud();
        int unidadAltoBarra = ((baseBarraY-topeBarraY)/ AuxilearGeneral.cantidadMaxima(listaCorta));



        pastel.append("<svg viewBox=\"0 0 "+ancho+" "+altura+"\" style=\"background: whitesmoke\" xmlns=\"http://www.w3.org/2000/svg\">\n");


        for (Palabra palabra:palabras) {

            //Codigo para grafica pastel.
            int cantidad = palabra.getRepeticiones();
            double centroXInicio = centroXPastel + Math.cos(anguloActual) * radio;
            double centroYInicio = centroY+ Math.sin(anguloActual) * radio;
            double centroXFinal = centroXPastel + Math.cos(anguloActual+(aumentoDeGrado*cantidad)) * radio;
            double centroYFinal = centroY+ Math.sin(anguloActual+(aumentoDeGrado*cantidad)) * radio;
            dibujaRebanada(pastel,centroXPastel,centroY,radio,centroXInicio,centroYInicio,centroXFinal,centroYFinal,contadorColor);
            contadorColor++;
            anguloActual += (aumentoDeGrado*cantidad);

        }
        //Codigo para gráfica de barras.
        barrasBuilder.append("<line x1=\""+topeIzquierdoX+"\" y1=\""+topeBarraY +"\" x2=\""+topeIzquierdoX+"\" y2=\""+baseBarraY+"\"\n" +
                "        stroke-width=\"0.3\" stroke-dasharray=\"2 2\" stroke=\"black\" />\n");
        barrasBuilder.append("<text x=\""+(topeIzquierdoX+2.5)+"\" y=\""+(topeBarraY+2)+"\" fill=\"black\" font-family=\"Arial, Helvetica, sans-serif\" font-size=\"3\"  style = \"text-anchor: middle\" dominant-baseline = \"middle\">"+ AuxilearGeneral.cantidadMaxima(listaCorta)+"</text>\n");

        contadorColor = 1;
        for (Palabra palabra:listaCorta) {
            int superiorY= baseBarraY-(palabra.getRepeticiones()*unidadAltoBarra);
            dibujaBarra(barrasBuilder,topeIzquierdoX,superiorY,anchoBarra,(unidadAltoBarra*palabra.getRepeticiones()),contadorColor);
            topeIzquierdoX += anchoBarra;
            contadorColor++;
        }


        pastel.append(barrasBuilder.toString());
        pastel.append("</svg>");
        texto.append(pastel.toString());
    }






    private static void dibujaRebanada(StringBuilder texto, int centroX, int centroY, int radio,
                                       double esquinaSuperiorX, double esquinaSuperiorY,
                                       double esquinaInferiorX, double esquinaInferiorY,int indiceColor){

        texto.append("<path d=\"M "+centroX+","+centroY+" L "+esquinaSuperiorX+","+esquinaSuperiorY+"  A "+radio+","+radio+" 40 0,1" +
                " "+esquinaInferiorX+","+esquinaInferiorY+" Z \" stroke=\""+ AuxilearGeneral.colorDeBase(indiceColor)+"\" fill=\""+ AuxilearGeneral.colorDeBase(indiceColor)+"\" stroke-width=\"0.5\"></path>");
    }

    private static void dibujaBarra(StringBuilder texto, int superiorX, int superiorY, int ancho,int alto , int indiceColor){

        texto.append("<rect x=\""+(superiorX+1)+"\" y=\""+superiorY+"\" width=\""+(ancho-1)+"\" height=\""+alto+"\"\n" +
                "        fill=\""+ AuxilearGeneral.colorDeBase(indiceColor)+"\" />");

    }












}
