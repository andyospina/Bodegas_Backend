# bodegas-backend

API REST en Spring Boot para gestionar bodegas y consultar los productos asociados a cada una.

Regla del modelo: una bodega puede tener varios productos, pero cada producto pertenece a una única bodega (relación JPA 1:N).

## Tecnologías

- Java 17 (compila en 17 y corre en 17 o superior)
- Spring Boot 4.1 (Spring Web MVC, Spring Data JPA, Bean Validation)
- H2 en memoria
- Maven (wrapper incluido)

## Requisitos

- JDK 17 o superior. Si sale `release version 17 not supported`, Maven esta usando un JDK mas viejo: revisa `mvn -v` y apunta `JAVA_HOME` a un JDK 17+
- Maven 3.9+ en Windows (en Linux/macOS se puede usar el wrapper `mvnw` incluido)

## Ejecución

```bash
# Linux / macOS
./mvnw spring-boot:run

# Windows (o cualquier sistema con Maven instalado)
mvn spring-boot:run
```

Para regenerar el wrapper completo (incluye `mvnw.cmd` para Windows):

```bash
mvn -N wrapper:wrapper
```

La API queda disponible en `http://localhost:8080`.

Al arrancar, `DatosIniciales` (un `CommandLineRunner`) carga los datos de prueba usando los repositorios.

### POST /bodegas

```bash
curl -X POST http://localhost:8080/bodegas \
  -H "Content-Type: application/json" \
  -d '{"nombre": "Bodega Centro", "ubicacion": "Carrera 7 # 32-10, Bogota"}'
```

Respuesta `201 Created`:

```json
{ "id": 3, "nombre": "Bodega Centro", "ubicacion": "Carrera 7 # 32-10, Bogota", "productos": [] }
```

Si `nombre` o `ubicacion` vienen vacíos responde `400 Bad Request` con el detalle de cada campo en `errores`.

### GET /bodegas

```bash
curl http://localhost:8080/bodegas
```

Respuesta `200 OK` (JSON anidado, sin referencias circulares):

```json
[
  {
    "id": 1,
    "nombre": "Bodega Norte",
    "ubicacion": "Calle 170 # 45-20, Bogota",
    "productos": [
      { "id": 1, "nombre": "Computador portatil", "descripcion": "Portatil de 15 pulgadas, 16 GB RAM", "precio": 2500000.00, "cantidad": 15 },
      { "id": 2, "nombre": "Monitor", "descripcion": "Monitor LED de 27 pulgadas", "precio": 750000.00, "cantidad": 20 }
    ]
  },
  {
    "id": 2,
    "nombre": "Bodega Sur",
    "ubicacion": "Autopista Sur # 60-15, Bogota",
    "productos": [
      { "id": 3, "nombre": "Impresora", "descripcion": "Impresora multifuncional laser", "precio": 1200000.00, "cantidad": 8 }
    ]
  }
]
```
## CORS

Por defecto se permite el frontend en `http://localhost:4200`. Para otro origen:

```bash
CORS_ALLOWED_ORIGINS=http://localhost:3000 ./mvnw spring-boot:run
```

## Pruebas

```bash
./mvnw test
```

`BodegaControllerIntegracionTest` verifica que GET devuelva los datos de prueba con sus productos, que POST registre una bodega y que los campos vacíos respondan 400.
