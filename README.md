# Configuração do Banco de Dados

1. Instale o MySQL Server e certifique-se de que está rodando em 127.0.0.1:3306.

2. Acesse o MySQL com o usuário root. Use a senha definida durante a instalação.

3. crie o banco de dados dbagenda:

```
CREATE DATABASE `dbagenda`
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_0900_ai_ci;
```
4. Crie a tabela contatos:

```
CREATE TABLE `contatos` (
  `idcon` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(50) NOT NULL,
  `fone` VARCHAR(15) NOT NULL,
  `email` VARCHAR(50),
  PRIMARY KEY (`idcon`)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_0900_ai_ci;
```
# Importando o Projeto no Eclipse

1. No Eclipse, vá em:
File > Import > General > Projects from Folder or Archive
Selecione o diretório do projeto (agenda).

2. Certifique-se de que as seguintes configurações estão corretas:

Java Build Path:
A pasta agenda/src/main/java deve estar marcada.
Web App Libraries deve estar incluída.

Project Facets:
Dynamic Web Module: versão 6.0
Java: versão 21
JavaScript: versão 1.0

Target Runtime:
Selecione o Apache Tomcat, versão 10 ou superior
(recomendado: Tomcat 11.0.4)

# Executando

http://localhost:8080/agenda

ou

http://localhost:8080/agenda/index.html
