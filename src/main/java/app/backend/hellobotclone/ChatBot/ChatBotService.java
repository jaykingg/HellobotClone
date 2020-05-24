package app.backend.hellobotclone.ChatBot;

import app.backend.hellobotclone.ChatResponse.ChatResponse;
import app.backend.hellobotclone.ChatResponse.ChatResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class ChatBotService {

    @Autowired
    ChatResponseRepository chatResponseRepository;

    @Transactional
    public boolean makeScenarioProcess() throws JSONException {

        ChatResponse chatResponse = null;

        /* Depth 1 reponse */
        String rcontent1 = "안녕!나는 개발냥이야ㅎㅎ만나게 되어서 반가워\n" +
                "나와 함께 개발자가 되어볼래?";
        String acontent1 = "응!해볼래";

        chatResponse = ChatResponse.builder()
                .responseContent(rcontent1)
                .responseAnswer(acontent1)
                .type(Set.of(TypeEnum.TEXT, TypeEnum.MULTIPART))
                .imageFilePath("https://jayass3cloud.s3.ap-northeast-2.amazonaws.com/IMG_8456.jpg")
                .build();
        chatResponseRepository.save(chatResponse);

        /* Depth 2 response */
        String rcontent2 = "$NAME$는 보아하니 책임감있고 신중해보여!\n" +
                "그래서 나는 보이지는 않지만 데이터를 효율적으로 관리하고 처리해주는\n" +
                "백앤드 개발자를 추천하고싶어 ㅎㅎ";

        String acontent2 = "응!좋아";

        chatResponse = ChatResponse.builder()
                .responseContent(rcontent2)
                .responseAnswer(acontent2)
                .type(Set.of(TypeEnum.TEXT))
                .imageFilePath("EMPTY")
                .build();
        chatResponseRepository.save(chatResponse);

        /* Depth 3 response */
        String rcontent3 = "냥이는 $NAME$한테 Spring Boot Framework를 이용하는 Java개발자가 되는게 좋아보이는데\n" +
                "혹시 Java를 이용해서 개발해본 경험이 있니?";

        String acontent3 = "응!문서를 참고해서 간단한 서비스개발가능해";

        chatResponse = ChatResponse.builder()
                .responseContent(rcontent3)
                .responseAnswer(acontent3)
                .type(Set.of(TypeEnum.TEXT))
                .imageFilePath("EMPTY")
                .build();
        chatResponseRepository.save(chatResponse);


        /* Depth 4 response */
        String rcontent4 = "$IMAGE$\n" +
                "오호~대단한데? 그럼 RESTful API를 개발해보자!\n" +
                "RESTful API가 뭔지알아?";

        String acontent4 = "아니 잘 모르겠어!";

        chatResponse = ChatResponse.builder()
                .responseContent(rcontent4)
                .responseAnswer(acontent4)
                .imageFilePath("https://lh3.googleusercontent.com/proxy/9B4_NmGyPZCtJOtaWAkbcnuqs1VtDPBozlcwm9CBogaMUFnr1WB3XWEGIhD8NcGuO_Sx7k4DMgF3l_xUzJ3cBqKTGNg-CmcNGTszO3FWoKM7oBkJ3I1zkUHx8tJCdQ")
                .type(Set.of(TypeEnum.TEXT,TypeEnum.MULTIPART))
                .build();
        chatResponseRepository.save(chatResponse);

        /* Depth 5 response */
        String rcontent5 = "현재 REST API로 불리우는 대부분의 API가 실제로는 " +
                "로이 필딩이 정의한 REST를 따르고 있지 않고있어..\n" +
                "그 중에서도 특히 Self-Descriptive Message와 HATEOAS가 지켜지고 있지 않지!\n" +
                "Self-Descriptive Message란 메세지가 스스로 자신을 설명할 수 있어야한다는 것!\n" +
                "HATEOAS는 어플리케이션의 상태가 Link를 통해 상태가 변경될 수 있다는 것을 뜻해!\n" +
                "자 그럼 다음 강의를 보면서 Self-Descriptive Message와 HATEOAS를 만족하는 REST API를 개발해보자!\n" +
                "5강까지 듣고 따라갔다면 했다고해줘!\n" +
                "$동영상LINK..$";

        String acontent5 = "응!다 했어";

        chatResponse = ChatResponse.builder()
                .responseContent(rcontent5)
                .responseAnswer(acontent5)
                .type(Set.of(TypeEnum.TEXT))
                .imageFilePath("EMPTY")
                .build();
        chatResponseRepository.save(chatResponse);

        /*  Last Depth 6 */
        String rcontent6 = "$NAME$은 백앤드 개발자로 폭풍 성장중입니다..";
        String acontent6 = "...";

        chatResponse = ChatResponse.builder()
                .responseContent(rcontent6)
                .responseAnswer(acontent6)
                .type(Set.of(TypeEnum.TEXT))
                .imageFilePath("EMPTY")
                .build();
        chatResponseRepository.save(chatResponse);

        return true;
    }



    public boolean validateMsg(String DbAnswer, ChatBotMsgDto chatBotMsgDto) {
        /* 자유 형식인 경우 */
        if(chatBotMsgDto.getType().equals(TypeEnum.FREE)) {
            String validKeyword = DbAnswer.substring(0,2);
            System.out.println(validKeyword);
            if(validKeyword.equals("응!") &&
                    (chatBotMsgDto.getUserAnswer().startsWith("응") ||
                    chatBotMsgDto.getUserAnswer().startsWith("어") ||
                    chatBotMsgDto.getUserAnswer().startsWith("그래") ||
                    chatBotMsgDto.getUserAnswer().startsWith("좋아"))) {
                return true;
            }
            else if(validKeyword.equals("아니") && (
                    chatBotMsgDto.getUserAnswer().startsWith("아니") ||
                    chatBotMsgDto.getUserAnswer().startsWith("별로") ||
                    chatBotMsgDto.getUserAnswer().startsWith("아직") ||
                    chatBotMsgDto.getUserAnswer().startsWith("싫"))) {
                return true;
            }
            else {
                return false;
            }
        }
        /* 빠른 선택지로 답한 경우 */
        else if(chatBotMsgDto.getType().equals(TypeEnum.BUTTON)) {
            if(chatBotMsgDto.getUserAnswer().equals(DbAnswer)) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }


}
