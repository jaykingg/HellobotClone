package app.backend.hellobotclone.ChatResponse;

import app.backend.hellobotclone.ChatBot.TypeEnum;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class ChatResponse {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<TypeEnum> type;

    @NotNull @Column(length = 1000)
    private String responseContent;

    private String imageFilePath;

    @NotNull
    private String responseAnswer;
}
