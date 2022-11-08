def call() {
  
  pipeline {
    agent any



  tools {
     terraform 'terraform'
    }



   stages {
        stage('terraform '){
        steps{
           
            sh "git clone https://github.com/singusandeep/terra/"
            
        }
            
        }



           stage('terraform init') {
        steps {   
            dir('/var/lib/jenkins/workspace/sharedlibrari/terra') {                                            
            sh 'terraform init'
            }
         }
    }
        stage('terraform apply') {
        steps {
            dir('/var/lib/jenkins/workspace/sharedlibrari/terra') { 
            withCredentials([aws(accessKeyVariable: 'AWS_ACCESS_KEY_ID', credentialsId: 'AWS-sandeep', secretKeyVariable: 'AWS_SECRET_ACCESS_KEY')]){
            sh 'aws --version'
            
            sh 'terraform apply -auto-approve -var AWS_ACCESS_KEY_ID=$AWS_ACCESS_KEY_ID -var AWS_SECRET_ACCESS_KEY=$AWS_SECRET_ACCESS_KEY'
            
            }
            }
        }
    }  
        
    }
}
  
}
