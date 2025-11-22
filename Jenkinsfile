pipeline {
  agent any

  triggers {
        // Her 2 dakikada bir GitHub'ı kontrol et
        pollSCM('H/2 * * * *') 
    }

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
        // -v $WORKSPACE/target:/app/target
        // ANLAMI: Benim bilgisayarımdaki (Jenkins) "target" klasörünü,
        // Konteynerin içindeki "/app/target" klasörüne eşitle.
        sh 'docker run --rm --shm-size=2g -v $WORKSPACE/target:/app/target patimo-automation'
      }
    }
  }

  post {
    always {
      sh 'docker rmi patimo-automation || true'
      archiveArtifacts artifacts: 'target/**/*.html', allowEmptyArchive: true
    }
  }
}
