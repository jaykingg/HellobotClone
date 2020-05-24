package app.backend.hellobotclone.Account;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

@JsonComponent
public class AccountSerializer extends JsonSerializer<Account> {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public void serialize(Account account, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("id", account.getId());
        jsonGenerator.writeStringField("name", account.getName());
        jsonGenerator.writeObjectField("birth", DATE_FORMAT.format(account.getBirth()));
        jsonGenerator.writeStringField("sex", account.getSex());
        jsonGenerator.writeStringField("keyword", account.getKeyword());
        jsonGenerator.writeStringField("ChatBotToken",account.getServiceAccessToken());
        jsonGenerator.writeEndObject();
    }

}
