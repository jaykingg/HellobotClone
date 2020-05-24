# HellobotClone
띵스플로우 개발자 사전과제

##사용 기술 및 스택
- 사용 언어/프레임워크 : Java 11 / Spring boot 2.1.7 RELEASE
- 사용 기술 스택 : Spring JPA, Spring Security, HATEOAS, Spring REST Docs, Oauth2
- 사용 DB : H2(in-memory)

## 요구사항 분석
- 챗봇 메시지 콘텐츠 입력(제작) API: 챗봇의 대화 시나리오를 미리 작성해 두기 위한 API
	> /api/chat/makeScenario API를 통하여 정해진 시나리오를 생성.
- 콘텐츠 사용 API: 사용자의 입력에따라 정해진 시나리오대로 답변을 받을 수 있는 메시지 API
	> /api/chat/{PathValue} API를 통하여 정해진 시나리오 별 답변.
- 시나리오는 한개의 정해진 시나리오가 나오는 것이 기본.
	> 정해진 시나리오를 첨부.
    >![시나리오](https://user-images.githubusercontent.com/20733918/82762125-32745d00-9e3a-11ea-8d88-3f349b548668.png)

- 메시지 타입
> 텍스트, 이미지, 빠른 답변(선택지), 자유입력
> 텍스트(TEXT), 이미지(MULTIPART) 는 챗봇 응답 메시지타입으로 사용.
> 빠른 답변(선택지, BUTTON), 자유입력(FREE)는 클라이언트 메시지 타입으로 사용.
> Enum 타입으로 정의.
- 테스트 코드 작성
	> Junit4 / MockMvc 를 사용하여 단위/통합 테스트 진행.
	1. 사전에 생성된 Account 계정 정보를 가져오는 API테스트
    2. 잘못된 토큰을 가지고 허용되지않은 자원에 접근할 경우의 API테스트
    3. 미리 작성된 시나리오를 생성하는 API테스트
    4. 채팅 시작시, 응답을 가져오는 API테스트
    5. 사용자가 빠른선택지를 이용했을 때 응답하는 API테스트
    6. 사용자가 자유입력형식을 이용했을 때 응답하는 API테스트
    7. 사용자가 '잘못된' 빠른선택지를 이용했을 때 응답하는 API테스트
    8. 사용자가 이용한 자유입력이 정해진 응답과 방향이 다를 경우 API테스트
    9. 프로세스 진행시, 온전하지 못한 Dto를 전송할 경우의 API테스트
    
- 동작하는 코드와 테스트 코드, 실행 방법에 대한 문서화
	> Spring REST Docs를 이용하여 빌드.
	> 프로젝트를 실행시키고, 아래 링크에서 html파일로 확인가능.
		: http://localhost:8080/docs/index.html
	> 프로젝트 구조 
    ![프로젝트구성](https://user-images.githubusercontent.com/20733918/82762199-a57dd380-9e3a-11ea-95cb-98b228b227f5.png)

- 프로젝트 동작 및 실행 방법  
	1. GET /api/chat 을 통해 사용자를 생성하고 Password를 암호화 시킨뒤 DB에 저장,
Spring security를 통하여 Oauth2 Token을 발급받고, Account/Token 값을 Return.
	2. GET /api/chat/makeScenario 에 Bearer Token을 넣고 시나리오를 생성.
	3. POST /api/chat/{Depth} 에 Bearer Token을 넣고 각 응답을 받음.

- Html 문서화
	> Spring REST Docs 를 이용하여 생성된 API PDF 문서를 함께 첨부.
	> API 문서에 포함된 내용
		1. 각 API Resource 설명
		2. 각 API Request / Response / Link 정보 및 그에 대한 설명
