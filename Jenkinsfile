pipeline {
    agent any

    environment {
        MAVEN_HOME = tool name: 'Maven', type: 'maven'
        MAVEN_OPTS = '-Dmaven.repo.local=.m2/repository -Dhttp.proxyHost=proxy-host -Dhttp.proxyPort=proxy-port'
        SONAR_HOST_URL = 'http://192.168.33.10:9000/'
        NEXUS_REPO_URL = 'http://192.168.33.10:8081/repository/maven-releases/'
    }

    options {
        skipDefaultCheckout() // Désactive la récupération automatique du code source pour le configurer manuellement dans chaque étape
        timestamps() // Ajoute des horodatages aux logs pour le suivi
    }

    stages {
        stage('Checkout') {
            steps {
                // Vérification et récupération du code depuis le dépôt Git
                git branch: 'Forum', url: 'https://github.com/Seddiki101/pi-ConnectingMinds.git'
            }
        }

        stage('Clean Maven Local Repository') {
            steps {
                // Nettoyer le cache local de Maven pour éviter les problèmes de cache
                sh "${tool 'Maven'}/bin/mvn dependency:purge-local-repository"
            }
        }

        stage('Build') {
            steps {
                // Construction du projet Maven
                sh "${tool 'Maven'}/bin/mvn clean package"
            }
        }

        stage('SonarQube Analysis') {
            steps {
                // Analyse avec SonarQube
                withSonarQubeEnv('SonarQube Server') {
                    sh "${tool 'Maven'}/bin/mvn sonar:sonar"
                }
            }
        }

        stage('Deploy to Nexus') {
            steps {
                // Déploiement des artefacts vers le repository Nexus
                sh "${tool 'Maven'}/bin/mvn deploy"
            }
        }

        stage('Deploy to Production') {
            when {
                branch 'Forum' // Déployer uniquement depuis la branche Forum
            }
            steps {
                // Exemple de déploiement vers l'environnement de production
                sh 'ssh user@production-server "deploy-script.sh"'
            }
        }
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
