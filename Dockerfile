#Virtualizacion de servicios concretos
FROM maven:3.9.6-eclipse-temurin-21

#Directorio de trabajo para la aplicacion
WORKDIR /spring
#Se va a copiar en absoluto lo de la carpeta o directorio de la aplicacion
COPY . .
#Instala las dependencias

RUN mvn clean install -DskipTests

#Ejecuta el comando para poder correr la aplicacion
CMD mvn spring-boot:run -DskipTests