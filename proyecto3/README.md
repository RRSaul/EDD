Estructuras de Datos
====================

Proyecto 3: Reportes de documentos en base a sus palabras.
-------------------------

### Fecha de entrega: viernes 29 de enero, 2021

El proyecto hace un conteo de cuántas veces aparece cada palabra en el archivo, sin considerar
mayúsculas/minúsculas ni acentos. Dicho de otra forma, líquido se considera
la misma palabra que Liquidó. El conteo debe realizarse en O(n),
donde n es el número de palabras en el archivo.
Una gráfica de pastel de las palabras más comunes en el archivo y qué
porcentaje del total de palabras ocupan; el resto de las palabras se juntarán
en una sola rebanada.
Una gráfica de barras con la misma información.
Un árbol rojinegro con las 15 palabras más utilizadas en el archivo, donde el
valor de cada palabra es el número de veces que aparece en el archivo.
Un árbol AVL con los mismos datos del árbol de arriba.

Además del archivo HTML correspondiente a cada archivo especificado en la línea
de comandos, deben generar un archivo index.html con ligas a cada archivo HTML
generado, el número de palabras en cada uno, y una gráfica donde cada archivo es
un vértice, y dos archivos son vecinos si ambos contienen al menos una palabra
en común de al menos 7 caracteres cada una.
El proyecto compila al hacer:

```
$ mvn compile
```

El proyecto no posee pruebas pues se han eliminado las pruebas de las practicas para no ser redundantes, no es necesario hacer:
```
$ mvn test
```

El proyecto se instala al hacer:
```
$ mvn install
```
Y por último, el proyecto se ejecuta al hacer, ademas claro de la entrada estandar:
```
$ java -jar target/proyecto3.jar archivo1.txt archivo2.txt -o directorio archivo3.txt
```

