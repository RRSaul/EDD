Estructuras de Datos
====================

Proyecto 2: Estructuras de Datos en SVG.
------------------------------

### Fecha de entrega: 8 de enero, 2021

El proyecto compila al hacer:

```
$ mvn compile
```

El proyecto no posee prubas pues se han eliminado las pruebas de las practicas para no se redundantes, no es necesario hacer:

```
$ mvn test
```

El proyecto corre el programa escrito en la clase:
[`Proyecto2`]
al ejecutar:

```
$ mvn install
...
$ java -jar target/proyecto2.jar
```

Algunas notas adicinales que quisiera agregar:

* `El programa es rubusto de manera inclusiva en ciertas ocasiones, 
principalmente cuando hay un error en un elemento, digamos, "Cola 3 4 7a 8"
en lugar de terminar omite este elemento y prosigue, asi algunas estrucutras
son mas amigables, no es el caso con graficas ya que si no es par el numero de
elementos si habra error.`,
*`El programa permite faltas de ortografia minimas, siendo estas fallar con alguna
mayuscula.`,
*`El nombre para identificar a las esctructuras el exactamente el mismo que el de las
clases usadas en las practicas, EXEPTUANDO arreglos que se usa como "Arreglo".`

