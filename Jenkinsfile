pipeline {
    agent any

    environment {
        MAVEN_HOME = "${tool 'Maven 3.6.3'}"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'Forum', url: 'https://github.com/Seddiki101/pi-ConnectingMinds.git'
            }
        }

        stage('Build') {
            steps {
                sh '${MAVEN_HOME}/bin/mvn clean package'
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
