package bg.user.service;

import bg.user.model.UserEntity;

public interface UserService {

    UserEntity getOrCreateUser(String email);

    boolean existsUser(String email);

    void createAndLoginUser(String email, String password);
}
