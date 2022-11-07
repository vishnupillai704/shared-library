def call() {
  
  pipeline {
    agent any

   tools {
     terraform 'terraform'
    }

    stages {
        stage('terraform '){
        steps{
            
            sh "git clone https://github.com/vishnupillai704/terraform/ "
        
        }
            
        }

            stage('terraform init') {
        steps {   
                                                                  
            sh 'terraform init'
            
         }
    }
        stage('terraform apply') {
        steps {
             
            withCredentials([aws(accessKeyVariable: 'AWS_ACCESS_KEY_ID', credentialsId: 'aws-jenkins', secretKeyVariable: 'AWS_SECRET_ACCESS_KEY')]){
            sh 'aws --version'
            
            sh 'terraform apply -auto-approve -var AWS_ACCESS_KEY_ID=$AWS_ACCESS_KEY_ID -var AWS_SECRET_ACCESS_KEY=$AWS_SECRET_ACCESS_KEY'
            sh 'aws --version'
            sh 'terraform apply -auto-approve'
            
            
            }
        }
    }  
        
    } 
}
  
}
