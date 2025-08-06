## Tiendita V2.5 - Uso de DTOs con Mapper y @Builder

### ✨ Cambios respecto a versiones anteriores

En esta versión 2.5 del backend de *Tiendita*, hemos actualizado la aplicación para trabajar exclusivamente con DTOs y mapeadores (mappers), incorporando también el patrón `@Builder` de Lombok para mejorar la legibilidad y fluidez al construir objetos de respuesta.

---

### 🔍 Qué es un DTO (Data Transfer Object)

Un **DTO** es un objeto plano que se utiliza para **transferir datos** entre diferentes capas de una aplicación. Sirve como intermediario entre el mundo externo (API) y el modelo interno (entidades).

**Ventajas del uso de DTOs:**

* Evitan exponer entidades directamente.
* Permiten tener estructuras adaptadas a las necesidades de entrada/salida.
* Mejoran la seguridad y encapsulación.
* Facilitan validaciones específicas.

---

### ✅ Separación entre `Request` y `Response`

* `ClienteRequest`: utilizado para recibir datos desde el cliente (POST/PUT).
* `ClienteResponse`: utilizado para devolver datos al cliente (GET).

**Motivos para separarlos:**

* Evita que un campo como `id` sea modificado por error desde el cliente.
* Permite mostrar solo lo necesario en las respuestas (p. ej. ocultar `password`).
* Permite adaptar la estructura sin modificar la entidad.

---

### 📏 Qué es un Mapper

Un **Mapper** es una clase que se encarga de **convertir entre Entidades y DTOs**.

**Ejemplo:**

```java
public class ClienteMapper {
    public static ClienteResponse toResponse(Cliente cliente) {
        return ClienteResponse.builder()
                .id(cliente.getId())
                .nombre(cliente.getNombre())
                .email(cliente.getEmail())
                .build();
    }

    public static Cliente toEntity(ClienteRequest request) {
        Cliente cliente = new Cliente();
        cliente.setNombre(request.nombre());
        cliente.setEmail(request.email());
        cliente.setTelefono(request.telefono());
        return cliente;
    }
}
```

**Ventajas:**

* Centraliza la conversión entre capas.
* Hace el código de servicios más limpio y enfocado.
* Facilita testeo unitario.

---

### 🧀 Uso de `@Builder`

La anotación `@Builder` de **Lombok** permite construir objetos de manera más legible:

```java
ClienteResponse.builder()
  .id(1L)
  .nombre("Juan")
  .email("juan@email.com")
  .build();
```

**Ventajas:**

* Evita constructores largos y fáciles de romper.
* Permite inicialización clara y ordenada.
* Es ideal para objetos de solo lectura (como `Response`).

---

### ⚡ Cambios clave en la arquitectura

* Los `Service` y `Controller` ya no interactúan con `Cliente` directamente.
* Todo fluye a través de `ClienteRequest` y `ClienteResponse`, usando `ClienteMapper`.
* La entidad `Cliente` queda encapsulada dentro de la lógica de negocio y persistencia.

---

### ⚠️ Cuándo NO usar DTOs o Mappers

* En proyectos muy pequeños o con un solo CRUD, puede ser sobreingeniería.
* Si no se va a exponer el backend y las entidades son seguras para mostrar.

---

### 🔧 Buenas prácticas

* Siempre separar `Request` de `Response` en proyectos reales.
* Centralizar lógica de mapeo en una clase `Mapper`, no en `Service`.
* Usar `record` para DTOs porque son inmutables y más limpios.
* Usar `@Builder` en DTOs de `Response` para mayor claridad.

---

### 📅 Cuándo adoptar esta arquitectura

* Desde el inicio en aplicaciones medianas/grandes.
* Al incorporar seguridad, versionado, transformaciones de datos, etc.
* Cuando necesitas desacoplar API del modelo interno.

---

Esta versión mejora mucho la **escalabilidad**, **legibilidad** y **mantenibilidad** del código. Es un paso intermedio ideal antes de introducir validaciones más complejas, seguridad o documentación.

Próxima versión: **v3 - Seguridad con Spring Security y JWT** 🔐
