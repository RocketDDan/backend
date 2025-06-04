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
                docker-compose down $REPOSITORY_NAME
                docker-compose pull $REPOSITORY_NAME
                docker-compose up -d $REPOSITORY_NAME

                # echo "1. 새 컨테이너 백그라운드로 실행"
                # echo "현재 위치: $(pwd)"
                # docker-compose -f ../docker-compose.blue.yml up -d backend_new

                # echo "2. 헬스체크 or 대기: 새 컨테이너 대기 중"
                # sleep 20  # 혹은 curl localhost:8080/actuator/health 반복 체크

                # echo "3. 기존 컨테이너 종료"
                # docker-compose stop backend
                # docker-compose rm -f backend

                # echo "4. 새 컨테이너를 backend로 승격: backend_new 태그를 backend로 수정"
                # docker tag backend_new backend
                '''
            }
        }

    }
}