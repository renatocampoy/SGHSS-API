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
- **`GET /users/{id}`** - Buscar usuário por ID
- **`PUT /users/{id}`** - Atualizar dados do usuário
- **`DELETE /users/{id}`** - Excluir usuário
- **`GET /users`** - Listar todos os usuários
- **`POST /users`** - Criar um novo usuário

### Teleatendimento
- **`POST /teleservice`** - Criar um teleatendimento
- **`PUT /teleservice/{id}/status`** - Atualizar status do teleatendimento
- **`GET /teleservice/{id}`** - Buscar teleatendimento por ID
- **`DELETE /teleservice/{id}`** - Cancelar teleatendimento
- **`GET /teleservice/professional/{professionalId}`** - Listar teleatendimentos por profissional
- **`GET /teleservice/patient/{patientId}`** - Listar teleatendimentos por paciente
- **`GET /teleservice/date-range`** - Listar teleatendimentos por período

### Pacientes
- **`GET /patients/{id}`** - Buscar paciente por ID
- **`PUT /patients/{id}`** - Atualizar paciente
- **`DELETE /patients/{id}`** - Excluir paciente
- **`GET /patients`** - Listar todos os pacientes
- **`POST /patients`** - Criar um paciente

### Profissionais de Saúde
- **`GET /professionals/{id}`** - Buscar profissional de saúde por ID
- **`PUT /professionals/{id}`** - Atualizar profissional de saúde
- **`DELETE /professionals/{id}`** - Excluir profissional de saúde
- **`GET /professionals`** - Listar todos os profissionais de saúde
- **`POST /professionals`** - Criar um profissional de saúde

### Consultas Médicas
- **`GET /medical-consultations/{id}`** - Buscar consulta médica por ID
- **`PUT /medical-consultations/{id}`** - Atualizar consulta médica
- **`DELETE /medical-consultations/{id}`** - Cancelar consulta médica
- **`GET /medical-consultations`** - Listar todas as consultas médicas
- **`POST /medical-consultations`** - Criar uma consulta médica

### Prescrições Médicas
- **`POST /medical-prescriptions`** - Criar uma prescrição médica
- **`GET /medical-prescriptions/consultation/{consultationId}`** - Listar prescrições por consulta

### Internações Hospitalares
- **`PUT /hospitalizations/{id}`** - Atualizar internação
- **`DELETE /hospitalizations/{id}`** - Cancelar internação
- **`POST /hospitalizations`** - Registrar uma internação
- **`PATCH /hospitalizations/{id}/discharge`** - Dar alta a um paciente internado

### Relatórios de Internação
- **`GET /hospitalization-reports/{id}`** - Buscar relatório de internação por ID
- **`PUT /hospitalization-reports/{id}`** - Atualizar relatório de internação
- **`DELETE /hospitalization-reports/{id}`** - Excluir relatório de internação
- **`POST /hospitalization-reports`** - Criar um relatório de internação
- **`GET /hospitalization-reports/hospitalization/{hospitalizationId}`** - Listar relatórios por internação
- **`GET /hospitalization-reports/date-range`** - Listar relatórios por intervalo de datas

### Auditoria do Sistema
- **`GET /system-audit`** - Listar todas as entradas de auditoria
- **`POST /system-audit`** - Criar uma entrada de auditoria
- **`GET /system-audit/user/{username}`** - Listar auditorias por usuário
- **`GET /system-audit/entity/{entityName}`** - Listar auditorias por entidade

### Estoque
- **`GET /stock/{id}`** - Buscar estoque por ID
- **`PUT /stock/{id}`** - Atualizar estoque
- **`DELETE /stock/{id}`** - Excluir estoque
- **`GET /stock`** - Listar todos os estoques
- **`POST /stock`** - Criar um estoque
- **`GET /stock/service-unit/{serviceUnitId}`** - Listar estoques por unidade de serviço

### Quartos
- **`GET /rooms/{id}`** - Buscar quarto por ID
- **`PUT /rooms/{id}`** - Atualizar um quarto
- **`DELETE /rooms/{id}`** - Excluir um quarto
- **`GET /rooms`** - Listar todos os quartos
- **`POST /rooms`** - Criar um quarto
- **`GET /rooms/service-unit/{serviceUnitId}`** - Listar quartos por unidade de serviço

### Ocupação de Quartos
- **`GET /rooms-occupancy/{id}`** - Buscar ocupação por ID
- **`GET /rooms-occupancy/room/{roomId}`** - Listar ocupações por quarto
- **`GET /rooms-occupancy/patient/{patientId}`** - Listar ocupações por paciente
- **`POST /rooms-occupancy`** - Registrar ocupação de quarto
- **`PATCH /rooms-occupancy/{id}/release`** - Liberar um quarto ocupado

### Autenticação
- **`POST /auth/token`** - Gerar token de autenticação

## Segurança
Todos os endpoints protegidos exigem um token JWT. O token deve ser enviado no cabeçalho de cada requisição autenticada.

---
Essa documentação fornece uma visão geral dos principais recursos da API SGHSS. Para detalhes adicionais sobre cada endpoint e exemplos de uso, consulte a especificação OpenAPI completa.

