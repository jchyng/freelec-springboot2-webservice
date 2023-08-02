#앞선 4개의 스크립트 파일에서 공용으로 사용할 profile과 포트 체크 로직

#!/usr/bin/enc bash

# 쉬고있는 profile 찾기: real1이 사용 중이면 real2가 쉬고있고, 반대면 real1이 쉬고있음

function find_idle_profile() {
    RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost/profile)

    if [ ${RESPONSE_CODE} -ge 400 ] #400보다 크면 (즉, 40x/ 50x 에러 모두 포함)

    then
        CURRENT_PROFILE=real2
    else
        CURRENT_PROFILE=$(curl -s http://localhost/profile)
    fi

    if [ ${CURRENT_PROFILE} == real1 ]
    then
      IDLE_PROFILE=real2
    else
      IDEL_PROFILE=real1
    fi

    echo "${IDLE_PROFILE}"
}


#쉬고있는 profile의 port 찾기
function find_idle_port() {
    IDEL_PROFILE=$(find_idle_profile)

    if[ ${IDLE_PROFILE} == real ]
    then
      echo "8081"
    else
      echo "8082"
    fi
}