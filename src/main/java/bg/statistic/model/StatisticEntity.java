package bg.statistic.model;

import bg.wallet.model.WalletEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "statistics")
@Getter
@Setter
@NoArgsConstructor
public class StatisticEntity {

    @Id
    @GeneratedValue(generator = "uuid-string")
    @GenericGenerator(name = "uuid-string", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    private BigDecimal currentAmount = BigDecimal.ZERO;

    private BigDecimal totalIncomes = BigDecimal.ZERO;

    private BigDecimal totalExpenses = BigDecimal.ZERO;

    @OneToOne
    private WalletEntity wallet;
}
