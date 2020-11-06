package bg.expense.model;

import bg.wallet.model.WalletEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

@Entity
@Table(name = "expenses")
@Getter
@Setter
@NoArgsConstructor
public class ExpenseEntity {

    @Id
    @GeneratedValue(generator = "uuid-string")
    @GenericGenerator(name = "uuid-string", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Enumerated(EnumType.STRING)
    private ExpenseCategory category;

    @DecimalMin(value = "0", message = "Enter valid amount.")
    private BigDecimal amount;

    @NotNull
    private Instant createdOn;

    @ManyToOne
    @JoinColumn(name = "wallet_id", referencedColumnName = "id")
    private WalletEntity wallet;
}
