pipeline {
    agent any
 stages {

     stage('Clean and compile with Maven') {
            steps {

                    sh 'mvn clean compile'
                }
            }
     stage('Test With Junit/Mockito') {
            steps {

                    sh "mvn test "

            }
        }
    }
}