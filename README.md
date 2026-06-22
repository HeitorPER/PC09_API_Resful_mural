# Mural API REST

API RESTful de um mural de mensagens, com autenticação **JWT** (stateless).
Spring Boot 4.1.0 · Java 21 · Apache Derby.

**Base URL:** `http://localhost:8080`

## Autenticação

A API é stateless e usa **JWT (Bearer Token)**.

1. Cadastre um usuário em `POST /api/usuarios` (ou use um já existente).
2. Faça login em `POST /api/auth/login` para receber o token.
3. Envie o token no header das rotas protegidas:

```
Authorization: Bearer <token>
```

O token expira em **24 horas**. Rotas sem token válido retornam **403 Forbidden**.

---

## Endpoints

| Método | Rota | Autenticação | Descrição |
|--------|------|:------------:|-----------|
| `POST` | `/api/auth/login` | 🔓 Pública | Autentica e retorna um JWT |
| `POST` | `/api/usuarios` | 🔓 Pública | Cadastra um novo usuário |
| `POST` | `/api/mensagens` | 🔒 JWT | Posta uma mensagem |
| `GET`  | `/api/mensagens` | 🔒 JWT | Lista todas as mensagens |

---

### 🔓 POST `/api/auth/login`

Autentica o usuário e retorna um token JWT.

**Request body** `application/json`
```json
{
  "username": "alice",
  "password": "123"
}
```

**Respostas**

| Código | Descrição | Corpo |
|--------|-----------|-------|
| `200 OK` | Autenticado | O token JWT (texto puro) |
| `403 Forbidden` | Credenciais inválidas | — |

**Exemplo de resposta `200`**
```
eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGljZSIsImlhdCI6MTcxOD...
```

---

### 🔓 POST `/api/usuarios`

Cadastra um novo usuário. A `role` é sempre fixada como `"USER"` (o cliente não pode definir). A senha é armazenada com hash **BCrypt**.

**Request body** `application/json`
```json
{
  "username": "alice",
  "password": "123"
}
```

**Respostas**

| Código | Descrição | Corpo |
|--------|-----------|-------|
| `201 Created` | Usuário criado | — |

---

### 🔒 POST `/api/mensagens`

Posta uma nova mensagem. O remetente (`from`) é extraído automaticamente do token JWT — **não é enviado no corpo**.

**Header obrigatório**
```
Authorization: Bearer <token>
```

**Request body** `application/json`
```json
{
  "to": "bob",
  "message": "Olá, Bob!"
}
```

| Campo | Tipo | Obrigatório | Observação |
|-------|------|:-----------:|------------|
| `to` | string | ✅ | Não pode ser vazio |
| `message` | string | ✅ | Não pode ser vazio |

**Respostas**

| Código | Descrição | Corpo |
|--------|-----------|-------|
| `200 OK` | Mensagem postada | Objeto da mensagem (abaixo) |
| `400 Bad Request` | `from` igual a `to`, ou campos em branco | — |
| `403 Forbidden` | Token ausente ou inválido | — |

**Exemplo de resposta `200`**
```json
{
  "from": "alice",
  "to": "bob",
  "message": "Olá, Bob!",
  "timestamp": "Sun Jun 21 23:13:45 GMT-03:00 2026"
}
```

---

### 🔒 GET `/api/mensagens`

Lista todas as mensagens, ordenadas da mais recente para a mais antiga.

**Header obrigatório**
```
Authorization: Bearer <token>
```

**Respostas**

| Código | Descrição | Corpo |
|--------|-----------|-------|
| `200 OK` | Lista de mensagens | Array de mensagens (abaixo) |
| `403 Forbidden` | Token ausente ou inválido | — |

**Exemplo de resposta `200`**
```json
[
  {
    "from": "alice",
    "to": "bob",
    "message": "Olá, Bob!",
    "timestamp": "Sun Jun 21 23:13:45 GMT-03:00 2026"
  },
  {
    "from": "heitor",
    "to": "joao",
    "message": "ola tudo bem?",
    "timestamp": "Sun Jun 21 20:29:10 GMT-03:00 2026"
  }
]
```

---

## Como executar

1. Inicie o **Derby Network Server** na porta `1527` (banco `muralspring`).
2. Rode a aplicação:
   ```
   ./mvnw spring-boot:run        # Linux/macOS
   .\mvnw.cmd spring-boot:run    # Windows
   ```
3. A API fica disponível em `http://localhost:8080`.

> ℹ️ As rotas `POST` e os headers de autenticação não podem ser testados pela barra de endereços do navegador. Use uma ferramenta de API (Postman, Insomnia, Bruno) ou `curl`.

## Modelos de dados

**Usuário** (`POST /api/usuarios`, `POST /api/auth/login`)
```json
{ "username": "string", "password": "string" }
```

**Mensagem enviada** (`POST /api/mensagens`)
```json
{ "to": "string", "message": "string" }
```

**Mensagem listada** (respostas de mensagens)
```json
{ "from": "string", "to": "string", "message": "string", "timestamp": "string" }
```
