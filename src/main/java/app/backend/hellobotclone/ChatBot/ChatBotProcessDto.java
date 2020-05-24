package app.backend.hellobotclone.ChatBot;

import app.backend.hellobotclone.Account.Account;
import app.backend.hellobotclone.ChatResponse.ChatResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatBotProcessDto {

    @NotNull
    private Account account;

    @NotNull
    private ChatResponse chatResponse;
}
