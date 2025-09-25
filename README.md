# 🏍️ MotoFácil - API Java + Spring Boot

API para gerenciamento de motos, pátios e localização em tempo real.

---

## 1️⃣ Pré-requisitos

- Java 17+
- Maven 3+
- Postman
- IDE: VS Code, IntelliJ ou Eclipse
- Node.js + npm/yarn para front-end

---

## 2️⃣ Clonando o projeto
```bash
git clone https://github.com/Cruz-011/motofacil-java.git
cd motofacil-java
```

---

## 3️⃣ Configurando o banco de dados

**H2 (em memória)**
```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true
spring.jpa.hibernate.ddl-auto=update
```

- Console H2: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:mem:testdb
- Usuário: sa
- Senha: (vazio)

---

## 4️⃣ Rodando o backend
```bash
# Build do projeto
mvn clean install

# Rodar aplicação
mvn spring-boot:run
```

- API disponível: http://localhost:8080

---

## 5️⃣ Endpoints da API

### 5.1 Autenticação

**Registrar Admin**
```
POST /api/auth/admin/register
Content-Type: application/json

{
  "email": "admin@email.com",
  "senha": "1234",
  "nome": "Admin"
}
```
**Resposta:**
```json
{
  "id": 1,
  "email": "admin@email.com",
  "nome": "Admin"
}
```

**Login**
```
POST /api/auth/login
Content-Type: application/json

{
  "email": "admin@email.com",
  "senha": "1234"
}
```
**Resposta:**
```json
{
  "token": "jwt-token-exemplo"
}
```

### 5.2 Pátios

**Criar Pátio**
```
POST /api/patios
Content-Type: application/json

{
  "nome": "Patio Central",
  "endereco": "Rua Exemplo, 123",
  "esp32Central": "ESP32-001",
  "coordenadasExtremidade": [0,0,10,10],
  "administrador": {"id":1}
}
```
**Resposta:**
```json
{
  "id": 1,
  "nome": "Patio Central",
  "endereco": "Rua Exemplo, 123",
  "esp32Central": "ESP32-001",
  "coordenadasExtremidade": [0,0,10,10],
  "administrador": {
    "id": 1,
    "nome": "Admin"
  }
}
```

**Listar Pátios**
```
GET /api/patios
```

### 5.3 Motos

**Criar Moto**
```
POST /api/motos
Content-Type: application/json

{
  "placa": "ABC1234",
  "modelo": "Mottu Sport",
  "chassi": "XYZ123",
  "codigo": "MOTO-001",
  "categoria": "Street",
  "patio": {"id":1}
}
```
**Resposta:**
```json
{
  "id": 1,
  "placa": "ABC1234",
  "modelo": "Mottu Sport",
  "chassi": "XYZ123",
  "codigo": "MOTO-001",
  "categoria": "Street",
  "status": "pendente",
  "patio": {
    "id": 1,
    "nome": "Patio Central"
  }
}
```

**Atualizar localização**
```
PUT /api/motos/1/location
Content-Type: application/json

{
  "x": 2.5,
  "y": 2.5,
  "patioId": 1,
  "tag": "patio"
}
```
**Resposta:**
```json
{
  "id": 1,
  "placa": "ABC1234",
  "modelo": "Mottu Sport",
  "status": "patio",
  "location": {
    "id": 1,
    "x": 2.5,
    "y": 2.5,
    "tag": "patio",
    "timestamp": "2025-09-24T23:53:45"
  }
}
```

**Listar todas motos**
```
GET /api/motos
```
**Resposta:**
```json
[
  {
    "id": 1,
    "placa": "ABC1234",
    "modelo": "Mottu Sport",
    "status": "patio",
    "patio": {
      "id": 1,
      "nome": "Patio Central"
    },
    "location": {
      "id": 1,
      "x": 2.5,
      "y": 2.5
    }
  }
]
```

**Histórico de localização**
```
GET /api/motos/1/history
```

### 5.4 Locations

Normalmente gerenciadas pelo endpoint de motos (PUT /api/motos/{id}/location).
Evita loops no JSON.

---

## 6️⃣ Testando no Postman

- Abra Postman e crie uma nova coleção "MotoFácil".
- Adicione requests:
  - POST /api/auth/admin/register
  - POST /api/patios
  - POST /api/motos
  - PUT /api/motos/{id}/location
  - GET /api/motos
  - GET /api/motos/{id}/history
- Use Body → raw → JSON para os POST e PUT.
- Verifique a resposta JSON limpa (sem loop infinito).

---

## 7️⃣ Front-end (React/Next)

Exemplo de fetch:
```js
fetch("http://localhost:8080/api/motos")
  .then(res => res.json())
  .then(data => console.log(data));
```

---

## 8️⃣ Observações

- Senhas são criptografadas (BCrypt).
- JWT atualmente simulado (jwt-token-exemplo).
- Use DTOs (MotoDTO, PatioDTO) para evitar loops e esconder dados sensíveis.

---

## 📄 Licença

Este projeto está sob a licença MIT. Consulte o arquivo [LICENSE](LICENSE) para mais informações.

---
