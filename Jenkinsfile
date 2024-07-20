pipeline {
    agent any

    tools {
        // Utilisation de l'installation Maven configurée dans Jenkins
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
                sh 'mvn clean package'
            }
        }

        // Autres étapes de votre pipeline...

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
