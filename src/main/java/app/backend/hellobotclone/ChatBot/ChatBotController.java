package app.backend.hellobotclone.ChatBot;

import app.backend.hellobotclone.Account.Account;
import app.backend.hellobotclone.Account.AccountRepository;
import app.backend.hellobotclone.Account.AccountResource;
import app.backend.hellobotclone.ChatResponse.ChatResponse;
import app.backend.hellobotclone.ChatResponse.ChatResponseRepository;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/api/chat", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class ChatBotController {


    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ChatResponseRepository chatResponseRepository;

    @Autowired
    ChatBotService chatBotService;

    @GetMapping
    public ResponseEntity getAccount() {

        Account getAccount = accountRepository.findById("admin")
                .orElseThrow(()->new UsernameNotFoundException("그런사람 없어요"));

        ControllerLinkBuilder selfLinkBuilder = linkTo(ChatBotController.class);
        URI createdUri = selfLinkBuilder.toUri();

        AccountResource accountResource = new AccountResource(getAccount);
        accountResource.add(linkTo(ChatBotController.class).withSelfRel());
        accountResource.add(linkTo(ChatBotController.class).slash("makeScenario").withRel("시나리오 만들기 링크"));
        accountResource.add(new Link("/docs/index.html#resource-getAccount").withRel("profile"));


        return ResponseEntity.created(createdUri).body(accountResource);
    }


    /* GetMapping을 통해 정해둔 시나리오를 만들 수 있다. */
    @GetMapping("/makeScenario")
    public ResponseEntity makeScenario() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String getUsername = authentication.getName();

        Account getAccount = accountRepository.findById(getUsername)
                .orElseThrow(()->new UsernameNotFoundException("그런사람 없어요"));


        if(chatBotService.makeScenarioProcess());
        List<ChatResponse> getChatResponses = chatResponseRepository.findAll();

        ChatBotScenarioDto newChatBotScenarioDto = ChatBotScenarioDto.builder()
                .account(getAccount)
                .scenario(getChatResponses)
                .build();

        ControllerLinkBuilder selfLinkBuilder = linkTo(ChatBotController.class);
        URI createdUri = selfLinkBuilder.toUri();

        ChatBotResource chatBotResource = new ChatBotResource(newChatBotScenarioDto);
        chatBotResource.add(linkTo(ChatBotController.class).slash("makeScenario").withSelfRel());
        chatBotResource.add(linkTo(ChatBotController.class).slash(1).withRel("시나리오 시작 링크"));
        chatBotResource.add(new Link("/docs/index.html#resource-makeScenario").withRel("profile"));

        return ResponseEntity.created(createdUri).body(chatBotResource);

    }


    /* 시나리오 시작은 받는 데이터 없음 */
    @PostMapping("/1")
    public ResponseEntity chatProcessStart() throws Exception{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String getUsername = authentication.getName();

        Account getAccount = accountRepository.findById(getUsername)
                .orElseThrow(()->new UsernameNotFoundException("그런사람 없어요."));

        ChatResponse getChatResponse = chatResponseRepository.findById(1)
                .orElseThrow(()->new IllegalArgumentException("해당 프로세스가 없음."));

        ChatBotProcessDto newChatBotProcessDto = ChatBotProcessDto.builder()
                .account(getAccount)
                .chatResponse(getChatResponse)
                .build();


        ControllerLinkBuilder selfLinkBuilder = linkTo(ChatBotController.class);
        URI createdUri = selfLinkBuilder.toUri();

        ChatBotProcessResource chatBotProcessResource = new ChatBotProcessResource(newChatBotProcessDto);
        chatBotProcessResource.add(linkTo(ChatBotController.class).slash(1).withSelfRel());
        chatBotProcessResource.add(linkTo(ChatBotController.class).slash(2).withRel("다음 단계 링크"));
        chatBotProcessResource.add(new Link("/docs/index.html#resource-chatProcessStart").withRel("profile"));

        return ResponseEntity.created(createdUri).body(chatBotProcessResource);

    }


    /* PostMapping을 통해 해당 텍스트를 분석하고, 다음 depth에 대한 값들을 리턴한다. */

    @PostMapping("/{processDepth}")
    public ResponseEntity chatProcess(@PathVariable @Range(max = 6) Integer processDepth, @RequestBody @Valid ChatBotMsgDto chatBotMsgDto) throws Exception{
        /* 전달 받은 Dto와 이전 상태 processDepth에서 DB 값과 같은지 확인 */
        ChatResponse getBeforeResopnse = chatResponseRepository.findById(processDepth-1)
                .orElseThrow(()-> new Exception("해당 프로세스가 없음"));

        String DbAnswer = getBeforeResopnse.getResponseAnswer();
        if(!chatBotService.validateMsg(DbAnswer, chatBotMsgDto)) {
            /* 응답 미일치 일경우 BAD request return */
            throw new Exception("해당 값에 대한 응답이 저장되어있지 않음.");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String getUsername = authentication.getName();

        Account getAccount = accountRepository.findById(getUsername)
                .orElseThrow(()->new UsernameNotFoundException("그런사람 없어요."));

        ChatResponse getChatResponse = chatResponseRepository.findById(processDepth)
                .orElseThrow(()->new Exception("해당 프로세스가 없음."));

        ChatBotProcessDto newChatBotProcessDto = ChatBotProcessDto.builder()
                .account(getAccount)
                .chatResponse(getChatResponse)
                .build();


        ControllerLinkBuilder selfLinkBuilder = linkTo(ChatBotController.class);
        URI createdUri = selfLinkBuilder.toUri();

        ChatBotProcessResource chatBotProcessResource = new ChatBotProcessResource(newChatBotProcessDto);
        chatBotProcessResource.add(linkTo(ChatBotController.class).slash(processDepth).withSelfRel());
        chatBotProcessResource.add(linkTo(ChatBotController.class).slash(processDepth-1).withRel("이전 단계 링크"));
        if(processDepth >= 6) {
            chatBotProcessResource.add(linkTo(ChatBotController.class).slash(6).withRel("Process 종료"));
        }
        else {
            chatBotProcessResource.add(linkTo(ChatBotController.class).slash(processDepth+1).withRel("다음 단계 링크"));
        }
        chatBotProcessResource.add(new Link("/docs/index.html#resource-chatProcess").withRel("profile"));

        return ResponseEntity.created(createdUri).body(chatBotProcessResource);

    }

}
