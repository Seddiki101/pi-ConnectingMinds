pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                script {
                    // Effectue le checkout depuis le repository Git
                    checkout([$class: 'GitSCM', branches: [[name: 'Forum']], userRemoteConfigs: [[url: 'https://github.com/Seddiki101/pi-ConnectingMinds.git']]])
                }
            }
        }

        stage('Build') {
            steps {
                script {
                    // Exécute la compilation avec Maven
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    // Exécute les tests avec Maven
                    sh 'mvn test'
                }
            }
        }

        stage('Static Analysis') {
            steps {
                script {
                    // Effectue l'analyse statique avec SonarQube
                    withSonarQubeEnv('SonarQube_Server') {
                        sh 'mvn sonar:sonar'
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    // Déploie l'application sur Tomcat
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
