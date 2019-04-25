package twitter.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

public class Token {
    @Getter
    @Setter
    private String userName;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String accessToken;

    @Getter
    @Setter
    private long expirationTime;

    @Getter
    @Setter
    private String type;

    public Token()
    {
    }

    public Token(String accessToken, long expirationTime, String type, String userName, String password) {
        this.accessToken = accessToken;
        this.expirationTime = expirationTime;
        this.type = type;
        this.userName = userName;
        this.password = password;
    }
}
