# Tiendita 🛍️ - Backend Educativo con Spring Boot

Este proyecto representa una tienda online muy simple llamada **Tiendita**, desarrollada en Java 21 con Spring Boot 3.5.4. El objetivo es ofrecer a estudiantes de bootcamp una introducción sólida a la construcción de APIs REST, buenas prácticas en backend y fundamentos de configuración en entornos modernos.

---

## 🌱 Objetivo educativo

* Comprender la estructura básica de un backend con Spring Boot.
* Implementar un CRUD completo (clientes y productos).
* Aplicar buenas prácticas: separación de capas, uso de DTOs, configuración externalizada, documentación y pruebas con Bruno/Postman.

---

## 🧬 Estructura del proyecto

```bash
src/
└── main/
    ├── java/
    │   └── com/bootcamp/
    │       ├── config/             # Configuraciones globales (WebConfig, Dotenv)
    │       ├── controller/         # Controladores REST (@RestController)
    │       ├── model/              # Entidades (JPA)
    │       ├── repository/         # Interfaces de persistencia
    │       ├── service/            # Interfaces de negocio
    │       └── service/impl/       # Implementaciones de la lógica de negocio
    └── resources/
        ├── application.yml
        ├── application-local.yml
        ├── application-dev.yml
        ├── schema.sql              # Script de creación de base de datos
        └── data.sql                # Datos de ejemplo
```

---

## 🧪 Análisis línea por línea y decisiones técnicas

### ✅ Clase principal `TienditaApplication`

```java
Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
System.setProperty("DB_URL", dotenv.get("DB_URL", ""));
```

🔹 Cargamos variables de entorno desde `.env` para ocultar credenciales (como usuario y contraseña de la base de datos) y facilitar el cambio de entorno sin recompilar.

🔹 `System.setProperty` permite usar estas variables dentro de `application.yml` como `${DB_URL}`, `${DB_USERNAME}`, etc.

---

### ✅ Anotaciones y clases clave

#### `@RestController`

Usamos `@RestController` en lugar de `@Controller` porque:

* Específico para APIs REST.
* Combina `@Controller` + `@ResponseBody`.
* Devuelve directamente objetos JSON (útil para APIs).

#### `@RequestMapping({"/api/productos", "/api/productos/"})`

🔸 Solución temporal para aceptar rutas con o sin slash.
🔸 Mejor solución: usar `WebConfig`:

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseTrailingSlashMatch(true);
    }
}
```

---

## 📦 Uso de Lombok

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
```

🔹 Lombok reduce el código repetitivo.

* `@Data` → Getters, setters, equals, hashCode, toString
* `@NoArgsConstructor` → Constructor vacío
* `@AllArgsConstructor` → Constructor con todos los campos

🔹 Evita escribir código innecesario, pero **requiere plugin en el IDE**.

⚠️ Buenas prácticas:

* No abusar de `@Data` en clases con lógica.
* Usar `@Getter` y `@Setter` si se necesita más control.

---

## 💡 Separación: `Service` vs `ServiceImpl`

🔸 `ClienteService` (interfaz): define los métodos del negocio.
🔸 `ClienteServiceImpl`: implementa la lógica concreta.

**Ventaja:**

* Facilita pruebas unitarias (se puede mockear la interfaz).
* Mejora el desacoplamiento entre capas.

---

## ⚙️ Archivos de configuración

### application.yml (base)

Define configuración común a todos los entornos:

```yaml
spring:
  datasource:
    url: ${DB_URL}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: none
    show-sql: true
```

### application-local.yml

Entorno para desarrollo local:

```yaml
spring:
  sql:
    init:
      mode: always
```

### application-dev.yml

Para un entorno de pruebas o staging. Podrías conectarte a una base de datos real o contenerizada.

---

## 🔐 Archivo `.env`

```env
DB_URL=jdbc:mysql://localhost:3306/tiendita
DB_USERNAME=root
DB_PASSWORD=
```

No debe subirse a Git (incluirlo en `.gitignore`). Ideal para manejar:

* Tokens JWT
* Claves API
* Rutas dinámicas

---

## 🛑 Qué evitar

* ❌ Escribir lógica de negocio en los controladores.
* ❌ Hacer acceso a base de datos directo desde el controller.
* ❌ Hardcodear credenciales en el código fuente.
* ❌ Duplicar rutas como solución permanente.

---

## ✅ Tips y buenas prácticas

* Usar `@Service`, `@Repository`, `@Configuration` según responsabilidad.
* Dividir las configuraciones por entorno para mayor claridad.
* Documentar los endpoints con Swagger (opcional).
* Validar datos de entrada con `@Valid` y anotaciones de `jakarta.validation`.
* Crear DTOs para desacoplar modelo de base de datos y representación externa (REST).
* Escribir pruebas unitarias para los servicios con JUnit y Mockito.

---

