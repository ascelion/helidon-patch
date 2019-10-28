pipeline {
	agent any
	options {
		disableConcurrentBuilds()
		timeout time: 15, unit: 'MINUTES'
		buildDiscarder logRotator( daysToKeepStr: "14" )
	}
	tools {
		jdk 'J8'
		maven 'M3'
	}
	environment {
		JENKINS_NODE_COOKIE = 'dontkillme'
	}

	stages {
		stage('Compile') {
			steps {
				sh "mvn clean package -DskipTests -DskipITs"
			}
		}
		stage('Checks') {
			steps {
				sh "mvn test integration-test"

				junit testResults: "**/TEST-*.xml"
			}
		}
		stage('Deploy') {
			steps {
				sh "mvn deploy -Psources -DaltSnapshotDeploymentRepository=ascelion-snapshots::default::https://nexus.ascelion.com/repositories/ascelion-snapshots -Dmaven.test.skip"

				archiveArtifacts fingerprint: true, artifacts: '**/*.jar'
			}
		}
	}
}

