# API REST - Papéis Artísticos E-commerce

## Base URL
```
http://localhost:8080
```

## Autenticação

Todos os endpoints protegidos requerem um header `Authorization` com o JWT token do Keycloak:

```
Authorization: Bearer {access_token}
```

## Endpoints

### Autenticação

#### GET /auth/me
Retorna dados do usuário autenticado.

**Requer:** Authorization header com JWT

**Response (200 OK):**
```json
{
  "id": 1,
  "nome": "Usuario",
  "login": "usuario@papeis.com",
  "perfil": "USER"
}
```

---

## Wishlist (Lista de Desejos)

### POST /wishlist/{produtoId}
Adiciona um produto à wishlist do usuário autenticado.

**Requer:** Authorization header com JWT e role USER

**Path Parameters:**
- `produtoId` (Long) - ID do produto a adicionar

**Response (201 Created):**
```json
{
  "id": 1,
  "produtoId": 5,
  "produtoNome": "Bloco A4",
  "produtoImagem": "fid_12345"
}
```

**Erros:**
- 409 Conflict - Produto já existe na wishlist
- 404 Not Found - Produto não encontrado
- 401 Unauthorized - Usuário não autenticado

---

### GET /wishlist
Lista todos os itens da wishlist do usuário autenticado.

**Requer:** Authorization header com JWT e role USER

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "produtoId": 5,
    "produtoNome": "Bloco A4",
    "produtoImagem": "fid_12345"
  },
  {
    "id": 2,
    "produtoId": 8,
    "produtoNome": "Papel Avulso 180g",
    "produtoImagem": "fid_67890"
  }
]
```

**Erros:**
- 401 Unauthorized - Usuário não autenticado

---

### DELETE /wishlist/{produtoId}
Remove um produto da wishlist do usuário autenticado.

**Requer:** Authorization header com JWT e role USER

**Path Parameters:**
- `produtoId` (Long) - ID do produto a remover

**Response (204 No Content)**

**Erros:**
- 404 Not Found - Item não encontrado na wishlist
- 401 Unauthorized - Usuário não autenticado

---

## Compras (Pedidos)

### POST /compras
Realiza uma nova compra. Calcula o total, valida e aplica cupom (se fornecido).

**Requer:** Authorization header com JWT e role USER

**Request Body:**
```json
{
  "clienteId": 1,
  "itens": [
    {
      "varianteProdutoId": 3,
      "quantidade": 2
    },
    {
      "varianteProdutoId": 5,
      "quantidade": 1
    }
  ],
  "formaDePagamento": "CARTAO_CREDITO",
  "cupomId": null
}
```

**Request Body (com cupom):**
```json
{
  "clienteId": 1,
  "itens": [
    {
      "varianteProdutoId": 3,
      "quantidade": 2
    }
  ],
  "formaDePagamento": "CARTAO_CREDITO",
  "cupomId": 2
}
```

**Response (201 Created):**
```json
{
  "id": 15,
  "clienteId": 1,
  "itens": [
    {
      "id": 1,
      "compra": {...},
      "variante": {...},
      "quantidade": 2,
      "preco": "25.50"
    }
  ],
  "formaDePagamento": "CARTAO_CREDITO",
  "dataPedido": "2026-05-26T14:30:00",
  "total": "50.00"
}
```

**Erros:**
- 400 Bad Request - Cupom expirado ou dados inválidos
- 404 Not Found - Cliente, variante ou cupom não encontrado
- 401 Unauthorized - Usuário não autenticado

---

### GET /compras
Lista todas as compras (apenas admin).

**Requer:** Authorization header com JWT e role ADMIN

**Query Parameters:**
- `page` (int, default: 0) - Número da página (0-based)
- `pageSize` (int, default: 10) - Tamanho da página

**Response (200 OK):**
```json
{
  "data": [
    {
      "id": 1,
      "clienteId": 1,
      "itens": [...],
      "formaDePagamento": "CARTAO_CREDITO",
      "dataPedido": "2026-05-26T14:00:00",
      "total": "100.00"
    }
  ],
  "totalRecords": 25
}
```

**Erros:**
- 401 Unauthorized - Usuário não autenticado
- 403 Forbidden - Usuário não tem role ADMIN

---

### GET /compras/me
Lista as compras do usuário autenticado.

**Requer:** Authorization header com JWT e role USER

**Query Parameters:**
- `page` (int, default: 0) - Número da página
- `pageSize` (int, default: 10) - Tamanho da página

**Response (200 OK):**
```json
{
  "data": [
    {
      "id": 1,
      "clienteId": 1,
      "itens": [...],
      "formaDePagamento": "CARTAO_CREDITO",
      "dataPedido": "2026-05-26T14:00:00",
      "total": "100.00"
    }
  ],
  "totalRecords": 5
}
```

**Erros:**
- 401 Unauthorized - Usuário não autenticado

---

### GET /compras/{id}
Busca detalhes de uma compra específica.

**Requer:** Authorization header com JWT e role USER ou ADMIN

**Path Parameters:**
- `id` (Long) - ID da compra

**Response (200 OK):**
```json
{
  "id": 1,
  "clienteId": 1,
  "itens": [
    {
      "id": 1,
      "compra": {...},
      "variante": {
        "id": 3,
        "formato": "A4",
        "gramatura": 180,
        "cor": "Branco",
        "preco": "25.50"
      },
      "quantidade": 2,
      "preco": "25.50"
    }
  ],
  "formaDePagamento": "CARTAO_CREDITO",
  "dataPedido": "2026-05-26T14:30:00",
  "total": "50.00"
}
```

**Erros:**
- 404 Not Found - Compra não encontrada
- 401 Unauthorized - Usuário não autenticado

---

## Cupons

### POST /cupons
Cria um novo cupom (apenas admin).

**Requer:** Authorization header com JWT e role ADMIN

**Request Body:**
```json
{
  "codigo": "DESCONTO10",
  "valor": "10.00",
  "validade": "2026-12-31T23:59:59",
  "produtosPermitidos": [1, 2, 3]
}
```

**Response (201 Created):**
```json
{
  "id": 2,
  "codigo": "DESCONTO10",
  "valor": "10.00",
  "validade": "2026-12-31T23:59:59"
}
```

---

### GET /cupons/{codigo}
Busca um cupom pelo código.

**Path Parameters:**
- `codigo` (String) - Código do cupom

**Response (200 OK):**
```json
{
  "id": 2,
  "codigo": "DESCONTO10",
  "valor": "10.00",
  "validade": "2026-12-31T23:59:59"
}
```

---

## Modelos de Dados

### ItemPedidoDTO
```json
{
  "varianteProdutoId": 3,
  "quantidade": 2
}
```

### WishlistDTOResponse
```json
{
  "id": 1,
  "produtoId": 5,
  "produtoNome": "Bloco A4",
  "produtoImagem": "fid_12345"
}
```

### CompraDTOResponse
```json
{
  "id": 15,
  "clienteId": 1,
  "itens": [ItemPedido],
  "formaDePagamento": "CARTAO_CREDITO",
  "dataPedido": "2026-05-26T14:30:00",
  "total": "50.00"
}
```

### UsuarioDTOResponse
```json
{
  "id": 1,
  "nome": "Usuario",
  "login": "usuario@papeis.com",
  "perfil": "USER"
}
```

---

## Roles de Autorização

- **USER** - Usuário regular do e-commerce
  - Pode adicionar/remover itens da wishlist
  - Pode realizar compras
  - Pode ver suas compras

- **ADMIN** - Administrador do sistema
  - Pode criar/editar cupons
  - Pode ver todas as compras
  - Pode gerenciar produtos (endpoints não listados acima)

---

## Status Codes

| Código | Descrição |
|--------|-----------|
| 200 | OK - Requisição bem-sucedida |
| 201 | Created - Recurso criado com sucesso |
| 204 | No Content - Operação bem-sucedida sem retorno |
| 400 | Bad Request - Dados inválidos |
| 401 | Unauthorized - Não autenticado |
| 403 | Forbidden - Não autorizado (sem role necessária) |
| 404 | Not Found - Recurso não encontrado |
| 409 | Conflict - Violação de constraint (ex: duplicidade) |
| 500 | Internal Server Error - Erro no servidor |

---

## Enums

### FormaDePagamento
- `CARTAO_CREDITO`
- `CARTAO_DEBITO`
- `PIX`
- `BOLETO`
- `TRANSFERENCIA_BANCARIA`

### Perfil
- `USER`
- `ADMIN`

---

## Exemplo Completo de Fluxo

### 1. Obter Token do Keycloak
```bash
curl -X POST http://localhost:8180/realms/papeis-artisticos/protocol/openid-connect/token \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "grant_type=password" \
  -d "client_id=papeis-frontend" \
  -d "client_secret=CLIENT_SECRET" \
  -d "username=usuario@papeis.com" \
  -d "password=senha"
```

Copie o `access_token` da resposta.

### 2. Adicionar à Wishlist
```bash
curl -X POST http://localhost:8080/wishlist/5 \
  -H "Authorization: Bearer {access_token}" \
  -H "Content-Type: application/json"
```

### 3. Listar Wishlist
```bash
curl -X GET http://localhost:8080/wishlist \
  -H "Authorization: Bearer {access_token}"
```

### 4. Realizar Compra
```bash
curl -X POST http://localhost:8080/compras \
  -H "Authorization: Bearer {access_token}" \
  -H "Content-Type: application/json" \
  -d '{\
    "clienteId": 1,\
    "itens": [{"varianteProdutoId": 3, "quantidade": 2}],\
    "formaDePagamento": "CARTAO_CREDITO",\
    "cupomId": null\
  }'
```

### 5. Ver Minhas Compras
```bash
curl -X GET "http://localhost:8080/compras/me?page=0&pageSize=10" \
  -H "Authorization: Bearer {access_token}"
```
