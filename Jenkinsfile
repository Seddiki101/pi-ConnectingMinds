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
                script {
                    sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=root'
                }
            }
        }


        stage('Deploy to Nexus') {
           steps {
               script {
                 sh 'mvn deploy'
               }
           }
        }



   }





}


