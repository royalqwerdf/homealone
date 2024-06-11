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
                sh './gradlew clean build'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing...'
                sh './gradlew test'
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
              sshagent(credentials: 'HomealoneKey') {
                sh 'scp -r build/libs/* elice@34.64.55.198:/home/elice/Back'
              }
            }
          }
        }
    }
}