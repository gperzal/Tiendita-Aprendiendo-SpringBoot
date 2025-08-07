# 🧾 Tiendita v4 – Seguridad con JWT, Roles y Excepciones Personalizadas

En esta versión de Tiendita, se ha incorporado un módulo de seguridad completo con Spring Security 6 y JWT, incluyendo roles y manejo centralizado de excepciones.

---

## 🎯 Objetivo de esta versión

- Seguridad robusta y escalable para proteger los endpoints de la API.
- Autenticación sin estado (JWT) que permite separar el backend de cualquier frontend.
- Roles de acceso claros: ROLE_USER y ROLE_ADMIN.
- Manejo de excepciones personalizadas para respuestas JSON uniformes.
- Preparación para integración con frontend (React u otro) mediante CORS configurable.

## 🚀 Características

- Autenticación y autorización mediante JWT.
- Roles de usuario y administrador, almacenados en base de datos.
- Protección de endpoints según roles:
  - Públicos:
    - POST /auth/register
    - POST /auth/login
    - GET /api/productos/**
  - Protegidos:
    - POST|PUT|DELETE /api/productos/** → ROLE_ADMIN
    - /api/clientes/** → ROLE_USER o ROLE_ADMIN
    - /api/admin/** → ROLE_ADMIN
- Filtro JWT (JwtAuthenticationFilter) que valida el token en cada request.
- DataSeeder para inicializar automáticamente los roles ROLE_USER y ROLE_ADMIN al arrancar

---

## 📁 Estructura de paquetes final con propósito

```
com.bootcamp
├── config/                   # Configuración global y beans generales
├── controller/               # Controladores generales de la app
├── dto/                      # DTOs generales
├── exception/                # Excepciones globales y manejo centralizado
│   ├── ApiException.java            # Excepción genérica de negocio
│   ├── BadRequestException.java     # Para errores 400
│   ├── NotFoundException.java       # Para errores 404
│   └── GlobalExceptionHandler.java  # Captura excepciones y devuelve JSON uniforme
├── mapper/                   # Mappers y convertidores DTO <-> Entidad
├── model/                    # Entidades globales de negocio
├── repository/               # Repositorios JPA globales
└── security/                 # Módulo de seguridad JWT
    ├── config/               # Configuración específica de seguridad
    │   ├── SecurityConfig.java      # Define filtros, roles y reglas de acceso
    │   └── DataSeeder.java          # Inicializa roles al arrancar
    ├── controller/
    │   └── AuthController.java      # Maneja registro y login de usuarios
    ├── dto/                  # DTOs de autenticación
    │   ├── LoginRequest.java        # Entrada para login
    │   ├── LoginResponse.java       # Respuesta con token y roles
    │   └── RegisterRequest.java     # Entrada para registro
    ├── jwt/
    │   ├── JwtAuthenticationFilter.java # Valida el token en cada request
    │   └── JwtUtils.java             # Genera y valida JWT
    ├── model/
    │   ├── NombreRol.java           # Enum con roles disponibles
    │   ├── Rol.java                  # Entidad de roles
    │   └── Usuario.java              # Entidad de usuarios
    ├── repository/
    │   ├── RolRepository.java       # Acceso a roles
    │   └── UsuarioRepository.java   # Acceso a usuarios
    └── service/
        ├── AuthService.java               # Lógica de login y registro
        ├── UsuarioService.java            # Lógica de dominio para usuarios
        ├── UsuarioDetailsService.java     # Interfaz para Spring Security
        └── UsuarioDetailsServiceImpl.java # Implementación de UserDetailsService
```

### 📌 Explicación de responsabilidades

* **config/** → Contiene la configuración de Spring Security, reglas de autorización, CORS y registro de filtros para proteger rutas y definir endpoints públicos.
* **controller/** → Expone endpoints de registro y login, centralizando la interacción del cliente.
* **dto/** → Define objetos de transferencia de datos, evitando exponer entidades y protegiendo información sensible.
* **jwt/** → Implementa el filtro de autenticación y utilidades para generar/validar tokens JWT.
* **model/** → Contiene las entidades persistentes: `Usuario` y `Rol`, junto al enum `NombreRol` para roles predefinidos.
* **repository/** → Provee acceso a la base de datos para usuarios y roles.
* **service/** → Contiene la lógica principal de autenticación, validación de credenciales y carga de usuarios para Spring Security.
* **exception/** → Maneja errores y excepciones personalizadas, garantizando respuestas JSON uniformes.

---

## 🔑 Flujo de Autenticación

1. **Registro (`POST /auth/register`)**

    * Guarda el usuario con contraseña cifrada (BCrypt).
    * Asigna automáticamente el rol `ROLE_USER`.
    * Devuelve **201 Created** sin token.

2. **Login (`POST /auth/login`)**

    * Valida email y contraseña.
    * Genera un JWT firmado (HS256).
    * Devuelve JSON con `nombre`, `email`, `token` y `roles`.

3. **Autenticación con JWT**

    * Cada request protegido debe enviar:

      ```http
      Authorization: Bearer <TOKEN>
      ```
    * `JwtAuthenticationFilter` valida el token, carga roles y agrega autenticación al contexto.

4. **Excepciones personalizadas**

    * `BadCredentialsException` → 401 Unauthorized
    * `AccessDeniedException` → 403 Forbidden
    * `BadRequestException` → 400 Bad Request
    * `ApiException` y `GlobalExceptionHandler` → JSON uniforme con timestamp, status y mensaje

---

## 📄 Endpoints y Reglas de Seguridad

**Públicos:**

* `POST /auth/register`
* `POST /auth/login`
* `GET /api/productos/**`

**Protegidos:**

* `POST|PUT|DELETE /api/productos/**` → `ROLE_ADMIN`
* `/api/clientes/**` → `ROLE_USER` o `ROLE_ADMIN`
* `/api/admin/**` → `ROLE_ADMIN`

**Cualquier otro endpoint** requiere autenticación.

---

## ⚙️ Configuración `SecurityConfig`

* **CSRF**: Deshabilitado.
* **JWT stateless**: sin sesiones.
* **CORS**: configurable con `app.security.cors.dev`.
* **Orden de filtros**: `JwtAuthenticationFilter` antes de `UsernamePasswordAuthenticationFilter`.
* **Roles**: almacenados como `ROLE_USER` y `ROLE_ADMIN`.

---

## 🔒 JWT y Variables de Entorno

**`.env`**

```env
JWT_SECRET=TuSuperClaveJWTSecreta123456
JWT_EXPIRATION_MS=3600000
```

**`JwtUtils`**

* Genera token
* Extrae `username`
* Valida firma y expiración

---

## 📄 application-prod.yml

```yaml
spring:
  datasource:
    url: ${DB_URL}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}

  jpa:
    hibernate:
      ddl-auto: none
    show-sql: false

  sql:
    init:
      mode: never

app:
  security:
    cors:
      dev: false  # En producción, solo habilitar CORS para frontend confiable
```

---

## 🧪 Pruebas recomendadas

1. Registrar usuario `/auth/register` → 201 Created
2. Login `/auth/login` → copiar token JWT
3. GET `/api/productos` sin token → 200 OK
4. POST `/api/productos` con token `ROLE_USER` → 403 Forbidden
5. POST `/api/productos` con token `ROLE_ADMIN` → 200 OK
6. Acceder a `/api/clientes` sin token → 401 Unauthorized

---

## 💡 Recomendaciones

* **Inicializar roles con `DataSeeder`** al arrancar.
* **Nunca exponer password** ni siquiera hasheado.
* **Separar DTOs de entidades** para seguridad.
* **Centralizar manejo de errores con `GlobalExceptionHandler`**.
* **Agregar Refresh Tokens** si se requiere sesión prolongada.
* **Usar perfiles `local` y `prod`** con configuración de CORS diferenciada.

---

*Documentación detallada para un módulo de seguridad robusto, claro y escalable.*
