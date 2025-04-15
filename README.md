# AppChat
<div align="center">

<img src="src\main\resources\images\appchat_logo.png" alt="AppChat Logo" width="250"/>

### Plataforma para el envío de mensajes.

[![Estado](https://img.shields.io/badge/Estado-En_Desarrollo-yellow.svg)](https://github.com/teerueel/AppChat.git)
[![Java](https://img.shields.io/badge/Java-23-orange.svg)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Maven-3.9.9-blue.svg)](https://maven.apache.org/)

## Descripción

<div align="center">
  
**AppChat** es una aplicación de mensajería instantánea diseñada para facilitar la comunicación en tiempo real entre usuarios. Con una interfaz intuitiva y fácil de usar, AppChat permite enviar y recibir mensajes de texto, buscar mensajes o incluso exportar conversaciones a PDF con su funcionalidad **PREMIUM**

</details>

## Equipo de Desarrollo

<table>
  <tr>
    <td align="center"><a href="https://github.com/teerueel"><img src="https://github.com/identicons/teerueel.png" width="100px;" alt=""/><br /><sub><b>Antonio Teruel Ruiz</b></sub></a></td>
  </tr>
</table>

## Ejecución del proyecto

<div align="center">
<img src="https://img.shields.io/badge/Maven-Requerido-1565C0?style=for-the-badge&logo=apache-maven" alt="Maven Requerido"/>
</div>

<details open>
<summary><b>Requisitos previos</b></summary>
<br/>

- [Java JDK 23](https://www.oracle.com/java/technologies/downloads/) o superior
- [Maven](https://maven.apache.org/install.html) 3.8.1 o superior
- Conexión a internet (para la primera compilación)

</details>

### Compilar el proyecto

```bash
mvn clean compile
```

### Ejecutar la aplicación

```bash
mvn exec:java
```

También puedes crear un .jar ejecutable y luego ejecutarlo:

```bash
mvn package
java -jar target/pds2025-plataforma-aprendizaje-1.0-SNAPSHOT.jar
```

---

<div align="center">
<i>Proyecto desarrollado para la asignatura de Tecnologías de Desarrollo de Software</i>
</div>
