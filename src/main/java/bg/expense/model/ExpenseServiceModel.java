package bg.expense.model;

import bg.wallet.model.WalletEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class ExpenseServiceModel {

    private String id;

    @NonNull
    @NotNull(message = "You should select a category!")
    private ExpenseCategory category;

    @NotNull(message = "Invalid amount!")
    @DecimalMin(value = "0", message = "The amount should be a positive number!")
    private BigDecimal amount;

    private Instant createdOn;

    private WalletEntity wallet;
}
