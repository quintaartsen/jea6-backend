package twitter.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "security.jwt")
public class TokenConstrains {
    @Getter
    static final String SECRET = "SECRET_KEY";

    @Getter
    static final long EXPIRATION_TIME = 108_108_000;

    @Getter
    static final String TOKEN_PREFIX = "Bearer ";

    @Getter
    static final String HEADER_STRING = "Authorization";

    @Getter
    public static final String SIGNUP_URL = "/users";

    @Getter
    public static final String TOKEN_URL = "/token";
}
