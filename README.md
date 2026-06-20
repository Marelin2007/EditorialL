# Editorial

Aplicación de escritorio en **Java (Swing)** con arquitectura por capas para la gestión de una editorial: usuarios, categorías y promociones, con persistencia en **SQL Server**.

## Estructura del proyecto

```
src/main/java/org/example/
├── datos/          # Capa de acceso a datos (DAO) y conexión a BD
│   ├── ConexionDB.java
│   ├── CategoriaDAO.java
│   ├── PromocionDAO.java
│   └── UsuarioDAO.java
├── dominio/        # Entidades de negocio
│   ├── Categoria.java
│   ├── Promocion.java
│   └── Usuario.java
├── presentacion/   # Interfaces gráficas (Swing)
│   ├── LoginForm.java
│   ├── MainForm.java
│   ├── FrmCategorias.java
│   └── FrmPromociones.java
└── Main.java       # Punto de entrada de la aplicación
```

## Tecnologías

- Java 21
- Maven
- Microsoft SQL Server (driver `mssql-jdbc`)
- Swing (interfaz gráfica de escritorio)

## Requisitos previos

- JDK 21
- Maven 3.8+
- SQL Server con la base de datos `ProyectoDB`

## Configuración de la base de datos

La cadena de conexión se encuentra en `src/main/java/org/example/datos/ConexionDB.java`. Ajusta el host, usuario y contraseña según tu entorno antes de ejecutar el proyecto.

## Cómo ejecutar

```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="org.example.Main"
```

O bien, importa el proyecto como un proyecto Maven en IntelliJ IDEA y ejecuta la clase `Main`.

## Autor

Proyecto académico desarrollado como parte de una tarea de programación orientada a objetos / bases de datos.
