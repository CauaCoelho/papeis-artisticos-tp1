# Guia Prático de Teste - Sistema de Autenticação e E-commerce

Este documento fornece passos práticos para testar toda a implementação de autenticação, wishlist e compras.

## 🔧 Pré-requisitos

- Docker Desktop instalado
- Maven 3.8+
- JDK 21+
- cURL ou Postman (para fazer requisições)

## 📋 Checklist de Setup

- [ ] Clonar/verificar código
- [ ] `docker-compose up -d` para iniciar Keycloak
- [ ] Configurar Keycloak (realm, client, roles, usuários)
- [ ] Atualizar `application.properties` com client secret
- [ ] `mvn quarkus:dev` para iniciar aplicação
- [ ] Testar endpoints

## 🔑 Step 1: Configurar Keycloak (5 min)

### 1.1 Iniciar Keycloak
```bash
cd /caminho/do/projeto
docker-compose up -d
```

Espere ~30 segundos para Keycloak iniciar.

### 1.2 Acessar Admin Console
- URL: http://localhost:8180/admin
- Username: `admin`
- Password: `admin`

### 1.3 Criar Realm
1. Clique em "Create Realm"
2. Nome: `papeis-artisticos`
3. Clique "Create"

### 1.4 Criar Client
1. Menu → Clients
2. "Create client"
3. Client ID: `papeis-frontend`
4. Next → Habilite "Standard flow" e "Direct access grants"
5. Save

### 1.5 Obter Client Secret
1. Na página do client
2. Aba "Credentials"
3. Copie o valor de "Client secret"
4. Salve em local seguro

### 1.6 Criar Roles
1. Menu → Realm roles
2. "Create role" → Nome: `USER` → Create
3. "Create role" → Nome: `ADMIN` → Create

### 1.7 Criar Usuários

**Usuário 1 - Regular**
1. Menu → Users
2. "Add user"
3. Username: `usuario@papeis.com`
4. Email: `usuario@papeis.com`
5. First name: `Usuario`
6. Create
7. Aba "Credentials" → Set password: `senha123` → OFF temporary
8. Aba "Role mapping" → Assign role `USER`

**Usuário 2 - Admin**
1. "Add user"
2. Username: `admin@papeis.com`
3. Email: `admin@papeis.com`
4. First name: `Admin`
5. Create
6. Aba "Credentials" → Set password: `admin123`
7. Aba "Role mapping" → Assign role `ADMIN`

## 🔄 Step 2: Configurar Aplicação (2 min)

### 2.1 Atualizar application.properties
```bash
# Abra src/main/resources/application.properties
# Procure por: %dev.quarkus.oidc.credentials.secret
# Substitua VAZIO pelo client secret copiado
```

### 2.2 Iniciar Quarkus
```bash
mvn quarkus:dev
```

Espere pela mensagem:
```
Listening on: http://localhost:8080
```

## 🧪 Step 3: Testar Endpoints (15 min)

### 3.1 Obter Token de Usuário Regular

```bash
#!/bin/bash
# Salve como get_token.sh

CLIENT_SECRET="seu_client_secret_aqui"
RESPONSE=$(curl -s -X POST \
  http://localhost:8180/realms/papeis-artisticos/protocol/openid-connect/token \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "grant_type=password" \
  -d "client_id=papeis-frontend" \
  -d "client_secret=${CLIENT_SECRET}" \
  -d "username=usuario@papeis.com" \
  -d "password=senha123")

TOKEN=$(echo $RESPONSE | grep -o '"access_token":"[^"]*' | cut -d'"' -f4)
echo "TOKEN=${TOKEN}"
export TOKEN=${TOKEN}
```

Execute:
```bash
chmod +x get_token.sh
./get_token.sh
```

Copie o token exibido.

### 3.2 Testar GET /auth/me

```bash
curl -X GET http://localhost:8080/auth/me \
  -H "Authorization: Bearer {TOKEN_COPIADO}"
```

**Resposta esperada (200 OK):**
```json
{
  "id": 1,
  "nome": "Usuario",
  "login": "usuario@papeis.com",
  "perfil": "USER"
}
```

### 3.3 Testar Wishlist - Adicionar Produto

```bash
curl -X POST http://localhost:8080/wishlist/1 \
  -H "Authorization: Bearer {TOKEN}" \
  -H "Content-Type: application/json"
```

**Resposta esperada (201 Created):**
```json
{
  "id": 1,
  "produtoId": 1,
  "produtoNome": "Bloco A4 180g",
  "produtoImagem": "fid_xxxxx"
}
```

### 3.4 Testar Wishlist - Listar

```bash
curl -X GET http://localhost:8080/wishlist \
  -H "Authorization: Bearer {TOKEN}"
```

**Resposta esperada (200 OK):**
```json
[
  {
    "id": 1,
    "produtoId": 1,
    "produtoNome": "Bloco A4 180g",
    "produtoImagem": "fid_xxxxx"
  }
]
```

### 3.5 Testar Wishlist - Remover

```bash
curl -X DELETE http://localhost:8080/wishlist/1 \
  -H "Authorization: Bearer {TOKEN}"
```

**Resposta esperada (204 No Content)**

### 3.6 Testar Compra - Realizar

```bash
curl -X POST http://localhost:8080/compras \
  -H "Authorization: Bearer {TOKEN}" \
  -H "Content-Type: application/json" \
  -d '{
    "clienteId": 1,
    "itens": [
      {
        "varianteProdutoId": 1,
        "quantidade": 2
      }
    ],
    "formaDePagamento": "CARTAO_CREDITO",
    "cupomId": null
  }'
```

