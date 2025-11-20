pipeline {
  agent any

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Build Docker Image') {
      steps {
        sh 'docker build -t patimo-automation .'
      }
    }

    stage('Run Tests') {
      steps {
        sh 'docker run --rm --shm-size=2g patimo-automation'
      }
    }
  }

  post {
    always {
      sh 'docker rmi patimo-automation || true'
    }
  }
}
