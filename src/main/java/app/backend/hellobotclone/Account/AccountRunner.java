//package app.backend.hellobotclone.Account;
//
//import app.backend.hellobotclone.Common.AppProperties;
//import org.apache.tomcat.jni.Local;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//
//import java.time.LocalDate;
//import java.util.*;
//
//@Configuration
//public class AccountRunner {
//
//    @Bean
//    public ApplicationRunner applicationRunnerAccount() {
//        return new ApplicationRunner() {
//
//            @Autowired
//            AppProperties appProperties;
//
//            @Autowired
//            AccountRepository accountRepository;
//
//            @Autowired
//            AccountService accountService;
//
//            /* 시작 계정 생성s */
//            @Override
//            public void run(ApplicationArguments args) throws Exception {
//
//                Account newAccount = Account.builder()
//                        .number(1)
//                        .id("admin")
//                        .password("1234")
//                        .birth(LocalDate.of(1992,2,28))
//                        .keyword("책임감있고 신중한")
//                        .sex("Male")
//                        .name("제이마인드")
//                        .roles(Set.of(AccountRole.USER))
//                        .build();
//
//                accountService.saveAccount(newAccount);
//
//
//                /* 시작하자마자 Oauth Token 받기 (테스트용) */
//                HttpHeaders headers = new HttpHeaders();
//                headers.setBasicAuth(appProperties.getClientId(), appProperties.getClientSecret());
//                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//                MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
//                parameters.add("grant_type", "password");
//                parameters.add("username", newAccount.getId());
//                parameters.add("password", "1234");
//                HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, headers);
//
//                Jackson2JsonParser parser2 = new Jackson2JsonParser();
//
//                RestTemplate restTemplate = new RestTemplate();
//                String response = restTemplate.postForObject(appProperties.getGetOauthURL(), requestEntity, String.class);
//
//                String getaccess_Token = parser2.parseMap(response).get("access_token").toString();
//                String getrefrsh_Token = parser2.parseMap(response).get("refresh_token").toString();
//
//                System.out.println("***ACCESS_TOKEN***");
//                System.out.println(getaccess_Token);
//                System.out.println("***REFRESH_TOKEN***");
//                System.out.println(getrefrsh_Token);
//
//                Optional<Account> getOptional = accountRepository.findById("admin");
//                Account getAccount = getOptional.get();
//                getAccount.setServiceAccessToken(getaccess_Token);
//                getAccount.setServiceRefreshToken(getrefrsh_Token);
//                accountService.saveAccount(getAccount);
//            }
//
//        };
//    }
//}
