pipeline {
    // Define que o pipeline rodará dentro do container Docker configurado
    agent {
        docker {
            image 'allan-jenkins-agent:latest'
            args '-u root' // Permissão de root para instalação de pacotes/drivers se necessário
        }
    }

    stages {
        // Estágio 1: Build
        // Compila todo o projeto e instala dependências do 'core'
        stage('Build') {
            steps {
                echo '🔨 Iniciando Build (Clean & Install)...'
                sh 'mvn clean install -DskipTests'
            }
        }

        // Estágio 2: Testes em Paralelo
        // Executa Backend, Frontend e Mobile simultaneamente para ganhar tempo
        stage('Test') {
            parallel {
                // Ramo 1: Backend (API)
                stage('API Tests') {
                    steps {
                        echo '🚀 Iniciando testes de API...'
                        // -pl: aponta para o módulo
                        // -Dmaven.test.failure.ignore=false: força o build a falhar se o teste quebrar
                        sh 'mvn test -pl backend-tests -Dcucumber.filter.tags="@api" -Dmaven.test.failure.ignore=false'
                    }
                }

                // Ramo 2: Frontend (Web)
                stage('UI Tests') {
                    steps {
                        echo '🌐 Iniciando testes de UI (Headless)...'
                        // DEXECUTION_MODE=headless: define execução sem interface gráfica
                        sh 'mvn test -pl frontend-tests -DEXECUTION_MODE=headless -Dmaven.test.failure.ignore=false'
                    }
                }

                // Ramo 3: Mobile (Android)
                stage('Mobile Tests') {
                    steps {
                        echo '📱 Iniciando testes Mobile...'
                        // DAPPIUM_SERVER_URL: Aponta para o IP real da máquina Windows onde o Appium roda
                        sh 'mvn test -pl mobile-tests -Dtest=RunCucumberMobTests -DAPPIUM_SERVER_URL="http://192.168.18.63:4723/" -Dmaven.test.failure.ignore=false'
                    }
                }
            }
        }
    } // Fim dos stages

    // Estágio 3: Pós-Execução
    // Executado sempre, independentemente de sucesso ou falha
    post {
        always {
            echo '📊 Gerando e Publicando Relatórios...'

            // 1. Relatório HTML do Backend
            publishHTML(target: [
                reportDir: 'backend-tests/target/cucumber-reports',
                reportFiles: 'api-report.html',
                reportName: 'Relatório de Testes API',
                keepAll: true,
                alwaysLinkToLastBuild: true,
                allowMissing: true
            ])

            // 2. Relatório HTML do Frontend
            publishHTML(target: [
                reportDir: 'frontend-tests/target/cucumber-reports',
                reportFiles: 'ui-report.html',
                reportName: 'Relatório de Testes UI',
                keepAll: true,
                alwaysLinkToLastBuild: true,
                allowMissing: true
            ])

            // 3. Relatório HTML do Mobile
            publishHTML(target: [
                reportDir: 'mobile-tests/target/cucumber-reports',
                reportFiles: 'mobile-report.html',
                reportName: 'Relatório de Testes Mobile',
                keepAll: true,
                alwaysLinkToLastBuild: true,
                allowMissing: true
            ])

            // 4. Relatório Consolidado (Cucumber Trends)
            // Gera gráficos de tendência baseados nos arquivos .json gerados
            cucumber buildStatus: 'null',
                     fileIncludePattern: '**/cucumber-reports/*.json',
                     sortingMethod: 'ALPHABETICAL'
        }
    }
}