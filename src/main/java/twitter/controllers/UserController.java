package twitter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import twitter.models.User;
import twitter.services.interfaces.IUserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "users")
public class UserController {

    private final IUserService service;

    @Autowired
    public UserController(IUserService service) {
        this.service = service;
    }

    @GetMapping(produces = "application/json")
    public @ResponseBody
    List<User> getUsers(@RequestParam(value = "username", required = false) String userName) {
        return service.getUsers(userName);
    }

    @GetMapping(path = "{id}", produces = "application/json")
    public @ResponseBody
    User getUserById(@PathVariable UUID id) {
        return service.getUserById(id);
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    User createUser(@RequestBody User user) {
        return service.createUser(user);
    }

    @DeleteMapping(path = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable UUID id) {
        service.deleteUserById(id);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@PathVariable User user) {
        service.updateUser(user);
    }

    @GetMapping(path = "{id}/followers", produces = "application/json")
    public @ResponseBody
    List<User> getFollowers(@PathVariable UUID id) {
        return service.getUserFollowers(id);
    }

    @GetMapping(path = "{id}/following", produces = "application/json")
    public @ResponseBody
    List<User> getFollowing(@PathVariable UUID id) {
        return service.getUserFollowing(id);
    }

    @PostMapping(path = "{id}/following/{followerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void followUser(@PathVariable UUID id, @PathVariable UUID followerId) {
        service.addFollowing(followerId, id);
    }

    @DeleteMapping(path = "{id}/following/{followerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unFollowUser(@PathVariable UUID id, @PathVariable UUID followerId) {
        service.removeFollowing(followerId, id);
    }
}
