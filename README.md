# 🏥 SGHSS API - Sistema de Gestão Hospitalar e de Serviços de Saúde

## 📖 Sobre o Projeto
O **SGHSS (Sistema de Gestão Hospitalar e de Serviços de Saúde)** é uma API para **gerenciamento de hospitais, clínicas e serviços de saúde**, incluindo:
- Cadastro de **pacientes, profissionais e usuários**
- **Consultas médicas, internações e telemedicina**
- **Prescrições médicas e prontuários**
- **Gestão de estoque e auditoria do sistema**
- **Controle de permissões e autenticação JWT**

A API segue **boas práticas de segurança e compliance com LGPD**.

---

## 🚀 Tecnologias Utilizadas
- **Java 17** + **Spring Boot 3**
- **Spring Security** + **JWT Authentication**
- **Spring Data JPA** (Hibernate) + **MySQL**
- **RabbitMQ** para filas de processamento
- **Swagger / OpenAPI 3** para documentação
- **Docker** para deploy containerizado

---

## 🛠️ Como Executar o Projeto

### 1️⃣ **Pré-requisitos**
- Java 17+
- Maven 3.8+
- Docker (para MySQL e RabbitMQ)

### 2️⃣ **Instalar dependências**
```sh
mvn clean install
```

### 3️⃣ **Rodar o projeto**
```sh
mvn spring-boot:run
```

### 4️⃣ **Acessar a documentação Swagger**
```sh
http://localhost:8088/api/swagger-ui/index.html
```

---

## 🔒 **Autenticação e Segurança**
Todos os endpoints protegidos exigem um **token JWT** no header `Authorization`.  
Para gerar um token:
```http
POST /auth/token
```
**Header:**  
`Content-Type: application/json`  
**Body:**
```json
{
  "username": "admin",
  "password": "admin123"
}
```
**Resposta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsIn..."
}
```
Agora, para acessar endpoints protegidos, inclua no **header**:
```
Authorization: Bearer <seu_token>
```

---

## 📌 **Módulos e Endpoints**
Abaixo está a lista completa de módulos do sistema e suas respectivas rotas.

### 🏥 **1. Pacientes (`/patients`)**
| Método | Rota | Descrição | Permissão |
|--------|------|-----------|------------|
| `POST` | `/patients` | Criar paciente | `ADMIN`, `PROFISSIONAL` |
| `GET` | `/patients/{id}` | Buscar paciente por ID | `ADMIN`, `PROFISSIONAL`, `PACIENTE` |
| `PUT` | `/patients/{id}` | Atualizar paciente | `ADMIN`, `PROFISSIONAL`, `PACIENTE` |
| `DELETE` | `/patients/{id}` | Remover paciente | `ADMIN` |

### 👨‍⚕️ **2. Profissionais (`/professionals`)**
| Método | Rota | Descrição | Permissão |
|--------|------|-----------|------------|
| `POST` | `/professionals` | Criar profissional de saúde | `ADMIN` |
| `GET` | `/professionals/{id}` | Buscar profissional por ID | Todos |
| `PUT` | `/professionals/{id}` | Atualizar profissional | `ADMIN` |
| `DELETE` | `/professionals/{id}` | Remover profissional | `ADMIN` |
| `GET` | `/professionals` | Listar todos os profissionais | Todos |

### 📋 **3. Consultas Médicas (`/medical-consultations`)**
| Método | Rota | Descrição | Permissão |
|--------|------|-----------|------------|
| `POST` | `/medical-consultations` | Criar consulta | `ADMIN`, `PROFISSIONAL` |
| `PUT` | `/medical-consultations/{id}` | Atualizar consulta | `ADMIN`, `PROFISSIONAL` |
| `DELETE` | `/medical-consultations/{id}` | Cancelar consulta | `ADMIN`, `PROFISSIONAL` |
| `GET` | `/medical-consultations/{id}` | Buscar consulta por ID | Todos |

### 💊 **4. Prescrições Médicas (`/medical-prescriptions`)**
| Método | Rota | Descrição | Permissão |
|--------|------|-----------|------------|
| `POST` | `/medical-prescriptions` | Criar prescrição | `PROFISSIONAL` |
| `GET` | `/medical-prescriptions/consultation/{consultationId}` | Listar prescrições por consulta | `ADMIN`, `PROFISSIONAL`, `PACIENTE` |

### 🏥 **5. Internações (`/hospitalizations`)**
| Método | Rota | Descrição | Permissão |
|--------|------|-----------|------------|
| `POST` | `/hospitalizations` | Criar internação | `ADMIN`, `PROFISSIONAL` |
| `PUT` | `/hospitalizations/{id}` | Atualizar internação | `ADMIN`, `PROFISSIONAL` |
| `DELETE` | `/hospitalizations/{id}` | Cancelar internação | `ADMIN` |
| `PATCH` | `/hospitalizations/{id}/discharge` | Dar alta ao paciente | `ADMIN`, `PROFISSIONAL` |

---

## 📝 **Licença**
Este projeto é licenciado sob a **Licença MIT**. Para mais informações, acesse:  
[MIT License](https://opensource.org/licenses/MIT)

---

## 📞 **Contato**
📌 **Desenvolvedor:** Renato Campoy  
📧 **Email:** renato@campoy.eng.br  
🌐 **Website:** [campoy.eng.br](https://campoy.eng.br)

---

🚀 **API completa, segura e bem documentada!** Caso tenha dúvidas, abra uma **issue** no repositório! 😃  
