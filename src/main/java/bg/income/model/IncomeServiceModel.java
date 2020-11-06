package bg.income.model;

import bg.wallet.model.WalletEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class IncomeServiceModel {

    private String id;

    @NonNull
    @NotNull(message = "You should select a category!")
    private IncomeCategory category;

    @NotNull(message = "Invalid amount!")
    @DecimalMin(value = "0", message = "The amount should be a positive number!")
    private BigDecimal amount;

    private Instant createdOn;

    private WalletEntity wallet;
}
