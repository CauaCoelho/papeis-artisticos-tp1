# Configuração de Autenticação com Keycloak

## Visão Geral

Este projeto utiliza Keycloak para gerenciar autenticação e autorização via OpenID Connect (OIDC). O Keycloak gerencia todas as operações de login, registro e gerenciamento de senhas, enquanto o Quarkus valida os JWTs recebidos.

## Fluxo de Autenticação

```
1. Frontend redireciona usuário para Keycloak
   ↓
2. Usuário faz login no Keycloak
   ↓
3. Keycloak retorna JWT token ao frontend
   ↓
4. Frontend inclui JWT no header Authorization
   ↓
5. Quarkus valida JWT automaticamente via OIDC
   ↓
6. Usuario pode acessar endpoints protegidos
```

## Setup Inicial - Keycloak Local

### Pré-requisitos
- Docker e Docker Compose instalados
- PostgreSQL (será iniciado via Docker Compose)

### Iniciar Keycloak

```bash
# Na raiz do projeto
docker-compose up -d

# Keycloak estará disponível em: http://localhost:8180
# Usuário admin: admin
# Senha admin: admin
```

### Criar Realm "papeis-artisticos"

1. Acesse http://localhost:8180/admin
2. Faça login com admin/admin
3. Clique em "Create Realm"
4. Nome: `papeis-artisticos`
5. Clique em "Create"

### Criar Client para Frontend

1. No menu lateral, selecione "Clients"
2. Clique em "Create client"
3. Client ID: `papeis-frontend`
4. Client Type: `OpenID Connect`
5. Clique em "Next"
6. Habilite:
   - Standard flow
   - Direct access grants (para testes)
7. Clique em "Save"

### Configurar Valid Redirect URIs

1. Na página do client `papeis-frontend`, abra a aba "Settings"
2. Em "Valid redirect URIs", adicione:
   ```
   http://localhost:4200/*
   http://localhost:4200
   ```
3. Em "Valid post logout redirect URIs", adicione:
   ```
   http://localhost:4200
   ```
4. Salve as alterações

### Obter Client Secret

1. Na página do client `papeis-frontend`, abra a aba "Credentials"
2. Copie o valor em "Client secret"
3. Este valor será usado em `application.properties`

### Criar Roles

1. No menu lateral, selecione "Realm roles"
2. Clique em "Create role"
3. Crie duas roles:
   - Nome: `USER`
   - Nome: `ADMIN`

### Criar Usuários de Teste

#### Usuário Regular (USER)
1. No menu lateral, selecione "Users"
2. Clique em "Add user"
3. Username: `usuario@papeis.com`
4. Email: `usuario@papeis.com`
5. First name: `Usuario`
6. Clique em "Create"
7. Na aba "Credentials", defina uma senha (ativando "Temporary" = OFF)
8. Na aba "Role mapping", adicione a role `USER`

#### Usuário Admin (ADMIN)
1. Clique em "Add user"
2. Username: `admin@papeis.com`
3. Email: `admin@papeis.com`
4. First name: `Admin`
5. Clique em "Create"
6. Na aba "Credentials", defina uma senha
7. Na aba "Role mapping", adicione a role `ADMIN`

### Configurar Quarkus

Defina variável de ambiente ou atualize `application.properties`:

```properties
%dev.quarkus.oidc.auth-server-url=http://localhost:8180/realms/papeis-artisticos
%dev.quarkus.oidc.client-id=papeis-frontend
%dev.quarkus.oidc.credentials.secret=<COLE_CLIENT_SECRET_AQUI>
```

## Endpoints de Autenticação

### GET /auth/me
Retorna dados do usuário autenticado.

**Headers:**
```
Authorization: Bearer {JWT_TOKEN}
```

**Response (200 OK):**
```json
{
  "id": 1,
  "nome": "Usuario",
  "login": "usuario@papeis.com",
  "perfil": "USER"
}
```

## Endpoints Protegidos

### Exemplo: Wishlist

- **POST /wishlist/{produtoId}** - Adicionar à wishlist (role: USER)
- **GET /wishlist** - Listar wishlist (role: USER)
- **DELETE /wishlist/{produtoId}** - Remover de wishlist (role: USER)

