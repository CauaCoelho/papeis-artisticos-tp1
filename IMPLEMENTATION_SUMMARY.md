# Implementação de Autenticação Keycloak e Funcionalidades de E-commerce

## Resumo da Implementação

Este documento descreve as alterações realizadas para integrar Keycloak, implementar autenticação via OIDC, gerenciamento de perfil de usuário, wishlist e pedidos com cálculo de total.

## 🔐 Autenticação com Keycloak (OBRIGATÓRIO)

### ✅ Implementado

1. **Integração OIDC**
   - Adicionada dependência `quarkus-oidc` ao pom.xml
   - Configuração de Keycloak em `application.properties` (dev e prod)
   - Quarkus valida JWT automaticamente

2. **Fluxo de Login**
   - Login gerenciado pelo Keycloak (não há login manual no backend)
   - Frontend redireciona para Keycloak para autenticação
   - JWT token retornado é validado em todos os endpoints protegidos
   - Recuperação de senha é gerenciada natively pelo Keycloak

3. **Validação de Token**
   - Todos os endpoints protegidos validam JWT via `@Authenticated`
   - Roles validadas via `@RolesAllowed("USER")` ou `@RolesAllowed("ADMIN")`

4. **Configuração de Roles**
   - Realm: `papeis-artisticos`
   - Client Frontend: `papeis-frontend`
   - Roles: `USER`, `ADMIN`

### Arquivo de Configuração
- **[KEYCLOAK_SETUP.md](./KEYCLOAK_SETUP.md)** - Guia completo de setup do Keycloak
- **docker-compose.yml** - Container do Keycloak com PostgreSQL

---

## 👤 Usuário / Perfil

### ✅ Implementado

1. **Entidade Usuario**
   - ❌ Removido: campo `senha` (gerenciado pelo Keycloak)
   - ❌ Removido: campo `username`
   - ✅ Adicionado: campo `sub` (ID do Keycloak - único)
   - Mantidos: `nome`, `login`, `perfil`

2. **Endpoint GET /auth/me**
   - Extrai `sub` (Subject) do token JWT
   - Busca dados adicionais no banco
   - Retorna: `id`, `nome`, `login`, `perfil`

3. **DTOs Atualizados**
   - `UsuarioDTO` - removida senha
   - `UsuarioDTOResponse` - removida senha

### Archivos Afetados
- [Usuario.java](src/main/java/br/unitins/tp1/model/Usuario.java)
- [UsuarioDTO.java](src/main/java/br/unitins/tp1/dto/UsuarioDTO.java)
- [UsuarioDTOResponse.java](src/main/java/br/unitins/tp1/dto/UsuarioDTOResponse.java)
- [AuthResource.java](src/main/java/br/unitins/tp1/resource/AuthResource.java)
- [UsuarioService.java](src/main/java/br/unitins/tp1/service/UsuarioService.java)
- [UsuarioServiceImpl.java](src/main/java/br/unitins/tp1/service/UsuarioServiceImpl.java)
- [UsuarioRepository.java](src/main/java/br/unitins/tp1/repository/UsuarioRepository.java)

---

## 📋 Lista de Desejos (Wishlist)

### ✅ Implementado

1. **Entidade Wishlist**
   - Relaciona: `Usuario` (ManyToOne) + `Produto` (ManyToOne)
   - Constraint único: (usuario_id, produto_id) - evita duplicidade

2. **DTOs**
   - `WishlistDTO` - para requisições (contém produtoId)
   - `WishlistDTOResponse` - para respostas (contém dados do produto)

3. **Endpoints**
   - ✅ **POST /wishlist/{produtoId}** - Adicionar à wishlist (role: USER)
   - ✅ **GET /wishlist** - Listar de forma paginada (role: USER)
   - ✅ **DELETE /wishlist/{produtoId}** - Remover (role: USER)

4. **Regras de Negócio**
   - Não permite duplicidade (HTTP 409)
   - Sempre identifica usuário via token JWT
   - Valida existência de produto antes de adicionar

