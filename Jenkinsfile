#!groovy

// Automatically build any new commits on master, and deploy them to the development environment.
// Successful builds will be automatically deployed to staging and production.
pipeline {
    // Run on the same Jenkins build slave as other Scala projects.
    agent { label 'slaveOK' }

    // These have to be set up globally in the Jenkins UI with these exact names.
    tools {
        // Must be JDK 8.
        jdk 'JDK-1.8'
        // Latest release version of sbt as of now.
        'org.jvnet.hudson.plugins.SbtPluginBuilder$SbtInstallation' 'sbt-1.2.1'
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
            when {
                beforeAgent true
                branch 'master'
            }
            steps {
                // Publish to the repo defined in build.sbt.
                sh 'sbt -no-colors publish'
            }
        }
    }
}
