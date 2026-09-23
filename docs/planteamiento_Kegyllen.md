# Kegyllen — Plataforma Web3 para Comisiones Artísticas y Protección de Propiedad Intelectual

## 1. Descripción del Problema
El crecimiento del mercado de arte digital a través de plataformas como VGen, Ko-fi y el uso de redes sociales ha impulsado la economía de los creadores independientes. Sin embargo, la descentralización del mercado por medio de redes sociales como X (anteriormente Twitter) y la irrupción de la Inteligencia Artificial, han generado una serie de dinámicas que afectan directamente a los artistas digitales y deterioran la confianza de los compradores.

---

## 2. Objetivos

### Objetivo General
Desarrollar una plataforma web integral para la gestión, contratación y protección de comisiones artísticas digitales que utilice la tecnología blockchain a través de librerías Web3 (como `ethers.js`) y proveedores de nodos RPC para garantizar la autenticidad, trazabilidad y protección de la propiedad intelectual de las obras.

### Objetivos Específicos
* **Requerimientos y Estados:** Analizar y definir los requerimientos funcionales, técnicos y de arquitectura de información para la gestión de comisiones artísticas, determinando los estados del flujo de trabajo (solicitud, aceptación, entrega y confirmación) y protección de la propiedad intelectual.
* **Contratos Inteligentes:** Diseñar los Contratos Inteligentes (*Smart Contracts*) para gestionar los cambios de estado de las comisiones y ejecutar el sellado de tiempo (*timestamping*) mediante el registro de la huella criptográfica (hash SHA-256) de las obras finales.
* **Desarrollo Fullstack:** Desarrollar el frontend y backend de la plataforma web utilizando tecnologías modernas que integren una interfaz intuitiva para clientes y artistas, desvinculada del procesamiento directo de pagos.
* **Integración Web3:** Integrar la plataforma web con la infraestructura blockchain mediante librerías Web3 (como `ethers.js`) y proveedores de nodos RPC (como Alchemy) para permitir la comunicación bidireccional, lectura y firma de transacciones.
* **Gestión de Identidad Simplificada:** Implementar un módulo de gestión de identidades y autenticación simplificado (mediante herramientas como Privy o Web3Auth) que permita a los usuarios firmar acuerdos y validar la propiedad de sus obras sin requerir conocimientos técnicos sobre criptomonedas.
* **Pruebas y Validaciones:** Realizar pruebas de integración y validación criptográfica en una red de pruebas (*Testnet*) para verificar el correcto funcionamiento del sellado de tiempo, la inmutabilidad de los datos y la usabilidad del sistema.

---

## 3. Alcance del Sistema
Desarrollar una plataforma web integral basada en una arquitectura Web3 para gestionar, contratar y proteger comisiones artísticas digitales de creadores independientes, integrando la tecnología blockchain para la certificación inmutable de propiedad intelectual. El sistema abarcará los siguientes módulos funcionales:

1. **Módulo de Gestión de Usuarios y Autenticación (Web3 Auth / Embedded Wallets)**
2. **Módulo de Protección de Propiedad Intelectual e Identidad Digital (Hashes SHA-256 / Timestamps)**
3. **Módulo de Gestión de Comisiones (Flujo Solicitud → Aceptación → Entrega → Confirmación)**