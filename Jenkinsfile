pipeline {
   
   agent any
   
   environment {
       PROJECT_NAME="JSOAGGER JFX Core"
   }
   
   options {
        timestamps()
    }
   
   stages {
        stage ('Prepare') {
			steps {
				sh '''
					echo "PATH = ${PATH}"
					echo "M2_HOME = ${M2_HOME}"
					echo "JAVA_HOME = ${JAVA_HOME}"					
				'''
			}
		}
       
        stage('Build') {
            steps {
                sh "mvn -Dmaven.test.failure.ignore=true -DskipTests=true -Dmaven.javadoc.skip=true install"
            }
        }
        
        stage('Unit Tests') {
            steps {
            	sh '''
                	echo "Running unit tests"
                	mvn -Dmaven.test.failure.ignore=false -Dmaven.javadoc.skip=true verify
                '''
            }
        }
        
        stage('Integration Tests') {
            steps {
                sh '''
                	echo "Running integration tests"
                	#mvn -Dmaven.test.failure.ignore=false -Dmaven.javadoc.skip=true verify
                '''
            }
        }
        
      	stage('Release confirmation') {
        	steps {
        		timeout(time: 600, unit: 'SECONDS'){
        			script {
	                    def perfomRelease = input(
 							id: 'perfomRelease', message: 'Do you want to perform version release?', ok:'Release this build' 
						)
                	}	
        		}
        	}
      	}
      
      	stage('Perform release') {
         	steps {
	         	withCredentials([usernamePassword(credentialsId: 'jenkins_github_credentials', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')])
	         	{
	                sh 'mvn --settings .maven.xml -DENV_GIT_USERNAME=$GIT_USERNAME -DENV_GIT_PASSWORD=$GIT_PASSWORD -Dresume=false -DdryRun=true -Dmaven.test.failure.ignore=true -DskipTests=true -Darguments=\"-Dmaven.javadoc.skip=true\" release:prepare -B -V -Prelease'
			        sh 'mvn --settings .maven.xml -DENV_GIT_USERNAME=$GIT_USERNAME -DENV_GIT_PASSWORD=$GIT_PASSWORD -Dresume=false -Dmaven.test.failure.ignore=true -DskipTests=true -Darguments=\"-Dmaven.javadoc.skip=true\" -B -V release:prepare release:perform -Prelease'
	         	}
         	}
         	
         	post {  
				 success {  
					 emailext   to: "${env.DEV_MAILING_LIST}",
					 			subject: "$PROJECT_NAME, released",
					 			body: "$PROJECT_NAME have been succesfully released.<br/> A new version of project $PROJECT_NAME is now avalaible.<br/><br/>Jenkins", 
								from: "${env.JOB_EMAIL_SENDER}", 
								attachLog: false;
				 }  
				 failure {
					emailext    to: "${env.DEV_MAILING_LIST}",
								subject: "$PROJECT_NAME, RELEASE Failed",
								body: "$PROJECT_NAME, RELEASE failed. <br/> You can check jenkins console output at $BUILD_URL to view full the results.<br/><br/>Jenkins", 
								from: "${env.JOB_EMAIL_SENDER}", 
								attachLog: true;
				}  
			}
      	}
    }
    
    post {  
     	failure {
        	emailext	to: "${env.DEV_MAILING_LIST}",    
        				subject: "$PROJECT_NAME - Build Failed",
        				body: "$PROJECT_NAME, build failed. <br/> You can check jenkins console output at $BUILD_URL to view full the results.<br/><br/>Jenkins", 
                    	from: "${env.JOB_EMAIL_SENDER}", 
                    	attachLog: true;
     	}  
	}
}
