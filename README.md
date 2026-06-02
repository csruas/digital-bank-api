# Digital Bank API

REST API para gerenciamento de contas bancárias digitais, transferências financeiras e controle de transações.

Projeto desenvolvido utilizando Java 21, Spring Boot 3, PostgreSQL, Docker e Flyway.

## Tecnologias

* Java 21
* Spring Boot 3
* Spring Data JPA
* PostgreSQL 16
* Flyway
* Docker
* Maven
* Lombok
* OpenAPI / Swagger

## Arquitetura

O projeto segue uma arquitetura em camadas:

```text
Controller
   ↓
Service
   ↓
Repository
   ↓
Database
```

Estrutura principal:

```text
src/main/java

├── account
│   ├── controller
│   ├── service
│   ├── repository
│   ├── entity
│   └── dto
│
├── transaction
│   ├── controller
│   ├── service
│   ├── repository
│   ├── entity
│   └── dto
│
├── transfer
│   ├── controller
│   ├── service
│   └── dto
│
└── shared
    ├── exception
    ├── config
    └── util
```

## Funcionalidades

### Contas

* Criar conta
* Buscar conta por ID
* Listar contas
* Consultar saldo

### Transferências

* Transferência entre contas
* Validação de saldo
* Controle transacional
* Histórico de transferências

### Transações

* Registro de movimentações
* Consulta de histórico
* Auditoria

## Banco de Dados

O versionamento do banco é realizado através do Flyway.

Exemplo:

```text
src/main/resources/db/migration
```

```text
V1__create_tables.sql
```

## Executando localmente

### Subir PostgreSQL

```bash
docker compose up -d
```

### Executar aplicação

```bash
./mvnw spring-boot:run
```

### Build

```bash
./mvnw clean install
```

## Swagger

Após iniciar a aplicação:

```text
http://localhost:8080/swagger-ui.html
```

ou

```text
http://localhost:8080/swagger-ui/index.html
```

## Actuator

```text
http://localhost:8080/actuator
```

## Melhorias Futuras

* JWT Authentication
* Kafka para notificações
* Testes de integração com Testcontainers
* Observabilidade com Micrometer
* Rate Limiting
* Circuit Breaker
* OpenTelemetry

## Autor

Cristiano De Sousa Ruas

GitHub:
https://github.com/csruas
