# Tiendita V2 - Uso de DTOs en Spring Boot

En esta versión de **Tiendita**, actualizamos nuestra aplicación para aplicar el patrón DTO (Data Transfer Object) con buenas prácticas de arquitectura limpia y desacoplamiento. Esto mejora la claridad del código, la mantenibilidad y la seguridad.

---

## 🤸‍♂️ ¿Qué es un DTO y por qué lo usamos?

Un **DTO (Data Transfer Object)** es un objeto que se utiliza para **transportar datos** entre procesos, en este caso entre el **frontend**, el **controller**, la **capa de servicio** y la **base de datos**.

**Ventajas:**

* Evita exponer entidades directamente (protege la estructura interna).
* Facilita validaciones específicas para inputs o outputs.
* Permite cambiar la entidad sin afectar al cliente (React, API, Swagger, etc).
* Mejora la legibilidad y responsabilidad de cada clase.

---

## ♻️ ¿Por qué usamos `record`?

Java `record` es una estructura inmutable que reemplaza clases DTO tradicionales:

```java
public record ProductoRequest(String nombre, BigDecimal precio, int stock) {}
```

**Ventajas del `record`:**

* Sintaxis concisa.
* Genera automáticamente constructor, getters, `equals`, `hashCode`, `toString`.
* Inmutabilidad (ideal para Request/Response).

**No eliminamos Lombok**. Lo seguimos usando en entidades como `Producto.java` porque se requiere mutabilidad y anotaciones como `@Getter` y `@Setter`.

---

## 📃 Separación: `Request` y `Response`

**ProductoRequest.java:** DTO para entradas (crear o actualizar)

```java
public record ProductoRequest(String nombre, BigDecimal precio, int stock) {}
```

**ProductoResponse.java:** DTO para salidas (listar o consultar)

```java
public record ProductoResponse(Long id, String nombre, BigDecimal precio, int stock) {}
```

**¿Por qué separar?**

* Para evitar que el cliente controle el `id` u otros campos sensibles.
* Porque los datos de entrada y salida **tienen propósitos distintos**.
* Permite mayor control y flexibilidad.

---

## 🚀 Estructura profesional en servicios

**ProductoService.java**

* Interface que define el contrato para los servicios.
* Contiene métodos con DTOs como parámetros y retorno.

**ProductoServiceImpl.java**

* Implementa la lógica usando las entidades.
* Hace la conversión: `Producto <-> ProductoResponse`
* Utiliza un método privado `toResponse()` para centralizar la conversión.

**Nota:** En esta versión, por simplicidad, mantenemos `ProductoService` y `ProductoServiceImpl` en el **mismo package** (`com.bootcamp.service`).

---

## 💡 Buenas prácticas aplicadas

* ✅ Usar `DTO` para inputs y outputs.
* ✅ Usar `record` en Java para objetos inmutables simples.
* ✅ No exponer directamente entidades.
* ✅ Devolver siempre `ResponseEntity` en el controller.
* ✅ Capturar errores con `orElseThrow()` para evitar `null`.
* ✅ Utilizar `@RestController` (en lugar de `@Controller`) para APIs REST.
* ✅ Usar `@RequestMapping({"/api/productos", "/api/productos/"})` temporalmente si se desea aceptar rutas con o sin `/`. **(Sugerencia: usar mejor `WebMvcConfigurer` en WebConfig).**

---

## 🌐 ¿Por qué usamos Dotenv?

```java
Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
System.setProperty("DB_URL", dotenv.get("DB_URL", ""));
```

Para **separar configuraciones según entorno (local/dev/prod)**:

* `.env` contiene variables como `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`.
* `application-local.yml` las consume usando `${}`.
* Es una forma de evitar hardcodear contraseñas y configuraciones.

---

## 📂 Configuración dividida: base, local, dev

**application.yml** (base):
Contiene configuraciones comunes para todos los entornos.

**application-local.yml**: Configuración de desarrollo local.
**application-dev.yml**: Pensado para entornos staging o pruebas.

Esto nos permite:

* Cambiar entre entornos con `--spring.profiles.active=local`
* Evitar conflictos o errores entre ambientes.

---

## ⚠️ Errores comunes a evitar

* No exponer `Entity` directamente desde el controller.
* No mezclar validaciones en la entidad con las del DTO (separar responsabilidades).
* Evitar `System.out.println` para logs (usa `Logger`).
* No colocar lógica de negocio en el controller (usa `Service`).
* No manejar rutas con y sin `/` sin configurar adecuadamente.

---

## 📄 Conclusión

Esta versión profesionaliza el backend de **Tiendita** y prepara el camino para:

* v3: Autenticación con JWT
* v4: Documentación con Swagger

El uso de `DTOs`, `records`, `Dotenv` y una arquitectura clara **mejora la escalabilidad, mantenibilidad y seguridad** del proyecto.

---

🚀