**Resposta esperada (201 Created):**
```json
{
  "id": 1,
  "clienteId": 1,
  "itens": [...],
  "formaDePagamento": "CARTAO_CREDITO",
  "dataPedido": "2026-05-26T14:30:00",
  "total": "50.00"
}
```

### 3.7 Testar Compra - Minhas Compras

```bash
curl -X GET "http://localhost:8080/compras/me?page=0&pageSize=10" \
  -H "Authorization: Bearer {TOKEN}"
```

**Resposta esperada (200 OK):**
```json
{
  "data": [
    {
      "id": 1,
      "clienteId": 1,
      "itens": [...],
      "formaDePagamento": "CARTAO_CREDITO",
      "dataPedido": "2026-05-26T14:30:00",
      "total": "50.00"
    }
  ],
  "totalRecords": 1
}
```

### 3.8 Testar Compra - Detalhes

```bash
curl -X GET http://localhost:8080/compras/1 \
  -H "Authorization: Bearer {TOKEN}"
```

**Resposta esperada (200 OK):**
```json
{
  "id": 1,
  "clienteId": 1,
  "itens": [...],
  "formaDePagamento": "CARTAO_CREDITO",
  "dataPedido": "2026-05-26T14:30:00",
  "total": "50.00"
}
```

## 🔐 Step 4: Testar Autorização (5 min)

### 4.1 Obter Token de Admin

```bash
# Modifique get_token.sh ou execute diretamente:
ADMIN_RESPONSE=$(curl -s -X POST \
  http://localhost:8180/realms/papeis-artisticos/protocol/openid-connect/token \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "grant_type=password" \
  -d "client_id=papeis-frontend" \
  -d "client_secret=${CLIENT_SECRET}" \
  -d "username=admin@papeis.com" \
  -d "password=admin123")

ADMIN_TOKEN=$(echo $ADMIN_RESPONSE | grep -o '"access_token":"[^"]*' | cut -d'"' -f4)
export ADMIN_TOKEN=${ADMIN_TOKEN}
```

### 4.2 Testar Endpoint Admin - Listar Todas Compras

```bash
curl -X GET "http://localhost:8080/compras?page=0&pageSize=10" \
  -H "Authorization: Bearer {ADMIN_TOKEN}"
```

**Resposta esperada (200 OK):** Lista de todas as compras

### 4.3 Testar Negação de Acesso (403)

Tente acessar endpoint admin com token de usuário regular:

```bash
curl -X GET "http://localhost:8080/compras?page=0&pageSize=10" \
  -H "Authorization: Bearer {TOKEN_REGULAR}"
```

**Resposta esperada (403 Forbidden)**

## 📊 Step 5: Validar Dados (5 min)

### 5.1 Verificar Banco de Dados

```bash
# Conectar ao PostgreSQL
docker exec -it postgres-papeis psql -U topicos1 -d topicos1db

# Executar queries:
SELECT * FROM usuario;                    -- Ver usuários
SELECT * FROM wishlist;                   -- Ver wishlists
SELECT * FROM compra;                     -- Ver compras
SELECT * FROM itempedido;                 -- Ver itens de compra

# Sair
\q
```

### 5.2 Validar Campos Monetários

```sql
-- Em PostgreSQL
SELECT id, nome, preco FROM variante_produto;  -- Deve ser NUMERIC/Decimal
SELECT id, quantidade, preco FROM itempedido;  -- Deve ser NUMERIC
SELECT id, codigo, valor FROM cupom;           -- Deve ser NUMERIC
```

### 5.3 Validar Cálculo de Total

1. Crie uma compra (já foi testada acima)
2. Verifique que o total foi calculado corretamente
3. Verifique que a data foi preenchida automaticamente

## 🚨 Troubleshooting

### Token Inválido
```
401 Unauthorized: Invalid token
```
**Solução:**
- Token expirou? Obtenha um novo
- Client secret correto em application.properties?

### Acesso Negado
```
403 Forbidden: No roles matches role
```
**Solução:**
- Usuário tem a role necessária no Keycloak?
- Role mapping foi feita?
- Reinicie Quarkus após alterar roles

### Produto Não Encontrado
```
404 Not Found: Produto não encontrado
```
**Solução:**
- Verifique ID do produto existe na BD
- ID passado está correto?

### Conexão Recusada
```
Connection refused: localhost:8180
```
**Solução:**
- Keycloak iniciou? `docker ps`
- Esperar mais tempo para container subir

### Wishlist Duplicada
```
409 Conflict: Produto já existe na wishlist
```
**Solução:**
- Comportamento esperado
- Remove antes de adicionar novamente

## ✅ Validação Final

Se todos os testes passaram:

- [x] Autenticação via Keycloak funcionando
- [x] Tokens JWT validados
- [x] Endpoints protegidos com @RolesAllowed
- [x] Wishlist funcionando (criar, listar, deletar)
- [x] Compras funcionando (criar, listar, detalhar)
- [x] Total calculado corretamente
- [x] Usuário identificado pelo sub do Keycloak
- [x] Tipos monetários em BigDecimal

## 📞 Próximas Etapas

1. Implementar frontend Angular
2. Integrar com gateway de pagamento
3. Configurar notificações por email
4. Deploy em produção
5. Configurar HTTPS

## 📚 Referências

- [KEYCLOAK_SETUP.md](./KEYCLOAK_SETUP.md) - Configuração Keycloak
- [API_DOCUMENTATION.md](./API_DOCUMENTATION.md) - Documentação API
- [IMPLEMENTATION_SUMMARY.md](./IMPLEMENTATION_SUMMARY.md) - Resumo implementação
