#!/bin/bash

REPOSITORY=/home/ec2-user/app/step3
PROJECT_NAME=freelec-springboot2-webservice
PROJECT_FILENAME=SpringPractice

echo "> Build 파일 복사"
cp $REPOSITORY/$PROJECT_NAME/build/libs/*.jar $REPOSITORY

# 프로세스 이름으로 PID 검색
echo "> 현재 구동중인 애플리케이션 pid 확인"
CURRENT_PID=$(pgrep -fl $PROJECT_FILENAME | grep jar | awk '{print $1}')

if [ -z "$CURRENT_PID" ]; then
    echo "현재 실행 중인 프로세스가 없습니다."
else
    echo "현재 실행 중인 프로세스 PID: $CURRENT_PID"
    echo "프로세스를 종료합니다."
    kill -15 $CURRENT_PID
    sleep 5
fi

echo "> 새 애플리케이션 배포"
JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo "> jar 파일 실행 권한 추가"
chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"
nohup java -jar \
    -Dspring.config.location=classpath:/application.properties,classpath:/application-real.properties,/home/ec2-user/app/application-oauth.properties,/home/ec2-user/app/application-real-db.properties \
    -Dspring.profiles.active=real \
    $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &