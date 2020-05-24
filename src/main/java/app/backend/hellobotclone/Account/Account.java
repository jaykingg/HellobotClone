package app.backend.hellobotclone.Account;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@JsonSerialize(using = AccountSerializer.class)
public class Account {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer number;

    @Id
    private String id;

    @NotNull
    private String password;

    @NotNull
    private String name;

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate birth;

    @NotNull
    private String sex;


    @NotNull
    private String keyword;

    @NotNull
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<AccountRole> roles;

    private String ServiceAccessToken;
    private String ServiceRefreshToken;





}
