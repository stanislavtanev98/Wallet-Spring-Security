package bg.user.service;

import bg.user.model.RoleEntity;
import bg.user.model.UserEntity;
import bg.user.model.UserServiceModel;
import bg.user.repository.UserRepository;
import bg.user.service.UserService;
import bg.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserDetailsService userDetailsService;
    private final WalletService walletService;
    private final ModelMapper modelMapper;

    public boolean existsUser(String email) {
        Objects.requireNonNull(email);

        return userRepository.findOneByEmail(email).isPresent();
    }

    public UserEntity getOrCreateUser(String email) {

        Objects.requireNonNull(email);

        Optional<UserEntity> userEntityOpt = userRepository.findOneByEmail(email);

        return userEntityOpt
                .orElseGet(() -> createUser(email));
    }

    private UserEntity createUser(String email, String password) {
        LOGGER.info("Creating a new user with email [GDPR].");
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);

        if(password != null){
            userEntity.setPasswordHash(passwordEncoder.encode(password));
        }

        RoleEntity userRole = new RoleEntity();
        userRole.setRole("ROLE_USER");

        userEntity.setRoles(List.of(userRole));

        userRepository.save(userEntity);
        UserServiceModel userServiceModel = modelMapper.map(userEntity, UserServiceModel.class);
        walletService.createWallet(userServiceModel);

        return userEntity;
    }

    private UserEntity createUser(String email) {
        return createUser(email, null);
    }

    public void createAndLoginUser(String email, String password){
        UserEntity newUser = createUser(email, password);

        UserDetails userDetails = userDetailsService.loadUserByUsername(newUser.getEmail());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, password, userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}