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
                    clone_or_pull() {
                        local repo=$1
                        if [ -d "$repo/.git" ]; then
                            cd $repo
                            git reset --hard
                            git pull origin main
                            cd ..
                        else
                            rm -rf $repo
                            git clone https://$GITHUB_TOKEN@github.com/$ORGANIZATION_NAME/$repo.git
                        fi
                    }

                    clone_or_pull "$REPOSITORY_NAME"
                    '''
                }
            }
        }

        stage('Build') {
            steps {
		            sh '''
                build_service() {
                    local service=$1
                    cd $service
                    ./gradlew build -x test
                    cd ..
                }

                build_service "$REPOSITORY_NAME"
                '''
            }
        }

        stage('Build Docker Image') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'Docker-Hub-Access-Token', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh '''
                    echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin

                    build_and_push() {
                        local service=$1
                        docker rmi -f $DOCKER_USER/$service:latest || true
                        cd $service
                        docker build -t $DOCKER_USER/$service:latest .
                        docker push $DOCKER_USER/$service:latest
                        cd ..
                    }
                    build_and_push "$REPOSITORY_NAME"
                    '''
                }
            }
        }

        stage('Deploy with Docker Compose') {
            steps {
                sh '''
                deploy_with_docker_compose() {
                    local service=$1
                    cd $service
                    docker-compose down $service
                    docker-compose pull $service
                    docker-compose up -d $service
                    cd ..
                }
                deploy_with_docker_compose "SERVICE_NAME"
                '''
            }
        }

    }
}