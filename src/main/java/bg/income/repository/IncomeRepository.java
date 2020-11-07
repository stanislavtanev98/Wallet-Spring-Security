package bg.income.repository;

import bg.expense.model.ExpenseEntity;
import bg.income.model.IncomeEntity;
import bg.wallet.model.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface IncomeRepository extends JpaRepository<IncomeEntity, String> {

    Collection<IncomeEntity> findByWallet(WalletEntity walletEntity);

}
