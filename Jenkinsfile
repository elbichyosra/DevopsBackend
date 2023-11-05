pipeline {
    agent any
 stages {

     stage('Clean and compile with Maven') {
            steps {

                    sh 'mvn clean compile'
                }
            }

     stage('Code Quality') {
            steps {
                script {
                    withSonarQubeEnv('SonarQubeServer') {
                        sh 'mvn sonar:sonar'
                    }
                }
            }
        }
          stage('Test With Junit') {
                    steps {

                            sh "mvn test "

                    }
                }
        stage('MOCKITO') {
            steps {
                script {
                    sh 'mvn test'
                }
            }
            post {
                always {
                    junit '**/target/surefire-reports/TEST-*.xml'
                }
            }
        }
    }
}