package twitter.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import twitter.models.User;
import twitter.services.concrete.UserService;

import java.util.List;

@Service
public class SecurityUserDetailsService implements UserDetailsService {
    private UserService userService;

    public SecurityUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return getUserDetails(userService.getUserByUsername(username));
    }

    private org.springframework.security.core.userdetails.User getUserDetails(User user) {
        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                getGrantedAuthorities(user));
    }

    private List<GrantedAuthority> getGrantedAuthorities(User user) {
        return AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRole().toString());
    }
}