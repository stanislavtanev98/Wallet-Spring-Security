package bg.user;

import bg.user.model.UserEntity;
import bg.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Primary
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<UserEntity> userOpt = userRepository.findOneByEmail(email);

        LOGGER.debug("Trying to load user {}. Successful? {}", email, userOpt.isPresent());

        return userOpt
                .map(this::map)
                .orElseThrow(() -> new UsernameNotFoundException("No such user " + email));
    }

    private User map(UserEntity user){
        List<GrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(r -> new SimpleGrantedAuthority(r.getRole()))
                .collect(Collectors.toList());

        User result = new User(
                user.getEmail(),
                user.getPasswordHash() != null ? user.getPasswordHash() : "",
                authorities
        );

        if(user.getPasswordHash() == null){
            result.eraseCredentials();
        }

        return result;
    }
}
