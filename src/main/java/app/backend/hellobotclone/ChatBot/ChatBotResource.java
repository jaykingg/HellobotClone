package app.backend.hellobotclone.ChatBot;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

public class ChatBotResource extends Resource<ChatBotScenarioDto> {
    public ChatBotResource(ChatBotScenarioDto content, Link... links) {
        super(content, links);
    }
}
