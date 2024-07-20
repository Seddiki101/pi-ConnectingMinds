pipeline {
    agent any

    environment {
        M2_HOME = '/opt/apache-maven-3.6.3'
        PATH = "$M2_HOME/bin:$PATH"
        JAVA_HOME = '/usr/lib/jvm/java-11-openjdk-amd64' // Assurez-vous de remplacer par le chemin correct de Java 11
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout du code depuis Git
                checkout scm
            }
        }

        stage('Build') {
            steps {
                script {
                    // Récupération de l'installation Maven configurée dans Jenkins
                    def mvnHome = tool name: 'Maven 3.6.3', type: 'maven'
                    if (mvnHome != null) {
                        // Compilation du projet avec Maven en spécifiant Java 11
                        sh "${mvnHome}/bin/mvn clean install -Dmaven.compiler.release=11"
                    } else {
                        error "Installation de Maven 3.6.3 non trouvée"
                    }
                }
            }
        }

        // Ajoutez d'autres étapes de votre pipeline si nécessaire

        stage('Tests') {
            steps {
                // Exécution des tests unitaires ou d'intégration
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

        stage('Deploy') {
            steps {
                // Déploiement de l'application (exemple : vers un serveur)
                script {
                    def mvnHome = tool name: 'Maven 3.6.3', type: 'maven'
                    if (mvnHome != null) {
                        sh "${mvnHome}/bin/mvn deploy"
                    } else {
                        error "Installation de Maven 3.6.3 non trouvée"
                    }
                }
            }
        }
    }

    post {
        always {
            // Actions à effectuer après chaque exécution du pipeline
            junit '**/target/surefire-reports/*.xml'
        }
    }
}
