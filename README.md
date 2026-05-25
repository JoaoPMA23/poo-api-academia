# 🏋️ Academia API — Sistema de Gerenciamento de Academia

API REST desenvolvida com **Spring Boot** para gerenciamento de uma academia, contemplando alunos, instrutores, modalidades, planos e matrículas.

---

## 📋 Requisitos Atendidos

| # | Requisito | Como foi atendido |
|---|-----------|-------------------|
| 1 | Spring Boot | `spring-boot-starter-web`, `spring-boot-starter-data-jpa` |
| 2 | Banco relacional | H2 em memória (dev) / PostgreSQL (prod) |
| 3 | Sem classes do professor | Projeto 100% original com tema academia |
| 4 | ≥ 4 classes persistentes | `Aluno`, `Instrutor`, `Modalidade`, `Plano`, `Matricula` |
| 5 | Herança | `Aluno` e `Instrutor` estendem `Pessoa` |
| 6 | OneToMany | `Aluno` ↔ `Matricula` / `Plano` ↔ `Matricula` |
| 7 | ManyToMany | `Aluno` ↔ `Modalidade` / `Instrutor` ↔ `Modalidade` |
| 8 | Estrutura MVC | `controller`, `service`, `repository`, `model`, `exception` |
| 9 | CRUD completo | Todos os controllers com insert, update, delete e consultas |

---

## 🗂 Estrutura do Projeto

```
academia-api/
├── src/main/java/com/academia/
│   ├── AcademiaApiApplication.java       ← Classe principal
│   ├── model/
│   │   ├── Pessoa.java                   ← Superclasse (herança)
│   │   ├── Aluno.java                    ← Estende Pessoa
│   │   ├── Instrutor.java                ← Estende Pessoa
│   │   ├── Modalidade.java               ← ManyToMany com Aluno e Instrutor
│   │   ├── Plano.java                    ← OneToMany com Matricula
│   │   └── Matricula.java                ← ManyToOne Aluno + Plano
│   ├── repository/
│   │   ├── AlunoRepository.java
│   │   ├── InstrutorRepository.java
│   │   ├── ModalidadeRepository.java
│   │   ├── PlanoRepository.java
│   │   └── MatriculaRepository.java
│   ├── service/
│   │   ├── AlunoService.java
│   │   ├── InstrutorService.java
│   │   ├── ModalidadeService.java
│   │   ├── PlanoService.java
│   │   └── MatriculaService.java
│   ├── controller/
│   │   ├── AlunoController.java
│   │   ├── InstrutorController.java
│   │   ├── ModalidadeController.java
│   │   ├── PlanoController.java
│   │   └── MatriculaController.java
│   └── exception/
│       ├── EntidadeNaoEncontradaException.java
│       ├── RegraDeNegocioException.java
│       └── GlobalExceptionHandler.java
└── src/main/resources/
    ├── application.properties
    └── data.sql                          ← Dados iniciais
```

---

## 🔗 Diagrama de Relacionamentos

```
         ┌─────────────┐
         │   Pessoa    │  ← Superclasse (herança TABLE_PER_CLASS)
         └──────┬──────┘
        ┌───────┴────────┐
        ▼                ▼
   ┌─────────┐      ┌────────────┐
   │  Aluno  │      │  Instrutor │
   └────┬────┘      └─────┬──────┘
        │                 │
        │  ManyToMany     │ ManyToMany
        └────────┬────────┘
                 ▼
          ┌────────────┐
          │ Modalidade │
          └────────────┘

   ┌─────────┐  OneToMany   ┌──────────┐  ManyToOne  ┌────────┐
   │  Aluno  │◄────────────►│ Matricula│◄────────────►│ Plano  │
   └─────────┘              └──────────┘              └────────┘
```

---

## 🚀 Como Executar

### Pré-requisitos
- Java 17+
- Maven 3.6+

### Executar
```bash
cd academia-api
mvn spring-boot:run
```

A API estará disponível em: `http://localhost:8080`

Console H2 (banco em memória): `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:academiadb`
- User: `sa` | Senha: (vazio)

---

## 📡 Endpoints da API

### Alunos `/alunos`

