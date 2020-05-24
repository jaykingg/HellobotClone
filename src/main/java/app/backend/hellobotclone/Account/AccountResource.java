package app.backend.hellobotclone.Account;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

public class AccountResource extends Resource<Account> {
    public AccountResource(Account account, Link... links) {
        super(account, links);
    }
}
