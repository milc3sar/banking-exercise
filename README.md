# Proyecto de Banca Digital - Arquitectura de Microservicios

Este proyecto es una solución de banca digital desarrollada como parte de una prueba técnica. La aplicación está construida siguiendo una arquitectura de microservicios, aplicando principios de Clean Architecture y Domain-Driven Design (DDD).

La solución se divide en dos microservicios principales:
* **`ms-clientes`**: Gestiona toda la información relacionada con clientes y personas (CRUD de clientes).
* **`ms-cuentas`**: Administra las cuentas bancarias y los movimientos asociados a ellas (Creación de cuentas, registro de movimientos y generación de reportes).

La comunicación entre ambos servicios se realiza de forma asíncrona a través de un bus de mensajes con **RabbitMQ** para garantizar el desacoplamiento y la resiliencia del sistema.

## 🚀 Tecnologías Utilizadas

-   **Lenguaje**: Java 21
-   **Framework**: Spring Boot 3
-   **Acceso a Datos**: Spring Data JPA / Hibernate
-   **Base de Datos**: PostgreSQL
-   **Migraciones de BD**: Flyway
-   **Mensajería Asíncrona**: RabbitMQ
-   **Contenerización**: Docker y Docker Compose
-   **Pruebas**:
    -   **Unitarias y de Integración**: JUnit 5, Mockito, Testcontainers
    -   **Pruebas de API (E2E)**: Karate DSL

## ✅ Prerrequisitos

Para levantar y ejecutar este proyecto, necesitas tener instalado:

-   [Docker](https://www.docker.com/get-started)
-   [Docker Compose](https://docs.docker.com/compose/install/) (generalmente incluido con Docker Desktop)
-   Java 21 (Opcional, solo si deseas ejecutar los servicios fuera de Docker)
-   Gradle (Opcional, para ejecutar comandos localmente)

## ⚙️ Cómo Levantar el Entorno Completo

La forma más sencilla de ejecutar la aplicación es utilizando Docker Compose, que levantará todos los servicios, bases de datos y el bus de mensajería necesarios.

1.  **Clona el repositorio** (si aún no lo has hecho):
    ```bash
    git clone https://github.com/milc3sar/banking-exercise
    cd banking-exercise
    ```

2.  **Construye y levanta los contenedores**:
    Desde la raíz del proyecto, ejecuta el siguiente comando. Este construirá las imágenes de los microservicios y levantará toda la infraestructura.
    ```bash
    docker-compose -f deploy/docker-compose.yml up --build
    ```
    Espera a que todos los servicios se inicien correctamente. Verás los logs de `ms-clientes` y `ms-cuentas` en tu terminal.

Una vez finalizado, los servicios estarán disponibles en:
* **Microservicio Clientes**: `http://localhost:8081`
* **Microservicio Cuentas**: `http://localhost:8082`
* **RabbitMQ Management UI**: `http://localhost:15672` (user: `guest`, pass: `guest`)

## 🧪 Cómo Ejecutar las Pruebas

El proyecto cuenta con un set completo de pruebas unitarias, de integración y de API.

### Ejecutar todas las pruebas (Unitarias y de Integración)

Puedes ejecutar todas las pruebas de un microservicio específico utilizando el wrapper de Gradle.

**Para `ms-clientes`:**
```bash
cd ms-clientes
./gradlew test
```

**Para `ms-cuentas`:**
```bash
cd ms-cuentas
./gradlew test
```

### Ejecutar las Pruebas de API con Karate

Las pruebas de extremo a extremo (E2E) están implementadas con Karate DSL en el microservicio `ms-clientes`. Estas pruebas validan el flujo completo, desde la creación de un cliente hasta la realización de movimientos en su cuenta.

**Requisito**: El entorno completo debe estar levantado con `docker-compose` como se indicó anteriormente.

Para ejecutar las pruebas de Karate, utiliza el siguiente comando:
```bash
cd ms-clientes
./gradlew test --tests "com.milcesar.msclientes.karate.KarateTestRunner"
```
Al finalizar, se generará un reporte detallado en `ms-clientes/build/karate-reports/karate-summary.html`.

## 🏛️ Arquitectura y Consideraciones de Diseño

La solución fue diseñada contemplando los siguientes factores clave para un sistema robusto y profesional:

#### Escalabilidad
El diseño basado en microservicios permite que `ms-clientes` y `ms-cuentas` se escalen de forma independiente. Si el servicio de cuentas recibe una carga transaccional alta, se pueden desplegar más instancias de `ms-cuentas` sin afectar el servicio de clientes.

#### Resiliencia
La comunicación asíncrona mediante **RabbitMQ** es fundamental para la resiliencia. Si `ms-clientes` no está disponible, los eventos de actualización de clientes quedan en cola para ser procesados más tarde. Además, `ms-cuentas` mantiene una **caché de clientes**, lo que le permite seguir operando (ej. crear cuentas para clientes activos conocidos) incluso si `ms-clientes` está temporalmente caído, evitando una falla en cascada.

#### Rendimiento
Para operaciones críticas como el registro de movimientos, se utiliza un **bloqueo pesimista a nivel de base de datos (`PESSIMISTIC_WRITE`)** sobre la fila de la cuenta. Esto garantiza la consistencia del saldo en escenarios de alta concurrencia, previniendo condiciones de carrera al debitar o acreditar fondos.

## 📖 Endpoints Principales de la API

A continuación, se listan algunos de los endpoints más importantes. Para ver un flujo completo, consulta el archivo de pruebas de Karate (`BankingFlow.feature`).

#### Microservicio Clientes (`http://localhost:8081`)

-   `POST /api/clientes`: Crea un nuevo cliente.
-   `GET /api/clientes`: Lista los clientes existentes.
-   `GET /api/clientes/{clienteId}`: Obtiene los detalles de un cliente.
-   `PATCH /api/clientes/{clienteId}/estado`: Activa o inactiva un cliente.

#### Microservicio Cuentas (`http://localhost:8082`)

-   `POST /api/cuentas`: Crea una nueva cuenta para un cliente.
-   `POST /api/movimientos`: Registra un nuevo movimiento (depósito o retiro).
-   `GET /api/reportes?cliente={clienteId}&desde={fechaDesde}&hasta={fechaHasta}`: Genera un estado de cuenta para un cliente en un rango de fechas.