# ✍🏻 studyKotlinSpring
Kotlin 언어를 익히기 위해 Kotlin 언어로 Spring Boot 기반의 백엔드 API를 인프런 강의를 통해서 예제로 학습
- Kakako API와 연동하여 Daum 블로그 내용을 검색하는 API 만들기
- 키워드를 검색할 때마다 count를 증가해, 상위 10개 인기 검색어를 집계하는 API 만들기

---

### 🔧 활용 기술

- **Language**: Kotlin
- **Framework**: Spring Boot
- **Build Tool**: Gradle (KTS)
- **DB**: MariaDB(Docker) / JPA
- **Kakao API 연동**: WebClient

---

### Kakao API 블로그 검색 호출 예시

```bash
호출
curl -v -G GET "https://dapi.kakao.com/v2/search/blog" \
--data-urlencode "query=https://brunch.co.kr/@tourism 집짓기" \
-H "Authorization: KakaoAK ${REST_API_KEY}"
```

```bash
응답
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8
{
  "meta": {
    "total_count": 5,
    "pageable_count": 5,
    "is_end": true
  },
  "documents": [
    {
    "title": "작은 <b>집</b> <b>짓기</b> 기본컨셉 - <b>집</b><b>짓기</b> 초기구상하기",
    "contents": "이 점은 <b>집</b>을 지으면서 고민해보아야 한다. 하지만, 금액에 대한 가성비 대비 크게 문제되지 않을 부분이라 생각하여 설계로 극복하자...",
    "url": "https://brunch.co.kr/@tourism/91",
    "blogname": "정란수의 브런치",
    "thumbnail": "http://search3.kakaocdn.net/argon/130x130_85_c/7r6ygzbvBDc",
    "datetime": "2017-05-07T18:50:07.000+09:00"
    },
    ...
  ]
}
```

---

### Postman으로 API 테스트
#### ✅ 블로그 검색 API 테스트 결과
![블로그 검색](docs/image%20(12).png)
#### ✅ 검색어 상위 10개 조회 결과
![검색어 상위 10개 조회](docs/image%20(13).png)
#### ❌ 유효하지 않은 입력값 검증 예시
![유효하지 않은 입력값 검증](docs/image%20(14).png)

---

### Docker를 통해 MariaDB 설치
```bash
docker pull mariadb
docker run —name mariadb -d -p 3306:3306 —restart=always -e MYSQL_ROOT_PASSWORD=패스워드 mariadb
# 접속
docker exec -it mariadb /bin/bash
# 실행 및 패스워드 입력
mariadb -u root -p

# MySQL workbench를 통해서도 DB 접속, 실행 가능
```
