# 🧾 Tiendita v5 – Documentación con Swagger y OpenAPI

En esta versión de **Tiendita**, nos enfocamos en **documentar la API** usando **Swagger (Springdoc OpenAPI 3)** de manera profesional y modular.

---

## 🎯 Objetivo de la V5

* Incorporar **Swagger UI** para visualizar y probar los endpoints.
* Generar **documentación OpenAPI** en formato JSON/YAML.
* Mantener **YAML modular** por dominio: `clientes`, `productos`, `usuarios`.
* Permitir que la documentación sea **pública**, sin requerir token JWT.

---

## ⚙️ Integración con Swagger / Springdoc OpenAPI

1. **Dependencia Maven**

```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.8.9</version>
</dependency>
```

2. **Clase de configuración `OpenApiConfig`**

```java
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Tiendita API")
                        .version("1.0.0")
                        .description("Documentación de la API Tiendita v5 con módulos de Productos, Clientes y Usuarios"));
    }
}
```

3. **Rutas de Swagger y OpenAPI**

```
http://localhost:8080/swagger-ui.html
http://localhost:8080/swagger-ui/index.html
http://localhost:8080/v3/api-docs
http://localhost:8080/v3/api-docs.yaml
http://localhost:8080/openapi.yaml  # Si sirves el YAML modular
```

4. **Permitir acceso público en `SecurityConfig`**

```java
.requestMatchers(
    "/swagger-ui.html",
    "/swagger-ui/**",
    "/v3/api-docs/**",
    "/v3/api-docs.yaml",
    "/openapi.yaml"
).permitAll()
```

---

## 📄 Estructura de Documentación OpenAPI

```
src/main/resources/openapi/
├── openapi.yaml        # Documento principal
├── clientes.yaml       # Endpoints de clientes
├── productos.yaml      # Endpoints de productos
└── usuarios.yaml       # Endpoints de usuarios
```

* `openapi.yaml` define la info global y puede referenciar módulos.
* Cada módulo incluye sus **paths**, **schemas** y **responses**.
* Se puede combinar automáticamente usando scripts (`combine_openapi.py`).

---

## 🧪 Pruebas recomendadas

1. Inicia la aplicación.
2. Accede a `http://localhost:8080/swagger-ui.html` para la UI.
3. Revisa `http://localhost:8080/v3/api-docs` para el JSON generado.
4. Si usas YAML modular, verifica `http://localhost:8080/openapi.yaml`.
5. Probar endpoints desde Swagger, incluyendo los protegidos con JWT.

---

## 💡 Recomendaciones finales

* Mantener la documentación **actualizada por módulo**.
* Exponer Swagger solo en entornos **dev/staging** si tu API es pública.
* Para producción, considerar un **API Gateway** que sirva la documentación.

---