### Arquivos Criados
- [Wishlist.java](src/main/java/br/unitins/tp1/model/Wishlist.java)
- [WishlistDTO.java](src/main/java/br/unitins/tp1/dto/WishlistDTO.java)
- [WishlistDTOResponse.java](src/main/java/br/unitins/tp1/dto/WishlistDTOResponse.java)
- [WishlistService.java](src/main/java/br/unitins/tp1/service/WishlistService.java)
- [WishlistServiceImpl.java](src/main/java/br/unitins/tp1/service/WishlistServiceImpl.java)
- [WishlistRepository.java](src/main/java/br/unitins/tp1/repository/WishlistRepository.java)
- [WishlistResource.java](src/main/java/br/unitins/tp1/resource/WishlistResource.java)

---

## 🛒 Pedidos (Compra)

### ✅ Implementado

1. **Reestruturação de Compra**
   - ✅ Adicionado: `dataPedido` (LocalDateTime, preenchido via @PrePersist)
   - ✅ Adicionado: `total` (BigDecimal, calculado com base em itens e cupom)
   - Mantidos: `usuario`, `itens`, `formaDePagamento`, `cupom`, `endereco`

2. **ItemPedido**
   - Captura preço no momento da compra (BigDecimal)
   - Relaciona com VarianteProduto e Compra

3. **Endpoints**
   - ✅ **POST /compras** - Finalizar compra (calcula total) (role: USER)
   - ✅ **GET /compras/me** - Listar compras do usuário (role: USER)
   - ✅ **GET /compras** - Listar todas (somente admin) (role: ADMIN)
   - ✅ **GET /compras/{id}** - Detalhes da compra (role: USER, ADMIN)

4. **Cálculo de Total**
   - Subtotal = preço_unitário × quantidade (por item)
   - Total = soma de subtotais
   - Se cupom válido: total -= valor_cupom
   - Total nunca fica negativo

### Arquivos Afetados
- [Compra.java](src/main/java/br/unitins/tp1/model/Compra.java)
- [CompraDTOResponse.java](src/main/java/br/unitins/tp1/dto/CompraDTOResponse.java)
- [CompraDTO.java](src/main/java/br/unitins/tp1/dto/CompraDTO.java)
- [CompraService.java](src/main/java/br/unitins/tp1/service/CompraService.java)
- [CompraServiceImpl.java](src/main/java/br/unitins/tp1/service/CompraServiceImpl.java)
- [CompraResource.java](src/main/java/br/unitins/tp1/resource/CompraResource.java)

---

## 🎟️ Cupom

### ✅ Implementado

1. **Entidade Cupom**
   - Campos: `id`, `codigo`, `validade`, `valor` (BigDecimal)
   - Relaciona com Produto (ManyToMany) para validar se cupom é válido para o produto

2. **Validações**
   - ✅ Data de validade (não aplicar cupom expirado)
   - ✅ Aplicar desconto no cálculo final
   - ✅ Validar produtos permitidos (quando aplicável)

3. **Integração com Compra**
   - Cupom é opcional na requisição
   - Se fornecido e válido: desconto é aplicado
   - Total final nunca fica negativo

### Arquivos Afetados
- [Cupom.java](src/main/java/br/unitins/tp1/model/Cupom.java) - campo valor atualizado para BigDecimal

---

## 🔒 Autorização

### ✅ Implementado

1. **@RolesAllowed em Endpoints**
   - **USER**: Wishlist, minhas compras, realizar compra
   - **ADMIN**: Ver todas compras, criar cupons, gerenciar produtos

2. **Proteção de Endpoints**
   ```
   @Authenticated - Requer token válido
   @RolesAllowed("USER") - Apenas usuários
   @RolesAllowed("ADMIN") - Apenas administradores
   @RolesAllowed({"USER", "ADMIN"}) - Ambas roles
   ```

3. **Endpoints Protegidos**
   - ✅ `/wishlist/**` - USER
   - ✅ `/compras` - ADMIN (listar todas)
   - ✅ `/compras/me` - USER
   - ✅ `/compras/{id}` - USER, ADMIN
   - ✅ `/auth/me` - USER, ADMIN

