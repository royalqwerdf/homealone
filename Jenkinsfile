pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git credentialsId: 'homealone', branch: 'test', url: 'https://kdt-gitlab.elice.io/cloud_track/class_02/web_project3/team07/team7-back.git'
            }
        }
        stage('Build') {
            steps {
                echo 'Building...'
                sh 'chmod +x ./gradlew'
                sh './gradlew clean build'
            }
        }
        stage('Archive') {
            steps {
                echo 'Archiving...'
                archiveArtifacts artifacts: '**/build/libs/**/*.jar', fingerprint: true
            }
        }
        stage("Deploy to VM") {
          steps {
            // vm에 배포하는 단계
            script {
              sshagent(credentials: ['homealonekey']) {
                sh 'scp -o StrictHostKeyChecking=no -r build/libs/* elice@team07-final:/home/elice/Back'
              }
            }
          }
        }
        stage("Build Docker Image") {
            steps {
                script {
                    // Docker 이미지 빌드
                    sh 'docker build -t homealone .'
                }
            }
        }
    }
}