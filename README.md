# SGHSS API - Sistema de Gestão Hospitalar e de Serviços de Saúde

## 📌 Sobre
A **SGHSS API** é um sistema de gestão hospitalar que fornece funcionalidades para **cadastro de pacientes**, **agendamentos**, **telemedicina**, **prontuários eletrônicos** e **auditoria**. Segue padrões de segurança e compliance com a **LGPD**.

## 🚀 Tecnologias Utilizadas
- **Java 17**
- **Spring Boot 3.1**
- **Spring Security com JWT**
- **Banco de Dados MySQL**
- **Swagger OpenAPI 3.1**

## 🌎 Servidores
| Ambiente        | URL |
|----------------|---------------------------------|
| Desenvolvimento | `http://localhost:8088/api`   |
| Produção       | `https://sghss-api-prod.com/api` |

## 📖 Módulos da API

### 🏥 **Unidades de Serviço** (`/service-units`)
- **POST** `/service-units` → Criar uma unidade de serviço
- **GET** `/service-units` → Listar todas as unidades de serviço
- **GET** `/service-units/{id}` → Buscar unidade de serviço por ID
- **PUT** `/service-units/{id}` → Atualizar unidade de serviço
- **DELETE** `/service-units/{id}` → Excluir unidade de serviço

### 🛏️ **Quartos Hospitalares** (`/rooms`)
- **POST** `/rooms` → Criar um quarto
- **GET** `/rooms` → Listar todos os quartos
- **GET** `/rooms/{id}` → Buscar quarto por ID
- **PUT** `/rooms/{id}` → Atualizar um quarto
- **DELETE** `/rooms/{id}` → Excluir um quarto
- **GET** `/rooms/service-unit/{serviceUnitId}` → Listar quartos por unidade de serviço

### 🏨 **Ocupação de Quartos** (`/rooms-occupancy`)
- **POST** `/rooms-occupancy` → Registrar ocupação de quarto
- **PATCH** `/rooms-occupancy/{id}/release` → Liberar um quarto
- **GET** `/rooms-occupancy/{id}` → Buscar ocupação por ID
- **GET** `/rooms-occupancy/room/{roomId}` → Listar ocupações por quarto
- **GET** `/rooms-occupancy/patient/{patientId}` → Listar ocupações por paciente

### 👨‍⚕️ **Profissionais de Saúde** (`/professionals`)
- **POST** `/professionals` → Criar um profissional de saúde
- **GET** `/professionals` → Listar todos os profissionais de saúde
- **GET** `/professionals/{id}` → Buscar profissional por ID
- **PUT** `/professionals/{id}` → Atualizar profissional de saúde
- **DELETE** `/professionals/{id}` → Excluir profissional de saúde

### 🏥 **Internações Hospitalares** (`/hospitalizations`)
- **POST** `/hospitalizations` → Registrar uma internação
- **PUT** `/hospitalizations/{id}` → Atualizar uma internação
- **PATCH** `/hospitalizations/{id}/discharge` → Dar alta a um paciente
- **DELETE** `/hospitalizations/{id}` → Cancelar uma internação

### 📄 **Relatórios de Internação** (`/hospitalization-reports`)
- **POST** `/hospitalization-reports` → Criar um relatório de internação
- **GET** `/hospitalization-reports/{id}` → Buscar relatório por ID
- **GET** `/hospitalization-reports/hospitalization/{hospitalizationId}` → Listar relatórios por internação
- **GET** `/hospitalization-reports/date-range?start=YYYY-MM-DD&end=YYYY-MM-DD` → Listar relatórios por período
- **PUT** `/hospitalization-reports/{id}` → Atualizar relatório
- **DELETE** `/hospitalization-reports/{id}` → Excluir relatório

### 💊 **Prescrições Médicas** (`/medical-prescriptions`)
- **POST** `/medical-prescriptions` → Criar uma prescrição médica
- **GET** `/medical-prescriptions/consultation/{consultationId}` → Listar prescrições por consulta

### 📋 **Consultas Médicas** (`/medical-consultations`)
- **POST** `/medical-consultations` → Criar uma consulta médica
- **GET** `/medical-consultations` → Listar todas as consultas
- **GET** `/medical-consultations/{id}` → Buscar consulta por ID
- **PUT** `/medical-consultations/{id}` → Atualizar consulta médica
- **DELETE** `/medical-consultations/{id}` → Cancelar consulta médica
- **GET** `/medical-consultations/patient/{patientId}` → Listar consultas por paciente
- **GET** `/medical-consultations/professional/{professionalId}` → Listar consultas por profissional

### 👨‍💼 **Usuários** (`/users`)
- **GET** `/users` → Listar todos os usuários
- **GET** `/users/{id}` → Buscar usuário por ID
- **PUT** `/users/{id}` → Atualizar usuário
- **DELETE** `/users/{id}` → Excluir usuário

### 🎭 **Papéis (Roles) e Permissões** (`/roles`, `/permissions`)
- **POST** `/roles` → Criar um papel (role)
- **GET** `/roles` → Listar todos os papéis
- **DELETE** `/roles/{id}` → Excluir um papel
- **POST** `/permissions` → Criar uma permissão
- **GET** `/permissions` → Listar todas as permissões
- **DELETE** `/permissions/{id}` → Excluir uma permissão

### 🔑 **Autenticação** (`/auth`)
- **POST** `/auth/token` → Gerar token JWT

### 📜 **Auditoria do Sistema** (`/system-audit`)
- **GET** `/system-audit` → Listar todas as auditorias
- **POST** `/system-audit` → Criar uma entrada de auditoria

### 📩 Contato
📧 **Desenvolvedor**: Renato Campoy | [renato@campoy.eng.br](mailto:renato@campoy.eng.br)  
🌍 **Website**: [campoy.eng.br](https://campoy.eng.br)

---
**Licença MIT** - Esta API é de código aberto e pode ser utilizada conforme os termos da [Licença MIT](https://opensource.org/licenses/MIT).

