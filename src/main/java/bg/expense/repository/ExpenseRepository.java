package bg.expense.repository;

import bg.expense.model.ExpenseEntity;
import bg.wallet.model.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseEntity, String> {

    Collection<ExpenseEntity> findByWallet(WalletEntity walletEntity);

}
