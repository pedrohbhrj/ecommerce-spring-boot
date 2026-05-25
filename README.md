# ecommerce-api
**API REST de e-commerce · Java 17 · Spring Boot 3 · PostgreSQL**

---

API construída para simular o backend de uma plataforma de e-commerce, cobrindo autenticação com JWT, controle de estoque, fluxo de pedido e pagamento. O foco foi arquitetura limpa, segurança stateless e rastreabilidade via logs estruturados.

---

## Stack

`Java 17` · `Spring Boot 3.5` · `Spring Security + JWT` · `PostgreSQL`  
`Flyway` · `Swagger / OpenAPI` · `Docker Compose` · `MapStruct` · `Lombok`

---

## Domínios da aplicação

| Domínio | Responsabilidade |
|---|---|
| Usuário & Auth | Registro, login JWT, roles (USER / ADMIN), atualização de perfil |
| Produto & Categoria | CRUD completo, paginação, categorias hierárquicas com parentId |
| Pedido | Criação com validação de estoque em tempo real |
| Pagamento | Fluxo PENDING → PROCESSING → APPROVED / DECLINED com transactionId único |
| Endereço | CRUD de endereços vinculados ao usuário autenticado |

---

## Endpoints principais

| Método | Rota | Acesso |
|---|---|---|
| POST | /api/auth/register | Público |
| POST | /api/auth/login | Público |
| GET | /api/products | Público |
| POST | /api/products | 🔒 ADMIN |
| POST | /api/orders | 🔒 Auth |
| GET | /api/orders | 🔒 Auth |
| PUT | /api/payments/{orderId} | 🔒 Auth |
| GET | /api/payments/{orderId} | 🔒 Auth |

Documentação completa disponível em `/swagger-ui.html` após subir a aplicação.

---

## Fluxo de pagamento

```
POST /orders → Valida estoque → Payment PENDING → PUT /payments/{id} → Revalida estoque → APPROVED / DECLINED
```

---

## Como rodar

**Pré-requisitos:** Java 17+, Maven, Docker

```bash
git clone https://github.com/pedrohbhrj/ecommerce-spring-boot.git
cd ecommerce-spring-boot

# sobe o banco via Docker
docker compose up -d

# roda a aplicação (Flyway cria as tabelas automaticamente)
mvn spring-boot:run
```

Configure as variáveis de banco em `application.properties` se necessário.  
O Flyway roda as migrations automaticamente na primeira execução.

---

## Próximos passos

- [ ] Entidade Delivery
- [ ] Testes unitários (JUnit / Mockito)
