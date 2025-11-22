pipeline {
  agent any

  parameters {
        string(name: 'TAG_EXPRESSION', defaultValue: 'not @ignore', description: 'Cucumber tag filtre ifadesi (örn: @smoke, @regression)')
    }

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
        // -v $WORKSPACE/target:/app/target ile raporları Jenkins tarafına kopyalıyoruz
        sh 'docker run --rm --shm-size=2g -v $WORKSPACE/target:/app/target -e CUCUMBER_FILTER_TAGS="${params.TAG_EXPRESSION}" patimo-automation'
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
