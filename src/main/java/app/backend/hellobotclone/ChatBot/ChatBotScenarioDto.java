package app.backend.hellobotclone.ChatBot;

import app.backend.hellobotclone.Account.Account;
import app.backend.hellobotclone.ChatResponse.ChatResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatBotScenarioDto {

    @NotNull
    private Account account;

    private List<ChatResponse> scenario;

}
