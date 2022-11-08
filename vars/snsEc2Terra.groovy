def call()
{
pipeline {
    agent any



  tools {
     terraform 'terraform'
    }



   stages {
        stage('build'){
            steps{
                
                sh "git clone https://github.com/MohnishBhonde/terraform"
                
            
        }
    }



           stage('terraform init') {
        steps {
          //dir('/var/lib/jenkins/workspace/mohnish_terraform/terraform') {
            
            
            sh 'terraform init'
          // }
         }
    }
        stage('terraform apply') {
        steps {
           //dir('/var/lib/jenkins/workspace/mohnish_terraform/terraform') {
            withCredentials([aws(accessKeyVariable: 'AWS_ACCESS_KEY_ID', credentialsId: 'aws_mohnish', secretKeyVariable: 'AWS_SECRET_ACCESS_KEY')]){
            sh 'aws --version'
            
            sh 'terraform apply -auto-approve -var AWS_ACCESS_KEY_ID=$AWS_ACCESS_KEY_ID -var AWS_SECRET_ACCESS_KEY=$AWS_SECRET_ACCESS_KEY'
           
            //}
            
            }
        }
    }  
        
    }
}
}
