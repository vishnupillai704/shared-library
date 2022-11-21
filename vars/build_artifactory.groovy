 def call() {
    
  pipeline {
     agent any

    tools {
        maven "maven1" // You need to add a maven with name "3.6.0" in the Global Tools Configuration page
    }

    stages {
        stage("Build") {
            steps {
                
             dir('/var/lib/jenkins/workspace/shared-library_vishnu_CI/Devops22'){
                sh "git clone https://github.com/vishnupillai704/Devops22"
             }
                
               
                
                
                
            }
         }
        stage("mvn install"){
            steps{
                 dir('/var/lib/jenkins/workspace/shared-library_vishnu_CI/Devops22'){
                  sh "mvn -version"
                  sh "mvn clean install"
                 }
            }
        }
        
            stage("mvn package"){
                steps{
                    dir('/var/lib/jenkins/workspace/shared-library_vishnu_CI/Devops22'){
                    sh "mvn package"
                    }
                }
            }
            stage("server"){
                steps{
                    rtServer (
                        id: "artifactory-server",
                        url:'http://ec2-15-206-94-62.ap-south-1.compute.amazonaws.com:8081/artifactory',
                        username:'admin',
                        password: "password", 
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
