# jxmlDixitAll

## Resumen

jxmlDixitAll es una aplicación Java con interfaz gráfica de usuario diseñada para gestionar un diccionario de términos y sus definiciones. Proporciona funcionalidades para agregar, modificar, eliminar y consultar términos y sus definiciones asociadas. Es una versión con la capacidad de acceso a datos en ficheros con formato XML, de tal manera que pueda ser importado y exportado fácilmente.

## Características

- **Agregar Término y Definición:** Permite agregar fácilmente nuevos términos junto con sus definiciones.
- **Modificar Término:** Modificar la definición de términos existentes.
- **Eliminar Término:** Eliminar términos del diccionario.
- **Consultar Término:** Buscar definiciones de términos específicos.

## Instalación

Para usar jDixitAll, necesitas tener Java instalado en tu sistema. Puedes descargar el código fuente y compilarlo utilizando cualquier IDE de Java o compilarlo directamente desde la línea de comandos.

1. Clona el repositorio:

   ```bash
   git clone https://github.com/luisvazle/jxmlDixitAll.git
   ```
2. Compila el código fuente:

   ```bash
   javac src/jxmlDixitAll/jxmlDixitAll.java
   ```
3. Ejecuta la aplicación:

   ```bash
   java src.jxmlDixitAll.jxmlDixitAll
   ```

## Uso

Al ejecutar la aplicación, se mostrará una interfaz de usuario gráfica donde puedes realizar varias acciones:

- **Agregar:** Ingresa un término y su definición, luego haz clic en "Guardar" para agregarlo al diccionario.
- **Modificar:** Ingresa el término cuya definición quieres modificar, actualiza la definición y haz clic en "Modificar".
- **Eliminar:** Ingresa el término que deseas eliminar y haz clic en "Eliminar".
- **Consultar:** Ingresa el término que deseas buscar y haz clic en "Consultar" para ver su definición.

La primera vez que se introduzca un término, se creará una carpeta con el archivo `datos.xml`. En cualquier uso posterior, este se actualizará.

## Autor

El autor de este programa es Luís Vázquez Lema. Puedes contactar con el autor enviando un correo electrónico a la siguiente dirección: <luisvazquezlema@gmail.com>

## Licencia

Se adjunta una copia de la licencia  MIT aplicable si clona el repositorio: sería el fichero `LICENSE`. Asimismo, en las primeras líneas comentadas de cada fichero `.java` también podrá visualizar el contenido de la licencia.
