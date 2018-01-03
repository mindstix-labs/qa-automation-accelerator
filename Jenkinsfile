pipeline {
	agent any
	parameters {
		choice(name: 'BROWSER', choices: 'chrome\nfirefox\nPhantomJS', description: 'Select Browser')
		choice(name: 'MODE', choices: 'grid', description: 'Select browser mode')
		string(name: 'DRIVER', defaultValue: 'drivers/linux/chromedriver_2_33', description: 'Enter Driver Path')
        choice(name: 'TARGET', choices: 'www\nstaging\nstaging2', description: 'Select Environment')
        choice(name: 'SELENIUM_GRID_NODE', choices: '5\n1\n2\n3\n4\n6\n7\n8\n9\n10', description: 'Select number of Nodes want to register')
        string(name: 'TAGS', defaultValue: '--tags @cbt', description: 'Enter tags here')
    }
    options {
		buildDiscarder(logRotator(numToKeepStr: '10'))
    }
	stages {
			stage('Grid up') {
            		steps {
                		sh "docker-compose -p cucumber_baseline down"
						sh "docker-compose -p cucumber_baseline up -d"
						sh "docker-compose -p cucumber_baseline scale chrome=${params.SELENIUM_GRID_NODE}"
        	    	}
        	}
        	stage('Clean Workspace') {
        			steps {
        				sh "./gradlew clean"
        				sh "mvn clean"
        			}        
        	}
			stage('Test environment availability') {
            		steps {
                		sh "./gradlew clean test -Denv.mode=${params.MODE} -Denv.urlPrefix=${params.TARGET} -Denv.browser=${params.BROWSER} -Denv.driverPath=${params.DRIVER} -Denv.threadCount=1 -Dcucumber.options=\"--tags @sitecheck\""
        	    	}
        	}
        	stage('Test bed setup') {
            		steps {
                		sh "./gradlew clean test -Denv.mode=${params.MODE} -Denv.urlPrefix=${params.TARGET} -Denv.browser=${params.BROWSER} -Denv.driverPath=${params.DRIVER} -Denv.threadCount=1 -Dcucumber.options=\"--tags @usercheck\""
        	    	}
        	}
    		stage('Test') {
            		steps {
            		    sh "mvn clean"
            			sh "./gradlew clean test -Denv.mode=${params.MODE} -Denv.urlPrefix=${params.TARGET} -Denv.browser=${params.BROWSER} -Denv.driverPath=${params.DRIVER} -Denv.threadCount=${params.SELENIUM_GRID_NODE} -Dcucumber.options=\"${params.TAGS}\""
        	    	}
        	}
	}
	post { 
        	always {
        			cucumber fileIncludePattern: '**/*.json', sortingMethod: 'ALPHABETICAL' 
            		sh "docker-compose down"
            		notifyBuild(currentBuild.result)
        	}
        	
	}
}

def notifyBuild(String buildStatus = 'STARTED') {
     buildStatus =  buildStatus ?: 'SUCCESSFUL'

   // Default values
   def colorName = 'RED'
   def colorCode = '#FF0000'
   def subject = "${buildStatus}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'"
   def summary = "${subject} (${env.BUILD_URL}cucumber-html-reports/overview-features.html)"
   def details = """
   		Jenkins Job: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]': (${env.BUILD_URL})
     	Report Link for Job '${env.JOB_NAME}' : ${env.BUILD_URL}cucumber-html-reports/overview-features.html
     	Latest Report Link: ${env.JENKINS_URL}job/${env.JOB_NAME}/lastCompletedBuild/cucumber-html-reports/overview-features.html
     """
   // Override default values based on build status
   if (buildStatus == 'STARTED') {
     color = 'YELLOW'
     colorCode = '#FFFF00'
   } else if (buildStatus == 'SUCCESSFUL') {
     color = 'GREEN'
     colorCode = '#00FF00'
   } else {
     color = 'RED'
     colorCode = '#FF0000'
   }

   // Step for sending a Jenkins build notification to specified Slack channel.
   slackSend (color: colorCode, message: details, channel: '#jenkins-notifications')

   // Step for sending a cucumber report to a slack channel.
   // We need to first configure Jenkins with "Incoming Webhook" link of slack channel to send cucumber report.
   cucumberSlackSend channel: '#jenkins-notifications', json: 'build/reports/cucumberreport/cucumber.json'

    // Step for sending a Jenkins build notification through mail
    mail (to: 'amit.mujawar@mindstix.com',
    subject: subject,
    body: details);
}
