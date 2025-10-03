# 🏦 Sistema Bancário

Um sistema bancário completo desenvolvido em Java com interface de linha de comando, que permite gerenciar contas correntes e poupanças com persistência em banco de dados MySQL.

## 📋 Funcionalidades

### ✨ Operações Bancárias
- **Cadastro de Contas**: Criação de contas correntes e poupanças
- **Depósitos**: Realização de depósitos em qualquer tipo de conta
- **Saques**: Saques com validação de saldo e taxa (conta corrente)
- **Transferências**: Transferências entre contas com validação
- **Rendimento**: Aplicação de rendimento em contas poupança (3% ao mês)

### 🔍 Consultas e Relatórios
- **Listagem**: Visualização de todas as contas cadastradas
- **Busca por Número**: Localização de conta específica
- **Busca por Titular**: Consulta de contas por nome do titular
- **Atualização**: Modificação de dados da conta
- **Remoção**: Exclusão de contas do sistema

## 🏗️ Arquitetura do Sistema

### 📁 Estrutura do Projeto
```
src/main/java/org/example/
├── Main.java                 # Interface principal do sistema
├── DAO/
│   └── ContaDAO.java         # Camada de acesso a dados
├── Model/
│   ├── Cliente.java          # Modelo de dados do cliente
│   ├── Conta.java            # Classe abstrata base
│   ├── ContaCorrente.java    # Implementação conta corrente
│   └── ContaPoupanca.java    # Implementação conta poupança
└── Util/
    └── Conexao.java          # Gerenciamento de conexão com BD
```

### 🎯 Padrões de Design Utilizados
- **DAO (Data Access Object)**: Separação da lógica de acesso a dados
- **Herança**: Hierarquia de classes para diferentes tipos de conta
- **Polimorfismo**: Comportamentos específicos para cada tipo de conta
- **Encapsulamento**: Controle de acesso aos atributos das classes

## 🚀 Tecnologias Utilizadas

- **Java 22**: Linguagem de programação principal
- **Maven**: Gerenciamento de dependências
- **MySQL**: Banco de dados relacional
- **JDBC**: Conexão com banco de dados
- **MySQL Connector/J 8.0.33**: Driver para MySQL

## 📦 Dependências

```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <version>8.0.33</version>
</dependency>
```

## ⚙️ Configuração e Instalação

### Pré-requisitos
- Java 22 ou superior
- Maven 3.6+
- MySQL 8.0 ou superior

### 1. Clone o repositório
```bash
git clone <url-do-repositorio>
cd Sistema_bancario
```

### 2. Configure o banco de dados
Crie um banco de dados MySQL chamado `Sistema_bancario` e execute os seguintes scripts SQL:

```sql
-- Criação da tabela cliente
CREATE TABLE cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) UNIQUE
);

-- Criação da tabela conta
CREATE TABLE conta (
    id INT AUTO_INCREMENT PRIMARY KEY,
    numero VARCHAR(20) UNIQUE NOT NULL,
    cliente_id INT NOT NULL,
    tipo ENUM('CORRENTE', 'POUPANCA') NOT NULL,
    saldo DECIMAL(10,2) DEFAULT 0.00,
    FOREIGN KEY (cliente_id) REFERENCES cliente(id)
);
```

### 3. Configure a conexão
Edite o arquivo `src/main/java/org/example/Util/Conexao.java` e ajuste as configurações:

```java
private static final String URL = "jdbc:mysql://localhost:3306/Sistema_bancario?useSSL=false&serverTimezone=UTC";
private static final String USER = "seu_usuario";
private static final String PASSWORD = "sua_senha";
```

### 4. Execute o projeto
```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="org.example.Main"
```

## 🎮 Como Usar

### Menu Principal
O sistema apresenta um menu interativo com as seguintes opções:

```
===== MENU BANCÁRIO =====
1 - Cadastrar conta
2 - Listar contas
3 - Depositar
4 - Sacar
5 - Transferir
6 - Atualizar conta
7 - Remover conta
8 - Buscar conta por número
9 - Buscar contas por titular
10 - Aplicar rendimento poupança
0 - Sair
```

### Exemplos de Uso

#### Cadastrar uma Conta Corrente
1. Escolha opção `1`
2. Informe o número da conta
3. Digite o nome do titular
4. Informe o CPF (opcional)
5. Defina o saldo inicial
6. Selecione tipo `1` para conta corrente

#### Realizar um Depósito
1. Escolha opção `3`
2. Informe o número da conta
3. Digite o valor do depósito

#### Aplicar Rendimento na Poupança
1. Escolha opção `10`
2. Informe o número da conta poupança
3. O sistema aplicará 3% de rendimento sobre o saldo atual

## 💡 Características Especiais

### Conta Corrente
- **Taxa de Saque**: R$ 2,50 por operação
- **Sem Limite**: Pode ter saldo negativo (com taxa)
- **Transferências**: Sem taxa adicional

### Conta Poupança
- **Rendimento**: 3% ao mês sobre o saldo
- **Sem Taxa**: Saques sem cobrança de taxa
- **Aplicação Manual**: Rendimento aplicado sob demanda

## 🔒 Segurança e Validações

- **Validação de Saldo**: Impede saques superiores ao saldo disponível
- **Transações**: Operações com commit/rollback automático
- **Prepared Statements**: Proteção contra SQL Injection
- **Validação de Entrada**: Verificação de valores positivos

## 🧪 Testando o Sistema

### Cenários de Teste Sugeridos

1. **Cadastre uma conta corrente** com saldo inicial
2. **Realize um depósito** e verifique o saldo
3. **Faça um saque** e observe a cobrança da taxa
4. **Cadastre uma conta poupança**
5. **Aplique rendimento** na poupança
6. **Realize uma transferência** entre contas
7. **Busque contas** por número e por titular

## 📈 Melhorias Futuras

- [ ] Interface gráfica (Swing/JavaFX)
- [ ] Autenticação de usuários
- [ ] Histórico de transações
- [ ] Relatórios em PDF
- [ ] API REST
- [ ] Testes unitários
- [ ] Logs de auditoria
- [ ] Backup automático

## 🤝 Contribuição

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.

## 👨‍💻 Autor

**Murilo Kerschbaum**
- GitHub: [@murilokerschbaum](https://github.com/murilokerschbaum)

## 📞 Suporte

Se você encontrar algum problema ou tiver dúvidas, por favor:

1. Verifique se seguiu todos os passos de instalação
2. Confirme se o MySQL está rodando
3. Verifique as configurações de conexão
4. Abra uma issue no repositório

---

⭐ **Se este projeto foi útil para você, considere dar uma estrela!** ⭐
