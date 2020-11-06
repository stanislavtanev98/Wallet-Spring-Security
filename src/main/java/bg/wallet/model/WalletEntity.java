package bg.wallet.model;

import bg.expense.model.ExpenseEntity;
import bg.income.model.IncomeEntity;
import bg.statistic.model.StatisticEntity;
import bg.user.model.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "wallets")
@Getter
@Setter
@NoArgsConstructor
public class WalletEntity {

    @Id
    @GeneratedValue(generator = "uuid-string")
    @GenericGenerator(name = "uuid-string", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @OneToOne
    private UserEntity user;

    @OneToOne(mappedBy = "wallet", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private StatisticEntity statistic;

}
