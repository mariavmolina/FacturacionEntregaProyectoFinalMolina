# Proyecto Facturación

## Descripción
Este es un proyecto de facturación desarrollado con Spring Boot. Permite gestionar clientes, productos y comprobantes de venta.

## Requisitos
- Java 11 o superior
- Maven
- Base de datos (H2 o MySQL, dependiendo de la configuración)

## Instalación

**Clonar el repositorio**

```bash
git clone <URL del repositorio>
cd <nombre del proyecto>
```

**Instalar Indepencias**
```bash
mvn install
```

## Ejecución
**Levantar la aplicación**
```bash
mvn spring-boot:run
```
**Correr los scripts**
Si tienes scripts SQL o cualquier otro tipo de inicialización, asegúrate de ejecutarlos. Estos scripts pueden estar en el directorio src/main/resources o en una ubicación especificada en la configuración de la base de datos.

**Probar con Postman**
- Agregar un producto
  Endpoint: POST /productos/agregar
  Body: { "id": 1, "nombre": "Producto1", "precio": 100.0 }
- Buscar un producto
  Endpoint: GET /productos/buscar/{id}
  URL: /productos/buscar/1
- Actualizar un producto
  Endpoint: PUT /productos/actualizar/{id}
  Body: { "nombre": "Producto1", "precio": 150.0 }
- Eliminar un producto
  Endpoint: DELETE /productos/eliminar/{id}
  URL: /productos/eliminar/1
- Crear un comprobante
  Endpoint: POST /comprobantes
Body:
```bash 
{
  "cliente": { "clienteid": 1 },
  "lineas": [
    { "producto": { "productoid": 1 }, "cantidad": 2 }
  ]
}
```

## Respuesta de la API
- Código 200: La operación se realizó de manera satisfactoria.
- Código 409: La operación no se pudo realizar. Ejemplos incluyen conflictos como falta de stock o entidad no existente.

## Consideraciones
- La modificación del precio de un producto no afectará los comprobantes ya creados.
- El sistema maneja validaciones para garantizar la integridad de los datos, como verificar la existencia de productos y clientes.
  
## Estructura del proyecto
- /controller: Controladores para manejar las solicitudes HTTP.
- /dto: Clases DTO para transferir datos.
- /exception: Excepciones personalizadas.
- /model: Clases de modelo de datos.
- /repository: Interfaces para el acceso a datos.
- /service: Lógica de negocio.










