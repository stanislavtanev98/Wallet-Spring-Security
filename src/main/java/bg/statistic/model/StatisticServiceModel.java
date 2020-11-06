package bg.statistic.model;

import bg.wallet.model.WalletEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class StatisticServiceModel {

    private String id;

    private BigDecimal currentAmount = BigDecimal.ZERO;

    private BigDecimal totalIncomes = BigDecimal.ZERO;

    private BigDecimal totalExpenses = BigDecimal.ZERO;

    private WalletEntity wallet;
}
