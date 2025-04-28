Proyecto Spring Boot - [FOOD_EASY]


Este proyecto está desarrollado en Spring Boot y utiliza Maven como gestor de dependencias.

Requisitos previos
Java JDK 17 o superior (recomendado JDK 21)

Maven 3.9.x o superior

Git instalado
---------------------------------------

Instalación de Maven (si no lo tienes)

Windows

Descargar Maven desde la página oficial:

https://maven.apache.org/download.cgi

Bajar el archivo: apache-maven-3.9.9-bin.zip.

Extraerlo en una ruta como:

C:\Program Files\Apache\Maven

Configurar las variables de entorno:

Crear una variable nueva: MAVEN_HOME

Valor: ruta donde extrajiste Maven, por ejemplo:

C:\Program Files\Apache\Maven\apache-maven-3.9.9


Agregar al Path del sistema:

%MAVEN_HOME%\bin

Verificar instalación:

mvn -v


Linux (Ubuntu/Debian)

sudo apt update
sudo apt install maven
mvn -v

---------------------------------------

Instalar Dependencias

Después de clonar el proyecto, debes descargar todas las dependencias necesarias usando Maven. Esto lo puedes hacer ejecutando el siguiente comando en la raíz de tu proyecto:

mvn install

---------------------------------------

Construir el proyecto

Para construir el proyecto y generar el .jar ejecutable:

mvn clean package

El .jar se generará en la carpeta target/.

Ejecutar el proyecto

Una vez empaquetado, puedes correr el .jar:

java -jar target/[nombre-del-jar-generado].jar

---------------------------------------
Ejecutar directamente (modo desarrollo)

Si quieres correr el proyecto sin empaquetar: 

mvn spring-boot:run

---------------------------------------
Dependencias principales
Este proyecto utiliza:

Spring Boot Web

Spring Boot Data JPA

Spring Boot Security

Lombok

MySQL Connector

H2 Database (en memoria, para pruebas)

Springdoc OpenAPI (Swagger UI para documentación API)
---------------------------------------
Documentación de la API (Swagger)
Una vez la aplicación esté corriendo, puedes acceder a la documentación interactiva de los endpoints (Swagger UI) aquí:

👉 http://localhost:{puerto}/swagger-ui.html

---------------------------------------

Notas adicionales

Recuerda configurar tu base de datos (H2 o MySQL) en el archivo:

src/main/resources/application.properties
Asegúrate de tener configurado correctamente el puerto y las credenciales si usas una base de datos externa.