# KAKAOTALK CHATBOT PROJECT(비동기/논블로킹 대화세션 저장한 GPT API 구현)

## 🌐 **Project Overview**

A web application that leverages a Large Language Model (LLM) (GPT OpenAPI) to provide JSON responses. This toy project aims to create a non-blocking, asynchronous KakaoTalk chatbot capable of remembering previous conversation contexts within a single session.

### **Technologies Used**

- **Language:** Kotlin
- **Framework:** Spring Boot
- **Key Skills:**
  - Spring Reactive Web (Webflux)
  - Coroutine
  - Redis
  - Spring AI (OpenAI)

### **Project Features**

1. **IP Filtering:**
   - Restricts access to the application, allowing only requests from the home network IP.

2. **Multi Session Memory:**
   - Utilizes Redis to record conversation contexts, enabling the chatbot to remember and respond based on previous interactions.


### **Deployment Environment**

- **Home Network Port Forwarding:**
  - The application is deployed in a home network setup with port forwarding to manage external access.

### **Troubleshooting**

1. **IP Filter Not Applying:**
   - **Issue:** Conflict between Webflux and Spring AI.
   - **Root Cause:** Spring AI required beans from `spring-boot-starter-web` which includes Spring 6's `RestClient`, but `spring-boot-starter-webflux` does not have `RestClient`.
   - **Solution:** Removed `spring-boot-starter-web` and manually registered the `RestClient` bean to resolve the conflict.

### **Future Enhancements**

1. **Android Application:**
   - Develop an Android application to act as a messenger bot, enhancing accessibility and user experience.

### **Project Purpose**

The primary goal is to implement a non-blocking, asynchronous chatbot for KakaoTalk that retains conversation history, providing a seamless and intelligent user interaction.

### ** 추가 구현해야 할 부분 및 기술적 검토 또는 구상안 **
1. 일반적인 레디스를 사용하지 않고, 최근 스프링AI에서 발표한 redis-vector를 사용하면 어떨지 (embedding vectordb)
2. GPT API 말고, 로컬LLM에서 응답받는 방법이 있을지


![스크린샷 2024-07-16 오후 12 23 03](https://github.com/user-attachments/assets/b3bbd047-d674-46ab-8724-dbde8278e277)

