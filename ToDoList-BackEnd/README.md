# ✅ ToDo App

## 📝 Descrição
Este é um aplicativo de lista de tarefas (ToDo) desenvolvido com Spring Boot que permite aos usuários criar, visualizar, atualizar e excluir tarefas.

## 🛠️ Tecnologias Utilizadas
- ☕ Java 17
- 🍃 Spring Boot 3.5.3
- 📊 Spring Data JPA
- 🌐 Spring Web
- 🧰 H2 Database (banco de dados em memória)
- 🏗️ Lombok
- 🔄 Spring DevTools

## 🏛️ Arquitetura
O projeto segue uma arquitetura em camadas:
- **Model**: Entidades e objetos de dados
- **Repository**: Camada de acesso a dados
- **Service**: Lógica de negócios
- **Validator**: Validação de dados
- **Controller**: Endpoints da API REST

## 🔍 Funcionalidades
- ➕ Criar novas tarefas
- 📋 Listar todas as tarefas
- 🔄 Atualizar o status de uma tarefa (concluída/não concluída)
- 🔎 Buscar tarefa por ID
- 🗑️ Excluir tarefas

## 📡 Endpoints da API
| Método | URL | Descrição |
|--------|-----|-----------|
| POST | `/todo` | Criar uma nova tarefa |
| GET | `/todo` | Listar todas as tarefas |
| PUT | `/todo/{id}` | Atualizar o status de uma tarefa |
| DELETE | `/todo/{id}` | Excluir uma tarefa |

## 📋 Estrutura da Tarefa (ToDo)

json { "id": 1, "descricao": "Exemplo de tarefa", "concluido": false }

## 🚀 Como Executar
1. **Pré-requisitos**:
   - Java 17 instalado
   - Maven instalado

2. **Clonar o repositório**:
   ```bash
   git clone https://github.com/seu-usuario/ToDo.git
   cd ToDo
   ```

3. **Compilar e executar**:
   ```bash
   ./mvnw spring-boot:run
   ```

4. **Acessar a aplicação**:
   - A API estará disponível em: `http://localhost:8080`
   - Banco de dados H2 (console): `http://localhost:8080/h2-console`

## 🧪 Testes
Para executar os testes:

bash ./mvnw test


## 👨‍💻 Desenvolvido por
Lucas Sousa Rocha

## 📄 Licença
Este projeto está licenciado sob a [Licença MIT](LICENSE).