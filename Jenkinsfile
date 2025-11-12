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
        // Apenas compila o projeto (incluindo o core), sem rodar testes ainda.
        stage('Build') {
            steps {
                echo 'Iniciando Build (compile)...'
                // Instala o core localmente para garantir que o mobile consiga lê-lo se tiver dependência
                sh 'mvn clean install -DskipTests'
            }
        }

        // Estágio 2: Testes em Paralelo
        // Roda os testes de UI, API e Mobile ao mesmo tempo.
        stage('Test') {
            parallel {
                // Ramo 1: Testes de API
                stage('API Tests') {
                    steps {
                        echo 'Iniciando testes de API...'
                        sh '''
                            mvn test -pl backend-tests -Dcucumber.filter.tags="@api" || \
                            echo 'Testes de API falharam, mas continuaremos.'
                        '''
                    }
                }

                // Ramo 2: Testes de UI (Frontend)
                stage('UI Tests') {
                    steps {
                        echo 'Iniciando testes de UI (Headless)...'
                        sh '''
                            mvn test -pl frontend-tests -DEXECUTION_MODE=headless || \
                            echo 'Testes de UI falharam, mas continuaremos.'
                        '''
                    }
                }

                // Ramo 3: Testes Mobile (Novo)
                stage('Mobile Tests') {
                    steps {
                        echo 'Iniciando testes Mobile...'
                        // Nota: O emulador ou Appium Server deve estar acessível pela rede deste container
                        sh '''
                            mvn test -pl mobile-tests -Dtest=RunCucumberMobTests || \
                            echo 'Testes Mobile falharam, mas continuaremos.'
                        '''
                    }
                }
            }
        }
    } // Fim dos stages

    // Estágio 3: Pós-Execução (Publicar Relatórios)
    post {
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

            // Publica o Relatório Mobile (Novo)
            publishHTML(target: [
                reportDir: 'mobile-tests/target/cucumber-reports',
                reportFiles: 'mobile-report.html',
                reportName: 'Relatório de Testes Mobile',
                keepAll: true,
                alwaysLinkToLastBuild: true,
                allowMissing: true
            ])
        }
    }
}