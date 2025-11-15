pipeline {
    agent any

    tools {
        maven 'Maven_Default'
    }

    options {
        // Define um tempo limite global de 30 minutos para o Pipeline não travar
        timeout(time: 30, unit: 'MINUTES')
    }

    stages {
        stage('Setup Environment') {
            steps {
                echo '🔧 Configurando ambiente...'
                // Tenta instalar o Chrome.
                // OBS: Se o usuario do Jenkins não for root, isso pode falhar.
                sh '''
                    if [ $(id -u) -eq 0 ]; then
                        apt-get update || true
                        apt-get install -y wget
                        wget -q https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb || true
                        apt-get install -y --fix-broken ./google-chrome-stable_current_amd64.deb || true
                        rm -f google-chrome-stable_current_amd64.deb
                    else
                        echo "⚠️ Aviso: Não é root. Pulando instalação do Chrome via apt-get."
                    fi
                    google-chrome --version || echo "⚠️ Chrome não detectado no PATH"
                '''
            }
        }

        // --- NOVO ESTÁGIO DE DEBUG ---
        // Isso vai listar os arquivos para provar que o APK está lá antes de rodar o teste
        stage('Debug Files') {
            steps {
                script {
                    echo '🔍 Verificando estrutura de arquivos e APK...'
                    sh 'pwd'
                    // Lista recursivamente a pasta mobile-tests para ver se a pasta apps e o apk estao la
                    sh 'ls -R mobile-tests/src/test/resources || echo "⚠️ Pasta resources não encontrada!"'
                }
            }
        }

        stage('Build') {
            steps {
                echo '🔨 Iniciando Build (Clean & Install)...'
                // Pula os testes unitários aqui para ganhar tempo, já que rodaremos os testes funcionais abaixo
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
                        // Aponta para host.docker.internal para acessar o Appium no Windows
                        sh "mvn test -pl mobile-tests -Dtest=RunCucumberMobTests -DAPPIUM_SERVER_URL=http://host.docker.internal:4723/"
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

            cucumber buildStatus: 'null',
                     fileIncludePattern: '**/cucumber-reports/*-report.json',
                     sortingMethod: 'ALPHABETICAL'
        }
    }
}