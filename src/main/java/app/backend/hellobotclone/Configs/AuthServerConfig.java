package app.backend.hellobotclone.Configs;

import app.backend.hellobotclone.Account.AccountService;
import app.backend.hellobotclone.Common.AppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {
    /*
        OAuth2 권한 서버 설정.
     */
    @Autowired
    AccountService accountService;


    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private AppProperties appProperties;

    /* OAuth2 인증서버 자체의 보안을 설정하는 부분 -> PasswordEncoder를 통한 암호화. */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.passwordEncoder(passwordEncoder);
    }


    /*
        Client 설정. 기기 단의 정보.
        Oauth/token 으로 접속시 Body에 해당 내용을 보냄 -> 올바르다면 Token을 발급함.
        Client들도 관리하고 싶다면 clients.jdbc(datasource)를 사용하고 테이블 맵핑해주어야함.
    */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
                clients.inMemory()
                        .withClient(appProperties.getClientId())
                        .authorizedGrantTypes("password","refresh_token")
                        .scopes("read","write")
                        .secret(this.passwordEncoder.encode(appProperties.getClientSecret()))
                        .accessTokenValiditySeconds(24 * 60 * 60 * 7)
                        .refreshTokenValiditySeconds(24 * 60 * 60 * 7 * 2);
    }

    /* Oauth2서버가 작동하기 위한 EndPoint에 대한 정보를 설정 */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(accountService)
                .tokenStore(tokenStore);
    }


}
