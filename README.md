# CRUD Generator - CE CRUD Express

## Descrição

O **CRUD Generator - CE CRUD Express** é uma aplicação desktop que facilita a criação de projetos Spring Boot com operações CRUD (Create, Read, Update, Delete). Com uma interface intuitiva, você pode configurar entidades e seus atributos, definir as configurações do banco de dados e o programa gera automaticamente uma estrutura completa do projeto com:

- **Classes Model** (entidades JPA)
- **Interfaces Repository** (extensão do JpaRepository)
- **Classes Service** (lógica de negócio)
- **Classes Controller** (API REST)
- **Arquivo pom.xml**
- **Arquivo application.properties**

## Tecnologias Utilizadas

- **Java** – Linguagem principal para o desenvolvimento.
- **Swing** – Framework para a criação da interface desktop, com o Look and Feel Nimbus para um design moderno.
- **Spring Boot** – Framework para desenvolvimento de aplicações Java com configuração mínima.
- **JPA (Hibernate)** – Para persistência de dados e mapeamento objeto-relacional.
- **Maven** – Gerenciador de dependências e build.
- **Git** – Controle de versão.

## Funcionalidades

- **Interface Gráfica Intuitiva:**  
  Utiliza Swing com o Look and Feel Nimbus para oferecer um design moderno e agradável.

- **Criação de Entidades Personalizadas:**  
  Insira o nome da entidade e defina os atributos (por exemplo: `nome:String, preco:Double`).  
  Suporte para adicionar múltiplas entidades antes de gerar o projeto completo.

- **Configuração do Banco de Dados:**  
  Escolha entre PostgreSQL ou MySQL e informe os dados de conexão (nome e senha do banco).

- **Geração Automática de Código:**  
  O sistema gera a estrutura completa do projeto Spring Boot, incluindo:
  - `pom.xml`
  - `application.properties`
  - Classe Principal do Spring Boot
  - Classes Model (entidades JPA)
  - Interfaces Repository (extensão do JpaRepository)
  - Classes Service (lógica de negócio)
  - Classes Controller (API REST)

## Como Funciona

1. **Configuração das Entidades:**  
   Insira o nome da entidade e os atributos desejados, utilizando a sintaxe `atributo:Tipo` separados por vírgulas.

2. **Configuração do Banco de Dados:**  
   Selecione o tipo de banco (PostgreSQL ou MySQL) e informe o nome e a senha do banco de dados.

3. **Adição de Entidades:**  
   Utilize o botão **"Adicionar Classe"** para armazenar as configurações atuais. Você pode adicionar quantas entidades desejar.

4. **Geração do Projeto:**  
   Ao clicar em **"Gerar Projeto"**, o sistema cria a estrutura de pastas e gera os arquivos do projeto Spring Boot no diretório escolhido pelo usuário.

## Como Executar

### Pela IDE

- Abra o projeto em sua IDE (por exemplo, IntelliJ IDEA).
- Execute a classe `CrudGeneratorFrame` ou a classe `Main` que a chama para iniciar a aplicação desktop.

### Pela Linha de Comando

1. **Empacotar o Projeto:**

   ``` 
   mvn clean package ```

## Diagrama de Arquitetura
![Imagem do WhatsApp](https://raw.githubusercontent.com/eriklisboa1/CE-CRUD-Express-/80836e60db86e9137e686867410e468a18ca6f7a/Imagem%20do%20WhatsApp%20de%202025-04-04%20%C3%A0(s)%2015.38.50_ca090f74.jpg)




Este projeto é licenciado sob a [MIT License](LICENSE).


Copyright (c) 2025 Erik Lisboa




