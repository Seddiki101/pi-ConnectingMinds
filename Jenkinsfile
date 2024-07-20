pipeline {
    agent any

    environment {
        // JAVA_HOME et MAVEN_HOME ne sont plus nécessaires ici car Jenkins a détecté les chemins automatiquement
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/Seddiki101/pi-ConnectingMinds.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Static Analysis') {
            steps {
                withSonarQubeEnv('SonarQube_Server') {
                    sh 'mvn sonar:sonar'
                }
            }
        }

        stage('Deploy') {
            steps {
                sh 'mvn tomcat7:redeploy'
            }
        }
    }

    post {
        success {
            echo 'Le pipeline a réussi !'
            // Ajoutez ici des notifications par email, Slack, etc.
        }
        failure {
            echo 'Le pipeline a échoué. Veuillez vérifier les logs.'
            // Ajoutez ici des notifications par email, Slack, etc.
        }
    }
}
