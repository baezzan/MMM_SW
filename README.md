# MMM_SW
MY MATZIP MAP

1/OpenJDK설치
2/vscode 설치 및 확장 프로그램 마켓 플레이스에서 Java Extension Pack, Spring Boot Extension Pack 설치

javax.servlet, spring-boot-starter-web, okhttp, jackson-databind 종속성을 추가하기위해 pom.xml 작성
<dependencies>
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>javax.servlet-api</artifactId>
        <version>4.0.1</version>
        <scope>provided</scope>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
        <version>2.5.2</version>
    </dependency>
    <dependency>
        <groupId>com.squareup.okhttp3</groupId>
        <artifactId>okhttp</artifactId>
        <version>4.9.1</version>
    </dependency>
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.12.3</version>
    </dependency>
</dependencies>
 
build.gradle 파일에 필요한 종속성을 추가
dependencies {
    implementation 'javax.servlet:javax.servlet-api:4.0.1'
    implementation 'org.springframework.boot:spring-boot-starter-web:2.5.2'
    implementation 'com.squareup.okhttp3:okhttp:4.9.1'
    implementation 'com.fasterxml.jackson.core:jackson-databind:2.12.3'
}

#back
package.json 파일에 정의된 의존성 라이브러리를 설치합니다:
npm install

/config/express.js 파일에서 Express 서버에 필요한 미들웨어와 라우터를 설정합니다.

#front
common.css: 전체적인 페이지에 적용되는 CSS
map.css: 지도와 관련된 CSS
sign.css: 로그인 & 회원가입 페이지 관련 CSS

url.js: 서버 API 요청에 사용할 공통된 URL 
header.js: 회원<->비회원의 헤더를 다르게 설정하는 로직
map.js: 지도 관련 로직
signin.js: 로그인 API 연동 로직
signup.js: 회원가입 API 연동 로직

index.html: 메인 페이지
signin.html: 로그인 페이지
signup.html: 회원가입 페이지

map.java:지도 관련 로직
signup.java:회원가입 API 연동 로직
SignupSevlet.java:로그인 API 연동 로직
header.java:회원<->비회원의 헤더를 다르게 설정하는 로직
AuthController.java:서버 API 요청에 사용할 공통된 URL 
