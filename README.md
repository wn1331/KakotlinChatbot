Language : Kotlin

Framework : Spring Boot

Skills : Spring Reactive Web(Webflux) + Coroutine + Redis + Spring AI(OpenAI)

내용 : LLM(GPT Openapi) Json 응답 웹 어플리케이션<토이프로젝트>.

특징 : 논블로킹, 비동기, 단일 세션 저장 기능

목적 : 대화내용을 기억하고 있는 논블로킹 응답 카톡 챗봇 구현

배포환경 : 홈네트워크 포트포워딩

기능 : 
1. 홈네트워크 이외의 ip 차단(filter)
2. 단일세션으로 대화의 내용을 기록하여 응답하는 LLM (Redis 활용)

트러블슈팅: 
1. 필터 적용 안되는문제
   -> 원인 : webflux + Spring AI 충돌
   -> Spring AI에 필요한 빈은 Spring-boot-starter-web에 있는 Spring6의 RestClient인데 webflux(spring-boot-starter-webflux)에는 RestClient가 존재하지 않았음.
   -> spring-boot-starter-web과 spring-boot-starter-webflux를 둘다 gradle에 추가했더니 필터가 먹히지 않음 (필터가 webflux환경에 맞춰져 있어 spring-boot-starter-web implementation 때문에 필터자체가 무시됨)
   -> spring-boot-starter-web 제거하고 RestClient bean을 임의로 등록하여 해결

추가 구현 해야할 것
1. Android Application(메신저봇 역할)
