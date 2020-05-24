package app.backend.hellobotclone.ChatBot;

import app.backend.hellobotclone.Account.*;
import app.backend.hellobotclone.Common.BaseControllerTest;
import app.backend.hellobotclone.Common.TestDescription;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.Set;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ChatBotControllerTest extends BaseControllerTest {
    //TODO
    /*
     * TDD 상황별로 짜기
     * 문서화
     * Spring docs 빌드
     * */

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountService accountService;

    @Test
    @TestDescription("사전에 생성된 Account 계정 정보를 가져오는 API테스트")
    public void getAccount() throws Exception{
//        //given
//        this.mockMvc.perform(get("/api/chat"))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("content.id").exists())
//                .andExpect(jsonPath("_links").exists())
//                .andDo(document("getAccount",links(
//                        linkWithRel("self").description("현재 링크"),
//                        linkWithRel("시나리오 만들기 링크").description("시나리오 만들기 링크"),
//                        linkWithRel("profile").description("도큐먼트 링크")
//
//                        ),
//                        responseFields(
//                                fieldWithPath("id").description("USER ID"),
//                                fieldWithPath("name").description("USER NAME"),
//                                fieldWithPath("birth").description("USER BIRTHDAY"),
//                                fieldWithPath("sex").description("USER SEX"),
//                                fieldWithPath("keyword").description("USER KEYWORD"),
//                                fieldWithPath("ChatBotToken").description("USER CHATBOT-TOKEN")
//                        )
//                ))
//        ;
    }

    @Test
    @TestDescription("잘못된 토큰을 가지고 허용되지않은 자원에 접근할 경우의 API테스트")
    public void accessWrongToken() {

    }

    @Test
    @TestDescription("미리 작성된 시나리오를 생성하는 API테스트")
    public void makeScenario() throws Exception{

        createTddAccount();

        this.mockMvc.perform(get("/api/chat/makeScenario")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken()))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("account").exists())
                .andExpect(jsonPath("scenario").exists())
                .andExpect(jsonPath("_links").exists())
                .andDo(document("makeScenario",links(
                        linkWithRel("self").description("현재 링크"),
                        linkWithRel("시나리오 시작 링크").description("시나리오 시작 링크"),
                        linkWithRel("profile").description("도큐먼트 링크")
                        ),
                        relaxedResponseFields(
                                fieldWithPath("account").description("ACCOUNT INFO"),
                                fieldWithPath("account.id").description("USER ID"),
                                fieldWithPath("account.name").description("USER NAME"),
                                fieldWithPath("account.birth").description("USER BIRTHDAY"),
                                fieldWithPath("account.sex").description("USER SEX"),
                                fieldWithPath("account.keyword").description("USER KEYWORD"),
                                fieldWithPath("account.ChatBotToken").description("USER CHATBOT-TOKEN"),
                                fieldWithPath("scenario").description("Scenario Data"),
                                fieldWithPath("scenario[0].id").description("Scenario Depth"),
                                fieldWithPath("scenario[0].type").description("Bot Response Type"),
                                fieldWithPath("scenario[0].responseContent").description("Bot Response TEXT"),
                                fieldWithPath("scenario[0].imageFilePath").description("Bot Response of Image Path"),
                                fieldWithPath("scenario[0].responseAnswer").description("Bot Response of Button")
                                )
                ))
        ;
    }

    @Test
    @TestDescription("채팅 시작시, 응답을 가져오는 API테스트")
    public void chatStart() throws Exception{
        //createTddAccount();

        this.mockMvc.perform(post("/api/chat/1")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken()))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("account").exists())
                .andExpect(jsonPath("chatResponse").exists())
                .andExpect(jsonPath("_links").exists())
                .andDo(document("chatStart",links(
                        linkWithRel("self").description("현재 링크"),
                        linkWithRel("다음 단계 링크").description("다음 단계 링크"),
                        linkWithRel("profile").description("도큐먼트 링크")
                        ),
                        relaxedResponseFields(
                                fieldWithPath("account").description("ACCOUNT INFO"),
                                fieldWithPath("account.id").description("USER ID"),
                                fieldWithPath("account.name").description("USER NAME"),
                                fieldWithPath("account.birth").description("USER BIRTHDAY"),
                                fieldWithPath("account.sex").description("USER SEX"),
                                fieldWithPath("account.keyword").description("USER KEYWORD"),
                                fieldWithPath("account.ChatBotToken").description("USER CHATBOT-TOKEN"),
                                fieldWithPath("chatResponse").description("ChatBot Response Data"),
                                fieldWithPath("chatResponse.id").description("ChatBot Response  Depth"),
                                fieldWithPath("chatResponse.type").description("ChatBot Response Type"),
                                fieldWithPath("chatResponse.responseContent").description("ChatBot Response TEXT. divide by \\n"),
                                fieldWithPath("chatResponse.imageFilePath").description("ChatBot Response of Image Path"),
                                fieldWithPath("chatResponse.responseAnswer").description("ChatBot Response of Button")
                        )
                ))
        ;
    }

    @Test
    @TestDescription("각 대화 Depth(단계)에 따라, 사용자가 빠른선택지를 이용했을 때 응답하는 API테스트")
    public void chatReponseButton() throws Exception{

        ChatBotMsgDto chatBotMsgDto = ChatBotMsgDto.builder()
                .type(TypeEnum.BUTTON)
                .userAnswer("응!해볼래")
                .build();

        this.mockMvc.perform(post("/api/chat/2")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(chatBotMsgDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("account").exists())
                .andExpect(jsonPath("chatResponse").exists())
                .andExpect(jsonPath("_links").exists())
                .andDo(document("chatResponseButton",links(
                        linkWithRel("self").description("현재 링크"),
                        linkWithRel("이전 단계 링크").description("이전 단계 링크"),
                        linkWithRel("다음 단계 링크").description("다음 단계 링크"),
                        linkWithRel("profile").description("도큐먼트 링크")
                        ),
                        requestFields(
                                fieldWithPath("type").description("Type of Message"),
                                fieldWithPath("userAnswer").description("User Answer")
                        ),
                        relaxedResponseFields(
                                fieldWithPath("account").description("ACCOUNT INFO"),
                                fieldWithPath("account.id").description("USER ID"),
                                fieldWithPath("account.name").description("USER NAME"),
                                fieldWithPath("account.birth").description("USER BIRTHDAY"),
                                fieldWithPath("account.sex").description("USER SEX"),
                                fieldWithPath("account.keyword").description("USER KEYWORD"),
                                fieldWithPath("account.ChatBotToken").description("USER CHATBOT-TOKEN"),
                                fieldWithPath("chatResponse").description("ChatBot Response Data"),
                                fieldWithPath("chatResponse.id").description("ChatBot Response  Depth"),
                                fieldWithPath("chatResponse.type").description("ChatBot Response Type"),
                                fieldWithPath("chatResponse.responseContent").description("ChatBot Response TEXT. divide by \\n"),
                                fieldWithPath("chatResponse.imageFilePath").description("ChatBot Response of Image Path"),
                                fieldWithPath("chatResponse.responseAnswer").description("ChatBot Response of Button")
                        )
                ))
        ;

    }

    @Test
    @TestDescription("각 대화 Depth(단계)에 따라, 사용자가 자유입력형식을 이용했을 때 응답하는 API테스트")
    public void chatResponseFree() throws Exception{
        ChatBotMsgDto chatBotMsgDto = ChatBotMsgDto.builder()
                .type(TypeEnum.FREE)
                .userAnswer("좋아")
                .build();

        this.mockMvc.perform(post("/api/chat/3")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(chatBotMsgDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("account").exists())
                .andExpect(jsonPath("chatResponse").exists())
                .andExpect(jsonPath("_links").exists())
                .andDo(document("chatResponseFree",links(
                        linkWithRel("self").description("현재 링크"),
                        linkWithRel("이전 단계 링크").description("이전 단계 링크"),
                        linkWithRel("다음 단계 링크").description("다음 단계 링크"),
                        linkWithRel("profile").description("도큐먼트 링크")
                        ),
                        requestFields(
                                fieldWithPath("type").description("Type of Message"),
                                fieldWithPath("userAnswer").description("User Answer")
                        ),
                        relaxedResponseFields(
                                fieldWithPath("account").description("ACCOUNT INFO"),
                                fieldWithPath("account.id").description("USER ID"),
                                fieldWithPath("account.name").description("USER NAME"),
                                fieldWithPath("account.birth").description("USER BIRTHDAY"),
                                fieldWithPath("account.sex").description("USER SEX"),
                                fieldWithPath("account.keyword").description("USER KEYWORD"),
                                fieldWithPath("account.ChatBotToken").description("USER CHATBOT-TOKEN"),
                                fieldWithPath("chatResponse").description("ChatBot Response Data"),
                                fieldWithPath("chatResponse.id").description("ChatBot Response  Depth"),
                                fieldWithPath("chatResponse.type").description("ChatBot Response Type"),
                                fieldWithPath("chatResponse.responseContent").description("ChatBot Response TEXT. divide by \\n"),
                                fieldWithPath("chatResponse.imageFilePath").description("ChatBot Response of Image Path"),
                                fieldWithPath("chatResponse.responseAnswer").description("ChatBot Response of Button")
                        )
                ))
        ;



    }

    @Test
    @TestDescription("각 대화 Depth(단계)에 따라, 사용자가 '잘못된' 빠른선택지를 이용했을 때 응답하는 API테스트")
    public void chatWroungResponseButton() throws Exception{
        ChatBotMsgDto chatBotMsgDto = ChatBotMsgDto.builder()
                .type(TypeEnum.BUTTON)
                .userAnswer("헬로우")
                .build();

        this.mockMvc.perform(post("/api/chat/2")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(chatBotMsgDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;

    }

    @Test
    @TestDescription("각 대화 Depth(단계)에 따라, 사용자가 이용한 자유입력이 정해진 응답과 방향이 다를 경우 API테스트")
    public void chatDifferentResponseFree() throws Exception{

        ChatBotMsgDto chatBotMsgDto = ChatBotMsgDto.builder()
                .type(TypeEnum.FREE)
                .userAnswer("별로")
                .build();

        this.mockMvc.perform(post("/api/chat/2")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(chatBotMsgDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @TestDescription("각 대화 Depth(단계)에 따른 프로세스 진행시, 온전하지 못한 Dto를 전송할 경우의 API테스트")
    public void transferWrongDto() throws Exception{

        ChatBotMsgDto chatBotMsgDto = ChatBotMsgDto.builder()
                .type(TypeEnum.BUTTON)
                .build();

        this.mockMvc.perform(post("/api/chat/2")
                .header(HttpHeaders.AUTHORIZATION, getBearerToken())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(chatBotMsgDto)))
                .andDo(print())
                .andExpect(status().)
        ;

    }
    public void createTddAccount() {
        Account newAccount = Account.builder()
                .number(1)
                .id("adminTDD")
                .password("1234")
                .birth(LocalDate.of(1992, 2, 28))
                .keyword("책임감있고 신중한")
                .sex("Male")
                .name("제이마인드")
                .roles(Set.of(AccountRole.USER))
                .build();

        accountService.saveAccount(newAccount);
    }

    public String getAccessToken() throws Exception{
        ResultActions perform = this.mockMvc.perform(post("/oauth/token")
                .with(httpBasic(appProperties.getClientId(),appProperties.getClientSecret()))
                .param("username","adminTDD")
                .param("password","1234")
                .param("grant_type","password"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token").exists()
                );

        var responseBody = perform.andReturn().getResponse().getContentAsString();
        Jackson2JsonParser parser = new Jackson2JsonParser();
        return parser.parseMap(responseBody).get("access_token").toString();
    }

    public String getBearerToken() throws Exception {
        return "Bearer " + getAccessToken();
    }


}