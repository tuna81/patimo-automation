pipeline {
  agent any

  parameters {
    // Varsayılan olarak hepsini koşsun ama istersek değiştirelim
    string(name: 'TAG_EXPRESSION', defaultValue: 'not @ignore', description: 'Hangi tagleri koşmak istersin? Örn: @smoke, @cart')
  }

  triggers {
    // Her 2 dakikada bir GitHub'ı kontrol et
    pollSCM('H/2 * * * *') 
    cron('H 2 * * *')
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
        script {
            // Parametreyi değişkene alıyoruz
            def tags = params.TAG_EXPRESSION
            
            // Çift tırnak (") kullanarak değişkeni içeri gömüyoruz.
            // Bu syntax artık "Bad substitution" hatası vermez.
            sh "docker run --rm --shm-size=2g -v ${WORKSPACE}/target:/app/target -e CUCUMBER_FILTER_TAGS='${tags}' patimo-automation"
        }
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