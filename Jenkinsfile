pipeline {
    // Usamos o 'agent' que configuramos (com Java, Maven e Chrome)
    agent {
        docker {
            image 'allan-jenkins-agent:latest'
            args '-u root' // Necessário se o WebDriverManager precisar rodar como root
        }
    }

    stages {
        // Estágio 1: Compilação
        // Instala o core localmente para garantir que os outros módulos o encontrem
        stage('Build') {
            steps {
                echo 'Iniciando Build (compile)...'
                sh 'mvn clean install -DskipTests'
            }
        }

        // Estágio 2: Testes em Paralelo
        // Roda os testes de UI, API e Mobile ao mesmo tempo.
        stage('Test') {
            // failFast: false (Padrão) garante que todos os ramos tentem rodar
            parallel {
                // Ramo 1: Testes de API
                stage('API Tests') {
                    steps {
                        echo 'Iniciando testes de API...'
                        // AJUSTE: Removemos o "|| echo..." e adicionamos -Dmaven.test.failure.ignore=false
                        // Isso garante que o build falhe se os testes de API quebrarem.
                        sh 'mvn test -pl backend-tests -Dcucumber.filter.tags="@api" -Dmaven.test.failure.ignore=false'
                    }
                }

                // Ramo 2: Testes de UI (Frontend)
                stage('UI Tests') {
                    steps {
                        echo 'Iniciando testes de UI (Headless)...'
                         // AJUSTE: Removemos o "|| echo..." e adicionamos -Dmaven.test.failure.ignore=false
                        sh 'mvn test -pl frontend-tests -DEXECUTION_MODE=headless -Dmaven.test.failure.ignore=false'
                    }
                }

                // Ramo 3: Testes Mobile (AJUSTADO)
                stage('Mobile Tests') {
                    steps {
                        echo 'Iniciando testes Mobile...'
                        // AJUSTE: Passando o IP do Host (192.168.18.63) e forçando a falha
                        sh 'mvn test -pl mobile-tests -Dtest=RunCucumberMobTests -DAPPIUM_SERVER_URL="http://192.168.18.63:4723/" -Dmaven.test.failure.ignore=false'
                    }
                }
            }
        }
    } // Fim dos stages

    // Estágio 3: Pós-Execução (Publicar Relatórios)
    post {
        // 'always' garante que os relatórios sejam publicados mesmo se os testes falharem (BUILD VERMELHO)
        always {
            echo 'Publicando relatórios HTML...'

            // Publica o Relatório de API
            publishHTML(target: [
                reportDir: 'backend-tests/target/cucumber-reports',
                reportFiles: 'api-report.html',
                reportName: 'Relatório de Testes API',
                keepAll: true,
                alwaysLinkToLastBuild: true,
                allowMissing: true
            ])

            // Publica o Relatório de UI
            publishHTML(target: [
                reportDir: 'frontend-tests/target/cucumber-reports',
                reportFiles: 'ui-report.html',
                reportName: 'Relatório de Testes UI',
                keepAll: true,
                alwaysLinkToLastBuild: true,
                allowMissing: true
            ])

            // Publica o Relatório Mobile
            publishHTML(target: [
                reportDir: 'mobile-tests/target/cucumber-reports',
                reportFiles: 'mobile-report.html',
                reportName: 'Relatório de Testes Mobile',
                keepAll: true,
                alwaysLinkToLastBuild: true,
                allowMissing: true
            ])

            // Adiciona o gráfico de tendências do Cucumber
            cucumber buildStatus: 'null',
                     fileIncludePattern: '**/cucumber-reports/*.json',
                     sortingMethod: 'ALPHABETICAL'
        }
    }
}