# 🏍️ MotoFácil - Guia Completo Backend & Frontend

Este guia cobre tudo para rodar o MotoFácil: backend em Java/Spring Boot (Oracle) e frontend em React/Expo. Inclui configuração, execução e testes de endpoints.

---

## 1️⃣ Pré-requisitos

- Java 17+ e Maven
- Node.js 18+ e npm ou yarn
- Git
- Postman (opcional, para testar a API)
- Driver Oracle JDBC (normalmente já incluso no Maven)
- Portas 8080 (backend) e 3000 (frontend) livres

---

## 2️⃣ Clonando o Backend

```bash
git clone https://github.com/Cruz-011/motofacil-java.git
cd motofacil-java
```

---

## 3️⃣ Configurando o Backend (Oracle)

No arquivo `src/main/resources/application.properties`, configure:

```properties
spring.datasource.url=jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL
spring.datasource.username=rm558238
spring.datasource.password=111105
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Swagger
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
```

🔹 Para desenvolvimento local, você pode usar H2 se não tiver Oracle.

---

## 4️⃣ Executando o Backend

No diretório `motofacil-java`:

**Linux/Mac:**
```bash
./mvnw spring-boot:run
```

**Windows:**
```bash
mvnw.cmd spring-boot:run
```

O backend estará disponível em:  
http://localhost:8080

**Swagger:**  
http://localhost:8080/swagger-ui.html

---

# ESP Simulator Server

Este projeto é um simulador de servidor ESP que simula dados RSSI (Received Signal Strength Indicator) para localização de motos em um pátio usando Flask.

## Funcionalidades

- Simula 5 ESPs posicionados em um pátio (4 nos cantos e 1 no centro)
- Gera dados RSSI baseados na distância simulada
- API REST para simulação e obtenção de coordenadas dos ESPs

## Pré-requisitos

- Python 3.6 ou superior
- pip (gerenciador de pacotes do Python)

## Instalação das Dependências

1. **Clone ou baixe o projeto** [(se ainda não tiver feito)](https://github.com/Cruz-011/simuladorESPS.git)


3. **Instale as dependências necessárias:**
   ```bash
   pip install flask


## Como Iniciar o Projeto

1. **Execute o servidor:**
   ```bash
   python esp_simulator_server.py
   ```

2. **O servidor será iniciado em:**
   - Host: `0.0.0.0` (todas as interfaces de rede)
   - Porta: `5001`
   - URL local: `http://localhost:5001`

3. **Você verá uma mensagem similar a:**
   ```
   * Running on all addresses (0.0.0.0)
   * Running on http://127.0.0.1:5001
   * Running on http://[seu-ip]:5001
   ```

## 5️⃣ Clonando o Frontend

Abra outro terminal e clone:

```bash
git clone https://github.com/Cruz-011/MOTOFACIL-APP.git
cd MOTOFACIL-APP
```

---

## 6️⃣ Configurando a URL da API no Frontend

No arquivo `src/config/api.js`:

```js
export const API_URL = "http://localhost:8080/api"; // URL do backend
```

🔹 Se o backend estiver em outro IP/porta, altere aqui.

---

## 7️⃣ Instalando Dependências do Frontend

```bash
npm install
# ou
yarn
```

---

## 8️⃣ Executando o Frontend

```bash
npm expo start
# ou
yarn expo start
```

O Expo abrirá o painel de desenvolvimento.  
Você pode abrir no emulador, navegador ou no celular via QR code.  
O frontend se conectará ao backend automaticamente usando a URL definida.

---

## 9️⃣ Estrutura do Frontend

```
MOTOFACIL-APP/
├─ app/
│  ├─ components/      # Componentes reutilizáveis
│  ├─ (tabs)/          # Telas (Motos, Pátios, Login)
│  ├─ App.js           # Arquivo principal
│  ├─ index.js         # Entrada da aplicação
│  └─ src/
│      └─ config/api.js    # Configuração da URL da API
├─ package.json        # Dependências e scripts
```

---

## 🔟 Testando Endpoints com Postman

**Listar todas as motos**
```
GET http://localhost:8080/api/motos
```

**Buscar moto por ID**
```
GET http://localhost:8080/api/motos/{id}
```

**Criar nova moto**
```
POST http://localhost:8080/api/motos
```
Body (JSON):
```json
{
  "placa": "ABC1234",
  "modelo": "Mottu Sport",
  "patio": { "id": 1 }
}
```

**Atualizar localização de uma moto**
```
PUT http://localhost:8080/api/motos/1/location
```
Body (JSON):
```json
{
  "x": 2.5,
  "y": 3.0,
  "patioId": 1,
  "tag": "patio"
}
```

**Histórico de uma moto**
```
GET http://localhost:8080/api/motos/1/history
```

---
