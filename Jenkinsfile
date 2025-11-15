pipeline {
    agent any

    tools {
        maven 'Maven_Default'
    }

    stages {
        stage('Setup Environment') {
            steps {
                echo '🔧 Configurando ambiente...'
                // Instalação do Chrome necessária para os testes de UI
                // O '|| true' evita falha se o update der erro temporário
                sh '''
                    apt-get update || true
                    apt-get install -y wget
                    wget -q https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb || true
                    apt-get install -y --fix-broken ./google-chrome-stable_current_amd64.deb || true
                    rm -f google-chrome-stable_current_amd64.deb
                    google-chrome --version || echo "Aviso: Chrome pode nao ter instalado corretamente"
                '''
            }
        }

        stage('Build') {
            steps {
                echo '🔨 Iniciando Build (Clean & Install)...'
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Test Execution') {
            parallel {
                stage('API Tests') {
                    steps {
                        echo '🚀 Executando testes de API...'
                        sh 'mvn test -pl backend-tests -Dcucumber.filter.tags="@api"'
                    }
                }

                stage('UI Tests') {
                    steps {
                        echo '🌐 Executando testes de UI (Headless)...'
                        sh 'mvn test -pl frontend-tests -DEXECUTION_MODE=headless'
                    }
                }

                stage('Mobile Tests') {
                    steps {
                        echo '📱 Executando testes Mobile...'
                        // Aponta para o host do Docker onde o Appium está rodando
                        sh 'mvn test -pl mobile-tests -Dtest=RunCucumberMobTests -DAPPIUM_SERVER_URL="http://host.docker.internal:4723/"'
                    }
                }
            }
        }
    }

    post {
        always {
            echo '📊 Gerando Relatórios...'

            publishHTML(target: [
                reportDir: 'backend-tests/target/cucumber-reports',
                reportFiles: 'api-report.html',
                reportName: 'Relatório API',
                keepAll: true, allowMissing: true
            ])

            publishHTML(target: [
                reportDir: 'frontend-tests/target/cucumber-reports',
                reportFiles: 'ui-report.html',
                reportName: 'Relatório UI',
                keepAll: true, allowMissing: true
            ])

            publishHTML(target: [
                reportDir: 'mobile-tests/target/cucumber-reports',
                reportFiles: 'mobile-report.html',
                reportName: 'Relatório Mobile',
                keepAll: true, allowMissing: true
            ])

            // ALTERAÇÃO AQUI: Filtro alterado de '*.json' para '*-report.json'
            // Isso garante que ele pegue apenas 'api-report.json', 'ui-report.json', etc.
            cucumber buildStatus: 'null',
                     fileIncludePattern: '**/cucumber-reports/*-report.json',
                     sortingMethod: 'ALPHABETICAL'
        }
    }
}