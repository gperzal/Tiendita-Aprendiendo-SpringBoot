# 🏪 Tiendita - Evolución del Backend Educativo

Este repositorio contiene la evolución de **Tiendita**, un proyecto educativo con **Spring Boot** diseñado para enseñar buenas prácticas de desarrollo backend.

Cada carpeta representa una versión del proyecto, mostrando mejoras progresivas en arquitectura, seguridad y profesionalización.

---

## 📚 Ruta de aprendizaje del proyecto

### **v1 - Backend básico**
- Primer backend educativo en Spring Boot.
- CRUD de **clientes** y **productos**.
- Buenas prácticas iniciales: separación en capas, uso de DTOs básicos, configuración por entornos.

### **v2 - Introducción de DTOs**
- Implementación de **DTOs** para Requests y Responses.
- Mejora de la seguridad al no exponer entidades directamente.
- Uso de `record` en Java para DTOs inmutables.

### **v2.5 - DTOs con Mapper y @Builder**
- Introducción de **Mappers** para convertir entidades a DTOs.
- Uso de **Lombok @Builder** para respuestas más legibles.
- Arquitectura más escalable y mantenible.

### **v3 - Búsquedas, Ordenamiento y Paginación**
- Uso de **Spring Data JPA** con `Pageable`.
- Endpoint único que soporta búsqueda, ordenamiento y paginación.
- Optimización para grandes volúmenes de datos.

### **v4 - Seguridad con JWT**
- Implementación de **Spring Security** con autenticación **JWT**.
- Roles y control de acceso a endpoints.
- Configuración modular con `.env` y buenas prácticas de seguridad.