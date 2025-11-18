#!groovy

// Automatically build any new commits on master, and deploy them to the development environment.
// Successful builds will be automatically deployed to staging and production.
pipeline {
    agent { label 'shared' }

    environment {
    	JAVA_HOME = '/usr/lib/jvm/temurin-8-jdk-amd64'
	PATH = "${JAVA_HOME}/bin:${env.PATH}"
    }
    stages {
        stage('Build') {
            steps {
                // Actually compile the project.
                sh 'sbt -no-colors compile'
            }
        }
        stage('Test') {
            steps {
                // Run unit and functional tests.
                // The 'testOnly -- -n' part runs all tests but suppresses output colors in JUnit.
                sh 'sbt -no-colors test \'testOnly -- -n\''
            }
            post {
                always {
                    // Read in any test results so they'll show up in the Jenkins UI.
                    junit 'target/test-reports/*.xml'
                }
            }
        }
        stage('Publish to Artifactory') {
            steps {
                // Publish to the repo defined in build.sbt.
                sh 'sbt -no-colors publish'
            }
        }
    }
}