### Exemplo: Compras

- **POST /compras** - Realizar compra (role: USER)
- **GET /compras/me** - Minhas compras (role: USER)
- **GET /compras** - Todas as compras (role: ADMIN)
- **GET /compras/{id}** - Detalhes da compra (role: USER, ADMIN)

## Teste com cURL

### Obter Token
```bash
curl -X POST http://localhost:8180/realms/papeis-artisticos/protocol/openid-connect/token \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "grant_type=password" \
  -d "client_id=papeis-frontend" \
  -d "client_secret=<CLIENT_SECRET>" \
  -d "username=usuario@papeis.com" \
  -d "password=<PASSWORD>"
```

**Response:**
```json
{
  "access_token": "eyJhbGc...",
  "expires_in": 300,
  "refresh_expires_in": 1800,
  "token_type": "Bearer",
  ...
}
```

### Usar Token em Request
```bash
curl -H "Authorization: Bearer {access_token}" \
  http://localhost:8080/auth/me
```

## Teste com Postman

1. Crie uma collection
2. Configure a autenticação:
   - Type: `OAuth 2.0`
   - Grant Type: `Authorization Code`
   - Auth URL: `http://localhost:8180/realms/papeis-artisticos/protocol/openid-connect/auth`
   - Access Token URL: `http://localhost:8180/realms/papeis-artisticos/protocol/openid-connect/token`
   - Client ID: `papeis-frontend`
   - Client Secret: `<CLIENT_SECRET>`
   - Scope: `openid profile email`
3. Clique em "Get New Access Token"
4. Use o token nos headers das requisições

## Integração com Frontend Angular

### Install Angular OAuth2 Library

```bash
npm install angular-oauth2-oidc
```

### Configure no app.module.ts

```typescript
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { OAuthModule } from 'angular-oauth2-oidc';

@NgModule({
  imports: [
    BrowserModule,
    OAuthModule.forRoot()
  ],
  ...
})
export class AppModule { }
```

### Configure o Serviço de Autenticação

```typescript
import { Injectable } from '@angular/core';
import { OAuthService, OAuthEvent } from 'angular-oauth2-oidc';
import { authCodeFlowConfig } from './auth.config';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private oauthService: OAuthService) {}

  configure() {
    this.oauthService.configure(authCodeFlowConfig);
    this.oauthService.tryLoginImplicitFlow();
  }

  login() {
    this.oauthService.initCodeFlow();
  }

  logout() {
    this.oauthService.logOut();
  }

  getToken() {
    return this.oauthService.getAccessToken();
  }

  isLoggedIn() {
    return this.oauthService.hasValidAccessToken();
  }
}
```

### Arquivo auth.config.ts

```typescript
import { AuthConfig } from 'angular-oauth2-oidc';

export const authCodeFlowConfig: AuthConfig = {
  clientId: 'papeis-frontend',
  redirectUrl: `${window.location.origin}/`,
  postLogoutRedirectUri: `${window.location.origin}/`,
  issuer: 'http://localhost:8180/realms/papeis-artisticos',
  scopes: ['openid', 'profile', 'email'],
  responseType: 'code',
  grantType: 'code',
  disableAtHashCheck: true
};
```

## Troubleshooting

### Token Inválido
- Verifique se o token não expirou
- Confirme que o issuer no Quarkus corresponde ao realm do Keycloak
- Valide os claims do JWT em https://jwt.io

### Acesso Negado (403)
- Verifique se o usuário tem a role necessária no Keycloak
- Role mapping foi feita corretamente?

### CORS Error
- Verifique configuração de CORS no `application.properties`
- Adicione a origem do frontend em "Valid redirect URIs"

### Keycloak não inicia
```bash
# Verificar logs
docker logs keycloak

# Reiniciar
docker-compose restart keycloak
```

## Próximos Passos

1. ✅ Implementar login via Keycloak no frontend
2. ✅ Armazenar token localmente
3. ✅ Incluir token em todas as requisições
4. ✅ Implementar logout
5. ✅ Gerenciar erros de autenticação
6. ✅ Implementar refresh de token
