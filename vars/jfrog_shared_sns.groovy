def call() {
    
  pipeline {
     agent any

    tools {
        maven "maven1" // You need to add a maven with name "3.6.0" in the Global Tools Configuration page
    }

    stages {
        stage("Build") {
            steps {
                
              dir('/var/lib/jenkins/workspace/shared_jfrog_m/SNSImplemen'){
                sh "git pull https://github.com/MohnishBhonde/SNSImplemen"
                
               
                
              }
                
            }
         }
        stage("mvn install"){
            steps{
                 dir('/var/lib/jenkins/workspace/shared_jfrog_m/SNSImplemen'){
                  sh "mvn -version"
                  sh "mvn clean install"
                 }
            }
        }
        
            stage("mvn package"){
                steps{
                    dir('/var/lib/jenkins/workspace/shared_jfrog_m/SNSImplemen'){
                    sh "mvn package"
                    }
                }
            }
            stage("server"){
                steps{
                    rtServer (
                        id: "artifactory-server",
                        url:'http://ec2-52-25-243-112.us-west-2.compute.amazonaws.com:8081/artifactory',
                        username:'jenkins',
                        password: "jenkins@123", 
                        bypassProxy: true,
                        timeout:300,
                        )
                    
                }
                
            }
            stage('Upload'){
                steps{
                    rtUpload(
                        serverId:"artifactory-server",
                        spec:'''{
                            "files":[
                            {
                            "pattern":"*.jar",
                            "target":"generic-local"
                            }]
                        }''',
                        
                        )
                }
            }
            stage('Publish build info'){
                steps{
                    rtPublishBuildInfo(
                        serverId:"artifactory-server"
                        )
                }
            }
           
    
        }
    }
}
