pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                script {
                    // Checkout du repository Git avec l'URL spécifiée et la branche 'Forum'
                    checkout([$class: 'GitSCM', branches: [[name: 'Forum']], userRemoteConfigs: [[url: 'https://github.com/Seddiki101/pi-ConnectingMinds.git']]])
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



      stage('Static Analysis') {

          steps {

                      sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=root'

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
