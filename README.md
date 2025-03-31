# SGHSS API - Sistema de Gestão Hospitalar e de Serviços de Saúde

## Descrição
API para gestão de hospitais, clínicas e serviços de saúde. Fornece funcionalidades para cadastro de pacientes, agendamentos, telemedicina, prontuários eletrônicos e auditoria. Segue padrões de segurança e compliance com a LGPD.

## Informações do Contato
- **Nome:** Renato Campoy
- **Site:** [campoy.eng.br](https://campoy.eng.br)
- **E-mail:** renato@campoy.eng.br

## Licença
Esta API está licenciada sob a [GNU General Public License v3.0]([https://opensource.org/licenses/MIT](https://github.com/renatocampoy/SGHSS-API/blob/main/LICENSE)).

## 🚀 Como rodar o projeto SGHSS-API

Este projeto é uma API REST desenvolvida com Java 17, Spring Boot 3.1, Spring Security com JWT, Banco de Dados MySQL e documentação Swagger OpenAPI 3.1.

## 🛠️ Pré-requisitos

Certifique-se que os seguintes itens estejam instalados:
	•	Java Development Kit (JDK) 17+
	•	Docker
	•	Docker Compose
	•	Git

## 📥 Clone o repositório

git clone https://github.com/renatocampoy/SGHSS-API.git

Navegue até o diretório clonado:

cd SGHSS-API

⸻

## 📦 Executando o Banco de Dados com Docker Compose

Inicie os containers necessários (banco MySQL):

docker-compose up -d

Este comando inicializa o banco de dados MySQL em segundo plano usando Docker.

## 🚧 Compilando e Executando o Projeto

Execute o comando abaixo para compilar o projeto utilizando Maven Wrapper:

./mvnw clean install

Após a compilação, inicie a aplicação com o seguinte comando:

./mvnw spring-boot:run

## 🔗 Acessando a API

Após execução bem-sucedida, a API estará disponível no endereço:

http://localhost:8088/api

Você pode acessar também a documentação Swagger (se configurada):

http://localhost:8088/swagger-ui/index.html

## 📌 Notas adicionais
	•	Verifique se as portas definidas no projeto (por padrão 8088) estão liberadas no seu sistema.
	•	Caso encontre problemas com versões ou dependências, confira o arquivo pom.xml e as instruções adicionais no repositório.

✅ Agora, o projeto SGHSS-API deverá estar rodando localmente em seu ambiente!

## Autenticação
A API utiliza autenticação via **JWT Bearer Token**. Para acessar endpoints protegidos, inclua o token no cabeçalho:

```http
Authorization: Bearer <seu_token_jwt>
```

## Endpoints Principais

### Usuários

#### `GET /users/{id}`
Retorna os dados de um usuário específico.

- **Requer autenticação:** Sim
- **Parâmetros:**
  - `id` (path) - ID do usuário
- **Resposta:**
```json
{
  "id": 1,
  "email": "usuario@dominio.com",
  "role": "ADMIN",
  "status": "ATIVO",
  "createdAt": "2023-01-01T12:00:00",
  "updatedAt": "2023-01-02T12:00:00"
}
```

#### `PUT /users/{id}`
Atualiza os dados de um usuário específico.

- **Requer autenticação:** Sim (Administrador)
- **Parâmetros:**
  - `id` (path) - ID do usuário
- **Corpo da requisição:**
```json
{
  "email": "usuario@dominio.com",
  "role": "MEDICO",
  "status": "ATIVO"
}
```
- **Resposta:**
```json
{
  "id": 1,
  "email": "usuario@dominio.com",
  "role": "MEDICO",
  "status": "ATIVO"
}
```

#### `DELETE /users/{id}`
Exclui um usuário específico.

- **Requer autenticação:** Sim (Administrador)
- **Parâmetros:**
  - `id` (path) - ID do usuário
- **Resposta:**
```json
{
  "message": "Usuário excluído com sucesso."
}
```

#### `POST /users`
Cria um novo usuário.

- **Requer autenticação:** Sim (Administrador)
- **Corpo da requisição:**
```json
{
  "email": "novo@usuario.com",
  "password": "senha123",
  "role": "MEDICO",
  "status": "ATIVO"
}
```
- **Resposta:**
```json
{
  "id": 2,
  "email": "novo@usuario.com",
  "role": "MEDICO",
  "status": "ATIVO"
}
```

### Teleatendimento

#### `POST /teleservice`
Cria um teleatendimento.

- **Requer autenticação:** Sim
- **Corpo da requisição:**
```json
{
  "patientId": 1,
  "professionalId": 2,
  "date": "2023-01-01T10:00:00"
}
```
- **Resposta:**
```json
{
  "id": 1,
  "status": "AGENDADO",
  "date": "2023-01-01T10:00:00"
}
```

#### `PUT /teleservice/{id}/status`
Atualiza o status do teleatendimento.

- **Requer autenticação:** Sim
- **Parâmetros:**
  - `id` (path) - ID do teleatendimento
- **Corpo da requisição:**
```json
{
  "status": "FINALIZADO"
}
```
- **Resposta:**
```json
{
  "id": 1,
  "status": "FINALIZADO"
}
```

#### `GET /teleservice/{id}`
Busca teleatendimento por ID.

- **Requer autenticação:** Sim
- **Parâmetros:**
  - `id` (path) - ID do teleatendimento
- **Resposta:**
```json
{
  "id": 1,
  "patientId": 1,
  "professionalId": 2,
  "status": "AGENDADO",
  "date": "2023-01-01T10:00:00"
}
```

#### `DELETE /teleservice/{id}`
Cancela teleatendimento.

- **Requer autenticação:** Sim
- **Parâmetros:**
  - `id` (path) - ID do teleatendimento
- **Resposta:**
```json
{
  "message": "Teleatendimento cancelado com sucesso."
}
```

### Pacientes

#### `GET /patients/{id}`
Busca paciente por ID.

- **Requer autenticação:** Sim
- **Parâmetros:**
  - `id` (path) - ID do paciente
- **Resposta:**
```json
{
  "id": 1,
  "name": "João da Silva",
  "email": "joao@dominio.com",
  "status": "ATIVO"
}
```

#### `PUT /patients/{id}`
Atualiza paciente.

- **Requer autenticação:** Sim
- **Parâmetros:**
  - `id` (path) - ID do paciente
- **Corpo da requisição:**
```json
{
  "name": "João da Silva",
  "email": "joao@dominio.com",
  "status": "ATIVO"
}
```
- **Resposta:**
```json
{
  "id": 1,
  "name": "João da Silva",
  "email": "joao@dominio.com",
  "status": "ATIVO"
}
```

#### `DELETE /patients/{id}`
Exclui paciente.

- **Requer autenticação:** Sim
- **Parâmetros:**
  - `id` (path) - ID do paciente
- **Resposta:**
```json
{
  "message": "Paciente excluído com sucesso."
}
```

#### `POST /patients`
Cria um paciente.

- **Requer autenticação:** Sim
- **Corpo da requisição:**
```json
{
  "name": "Novo Paciente",
  "email": "novo@paciente.com",
  "status": "ATIVO"
}
```
- **Resposta:**
```json
{
  "id": 2,
  "name": "Novo Paciente",
  "email": "novo@paciente.com",
  "status": "ATIVO"
}
```

### Profissionais de Saúde

#### `GET /professionals/{id}`
Busca profissional de saúde por ID.

- **Requer autenticação:** Sim
- **Parâmetros:**
  - `id` (path) - ID do profissional
- **Resposta:**
```json
{
  "id": 1,
  "name": "Dr. Pedro",
  "email": "pedro@dominio.com",
  "specialty": "CARDIOLOGIA",
  "status": "ATIVO"
}
```

#### `PUT /professionals/{id}`
Atualiza profissional de saúde.

- **Requer autenticação:** Sim
- **Parâmetros:**
  - `id` (path) - ID do profissional
- **Corpo da requisição:**
```json
{
  "name": "Dr. Pedro",
  "email": "pedro@dominio.com",
  "specialty": "CARDIOLOGIA",
  "status": "ATIVO"
}
```
- **Resposta:**
```json
{
  "id": 1,
  "name": "Dr. Pedro",
  "email": "pedro@dominio.com",
  "specialty": "CARDIOLOGIA",
  "status": "ATIVO"
}
```

#### `DELETE /professionals/{id}`
Exclui profissional de saúde.

- **Requer autenticação:** Sim
- **Parâmetros:**
  - `id` (path) - ID do profissional
- **Resposta:**
```json
{
  "message": "Profissional excluído com sucesso."
}
```

#### `POST /professionals`
Cria um profissional de saúde.

- **Requer autenticação:** Sim (Administrador)
- **Corpo da requisição:**
```json
{
  "name": "Novo Profissional",
  "email": "novo@profissional.com",
  "specialty": "PEDIATRIA",
  "status": "ATIVO"
}
```
- **Resposta:**
```json
{
  "id": 2,
  "name": "Novo Profissional",
  "email": "novo@profissional.com",
  "specialty": "PEDIATRIA",
  "status": "ATIVO"
}
```

### Consultas Médicas

#### `GET /medical-consultations/{id}`
Busca consulta médica por ID.

- **Requer autenticação:** Sim
- **Parâmetros:**
  - `id` (path) - ID da consulta
- **Resposta:**
```json
{
  "id": 1,
  "patientId": 1,
  "professionalId": 2,
  "date": "2023-01-01T10:00:00",
  "status": "AGENDADA"
}
```

#### `PUT /medical-consultations/{id}`
Atualiza consulta médica.

- **Requer autenticação:** Sim
- **Parâmetros:**
  - `id` (path) - ID da consulta
- **Corpo da requisição:**
```json
{
  "date": "2023-01-01T11:00:00",
  "status": "REALIZADA"
}
```
- **Resposta:**
```json
{
  "id": 1,
  "patientId": 1,
  "professionalId": 2,
  "date": "2023-01-01T11:00:00",
  "status": "REALIZADA"
}
```

#### `DELETE /medical-consultations/{id}`
Cancela consulta médica.

- **Requer autenticação:** Sim
- **Parâmetros:**
  - `id` (path) - ID da consulta
- **Resposta:**
```json
{
  "message": "Consulta médica cancelada com sucesso."
}
```

#### `POST /medical-consultations`
Cria uma consulta médica.

- **Requer autenticação:** Sim
- **Corpo da requisição:**
```json
{
  "patientId": 1,
  "professionalId": 2,
  "date": "2023-01-01T10:00:00"
}
```
- **Resposta:**
```json
{
  "id": 2,
  "patientId": 1,
  "professionalId": 2,
  "date": "2023-01-01T10:00:00",
  "status": "AGENDADA"
}
```

### Prescrições Médicas

#### `POST /medical-prescriptions`
Cria uma prescrição médica.

- **Requer autenticação:** Sim
- **Corpo da requisição:**
```json
{
  "consultationId": 1,
  "medications": [
    {
      "name": "Medicamento A",
      "dosage": "1 comprimido",
      "frequency": "2 vezes ao dia"
    }
  ]
}
```
- **Resposta:**
```json
{
  "id": 1,
  "consultationId": 1,
  "medications": [
    {
      "name": "Medicamento A",
      "dosage": "1 comprimido",
      "frequency": "2 vezes ao dia"
    }
  ]
}
```

#### `GET /medical-prescriptions/consultation/{consultationId}`
Lista prescrições por consulta.

- **Requer autenticação:** Sim
- **Parâmetros:**
  - `consultationId` (path) - ID da consulta
- **Resposta:**
```json
[
  {
    "id": 1,
    "consultationId": 1,
    "medications": [
      {
        "name": "Medicamento A",
        "dosage": "1 comprimido",
        "frequency": "2 vezes ao dia"
      }
    ]
  }
]
```

### Internações Hospitalares

#### `PUT /hospitalizations/{id}`
Atualiza internação.

- **Requer autenticação:** Sim
- **Parâmetros:**
  - `id` (path) - ID da internação
- **Corpo da requisição:**
```json
{
  "status": "EM_ALTA"
}
```
- **Resposta:**
```json
{
  "id": 1,
  "status": "EM_ALTA"
}
```

#### `DELETE /hospitalizations/{id}`
Cancela internação.

- **Requer autenticação:** Sim
- **Parâmetros:**
  - `id` (path) - ID da internação
- **Resposta:**
```json
{
  "message": "Internação cancelada com sucesso."
}
```

#### `POST /hospitalizations`
Registra uma internação.

- **Requer autenticação:** Sim
- **Corpo da requisição:**
```json
{
  "patientId": 1,
  "roomId": 2,
  "admissionDate": "2023-01-01T10:00:00"
}
```
- **Resposta:**
```json
{
  "id": 1,
  "patientId": 1,
  "roomId": 2,
  "admissionDate": "2023-01-01T10:00:00",
  "status": "ATIVA"
}
```

#### `PATCH /hospitalizations/{id}/discharge`
Dá alta a um paciente internado.

- **Requer autenticação:** Sim
- **Parâmetros:**
  - `id` (path) - ID da internação
- **Resposta:**
```json
{
  "message": "Paciente recebeu alta com sucesso."
}
```

### Relatórios de Internação

#### `GET /hospitalization-reports/{id}`
Busca relatório de internação por ID.

- **Requer autenticação:** Sim
- **Parâmetros:**
  - `id` (path) - ID do relatório
- **Resposta:**
```json
{
  "id": 1,
  "hospitalizationId": 1,
  "reportDetails": "Detalhes sobre a internação."
}
```

#### `PUT /hospitalization-reports/{id}`
Atualiza relatório de internação.

- **Requer autenticação:** Sim
- **Parâmetros:**
  - `id` (path) - ID do relatório
- **Corpo da requisição:**
```json
{
  "reportDetails": "Detalhes atualizados sobre a internação."
}
```
- **Resposta:**
```json
{
  "id": 1,
  "hospitalizationId": 1,
  "reportDetails": "Detalhes atualizados sobre a internação."
}
```

#### `DELETE /hospitalization-reports/{id}`
Exclui relatório de internação.

- **Requer autenticação:** Sim
- **Parâmetros:**
  - `id` (path) - ID do relatório
- **Resposta:**
```json
{
  "message": "Relatório de internação excluído com sucesso."
}
```

#### `POST /hospitalization-reports`
Cria um relatório de internação.

- **Requer autenticação:** Sim
- **Corpo da requisição:**
```json
{
  "hospitalizationId": 1,
  "reportDetails": "Detalhes sobre a internação."
}
```
- **Resposta:**
```json
{
  "id": 1,
  "hospitalizationId": 1,
  "reportDetails": "Detalhes sobre a internação."
}
```

#### `GET /hospitalization-reports/hospitalization/{hospitalizationId}`
Lista relatórios por internação.

- **Requer autenticação:** Sim
- **Parâmetros:**
  - `hospitalizationId` (path) - ID da internação
- **Resposta:**
```json
[
  {
    "id": 1,
    "hospitalizationId": 1,
    "reportDetails": "Detalhes sobre a internação."
  }
]
```

#### `GET /hospitalization-reports/date-range`
Lista relatórios por intervalo de datas.

- **Requer autenticação:** Sim
- **Parâmetros:**
  - `startDate` (query) - Data de início
  - `endDate` (query) - Data de fim
- **Resposta:**
```json
[
  {
    "id": 1,
    "hospitalizationId": 1,
    "reportDetails": "Detalhes sobre a internação."
  }
]
```

### Auditoria do Sistema

#### `GET /system-audit`
Lista todas as entradas de auditoria.

- **Requer autenticação:** Sim
- **Resposta:**
```json
[
  {
    "id": 1,
    "action": "CREATE",
    "entity": "Usuario",
    "username": "admin",
    "timestamp": "2023-01-01T12:00:00"
  }
]
```

#### `POST /system-audit`
Cria uma entrada de auditoria.

- **Requer autenticação:** Sim
- **Corpo da requisição:**
```json
{
  "action": "CREATE",
  "entity": "Usuario",
  "username": "admin"
}
```
- **Resposta:**
```json
{
  "id": 1,
  "action": "CREATE",
  "entity": "Usuario",
  "username": "admin",
  "timestamp": "2023-01-01T12:00:00"
}
```

#### `GET /system-audit/user/{username}`
Lista auditorias por usuário.

- **Requer autenticação:** Sim
- **Parâmetros:**
  - `username` (path) - Nome do usuário
- **Resposta:**
```json
[
  {
    "id": 1,
    "action": "CREATE",
    "entity": "Usuario",
    "username": "admin",
    "timestamp": "2023-01-01T12:00:00"
  }
]
```

#### `GET /system-audit/entity/{entityName}`
Lista auditorias por entidade.

- **Requer autenticação:** Sim
- **Parâmetros:**
  - `entityName` (path) - Nome da entidade
- **Resposta:**
```json
[
  {
    "id": 1,
    "action": "CREATE",
    "entity": "Usuario",
    "username": "admin",
    "timestamp": "2023-01-01T12:00:00"
  }
]
```

### Estoque

#### `GET /stock/{id}`
Busca estoque por ID.

- **Requer autenticação:** Sim
- **Parâmetros:**
  - `id` (path) - ID do estoque
- **Resposta:**
```json
{
  "id": 1,
  "item": "Medicamento A",
  "quantity": 100,
  "status": "DISPONIVEL"
}
```

#### `PUT /stock/{id}`
Atualiza estoque.

- **Requer autenticação:** Sim
- **Parâmetros:**
  - `id` (path) - ID do estoque
- **Corpo da requisição:**
```json
{
  "quantity": 150
}
```
- **Resposta:**
```json
{
  "id": 1,
  "item": "Medicamento A",
  "quantity": 150,
  "status": "DISPONIVEL"
}
```

#### `DELETE /stock/{id}`
Exclui estoque.

- **Requer autenticação:** Sim
- **Parâmetros:**
  - `id` (path) - ID do estoque
- **Resposta:**
```json
{
  "message": "Estoque excluído com sucesso."
}
```

#### `POST /stock`
Cria um estoque.

- **Requer autenticação:** Sim
- **Corpo da requisição:**
```json
{
  "item": "Medicamento B",
  "quantity": 200
}
```
- **Resposta:**
```json
{
  "id": 2,
  "item": "Medicamento B",
  "quantity": 200,
  "status": "DISPONIVEL"
}
```

#### `GET /stock/service-unit/{serviceUnitId}`
Lista estoques por unidade de serviço.

- **Requer autenticação:** Sim
- **Parâmetros:**
  - `serviceUnitId` (path) - ID da unidade de serviço
- **Resposta:**
```json
[
  {
    "id": 1,
    "item": "Medicamento A",
    "quantity": 100,
    "status": "DISPONIVEL"
  }
]
```

### Quartos

#### `GET /rooms/{id}`
Busca quarto por ID.

- **Requer autenticação:** Sim
- **Parâmetros:**
  - `id` (path) - ID do quarto
- **Resposta:**
```json
{
  "id": 1,
  "number": "101",
  "status": "DISPONIVEL"
}
```

#### `PUT /rooms/{id}`
Atualiza um quarto.

- **Requer autenticação:** Sim
- **Parâmetros:**
  - `id` (path) - ID do quarto
- **Corpo da requisição:**
```json
{
  "status": "OCUPADO"
}
```
- **Resposta:**
```json
{
  "id": 1,
  "number": "101",
  "status": "OCUPADO"
}
```

#### `DELETE /rooms/{id}`
Exclui um quarto.

- **Requer autenticação:** Sim
- **Parâmetros:**
  - `id` (path) - ID do quarto
- **Resposta:**
```json
{
  "message": "Quarto excluído com sucesso."
}
```

#### `POST /rooms`
Cria um quarto.

- **Requer autenticação:** Sim
- **Corpo da requisição:**
```json
{
  "number": "102",
  "status": "DISPONIVEL"
}
```
- **Resposta:**
```json
{
  "id": 2,
  "number": "102",
  "status": "DISPONIVEL"
}
```

#### `GET /rooms/service-unit/{serviceUnitId}`
Lista quartos por unidade de serviço.

- **Requer autenticação:** Sim
- **Parâmetros:**
  - `serviceUnitId` (path) - ID da unidade de serviço
- **Resposta:**
```json
[
  {
    "id": 1,
    "number": "101",
    "status": "DISPONIVEL"
  }
]
```

### Ocupação de Quartos

#### `GET /rooms-occupancy/{id}`
Busca ocupação por ID.

- **Requer autenticação:** Sim
- **Parâmetros:**
  - `id` (path) - ID da ocupação
- **Resposta:**
```json
{
  "id": 1,
  "roomId": 1,
  "patientId": 1,
  "occupancyDate": "2023-01-01T10:00:00"
}
```

#### `GET /rooms-occupancy/room/{roomId}`
Lista ocupações por quarto.

- **Requer autenticação:** Sim
- **Parâmetros:**
  - `roomId` (path) - ID do quarto
- **Resposta:**
```json
[
  {
    "id": 1,
    "roomId": 1,
    "patientId": 1,
    "occupancyDate": "2023-01-01T10:00:00"
  }
]
```

#### `GET /rooms-occupancy/patient/{patientId}`
Lista ocupações por paciente.

- **Requer autenticação:** Sim
- **Parâmetros:**
  - `patientId` (path) - ID do paciente
- **Resposta:**
```json
[
  {
    "id": 1,
    "roomId": 1,
    "patientId": 1,
    "occupancyDate": "2023-01-01T10:00:00"
  }
]
```

#### `POST /rooms-occupancy`
Registra ocupação de quarto.

- **Requer autenticação:** Sim
- **Corpo da requisição:**
```json
{
  "roomId": 1,
  "patientId": 1,
  "occupancyDate": "2023-01-01T10:00:00"
}
```
- **Resposta:**
```json
{
  "id": 1,
  "roomId": 1,
  "patientId": 1,
  "occupancyDate": "2023-01-01T10:00:00"
}
```

#### `PATCH /rooms-occupancy/{id}/release`
Libera um quarto ocupado.

- **Requer autenticação:** Sim
- **Parâmetros:**
  - `id` (path) - ID da ocupação
- **Resposta:**
```json
{
  "message": "Quarto liberado com sucesso."
}
```

### Autenticação

#### `POST /auth/token`
Gera token de autenticação.

- **Corpo da requisição:**
```json
{
  "email": "usuario@dominio.com",
  "password": "senha123"
}
```
- **Resposta:**
```json
{
  "token": "<seu_token_jwt>"
}
```

## Segurança
Todos os endpoints protegidos exigem um token JWT. O token deve ser enviado no cabeçalho de cada requisição autenticada.

---
Essa documentação fornece uma visão geral dos principais recursos da API SGHSS. Para detalhes adicionais sobre cada endpoint e exemplos de uso, consulte a especificação OpenAPI completa.
