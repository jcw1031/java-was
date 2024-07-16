# Java WAS

## 2024 우아한 테크캠프 프로젝트 WAS

### 구현 목록

#### 1단계

- [x] http://localhost:8080/index.html로 요청이 오면 `resources/static/index.html` 파일을 읽어 응답
    - [x] 만약 존재하지 않은 파일을 요청하면 `404 Not Found`로 응답
- [x] HTTP request를 적절히 파싱해 `HttpRequest` 객체와 `HttpHeaders` 객체 생성
    - [x] HTTP request를 logger를 사용해 debug 레벨로 출력
- [x] `ExecutorService`(`ThreadPoolExecutor`)를 사용해 10개의 스레드로 사용자 요청 멀티 스레드 처리

#### 2단계

- [x] 여러 컨텐츠 타입 지원하기
    - html
    - css
    - js
    - ico
    - png
    - jpg

#### 3단계

- [x] 정적 리소스 요청의 경로가 디렉토리인 경우, 해당 디렉토리 내의 `index.html`을 찾아 반환
- [x] 요청 URI를 통해 처리 가능한 핸들러를 통해 처리
- [x] 회원가입 요청이 오면 query string에 담긴 정보로 `model.User` 객체를 생성해 저장

#### 4단계

- [x] 회원가입 요청을 HTTP POST 메서드로 변경
    - [X] GET으로 요청이 오면 405 Method Not Allowed 응답(나중에 구현하기)
- [x] HTTP redirection 기능을 사용해 회원가입 후 메인 페이지로 이동

#### 5단계

- [x] 가입한 회원 정보로 로그인
- [x] 로그인 메뉴를 클릭하면 `/login`로 이동
- [x] 로그인이 성공하면 `/main`으로 redirection
- [x] 로그인이 실패하면 `/login/failed.html`로 이동
- [x] 로그아웃 기능

#### 6단계

- [x] 사용자가 로그인 상태일 경우 `/index.html`에서 사용자 이름을 표시
    - [x] 로그인 상태가 아닐 경우 로그인 버튼을 표시
- [x] 사용자가 로그인 상태일 경우 `/user/list`에서 사용자 목록 출력
    - [x] 로그인 상태가 아닐 경우 로그인 페이지로 redirection

#### 7단계

- [x] 메인 페이지에 `글쓰기` 버튼 추가
    - [x] 로그인한 사용자의 경우 `/write`로 이동
    - [x] 로그인하지 않은 사용자의 경우 `/login`으로 이동
- [x] 글쓰기 페이지에서 글의 본문을 입력할 수 있는 폼 표시
- [ ] 작성한 게시글을 메인 페이지에 표시
- [ ] 게시글 아래에 댓글 작성 (optional)
