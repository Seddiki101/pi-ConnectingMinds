pipeline {
    agent any

    environment {
        M2_HOME = '/opt/apache-maven-3.6.3'
        PATH = "$M2_HOME/bin:$PATH"
    }

    stages {
        stage('Checkout') {
            steps {
                // Étape de checkout du code depuis Git
                checkout scm
            }
        }
        stage('Build') {
            steps {
                // Utilisation de Maven pour compiler et construire le projet
                script {
                    def mvnHome = tool name: 'Maven 3.6.3', type: 'maven'
                    if (mvnHome != null) {
                        sh "${mvnHome}/bin/mvn clean install"
                    } else {
                        error "Installation de Maven 3.6.3 non trouvée"
                    }
                }
            }
        }
        stage('Test') {
            steps {
                // Exécuter les tests unitaires
                script {
                    def mvnHome = tool name: 'Maven 3.6.3', type: 'maven'
                    if (mvnHome != null) {
                        sh "${mvnHome}/bin/mvn test"
                    } else {
                        error "Installation de Maven 3.6.3 non trouvée"
                    }
                }
            }
        }
        stage('Package') {
            steps {
                // Packaging de l'application (si nécessaire)
                script {
                    def mvnHome = tool name: 'Maven 3.6.3', type: 'maven'
                    if (mvnHome != null) {
                        sh "${mvnHome}/bin/mvn package"
                    } else {
                        error "Installation de Maven 3.6.3 non trouvée"
                    }
                }
            }
        }
    }

    post {
        always {
            // Actions post-build, comme la collecte des résultats de test
            junit '**/target/surefire-reports/*.xml'
        }
    }
}
