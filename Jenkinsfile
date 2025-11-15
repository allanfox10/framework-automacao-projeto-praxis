pipeline {
    agent {
        docker {
            image 'allan-jenkins-agent:latest'
            // Permite que o container acesse o Appium rodando no Windows (host)
            args '-u root --add-host=host.docker.internal:host-gateway'
        }
    }

    tools {
        maven 'Maven_Default'
    }

    stages {
        stage('Setup Environment') {
            steps {
                echo '🔧 Configurando ambiente...'
                // Instalação do Chrome necessária para os testes de UI
                sh '''
                    apt-get update
                    apt-get install -y wget
                    wget -q https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
                    apt-get install -y --fix-broken ./google-chrome-stable_current_amd64.deb
                    rm google-chrome-stable_current_amd64.deb
                    google-chrome --version
                '''
            }
        }

        stage('Build') {
            steps {
                echo '🔨 Iniciando Build (Clean & Install)...'
                // Roda o clean install ignorando os testes inicialmente para garantir que compila
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
                        // O Chrome já foi instalado no estágio de Setup
                        sh 'mvn test -pl frontend-tests -DEXECUTION_MODE=headless'
                    }
                }

                stage('Mobile Tests') {
                    steps {
                        echo '📱 Executando testes Mobile...'
                        // Aqui está o segredo: Aponta para o host do Docker onde o Appium está rodando
                        sh 'mvn test -pl mobile-tests -Dtest=RunCucumberMobTests -DAPPIUM_SERVER_URL="http://host.docker.internal:4723/"'
                    }
                }
            }
        }
    }

    post {
        always {
            echo '📊 Gerando Relatórios...'

            // Publica relatório do Backend
            publishHTML(target: [
                reportDir: 'backend-tests/target/cucumber-reports',
                reportFiles: 'api-report.html',
                reportName: 'Relatório API',
                keepAll: true, allowMissing: true
            ])

            // Publica relatório do Frontend
            publishHTML(target: [
                reportDir: 'frontend-tests/target/cucumber-reports',
                reportFiles: 'ui-report.html',
                reportName: 'Relatório UI',
                keepAll: true, allowMissing: true
            ])

            // Publica relatório Mobile
            publishHTML(target: [
                reportDir: 'mobile-tests/target/cucumber-reports',
                reportFiles: 'mobile-report.html',
                reportName: 'Relatório Mobile',
                keepAll: true, allowMissing: true
            ])

            // Plugin do Cucumber para visão unificada
            cucumber buildStatus: 'null',
                     fileIncludePattern: '**/cucumber-reports/*.json',
                     sortingMethod: 'ALPHABETICAL'
        }
    }
}