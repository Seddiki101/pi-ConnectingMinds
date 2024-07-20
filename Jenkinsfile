pipeline {
    agent any

    environment {
        SONAR_HOST_URL = 'http://192.168.33.10:9000/'
        NEXUS_REPO_URL = 'http://192.168.33.10:8081/repository/maven-releases/'
        MAVEN_HOME = '/opt/apache-maven-3.6.3' // Définissez le chemin vers votre installation de Maven
    }

    tools {
        // Spécifiez l'installation de Maven
        maven 'Maven 3.6.3'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'Forum', url: 'https://github.com/Seddiki101/pi-ConnectingMinds.git'
            }
        }

        stage('Build') {
            steps {
                // Utilisation de Maven pour nettoyer et empaqueter le projet
                sh "${tool 'Maven 3.6.3'}/bin/mvn clean package"
            }
        }

        stage('SonarQube Analysis') {
            steps {
                // Analyser avec SonarQube
                withSonarQubeEnv('SonarQube Server') {
                    sh "${tool 'Maven 3.6.3'}/bin/mvn sonar:sonar"
                }
            }
        }

        stage('Deploy to Nexus') {
            steps {
                // Déployer les artefacts vers Nexus
                sh "${tool 'Maven 3.6.3'}/bin/mvn deploy"
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
