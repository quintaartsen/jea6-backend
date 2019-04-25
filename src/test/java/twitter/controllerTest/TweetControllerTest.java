package twitter.controllerTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import twitter.controllers.TweetController;
import twitter.models.Role;
import twitter.models.Tweet;
import twitter.models.User;
import twitter.services.interfaces.ITweetService;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TweetController.class)
public class TweetControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ITweetService service;

    @Before
    public void setUp() {

    }

    @Test
    public void getAllTweets() throws Exception{
        mvc.perform(get("/tweets").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getTweetById() throws Exception{
        mvc.perform(get("/tweets/"+new UUID(1, 1)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getTweetById2() throws Exception{
        User user = new User(Role.ROLE_USER, "email@mail.com", "username", "password");
        user.setId(new UUID(1, 1));
        Tweet tweet = new Tweet("test", user);
        service.createTweet(tweet,new UUID(1, 1));
        mvc.perform(get("/tweets/"+new UUID(1, 1)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}