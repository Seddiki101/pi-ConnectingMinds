pipeline {
    agent any

    environment {
        SONAR_HOST_URL = 'http://192.168.33.10:9000/'
        NEXUS_REPO_URL = 'http://192.168.33.10:8081/repository/maven-releases/'
    }

    stages {
        stage('Checkout') {
            steps {
                // Vérification et récupération du code depuis le dépôt Git
                git branch: 'Forum', url: 'https://github.com/Seddiki101/pi-ConnectingMinds.git'
            }
        }

        stage('Build') {
            steps {
                // Construction du projet Maven
                sh 'mvn clean package'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                // Analyse avec SonarQube
                withSonarQubeEnv('SonarQube Server') {
                    sh 'mvn sonar:sonar'
                }
            }
        }

        stage('Deploy to Nexus') {
            steps {
                // Déploiement des artefacts vers le repository Nexus
                sh 'mvn deploy'
            }
        }

        stage('Deploy to Production') {
            when {
                branch 'Forum' // Déployer uniquement depuis la branche Forum
            }
            steps {
                // Exemple de déploiement vers l'environnement de production
                sh 'ssh user@production-server "deploy-script.sh"'
            }
        }
    }

    post {
        success {
            echo 'Pipeline exécuté avec succès!'
        }
        failure {
            echo 'Le pipeline a échoué - vérifiez les logs pour plus de détails.'
        }
    }
}
