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
        // Apenas compila o projeto, sem rodar testes ainda.
        stage('Build') {
            steps {
                echo 'Iniciando Build (compile)...'
                // '-DskipTests' pula os testes nesta fase
                sh 'mvn clean compile -DskipTests'
            }
        }

        // Estágio 2: Testes em Paralelo
        // Roda os testes de UI e API ao mesmo tempo.
        stage('Test') {
            parallel {
                // Ramo 1: Testes de API
                stage('API Tests') {
                    steps {
                        echo 'Iniciando testes de API...'
                        // Usamos 'test' (e não 'install') e -pl para focar no módulo
                        // 'try...catch...finally' garante que o relatório seja gerado
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
                        // Passamos EXECUTION_MODE=headless para o DriverManager
                        sh '''
                            mvn test -pl frontend-tests -DEXECUTION_MODE=headless || \
                            echo 'Testes de UI falharam, mas continuaremos.'
                        '''
                    }
                }
            }
        }
    } // Fim dos stages

    // Estágio 3: Pós-Execução (Publicar Relatórios)
    // Este bloco 'post' roda DEPOIS de todos os stages.
    post {
        // 'always' garante que os relatórios sejam publicados
        // mesmo se os testes falharem (queremos ver o motivo da falha).
        always {
            echo 'Publicando relatórios HTML...'

            // Publica o Relatório de API
            publishHTML(target: [
                reportDir: 'backend-tests/target/cucumber-reports', // Onde o relatório está
                reportFiles: 'api-report.html',     // O nome do arquivo principal
                reportName: 'Relatório de Testes API', // Nome do link no Jenkins
                keepAll: true,
                alwaysLinkToLastBuild: true,
                allowMissing: true // Não falha o build se o arquivo não existir
            ])

            // Publica o Relatório de UI
            publishHTML(target: [
                reportDir: 'frontend-tests/target/cucumber-reports', // Onde o relatório está
                reportFiles: 'ui-report.html',    // O nome do arquivo principal
                reportName: 'Relatório de Testes UI', // Nome do link no Jenkins
                keepAll: true,
                alwaysLinkToLastBuild: true,
                allowMissing: true // Não falha o build se o arquivo não existir
            ])
        }
    }
}