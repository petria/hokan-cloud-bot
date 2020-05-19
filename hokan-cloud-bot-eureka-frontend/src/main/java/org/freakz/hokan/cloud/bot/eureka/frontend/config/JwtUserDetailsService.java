package org.freakz.hokan.cloud.bot.eureka.frontend.config;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    if ("1".equals(username)) {
      return new User("1", "$2a$10$2ZYHCUKvaF2DaGt2E9IdLelV2UrZBHaGXJ1r04RHKPP2wy17g3wSm",
        new ArrayList<>());
    } else {
      throw new UsernameNotFoundException("User not found with username: " + username);
    }
  }
}