---

## 💰 Tipos Monetários Corrigidos

### ✅ Alterado de Double para BigDecimal

| Classe | Campo | Tipo Original | Tipo Atual |
|--------|-------|---------------|-----------| 
| VarianteProduto | preco | double | **BigDecimal** |
| ItemPedido | preco | Double | **BigDecimal** |
| Cupom | valor | double | **BigDecimal** |

**Razão:** BigDecimal é mais preciso para operações monetárias, evitando erros de arredondamento.

### Arquivos Afetados
- [VarianteProduto.java](src/main/java/br/unitins/tp1/model/VarianteProduto.java)
- [ItemPedido.java](src/main/java/br/unitins/tp1/model/ItemPedido.java)
- [Cupom.java](src/main/java/br/unitins/tp1/model/Cupom.java)

---

## 📄 Documentação Criada

1. **[KEYCLOAK_SETUP.md](./KEYCLOAK_SETUP.md)**
   - Guia completo de configuração do Keycloak
   - Como criar realm, client, roles e usuários
   - Instruções para integração com Angular
   - Troubleshooting

2. **[API_DOCUMENTATION.md](./API_DOCUMENTATION.md)**
   - Documentação completa de todos os endpoints
   - Exemplos de requisições e respostas
   - Status codes e erros
   - Exemplo de fluxo completo

3. **docker-compose.yml**
   - Container do Keycloak com PostgreSQL
   - Database também para o aplicativo
   - Fácil setup local

---

## 🚀 Como Usar

### 1. Setup Inicial
```bash
# Inicie containers do Keycloak e PostgreSQL
docker-compose up -d

# Acesse http://localhost:8180 para configurar Keycloak
# Siga as instruções em KEYCLOAK_SETUP.md
```

### 2. Configurar Quarkus
Atualize `application.properties` com:
```properties
%dev.quarkus.oidc.credentials.secret=<CLIENT_SECRET_DO_KEYCLOAK>
```

### 3. Executar Aplicação
```bash
# Desenvolvimento
mvn quarkus:dev

# Produção
mvn clean package -Pnative
```

### 4. Testar Endpoints
Veja exemplos em [API_DOCUMENTATION.md](./API_DOCUMENTATION.md)

---

## 🔄 Fluxos Implementados

### Autenticação
```
Frontend → Keycloak (Login) → JWT Token → Quarkus (Valida)
```

### Wishlist
```
GET /wishlist/me → Extrai usuário do JWT → Lista itens
POST /wishlist/{id} → Valida duplicidade → Adiciona
DELETE /wishlist/{id} → Remove item
```

### Compra
```
POST /compras (com itens) → Valida produtos → Calcula total
  → Aplica cupom (se válido) → Persiste → Retorna total
```

---

## 📝 Notas Importantes

1. **Senha não é mais armazenada** - Keycloak gerencia autenticação
2. **Sub (Keycloak ID)** - Identifica usuário de forma única
3. **Todas as operações usam token JWT** - Sem sessões server-side
4. **Cupom é opcional** - Pode ser null na requisição
5. **Total é calculado automaticamente** - Cliente não envia total

---

## ✅ Checklist de Validação

- [x] Keycloak integrado com OIDC
- [x] JWT validado em endpoints protegidos
- [x] Senha removida da entidade Usuario
- [x] Campo sub adicionado
- [x] Endpoint GET /auth/me implementado
- [x] Wishlist criada com endpoints completos
- [x] Pedidos com cálculo de total
- [x] Cupom com validação de data
- [x] Desconto de cupom aplicado no total
- [x] Autorização com @RolesAllowed
- [x] Tipos monetários em BigDecimal
- [x] Documentação completa
- [x] Docker Compose para setup local

---

## 🚧 Próximos Passos (Futuros)

1. Implementar preferências de usuário (futuro)
2. Adicionar histórico de pedidos
3. Notificações por email (status de pedido)
4. Sistema de pontos/programa de fidelidade
5. Integração com gateway de pagamento
6. Dashboard administrativo
