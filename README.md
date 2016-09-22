# DevAge

## Versión 1.2.0.0

## Developers Agenda Android App. Curso de Perfeccionamiento Android. EII UEX.

### Novedades versión 1.2.0.0:

> Corregida la sincronización entre la persistencia local y el servicio web.

> Corregida la salida inesperada de la aplicación al pulsar el botón atras.

> En desarrollo la función borrar developer.

### Implementaciones.

> Base de datos SQLite para la persistencia local.

> Servicio Web Api RESTful desarrollada en PHP, MySQL y Json.

> Fuente de iconos Material Design.

> Logotipo e imagenes de títulos diseñadas por el autor.

> Lib ``com.android.support:design:24.2.1`` para Design Support Library.

> Lib ``com.android.volley:volley:1.0.0`` para comunicación.

### Descripción.

La aplicación DevAge consiste en una App Android escrito en Java. La finalidad
de la aplicación es la creación de una red de desarrolladores de diferentes
especialidades, compartiendo un mismo interes común, facilitando la comunicación
entre ellos. Se podrá acceder a un listado con los desarrolladores, consultar
los datos completos de uno en particular, buscar uno por su DNI o ingresar nuevos
cumplimentando un formulario.

Se han diseñado dos bases de datos. Una contiene los usuarios de la aplicación,
para realizar login en la misma, y almacenando los nuevos usuarios que se puedan
registrar. La segunda contiene la lista común de desarrolladores. La base de
datos de usuarios permanece en el servidor web, en cambio la de desarrolladores
se sincroniza con la base de datos local SQLite para agilizar el uso de la aplicación.

El acceso se realiza a través del servicio web, facilitando un correo electrónico
y una clave de acceso, la cual se encripta con sha1, y se envía, junto con la dirección
de correo electrónico, al servicio web, en formato Json mediante POST. La Api se
encarga de identificar las operaciones y redireccionar a los métodos oportunos
para conseguir realizar un login o un signup. Para tal efecto, se han diseñado
pantallas de login y signup con sus respectivas validaciones.

Realizado el login en la app, se produce la sincronización de la base de Datos
del servidor web con la base de datos local SQLite, asignando la versión inicial
a la base de datos SQLite. Cada vez que se actualice la base de datos local,
debido al ingreso de un nuevo developer, se deberá realizar una sincronización
manual, o automática al reiniciar la aplicación.

Se ha redirigido el botón atras con un cuadro de diálogo para que el usuario
confirme si desea salir de la aplicación o permanecer en ella.

La aplicación Android se ha desarrollado en el lenguaje de programación Java, con
la ayuda del IDE **Android Studio** con **Gradle**.

### Dependencias.

Icono e imágenes de títulos de la aplicación:

> [DevAge] (http://www.antoniohorrillo.com)

Iconos Material Desing:

> [Iconos Material Desing] (https://design.google.com/icons/)

Librería Design Support Library:

> [Design] (https://developer.android.com/topic/libraries/support-library/features.html)

Librería Volley:

> [Volley] (https://developer.android.com/training/volley/index.html)

Base de Datos SQLite:

> [SQLite] (https://sqlite.org/)

### Capturas.

Portada:

![Portada](https://dl.dropboxusercontent.com/u/3193442/Proyectos/devage01.png)

Login:

![Login](https://dl.dropboxusercontent.com/u/3193442/Proyectos/devage02.png)

Registro Usuario:

![Registro Usuario](https://dl.dropboxusercontent.com/u/3193442/Proyectos/devage03.png)

Menú Lateral:

![Menú Lateral](https://dl.dropboxusercontent.com/u/3193442/Proyectos/devage04.png)

Listado Desarrolladores:

![Listado Desarrolladores:](https://dl.dropboxusercontent.com/u/3193442/Proyectos/devage05.png)

Registro Desarrollador:

![Registro Desarrollador](https://dl.dropboxusercontent.com/u/3193442/Proyectos/devage06.png)

Buscar Desarrollador:

![Buscar Desarrollador](https://dl.dropboxusercontent.com/u/3193442/Proyectos/devage07.png)

Confirmación al Salir:

![Confirmación al Salir:](https://dl.dropboxusercontent.com/u/3193442/Proyectos/devage08.png)

### Licencia.

**GNU GENERAL PUBLIC LICENSE** Version 3, 29 June 2007. Ver el archivo LICENSE.

DevAge.
