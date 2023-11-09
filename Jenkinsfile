pipeline {
    agent any
 stages {

     stage('Clean and compile with Maven') {
            steps {

                    sh 'mvn clean compile'
                }
            }

        stage('Test With JUNIT/MOCKITO') {
            steps {

                    sh 'mvn test'

            }
              post {
                always {
                    junit '**/target/surefire-reports/TEST-*.xml'
                }
            }

        }
     stage('JaCoCo Code Coverage') {
            steps {

                    sh 'mvn clean test org.jacoco:jacoco-maven-plugin:prepare-agent'

            }
                post {
                    success {
                        jacoco(
                            execPattern: '**/build/jacoco/*.exec',
                            classPattern: '**/build/classes/java/main',
                            sourcePattern: '**/src/main'
            )
        }
    }}
   stage('SonarQube Analysis') {
            steps {
          withSonarQubeEnv('SonarQubeServer') {
    sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=sonarqube'

            }

          }

        }


    stage(' Artifact construction') {
            steps {
                // Étape pour construire l'artefact (par exemple, un fichier JAR)
                sh 'mvn package'
            }
            post {
                success {
                    archiveArtifacts artifacts: '**/target/*.jar', allowEmptyArchive: true
                }
            }
        }
    stage('NEXUS') {
       steps {
        script {
            withCredentials([usernamePassword(credentialsId: 'NEXUS', usernameVariable: 'admin', passwordVariable: 'nexus')]) {
                def mavenCmd = 'mvn deploy -DskipTests'
                sh label: '', script: mavenCmd
            }
        }
    }
}
      stage('Docker Image') {
            steps {
                script {
                    def jarUrl = 'http://localhost:8081/repository/maven-releases/com/example/DevOps_Project/0.0.1/DevOps_Project-2.1.jar'
                    sh "curl -o DevOps_Project-2.1.jar ${jarUrl}"

                    def dockerImage = 'devopsprojectimage'
                    def dockerFile = 'Dockerfile'

                    sh "docker build -t ${dockerImage} -f ${dockerFile} ."
                }
            }
        }

    }
}
