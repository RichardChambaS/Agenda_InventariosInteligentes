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

  * 1. Descargar el repositorio en una ubicacion de fácil acceso.
  * 2. Abrir el proyecto con un IDE compatible con JAVA.
  * 3. Ejecutar la clase principal SearchDemo.

## Casos borde y precondiciones

  * Las búsquedas secuenciales aceptan estructuras vacías y no ordenadas.
  * La búsqueda binaria requiere estrictamente arreglos ordenados.
  * En listas SLL, se debe validar previamente si la cabeza es null.

## Discución



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

