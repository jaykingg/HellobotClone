package app.backend.hellobotclone.ChatBot;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

public class ChatBotScenarioResource extends Resource<ChatBotScenarioDto> {
    public ChatBotScenarioResource(ChatBotScenarioDto content, Link... links) {
        super(content, links);
    }
}
