// Jenkinsfile

pipeline {
    agent any // Indica que o pipeline pode rodar em qualquer agente disponível

    tools {
        // Garante que o Maven esteja disponível para o pipeline
        // O nome 'M3' deve ser o mesmo configurado no seu Jenkins
        // em "Gerenciar Jenkins" > "Global Tool Configuration" > "Maven"
        maven 'M3' 
    }

    stages {
        // Estágio 1: Baixar o Código
        stage('Checkout') {
            steps {
                // Puxa o código do repositório Git configurado no Job
                git 'https/seu-repositorio.git/seu-projeto.git' // <- TROQUE PELO SEU REPO
            }
        }

        // Estágio 2: Compilar e Rodar os Testes
        stage('Build & Test') {
            steps {
                // O 'sh' é para Linux/MacOS. Se seu Jenkins rodar em Windows, use 'bat'
                sh 'mvn clean test'
                // ou: bat 'mvn clean test'
                
                // Este é o comando mágico!
                // O Jenkins vai executar o 'test' do Maven, que 
                // por sua vez vai rodar o Cucumber (via Surefire/Failsafe),
                // que vai gerar o seu report HTML (Item 2).
            }
        }
    }

    // Pós-Execução: O que fazer depois que tudo rodar (com sucesso ou falha)
    post {
        always {
            // Este passo é crucial para o Item 2 (Gerar Report)
            // Ele "publica" o report HTML que o Cucumber gerou.
            
            // Lembre-se que configuramos o report para 'target/cucumber-report/index.html'
            archiveArtifacts artifacts: 'target/cucumber-report/index.html', fingerprint: true
            
            // Opcional: Se você instalar o plugin "Cucumber Reports"
            // Você pode ter gráficos bonitos direto na página do Job
            // cucumber 'target/cucumber.json' 

            // Limpa o workspace para a próxima execução
            cleanWs()
        }
    }
}