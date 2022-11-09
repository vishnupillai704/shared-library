def call() {
  
  pipeline {
    agent any

   tools {
     terraform 'terraform'
    }

    stages {
        stage('terraform '){
        steps{
           dir('/var/lib/jenkins/workspace/sharred/terraform') {
            sh "git pull --ff-only https://github.com/vishnupillai704/terraform"
           }
        }
            
        }

            stage('terraform init') {
        steps {   
             dir('/var/lib/jenkins/workspace/sharred/terraform') {                                           
            sh 'terraform init'
             }
         }
    }
        stage('terraform apply') {
        steps {
            dir('/var/lib/jenkins/workspace/sharred/terraform') { 
            withCredentials([aws(accessKeyVariable: 'AWS_ACCESS_KEY_ID', credentialsId: 'aws-jenkins', secretKeyVariable: 'AWS_SECRET_ACCESS_KEY')]){
            sh 'aws --version'
            
            sh 'terraform apply -auto-approve -var AWS_ACCESS_KEY_ID=$AWS_ACCESS_KEY_ID -var AWS_SECRET_ACCESS_KEY=$AWS_SECRET_ACCESS_KEY'
            }
            
            }
        }
    } 
       stage('terraform destroy') {
        steps {   
             dir('/var/lib/jenkins/workspace/sharred/terraform') {
                withCredentials([aws(accessKeyVariable: 'AWS_ACCESS_KEY_ID', credentialsId: 'aws-jenkins', secretKeyVariable: 'AWS_SECRET_ACCESS_KEY')]){
              sh 'terraform destroy -force -var AWS_ACCESS_KEY_ID=$AWS_ACCESS_KEY_ID -var AWS_SECRET_ACCESS_KEY=$AWS_SECRET_ACCESS_KEY'
                }
             }
         }
    }
      
        
    } 
}
  
}
