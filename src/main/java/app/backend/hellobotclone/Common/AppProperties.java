package app.backend.hellobotclone.Common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;


@Component
@ConfigurationProperties(prefix = "hello-bot")
@Getter
@Setter
public class AppProperties {

    @NotEmpty
    @Value("${hello-bot.client-id}")
    private String clientId;

    @NotEmpty
    @Value("${hello-bot.client-secret}")
    private String clientSecret;

    @NotEmpty
    @Value("${hello-bot.get-oauth-u-r-l}")
    private String getOauthURL;
}
