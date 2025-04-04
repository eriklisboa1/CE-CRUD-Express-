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

## Estrutura Gerada

Após a geração, o projeto terá uma estrutura similar a:
CrudProject/ ├── pom.xml ├── src/ │ └── main/ │ ├── java/ │ │ └── org/ │ │ └── example/ │ │ └── crudproject/ │ │ ├── CrudProjectApplication.java │ │ ├── model/ │ │ │ └── [Entidade].java │ │ ├── repository/ │ │ │ └── [Entidade]Repository.java │ │ ├── service/ │ │ │ └── [Entidade]Service.java │ │ └── controller/ │ │ └── [Entidade]Controller.java │ └── resources/ │ └── application.properties