| Método | URL | Descrição |
|--------|-----|-----------|
| GET | `/alunos` | Lista todos os alunos |
| GET | `/alunos?nome=João` | Busca alunos por nome |
| GET | `/alunos?status=ATIVO` | Filtra por status |
| GET | `/alunos/{id}` | Busca aluno por ID |
| POST | `/alunos` | Cadastra novo aluno |
| PUT | `/alunos/{id}` | Atualiza aluno |
| DELETE | `/alunos/{id}` | Remove aluno |
| POST | `/alunos/{id}/modalidades/{modId}` | Inscreve aluno em modalidade |
| DELETE | `/alunos/{id}/modalidades/{modId}` | Remove aluno de modalidade |

**Exemplo de cadastro (POST /alunos):**
```json
{
  "nome": "João Silva",
  "cpf": "12345678901",
  "email": "joao@email.com",
  "telefone": "11987654321",
  "dataNascimento": "1995-05-20",
  "peso": 80.5,
  "altura": 1.75
}
```

### Instrutores `/instrutores`

| Método | URL | Descrição |
|--------|-----|-----------|
| GET | `/instrutores` | Lista todos |
| GET | `/instrutores/{id}` | Busca por ID |
| POST | `/instrutores` | Cadastra novo instrutor |
| PUT | `/instrutores/{id}` | Atualiza instrutor |
| DELETE | `/instrutores/{id}` | Remove instrutor |
| POST | `/instrutores/{id}/modalidades/{modId}` | Vincula a modalidade |
| DELETE | `/instrutores/{id}/modalidades/{modId}` | Remove vínculo |

**Exemplo (POST /instrutores):**
```json
{
  "nome": "Carlos Ferreira",
  "cpf": "98765432100",
  "email": "carlos@academia.com",
  "telefone": "11912345678",
  "dataNascimento": "1988-03-15",
  "cref": "012345-G/SP",
  "salario": 3500.00,
  "especialidade": "Musculação e Condicionamento Físico"
}
```

### Modalidades `/modalidades`

| Método | URL | Descrição |
|--------|-----|-----------|
| GET | `/modalidades` | Lista todas |
| GET | `/modalidades?ativas=true` | Apenas ativas |
| GET | `/modalidades?tipo=YOGA` | Por tipo |
| POST | `/modalidades` | Cadastra modalidade |
| PUT | `/modalidades/{id}` | Atualiza modalidade |
| DELETE | `/modalidades/{id}` | Remove modalidade |

**Tipos disponíveis:** `MUSCULACAO`, `CARDIO`, `YOGA`, `DANCA`, `LUTA`, `NATACAO`, `PILATES`, `OUTRO`

### Planos `/planos`

| Método | URL | Descrição |
|--------|-----|-----------|
| GET | `/planos` | Lista todos |
| GET | `/planos?ativos=true` | Apenas ativos |
| POST | `/planos` | Cadastra plano |
| PUT | `/planos/{id}` | Atualiza plano |
| DELETE | `/planos/{id}` | Remove plano |

### Matrículas `/matriculas`

| Método | URL | Descrição |
|--------|-----|-----------|
| GET | `/matriculas` | Lista todas |
| GET | `/matriculas?alunoId=1` | Matrículas de um aluno |
| GET | `/matriculas?vencidas=true` | Matrículas vencidas |
| GET | `/matriculas?vencendoEmDias=7` | Vencendo em N dias |
| POST | `/matriculas` | Realizar matrícula |
| PATCH | `/matriculas/{id}/status?status=SUSPENSA` | Atualizar status |
| PATCH | `/matriculas/{id}/cancelar` | Cancelar matrícula |
| DELETE | `/matriculas/{id}` | Excluir matrícula |

**Exemplo (POST /matriculas):**
```json
{
  "alunoId": 1,
  "planoId": 2,
  "valorPago": 239.90
}
```

---

## 💡 Conceitos de POO Demonstrados

- **Herança**: `Aluno` e `Instrutor` herdam atributos de `Pessoa` (nome, CPF, e-mail, telefone, dataNascimento)
- **Encapsulamento**: Atributos privados com getters/setters via Lombok
- **Polimorfismo**: Tratamento de `Aluno` e `Instrutor` como `Pessoa`
- **Abstração**: Classe `Pessoa` é abstrata, não pode ser instanciada diretamente
- **Associações**: OneToMany, ManyToMany conforme regras de negócio

---

## 👥 Grupo
> Preencher com os nomes dos integrantes do grupo
