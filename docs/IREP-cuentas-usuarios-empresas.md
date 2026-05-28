# IREP – Cuentas, usuarios y empresas (Tomás)

## 1. Usuario

Representa a una persona que usa Billete.ar.

| Atributo | Tipo | Descripción |
|----------|------|-------------|
| dni | String | Identificador único |
| nombre | String | Nombre y apellido |
| telefono | String | Contacto |
| email | String | Mail |
| cuentas | Map<String, Cuenta> | Cuentas del usuario (clave = CVU) |
| totalInvertido | double | Monto invertido (lo actualiza el módulo de inversiones) |

**Operaciones principales:** `agregarCuenta`, `actualizarTotalInvertido`, getters y `toString`.

**Relaciones:** un usuario tiene varias cuentas; es titular de cada una.

---

## 2. Empresa

Persona jurídica que puede tener cuentas corporativas.

| Atributo | Tipo | Descripción |
|----------|------|-------------|
| cuit | String | Identificador único |
| razonSocial | String | Nombre de fantasía / razón social |
| telefono, email, nombreContacto | String | Datos de contacto |
| dnisAutorizados | Set<String> | DNIs habilitados para operar |

**Operaciones:** `autorizar(dni)`, `estaAutorizado(dni)`.

**Relaciones:** una empresa puede vincularse a varias `CuentaCorporativa`; los autorizados no tienen que estar registrados como usuarios todavía.

---

## 3. Cuenta (abstracta)

Cuenta bancaria virtual con CVU y alias.

| Atributo | Tipo | Descripción |
|----------|------|-------------|
| cvu | String | Clave virtual uniforme (22 dígitos, `Utilitarios`) |
| alias | String | Apodo para transferencias |
| saldo | double | Dinero disponible |
| titular | Usuario | Dueño de la cuenta |
| actividades | List<Actividad> | Movimientos registrados |

**Operaciones comunes:** `depositar`, `extraer`, `registrarActividad`, `aplicarBeneficio`, `volumenDeTransacciones`, `tipo()` (abstracto), `descripcionCuenta()` para listados.

### 3.1 CuentaRegular

- Tope de saldo: **$5.000.000** (`SALDO_MAX`).
- Si un depósito supera el tope → `IllegalStateException`.

### 3.2 CuentaPremium

- Saldo mínimo: **$500.000** (`SALDO_MIN`).
- Al crear la cuenta el depósito inicial debe ser ≥ mínimo.
- Al extraer no puede quedar por debajo del mínimo.
- `aplicarBeneficio`: devuelve el monto con un **5%** extra (beneficio premium en inversiones).

### 3.3 CuentaCorporativa

- Asociada a una `Empresa`.
- Solo la puede abrir un usuario **autorizado** por esa empresa.
- Depósitos y extracciones como la cuenta base (sin tope ni mínimo especial).

---

## 4. Herencia y polimorfismo

```
        Cuenta (abstracta)
       /        |         \
CuentaRegular  CuentaPremium  CuentaCorporativa
```

- `tipo()` y reglas de `depositar` / `extraer` / `aplicarBeneficio` se resuelven por **polimorfismo**.
- La billetera trabaja con referencias `Cuenta` y no necesita saber el tipo concreto salvo al crear.

---

## 5. Requerimientos implementados en `Billetera`

| # | Método | Descripción breve |
|---|--------|-------------------|
| 1 | `registrarUsuario` | Alta en mapa por DNI |
| 2 | `crearCuentaRegular/Premium/Corporativa` | Genera CVU, valida alias y reglas de cada tipo |
| 3 | `obtenerCuentas` | Lista `"Tipo: alias (cvu)"` |
| 4 | `obtenerSaldoDisponible` | Consulta saldo por CVU |
| 11 | `registrarEmpresa` | Alta por CUIT |
| 12 | `agregarPersonaAutorizada` | Agrega DNI al set de la empresa |
| 14 | `consultarCvu` | Busca CVU por alias |

**Estructuras internas de la billetera:** `usuarios`, `empresas`, `cuentasPorCvu`, `cvuPorAlias`.

Los métodos de transferencias, inversiones e historial quedan para Federico y Romina (`UnsupportedOperationException` por ahora).

---

## 6. Excepciones usadas

- `IllegalArgumentException`: datos inválidos, usuario/empresa/cuenta/alias inexistente o duplicado.
- `IllegalStateException`: reglas de negocio (tope regular, mínimo premium).
