\# 🚀 Framework de Automação "Praxis"



Este é um framework de automação de testes (Web/UI e API) construído com Java, Selenium, Cucumber e Rest-Assured. O projeto está 100% integrado a um pipeline de Integração Contínua (CI) rodando com Jenkins em um container Docker.



---



\## 🛠️ Tecnologias Utilizadas



\* \*\*Java (JDK 11+)\*\*: Linguagem base do projeto.

\* \*\*Maven\*\*: Gerenciamento de dependências e do ciclo de vida do build.

\* \*\*Selenium WebDriver\*\*: Para automação dos testes de interface (Web/UI).

\* \*\*Cucumber (BDD)\*\*: Para a escrita dos testes em Gherkin (Dado, Quando, Então).

\* \*\*Rest-Assured\*\*: Para automação dos testes de API (backend).

\* \*\*JUnit\*\*: Como "runner" (executor) dos testes Cucumber.

\* \*\*WebDriverManager\*\*: Para o gerenciamento automático dos binários dos navegadores (como o ChromeDriver).

\* \*\*Jenkins\*\*: Servidor de CI para orquestrar e executar a pipeline.

\* \*\*Docker\*\*: Para rodar o Jenkins em um ambiente de container isolado e portátil.



---



\## 📂 Estrutura do Projeto



O framework é multi-módulo para uma melhor separação de responsabilidades:



\* \*\*`automacao-parent`\*\*: O POM pai que gerencia todas as dependências e plugins comuns.

\* \*\*`core`\*\*: Módulo central com classes utilitárias (ex: `DriverManager`, `ConfigLoader`).

\* \*\*`frontend-tests`\*\*: Módulo contendo os testes de UI (Selenium/Cucumber).

\* \*\*`backend-tests`\*\*: Módulo contendo os testes de API (RestAssured/Cucumber).



---



\## ▶️ Como Executar



\### 1. Execução Local (Com Interface Gráfica)



Para rodar os testes na sua máquina local (com o navegador abrindo):



```bash

\# Executa todos os módulos (UI e API)

mvn clean install

