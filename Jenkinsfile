pipeline {
    agent any

    tools {
        gradle 'gradle 8.14'
        jdk 'jdk-21'
    }
    environment {
        REPOSITORY_NAME = 'runners-hi-backend'
        SERVICE_NAME = 'backend'
    }

    stages {
        stage('Check pwd location') {
            steps {
                sh '''
                which pwd
                '''
            }
        }
        stage('Git Clone or Pull') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'RocketDDan-Organization-Access-Token', usernameVariable: 'ORGANIZATION_NAME', passwordVariable: 'GITHUB_TOKEN')]) {
                    sh '''
                    if [ -d "REPOSITORY_NAME/.git" ]; then
                        cd $REPOSITORY_NAME
                        git reset --hard
                        git pull origin main
                        cd ..
                    else
                        rm -rf $REPOSITORY_NAME
                        git clone https://$GITHUB_TOKEN@github.com/$ORGANIZATION_NAME/$REPOSITORY_NAME.git
                    fi
                    '''
                }
            }
        }

        stage('Build') {
            steps {
		            sh '''
                cd $REPOSITORY_NAME
                ./gradlew build -x test
                cd ..
                '''
            }
        }

        stage('Build Docker Image') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'Docker-Hub-Access-Token', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh '''
                    echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin

                    docker rmi -f $DOCKER_USER/$REPOSITORY_NAME:latest || true
                    cd $REPOSITORY_NAME
                    docker build -t $DOCKER_USER/$REPOSITORY_NAME:latest .
                    docker push $DOCKER_USER/$REPOSITORY_NAME:latest
                    cd ..
                    '''
                }
            }
        }

        stage('Deploy with Docker Compose') {
            steps {
                sh '''
                cd $REPOSITORY_NAME
                docker-compose down $SERVICE_NAME
                docker-compose pull $SERVICE_NAME
                docker-compose up -d $SERVICE_NAME
                cd ..
                '''
            }
        }

    }
}