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
import twitter.controllers.UserController;
import twitter.models.Role;
import twitter.models.User;
import twitter.services.interfaces.IUserService;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IUserService service;

    @Before
    public void setUp() {

    }

    @Test
    public void getAllUsers() throws Exception{
        mvc.perform(get("/users").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getUserById() throws Exception{
        mvc.perform(get("/users/"+new UUID(1, 1)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getUserById2() throws Exception{
        User user = new User(Role.ROLE_USER, "email@mail.com", "username", "password");
        user.setId(new UUID(1, 1));
        service.createUser(user);
        mvc.perform(get("/users/"+new UUID(1, 1)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}