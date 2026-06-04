

# Taberna del Groschen - Distributed Order Management System

A medieval-themed, decentralized restaurant management system engineered in Java. This project transitions a traditional manual operational workflow into a high-performance **Client-Server Architecture** communicating over **TCP/IP Sockets**.

<img width="847" height="609" alt="image" src="https://github.com/user-attachments/assets/1f98d75a-bd31-4f7b-8b7c-4d8fb167e38a" />



---

## Key Architectural Features

* **Decoupled Architecture (Client-Server):** The frontend user interface never interacts directly with the storage engine. Instead, it serializes lightweight Data Transfer Objects (DTOs) across network streams to a dedicated backend controller.
* **Pure TCP/IP Sockets:** Implements low-level network communication via `java.net.ServerSocket` and `java.net.Socket`, establishing dedicated request-response tunnels on port `40008` to handle clients sequentially.
* **Embedded Relational Storage:** Backed by a normalized **SQLite** database managed on the server side via JDBC transactions to guarantee data integrity during concurrent requests.

---

## Interface Preview

<img width="931" height="627" alt="login" src="https://github.com/user-attachments/assets/251a3aae-d997-4eb5-9105-eb0e407e4b18" />
<img width="866" height="519" alt="menu" src="https://github.com/user-attachments/assets/f1103bc0-5c84-4fed-a5a2-b3f7f17db545" />



---

## Tech Stack

* **Language:** Java (JDK 11+)
* **Build System:** Maven
* **Database Engine:** SQLite JDBC Driver
* **Graphical Interface:** Java Swing (Thematic Canvas Layout with Custom Embedded Fonts)
* **Networking:** Native Java Sockets API

---

## Class Diagram & Architecture

The following diagram illustrates the relationship between our structural models, the decoupled query executors, and the network DTO handlers:

<img width="1515" height="682" alt="diagram" src="https://github.com/user-attachments/assets/6757d983-2647-4ecb-b2bd-5fe4b55e5399" />


---

## Design Patterns & Clean Code Implementation

This project was built following strict Object-Oriented Design Principles (SOLID) and incorporates key Enterprise Design Patterns:

1. **Decorator Pattern:** Leveraged to dynamically inject runtime food menu modifiers (e.g., adding dynamic prices for extra toppings or Bohemian side dishes) to a core `Producto` instance without breaking the *Open/Closed Principle*.
2. **State Pattern:** Governs the asynchronous finite lifecycle transitions of a customer order (*En Barra* $\rightarrow$ *En Forja* $\rightarrow$ *Entregado*), encapsulating behavioral shifts inside dedicated state objects instead of standard conditional flows.
3. **Singleton Pattern:** Restricts the media streaming pipeline to a single execution thread, optimizing CPU performance for continuous audio playback.
4. **Data Transfer Object (DTO):** Unified network contracts handled through the `PeticionTaberna` entity, ensuring data packets are easily serializable across local or remote infrastructure.

---

## Repository Topology

```text
├── src/main/java
│   ├── conexion/         # Relational database connectors and JDBC drivers
│   ├── consultas/        # Core Query Engines (Client network wrappers & Server SQL execution)
│   ├── modelo/           # Serializable domain objects and entities
│   └── servidor/         # Central Server network event-loop listener
└── src/main/resources/   # Application assets: custom fonts, UI components, and raw audio clips

```

---

## Quick Start (Local Deployment)

### 1. Fire up the Central Server

The centralized storage engine must be initialized first to listen for inbound socket requests:

```bash
mvn clean install
java -cp target/classes servidor.ServidorTaberna

```

### 2. Boot the Client Application

Launch another terminal instance or execute the main class from your IDE wrapper:

```bash
java -cp target/classes cliente.MainClienteApp

```

---

## Development Team

This system was engineered with full collaboration by:

* **Víctor Javier García Chavarría** - [GitHub Profile](https://github.com/ScoovicdooVG)
* **Imanol Elizondo Perucich** - [GitHub Profile](https://github.com/sauriopqno)
* **Elsa Gabriela Valadez Sánchez** - [GitHub Profile](https://github.com/Ikarian9306)

---



