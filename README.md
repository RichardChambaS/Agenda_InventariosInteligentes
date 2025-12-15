# Búsqueda en Java: Secuencial y Binaria

## Integrantes
* Richar Chamba
* Katheryn Contento
* Julian Vega
* Anthony Yaguana

## Objetivos de la práctica
* Implementar correctamente las variantes canónicas de **búsqueda secuencial** y **búsqueda binaria** en Java.
* Analizar y validar los algoritmos mediante **casos borde**.
* Justificar el uso de cada método de búsqueda según la **estructura de datos** utilizada (arreglos vs listas simplemente enlazadas - SLL).
--- 

## Marco teórico

### Búsqueda Secuencial
La búsqueda secuencial (o lineal) consiste en recorrer los elementos de una estructura de datos uno por uno hasta encontrar el valor deseado o llegar al final de la estructura.

**Características principales:**

* No requiere que los datos se encuentren ordenados.
* Puede aplicarse a arreglos y listas simplemente enlazadas (SLL).

### Búsqueda Binaria

La búsqueda binaria divide repetidamente todo el conjunto de datos a la mitad, descartando la parte donde no puede encontrarse el elemento buscado.

**Características principales:**

* Requiere que los datos estén previamente ordenados.
* Se aplica eficientemente sobre arreglos.

---

## Compilación y ejecución

 1. Descargar el repositorio en una ubicacion de fácil acceso.
 2. Abrir el proyecto con un IDE compatible con JAVA.
 3. Ejecutar la clase principal SearchDemo.

## Casos borde y precondiciones

  * Las búsquedas secuenciales aceptan estructuras vacías y no ordenadas.
  * La búsqueda binaria requiere estrictamente arreglos ordenados.
  * En listas SLL, se debe validar previamente si la cabeza es null.

## Discución
**¿Cuándo conviene usar búsqueda secuencial?**

La búsqueda secuencial es recomendable cuando:
 * La estructura de datos es pequeña.
 * Los datos no están ordenados.
 * Se trabaja con listas simplemente enlazadas (SLL), donde no existe acceso directo por índice.
 * El costo de ordenar los datos supera el beneficio de aplicar búsqueda binaria.

**¿Cuándo conviene usar búsqueda binaria?**

La búsqueda binaria resulta más eficiente cuando:

 * Se trabaja con arreglos ordenados.
 * El volumen de datos es grande.
 * Se realizan múltiples búsquedas sobre la misma estructura.

**Uso de centinela y en el caso “no encontrado”**

La técnica del centinela optimiza la búsqueda secuencial en arreglos al eliminar la verificación de límites dentro del bucle. Al insertar temporalmente la clave buscada como último elemento:

 * Se garantiza la finalización del bucle.
 * Se reduce el número de comparaciones.
 * En el caso de “no encontrado”, el algoritmo detecta esta situación al verificar si la coincidencia se produjo en la posición del centinela. Si ocurre allí, se concluye que el valor no estaba presente
   originalmente en el arreglo.
 * Esta técnica es especialmente útil en arreglos grandes, donde pequeñas optimizaciones en el número de comparaciones pueden representar mejoras de rendimiento apreciables.


## Tecnologías utilizadas

* Lenguaje: Java
* IDE: IntelliJ IDEA
* Sistema de control de versioes: Git: repositorio en Github

---

## Notas finales

* La elección del algoritmo depende del tamaño de la estructura y de si los datos están ordenados.
* El uso de predicados permite generalizar la búsqueda y reutilizar código.
* La técnica del centinela reduce comparaciones, optimizando la búsqueda secuencial en arreglos.
* Para mayor información y resultados se debe leer el informe que también se encuentra en este repositorio. 

