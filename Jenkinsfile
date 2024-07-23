pipeline {
    agent any

     environment {
            NEXUS_URL = 'http://192.168.33.10:8081/' // Remplacez par l'URL de votre Nexus
            NEXUS_USERNAME = credentials('admin') // Remplacez 'nexus-username' par le nom de votre credential dans Jenkins
            NEXUS_PASSWORD = credentials('admin') // Remplacez 'nexus-password' par le nom de votre credential dans Jenkins
        }

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
                            sh 'mvn deploy -DskipTests -DrepositoryId=nexus-releases -Durl=env.NEXUS_URL -Dnexus.username=env.NEXUS_USERNAME -Dnexus.password=env.NEXUS_PASSWORD'
                        }
                    }
                }
   }





}


