package app.backend.hellobotclone.ChatBot;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

public class ChatBotProcessResource extends Resource<ChatBotProcessDto> {
    public ChatBotProcessResource(ChatBotProcessDto content, Link... links) {
        super(content, links);
    }
}
