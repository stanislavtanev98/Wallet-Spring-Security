package bg.statistic.repository;

import bg.statistic.model.StatisticEntity;
import bg.wallet.model.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatisticRepository extends JpaRepository<StatisticEntity, String> {

    Optional<StatisticEntity> findByWallet(WalletEntity walletEntity);

}
