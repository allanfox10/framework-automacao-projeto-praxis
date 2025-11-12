pipeline {
    agent {
        docker {
            image 'allan-jenkins-agent:latest'
            // AJUSTE CRÍTICO: Adiciona o mapeamento de rede para o container enxergar o Windows
            args '-u root --add-host=host.docker.internal:host-gateway'
        }
    }

    stages {
        stage('Build') {
            steps {
                echo '🔨 Iniciando Build (Clean & Install)...'
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Test') {
            parallel {
                stage('API Tests') {
                    steps {
                        echo '🚀 Iniciando testes de API...'
                        sh 'mvn test -pl backend-tests -Dcucumber.filter.tags="@api" -Dmaven.test.failure.ignore=false'
                    }
                }

                stage('UI Tests') {
                    steps {
                        echo '🌐 Iniciando testes de UI (Headless)...'
                        sh 'mvn test -pl frontend-tests -DEXECUTION_MODE=headless -Dmaven.test.failure.ignore=false'
                    }
                }

                stage('Mobile Tests') {
                    steps {
                        echo '📱 Iniciando testes Mobile...'
                        // AJUSTE: Agora usamos 'host.docker.internal' que foi configurado no agent acima
                        sh 'mvn test -pl mobile-tests -Dtest=RunCucumberMobTests -DAPPIUM_SERVER_URL="http://host.docker.internal:4723/" -Dmaven.test.failure.ignore=false'
                    }
                }
            }
        }
    }

    post {
        always {
            echo '📊 Processando Relatórios...'

            publishHTML(target: [
                reportDir: 'backend-tests/target/cucumber-reports',
                reportFiles: 'api-report.html',
                reportName: 'Relatório de Testes API',
                keepAll: true, allowMissing: true
            ])

            publishHTML(target: [
                reportDir: 'frontend-tests/target/cucumber-reports',
                reportFiles: 'ui-report.html',
                reportName: 'Relatório de Testes UI',
                keepAll: true, allowMissing: true
            ])

            publishHTML(target: [
                reportDir: 'mobile-tests/target/cucumber-reports',
                reportFiles: 'mobile-report.html',
                reportName: 'Relatório de Testes Mobile',
                keepAll: true, allowMissing: true
            ])

            cucumber buildStatus: 'null',
                     fileIncludePattern: '**/cucumber-reports/*.json',
                     sortingMethod: 'ALPHABETICAL'
        }
    }
}