# 🏥 Sistema Inteligente de Gestão de Fila Cirúrgica

Plataforma desenvolvida para automatizar, priorizar e acompanhar filas de cirurgias eletivas no contexto do SUS, utilizando critérios clínicos baseados na **Classificação da Sociedade Americana de Anestesiologia** e na validade do **Laudo Anestésico**, além de controle por perfis de usuário.

O sistema substitui processos manuais (planilhas e agendas) por uma solução digital orientada por dados, promovendo transparência, eficiência operacional e apoio à tomada de decisão.

---

## 🚀 Funcionalidades

### 📋 Gestão da Fila Cirúrgica

- Cadastro de pacientes na fila
- Priorização automática baseada em:
    - Data de vencimento do Laudo Anestésico
    - Data de entrada na fila
- Classificação clínica pela Sociedade Americana de Anestesiologia (ASA I, II e III)
- Visualização da fila ordenada em tempo real
- Conclusão de atendimentos cirúrgicos
- Atualização automática de status:
    - EM_ESPERA
    - CONCLUIDO
    - PERDA_LA
- Notificação de Vencimento do Laudo Anestésico 

---

### 👥 Perfis de Usuário

O sistema opera com três perfis distintos:

#### 🧑‍⚕️ MÉDICO
- Visualiza a lista de pacientes ordenada por prioridade

#### 🧑‍💼 ADMINISTRATIVO
- Cadastra novos pacientes
- Conclui atendimentos cirúrgicos


#### 📊 GESTOR
- Gera relatórios estratégicos
---

### ⏰ Monitoramento Automático

Um scheduler diário verifica pacientes com **Laudo Anestésico vencido** e atualiza automaticamente o status para `PERDA_LA`, reduzindo desperdício de vagas cirúrgicas.

---

## 🧠 Regras de Negócio

### Validade do Laudo de Anestésico por Classificação ASA

| Classificação da Sociedade Americana de Anestesiologia | Validade |
|--------------------------------------------------------|----------|
| ASA I  | 365 dias |
| ASA II | 180 dias |
| ASA III| 90 dias  |

A fila é ordenada por:

1. Data de vencimento do Laudo Anestésico
2. Data de entrada na fila

---

### 🏗️ Arquitetura do Sistema

A arquitetura deste projeto foi desenhada seguindo o padrão de **Renderização no Lado do Servidor (SSR)**. Diferente de modelos onde o Frontend e o Backend são independentes, esta abordagem utiliza uma integração profunda para gerar a interface diretamente no servidor, otimizando o fluxo de dados.

#### 1. Camada de Apresentação (Frontend com Thymeleaf)
A interface do usuário é construída utilizando o **Thymeleaf** como engine de templates.
- **Processamento no Servidor:** Ao receber uma requisição, o servidor processa o arquivo HTML, injeta os dados dinâmicos (Model) e envia ao navegador um HTML puro já renderizado.
- **Vantagem:** Elimina a necessidade de um servidor de frontend separado (como Nginx) e simplifica o gerenciamento de segurança e rotas, que ficam centralizados no Spring Boot.

#### 2. Camada de Aplicação (Backend Spring Boot)
O núcleo da aplicação reside em um container Java utilizando o **Spring Boot**, estruturado da seguinte forma:
- **Controllers:** Gerenciam as requisições HTTP e determinam qual template Thymeleaf deve ser exibido.
- **Service Layer:** Onde estão isoladas as regras de negócio da aplicação.
- **Data Access (JPA/Hibernate):** Camada responsável por traduzir as operações de objetos Java para comandos SQL compatíveis com o banco de dados.

#### 3. Camada de Persistência (PostgreSQL)
Os dados são armazenados em um banco de dados relacional **PostgreSQL**, rodando em um container dedicado.
- **Conectividade:** A comunicação ocorre via porta interna `5432`.
- **Persistência:** No ambiente Docker, são utilizados volumes para garantir que as informações permaneçam íntegras mesmo após o reinício dos containers.

#### 4. Orquestração e Containers (Docker)
Todo o ecossistema é gerenciado pelo **Docker Compose**, que cria uma rede isolada para os serviços:
- **Container App:** Empacota o Backend + Frontend (Thymeleaf) na porta `8080`.
- **Container DB:** Gerencia o banco de dados PostgreSQL.
- **Unificação:** Esta estrutura permite que o ambiente de desenvolvimento seja idêntico ao de produção, bastando um único comando para subir toda a infraestrutura.


---


## 🐳 Execução via Docker
O projeto está preparado para execução totalmente containerizada utilizando Docker Compose.

### Pré-requisitos

- Docker
- Docker Compose

### ▶️ Subir ambiente completo

Na raiz do projeto, execute:

```bash
docker compose up --build

```
A aplicação estará disponível em http://localhost:8080/pacientes

---

### 🎯 Impacto Social

O projeto foi desenvolvido com foco na responsabilidade social e na otimização de recursos públicos, gerando benefícios diretos para a gestão de saúde:

* **Redução de falhas manuais:** Automatização de processos que anteriormente dependiam de registros físicos, diminuindo o risco de erros de agendamento.
* **Eficiência Hospitalar:** Otimização do fluxo de trabalho das equipes, permitindo que a infraestrutura hospitalar seja utilizada em sua capacidade máxima.
* **Transparência e Ética:** Garantia de maior clareza na gestão da fila cirúrgica, promovendo equidade no atendimento aos cidadãos.
* **Apoio à Gestão Pública:** Fornecimento de dados estruturados que auxiliam gestores na tomada de decisões estratégicas baseadas em evidências.

---

## 👨‍💻 Autor

**Anderson Argollo** *Backend Engineer — Java | Spring | Cloud | APIs*

---

