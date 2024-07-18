pipeline {
    agent any

    environment {
        // Define environment variables as needed
        SONAR_HOST_URL = 'http://192.168.33.10:9000/'
        NEXUS_REPO_URL = 'http://192.168.33.10:8081//repository/maven-releases/'
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout code from Git repository
                git 'https://github.com/Seddiki101/pi-ConnectingMinds.git'
            }
        }

        stage('Build') {
            steps {
                // Build the Maven project
                sh 'mvn clean package'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                // Execute SonarQube analysis
                withSonarQubeEnv('SonarQube Server') {
                    sh 'mvn sonar:sonar'
                }
            }
        }

        stage('Deploy to Nexus') {
            steps {
                // Deploy artifacts to Nexus repository
                sh 'mvn deploy'
            }
        }

        stage('Deploy to Production') {
            when {
                branch 'master' // Example condition: deploy only on master branch
            }
            steps {
                // Example deployment step to production
                sh 'ssh user@production-server "deploy-script.sh"'
            }
        }
    }
}
