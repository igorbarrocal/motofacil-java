# Motofacil Java

## Instalação
1. Clone o repositório
2. Configure o banco de dados em `application.properties`
3. Execute as migrações Flyway: `mvn flyway:migrate`
4. Rode a aplicação: `mvn spring-boot:run`

## Funcionalidades
- CRUD de motos, pátios, usuários
- Monitoramento IoT
- Perfis de usuário: admin, operador

## IoT
- Endpoints: `/api/iot/monitoramento`, `/api/iot/manutencao`
- Exemplo de requisição: `POST /api/iot/monitoramento`

## Segurança
- Login via formulário
- Permissões por perfil

## Migrações Flyway
- Scripts em `src/main/resources/db/migration/`
