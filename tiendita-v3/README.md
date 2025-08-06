# 🧾 Tiendita v3 - Búsquedas, Ordenamiento y Paginación con Spring Data JPA

En esta versión de la app Tiendita, se ha mejorado la API para clientes incorporando **búsqueda por nombre**, **ordenamiento dinámico** y **paginación**, utilizando las capacidades nativas de **Spring Data JPA** y `Pageable`.

---

## 🎯 Objetivo de esta versión

1. Mejorar la eficiencia al trabajar con grandes volúmenes de datos.
2. Ofrecer flexibilidad para consultar, ordenar y navegar por los registros de clientes.
3. Exponer un único endpoint que permita aplicar filtros dinámicos.

---

## 🛠️ Cambios principales

### 1. ClienteRepository

```java
Page<Cliente> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
```

* Permite buscar clientes por coincidencia parcial de nombre (case insensitive).
* Integra orden y paginación gracias al uso de `Pageable`.

### 2. ClienteService

```java
Page<Cliente> buscarClientes(String nombre, Pageable pageable);
```

* Encapsula la lógica de búsqueda, manteniendo separado el controlador de la lógica de negocio.

### 3. ClienteController

```java
@GetMapping("/buscar")
public Page<Cliente> buscarClientes(
        @RequestParam(required = false, defaultValue = "") String nombre,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        @RequestParam(defaultValue = "id,asc") String[] sort)
```

* Exponemos un endpoint flexible que permite:

    * Filtrar por nombre.
    * Paginar resultados.
    * Ordenar por uno o varios campos.

---

## 🔍 Ejemplos de uso (URLs)

### 1. Buscar por nombre:

```
GET /api/clientes/buscar?nombre=juan
```

### 2. Paginación:

```
GET /api/clientes/buscar?page=1&size=10
```

### 3. Ordenamiento:

```
GET /api/clientes/buscar?sort=nombre,desc
```

### 4. Combinado:

```
GET /api/clientes/buscar?nombre=ana&page=0&size=3&sort=id,desc
```

---

## 📦 ¿Por qué usar Pageable y Sort?

✅ Centraliza lógica de búsqueda + orden + paginación.
✅ Utiliza el motor de consultas de Spring Data sin necesidad de SQL manual.
✅ Alta flexibilidad con bajo esfuerzo.

---

## 🧠 ¿Cuándo usar esta técnica?

✔️ Listas grandes (clientes, productos, órdenes, etc.)
✔️ Consultas de frontend donde se necesita scroll o paginación
✔️ Exportación parcial de datos

### ⚠️ ¿Cuándo evitarlo?

* En queries extremadamente complejas (join + agrupaciones), conviene usar `@Query` o consultas nativas.
* Si necesitas lógica condicional compleja, evalúa usar **Specifications** o **QueryDSL**.

---

## 📌 Endpoint final resumido

```http
GET /api/clientes/buscar
```

### Parámetros opcionales:

* `nombre`: filtra por nombre (contiene, sin importar mayúsculas).
* `page`: número de página (comienza en 0).
* `size`: cantidad de elementos por página.
* `sort`: campo y dirección, por ejemplo: `nombre,asc` o `id,desc`.

---

## 💡 Tips y buenas prácticas

* Usa valores por defecto seguros (`page=0`, `size=5`, `sort=id,asc`).
* Siempre valida los parámetros del cliente (tamaño máximo, orden válido).
* Ideal para combinar con **DTOs** para evitar exponer entidades directamente.
* Evita exponer ordenamiento por campos sensibles (como `password` o `email`).

---

¿Listo para escalar tu API? Este patrón es profesional, flexible y muy mantenible para cualquier aplicación empresarial.

Próximo paso: `v4 - Seguridad con Spring Security y JWT 🔐`
