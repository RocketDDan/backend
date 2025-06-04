pipeline {
    agent any

    tools {
        gradle 'gradle 8.14'
        jdk 'jdk-21'
    }
    environment {
        REPOSITORY_NAME = 'backend'
    }

    stages {
        stage('Check workspace') {
            steps {
                sh 'pwd && ls -al'
            }
        }

        stage('Build') {
            steps {
		            sh '''
                ./gradlew build -x test
                '''
            }
        }

        stage('Build Docker Image') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'Docker-Hub-Access-Token', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh '''
                    echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin

                    docker rmi -f $DOCKER_USER/$REPOSITORY_NAME:latest || true
                    docker build -t $DOCKER_USER/$REPOSITORY_NAME:latest .
                    docker push $DOCKER_USER/$REPOSITORY_NAME:latest
                    '''
                }
            }
        }

        stage('Deploy with Docker Compose') {
            steps {
                sh '''
                # 1. 새 컨테이너 백그라운드로 실행 (다른 서비스 이름으로)
                docker-compose -f docker-compose.blue.yml up -d backend_new

                # 2. 헬스체크 or 대기
                echo "Waiting for new container to be ready..."
                sleep 20  # 혹은 curl localhost:8080/actuator/health 반복 체크

                # 3. 기존 컨테이너 종료
                echo "Stop original container"
                docker-compose stop backend
                docker-compose rm -f backend

                # 4. 새 컨테이너를 backend로 승격 (ex. 태그 스왑 or nginx switch)
                echo "Change backend_new tag to backend"
                docker tag backend_new backend
                '''
            }
        }

    }
}