# 🏦 ParaBank — Automação E2E com Selenium + JUnit

Projeto acadêmico de automação de testes end-to-end desenvolvido em **Java**, utilizando **Selenium WebDriver**, **JUnit 5**, **Maven** e **Allure Report**, aplicado ao sistema de demonstração **ParaBank**.

A proposta deste projeto foi praticar a automação de fluxos reais de usuário em uma aplicação web, organizando os testes de forma clara, executável e mais próxima de um cenário profissional de QA.

## 🎯 Objetivo

O principal objetivo foi construir uma suíte automatizada capaz de validar jornadas importantes do sistema, cobrindo funcionalidades críticas da aplicação e aplicando boas práticas de estruturação dos testes.

Com este projeto, busquei praticar:

- automação E2E em aplicação web realista
- organização de testes com Java + JUnit
- execução via Maven
- uso de relatórios para leitura rápida dos resultados
- preparação da suíte para uso local e evolução em CI

---

## ✅ Resultado atual

- **10 casos de teste automatizados**
- **100% de aprovação na execução reportada**
- **Relatório Allure com visão consolidada das suítes**
- **Execução via Maven**
- **Gerenciamento de drivers com WebDriverManager**

---

## 🧪 Cenários automatizados

A suíte cobre fluxos relevantes do ParaBank, incluindo:

- **Login válido**
- **Login inválido**
- **Abertura de nova conta**
- **Transferência de fundos**
- **Pagamento de contas**
- **Atualização de informações do perfil**
- **Solicitação de empréstimo**
- **Acesso ao histórico / extrato**
- **Logout**
- **Acesso sem autenticação / validações de navegação**

> Os nomes exatos dos testes podem variar conforme a implementação no código, mas a suíte foi estruturada para cobrir os principais fluxos do sistema.

---

## 🛠️ Stack utilizada

- **Java**
- **Selenium WebDriver**
- **JUnit 5**
- **Maven**
- **Allure Report**
- **WebDriverManager**
- **GitHub Actions** *(quando configurado no repositório)*

---

## 📊 Relatórios

O projeto utiliza **Allure Report** para facilitar a leitura da execução dos testes.

Com isso, é possível visualizar:

- quantidade total de casos executados
- taxa de sucesso da suíte
- suites organizadas por classe
- visão mais clara da execução do que apenas logs de terminal

---

## 🚀 Como executar

### 1. Clone o repositório

```bash
git clone https://github.com/Dev02553/Testes-Automatizados_ParaBank.git
2. Acesse a pasta do projeto

Execute os comandos na pasta que contém o pom.xml.

cd Testes-Automatizados_ParaBank
3. Execute os testes
Execução padrão
mvn test
Execução headless
mvn test -Dheadless=true
4. Abrir o relatório Allure
mvn allure:serve

No PowerShell, se quiser executar os comandos em sequência, prefira rodar em linhas separadas ou usar ; no lugar de &&.

Exemplo:

mvn test -Dheadless=true
mvn allure:serve
📁 Estrutura esperada do projeto

A organização segue a ideia de separar testes, configuração e execução, mantendo a suíte mais fácil de entender e evoluir.

Exemplo de responsabilidades presentes no projeto:

classes de teste por fluxo
setup/teardown centralizados
dependências via Maven
execução local por terminal
suporte a relatório com Allure
📚 Observações
O ParaBank é um sistema público de demonstração mantido pela Parasoft.
Por ser um ambiente demo, a aplicação pode apresentar variações ou instabilidades pontuais.
Este projeto tem finalidade acadêmica e de portfólio, servindo como prova prática de automação E2E com Java.
📈 Próximos passos

Como evolução do projeto, os próximos passos mais naturais seriam:

ampliar a cobertura para mais cenários negativos
melhorar o isolamento de dados entre execuções
expandir a integração com CI
publicar relatórios como artefatos automatizados
evoluir a estrutura para padrões ainda mais reutilizáveis
👨‍💻 Autor

David Silva Rodrigues

Estudante de Análise e Desenvolvimento de Sistemas, com foco em projetos de QA, automação, dados e desenvolvimento.

GitHub: https://github.com/Dev02553
LinkedIn: https://www.linkedin.com/in/david-silva-rodrigues-500190284/
🇺🇸 English version
🏦 ParaBank — E2E Automation with Selenium + JUnit

Academic end-to-end test automation project built with Java, Selenium WebDriver, JUnit 5, Maven, and Allure Report, using the ParaBank demo application.

The goal of this project was to practice automating real user flows in a web application while organizing the suite in a clearer and more maintainable way.

🎯 Goal

The main goal was to create an automated suite capable of validating important user journeys in the system, covering critical flows and applying solid automation practices.

This project was also used to practice:

E2E automation in a realistic web application
test organization with Java + JUnit
Maven-based execution
result analysis through reports
preparation for local execution and CI evolution
✅ Current result
10 automated test cases
100% pass rate in the reported execution
Allure Report with suite overview
Maven-based execution
Driver management with WebDriverManager
🧪 Automated scenarios

The suite covers relevant ParaBank flows, including:

Valid login
Invalid login
Open new account
Fund transfer
Bill payment
Update profile information
Loan request
Transaction history / account activity
Logout
Access without authentication / navigation validations
🛠️ Tech stack
Java
Selenium WebDriver
JUnit 5
Maven
Allure Report
WebDriverManager
GitHub Actions (when configured in the repository)
📊 Reports

The project uses Allure Report to make test execution easier to read and analyze.

This helps visualize:

total executed test cases
suite success rate
organized suite view by class
clearer execution results than terminal logs alone
🚀 How to run
1. Clone the repository
git clone https://github.com/Dev02553/Testes-Automatizados_ParaBank.git
2. Open the project folder

Run commands from the folder that contains the pom.xml file.

cd Testes-Automatizados_ParaBank
3. Run the tests
Standard execution
mvn test
Headless execution
mvn test -Dheadless=true
4. Open the Allure report
mvn allure:serve
📚 Notes
ParaBank is a public demo system maintained by Parasoft.
Because it is a demo environment, occasional instability or UI variation may happen.
This project was built for academic and portfolio purposes, as a practical demonstration of E2E automation with Java.
👨‍💻 Author

David Silva Rodrigues

Systems Analysis and Development student focused on QA, automation, data, and software development.

GitHub: https://github.com/Dev02553
LinkedIn: https://www.linkedin.com/in/david-silva-rodrigues-500190284/
