pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                script {
                    git 'https://github.com/Seddiki101/pi-ConnectingMinds.git'
                }
            }
        }

        stage('Build') {
            steps {
                script {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    sh 'mvn test'
                }
            }
        }

        stage('Static Analysis') {
            steps {
                script {
                    withSonarQubeEnv('SonarQube_Server') {
                        sh 'mvn sonar:sonar'
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    sh 'mvn tomcat7:redeploy'
                }
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
