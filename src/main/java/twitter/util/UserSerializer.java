package twitter.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import twitter.models.User;

import java.io.IOException;

public class UserSerializer extends StdSerializer<User> {

    public UserSerializer(){
        this(null);
    }

    protected UserSerializer(final Class<User> t) {
        super(t);
    }

    @Override
    public void serialize(final User user, final JsonGenerator jsonGenerator, final SerializerProvider serializerProvider) throws IOException {
        user.setFollowers(null);
        user.setFollowing(null);
        user.setTweets(null);
        jsonGenerator.writeObject(user);
    }
}