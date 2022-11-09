
pipeline {
    agent any

    tools {
        maven "maven1" // You need to add a maven with name "3.6.0" in the Global Tools Configuration page
    }

    stages {
        stage("Build") {
            steps {
                
                sh "ls -a"
                sh "rm -rf *"
                sh "git clone https://github.com/MohnishBhonde/SNSImplemen"
                
                sh "ls -a"
              
                
                sh "mvn -version"
                sh "cd SNSImplemen && mvn clean install"
            }
            }
       stage("mvn clean install"){
                steps{
                    sh " cd SNSImplemen && mvn package"
                }
            }
            stage("server"){
                steps{
                    rtServer (
                        id: "artifactory-server",
                        url:'http://ec2-54-191-70-152.us-west-2.compute.amazonaws.com:8081/artifactory',
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
