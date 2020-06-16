package me.janeve.oss.ofoss.auth;

import me.janeve.oss.ofoss.dao.UserRepository;
import me.janeve.oss.ofoss.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.logging.Logger;

public class AuthUserDetailsService implements UserDetailsService {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user != null) {
            logger.info(user.toString());
            return new ApplicationUserDetails(user);
        }
        throw new UsernameNotFoundException("User not found!");
    }
}
