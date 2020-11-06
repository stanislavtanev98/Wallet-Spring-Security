package bg.wallet.repository;

import bg.user.model.UserEntity;
import bg.wallet.model.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<WalletEntity, String> {

    Optional<WalletEntity> findByUser(UserEntity userEntity);
}
