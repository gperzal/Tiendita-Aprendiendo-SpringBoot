# 📌 Tiendita v6 – Testing con H2 y Maven

En la versión **V6** de **Tiendita**, el foco está en **automatizar pruebas** (unitarias y de integración) y aprender a **trabajar con H2** como base de datos para testear.

---

## 🎯 Objetivos

- Tener **tests de controlador** rápidos y aislados.
- Tener **tests de integración** que validen el flujo completo con base de datos.
- Probar **H2 en memoria** (volátil) y **H2 persistente** (archivo).
- Integrar la ejecución con **Maven** y poder inspeccionar datos reales.

---

## 📂 Estructura de tests

Los tests viven en:

```
src/test/java
  ├── com.bootcamp.security.controller
  │     └── AuthControllerTest.java      # Unitario (slice web)
  └── com.bootcamp.security.integration
        └── AuthIntegrationTest.java     # Integración con BD
```

---

## 🧪 Tipos de test

### 1️⃣ `AuthControllerTest` (Unitario / Web Slice)

- Anotación:
  ```java
  @WebMvcTest(controllers = AuthController.class)
  @AutoConfigureMockMvc(addFilters = false)
  ```
- Objetivo: probar **solo la capa web** (`MockMvc`) sin levantar BD ni seguridad real.
- Mocks: se simula `AuthService` con `@MockitoBean`.
- Rápido: no levanta todo Spring Boot.

---

### 2️⃣ `AuthIntegrationTest` (Integración con H2)

- Anotación:
  ```java
  @SpringBootTest
  @AutoConfigureMockMvc
  @ActiveProfiles("test")
  ```
- Objetivo: levantar la aplicación completa (web, service, repository) y probar la persistencia real.
- Usa **H2 en archivo** para que los datos se puedan inspeccionar después.

---

## 🗄️ Configuración H2

En `src/test/resources/application-test.yml`:

### 📍 H2 Persistente (archivo)
Guarda los datos y permite abrir la BD luego en DBeaver:
```yaml
spring:
  datasource:
    url: jdbc:h2:file:./data/tiendita-test;MODE=PostgreSQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
    driver-class-name: org.h2.Driver
    username: sa
    password:
  jpa:
    hibernate:
      ddl-auto: update
```
📌 Ruta real en Windows:
```
E:/Curso Java/SB/tiendita-v6/data/tiendita-test.mv.db
```

### 📍 H2 Volátil (memoria)
La BD vive solo mientras corre el test:
```yaml
spring:
  datasource:
    url: jdbc:h2:mem:tiendita;MODE=PostgreSQL;DB_CLOSE_DELAY=-1
    driver-class-name: org.h2.Driver
    username: sa
    password:
```

---

## 🚀 Ejecución con Maven

Ejecutar un test específico:
```bash
mvn test -Dtest=AuthControllerTest
```
Ejecutar todos los tests:
```bash
mvn test
```
Forzar perfil `test` en todos los tests (crea un `application.yml` en `src/test/resources` con):
```yaml
spring:
  profiles:
    active: test
```

---

## 🔍 Ver datos en DBeaver (H2 persistente)

1. Nueva conexión → **H2 Embedded**
2. URL:
```
jdbc:h2:file:E:/Curso Java/SB/tiendita-v6/data/tiendita-test
```
3. Usuario: `sa` – Password: *(vacío)*
4. Probar conexión → Finish.
5. Consultar:
```sql
SHOW TABLES;
SELECT * FROM USUARIO;
```

---

## 💡 Buenas prácticas

- **Unitarios** (`@WebMvcTest`) para respuestas rápidas y validación de lógica HTTP.
- **Integración** (`@SpringBootTest`) para validar flujo real con BD y seguridad.
- Separar **perfiles** (`test`, `local`, `prod`) para aislar configuraciones.
- Si quieres inspeccionar datos luego, usa H2 en **archivo** y `ddl-auto: update`.
- Ignorar carpeta `/data` en `.gitignore` para no subir la BD.
