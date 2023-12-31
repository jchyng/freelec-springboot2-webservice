#!/bin/bash

REPOSITORY=/home/ec2-user/app/step1
PROJECT_NAME=freelec-springboot2-webservice
PROJECT_FILENAME=SpringPractice-0.0.1-SNAPSHOT.jar

cd $REPOSITORY/$PROJECT_NAME/

echo "> Git pull"
git pull

echo "> 프로젝트 Build 시작"
./gradlew build

echo "> step1 디렉토리로 이동"
cd $REPOSITORY

echo "> Build 파일 복사"
cp $REPOSITORY/$PROJECT_NAME/build/libs/$PROJECT_FILENAME $REPOSITORY/

# 프로세스 이름으로 PID 검색
echo "> 현재 구동중인 애플리케이션 pid 확인"
CURRENT_PID=$(pgrep -f "${PROJECT_FILENAME}")

if [ -z "$CURRENT_PID" ]; then
    echo "현재 실행 중인 $PROJECT_FILENAME 프로세스가 없습니다."
else
    echo "현재 실행 중인 $PROJECT_FILENAME 프로세스 PID: $CURRENT_PID"
    echo "프로세스를 종료합니다."
    kill -15 $CURRENT_PID
fi

echo "> 새 애플리케이션 배포"
nohup java -jar \
    -Dspring.config.location=classpath:/application.properties,classpath:/application-real.properties,/home/ec2-user/app/application-oauth.properties,/home/ec2-user/app/application-real-db.properties \
    -Dspring.profiles.active=real \
    $PROJECT_FILENAME > $REPOSITORY/nohup.out 2>&1 &