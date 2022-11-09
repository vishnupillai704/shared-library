call(){
pipeline {
    agent any

   tools {
     terraform 'terraform'
    }

    stages {
        stage('terraform '){
        steps{
             
            //dir('/var/lib/jenkins/workspace/Abhi Terraform/Terraform'){ 
            sh  "git clone https://github.com/Abhishek1099/Terraform/ "
            }
        //}
            
        }

            stage('terraform init') {
        steps {
            dir('/var/lib/jenkins/workspace/shared_filestorage/Terraform') {
            
            
            sh 'terraform init'
            }
         }
    }
        stage('terraform apply') {
        steps {
            dir('/var/lib/jenkins/workspace/shared_filestorage/Terraform') {
            withCredentials([aws(accessKeyVariable: 'AWS_ACCESS_KEY_ID', credentialsId: 'Abhi', secretKeyVariable: 'AWS_SECRET_ACCESS_KEY')]){
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
}
