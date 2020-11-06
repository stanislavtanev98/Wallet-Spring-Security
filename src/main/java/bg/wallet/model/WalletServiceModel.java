package bg.wallet.model;


import bg.statistic.model.StatisticEntity;
import bg.user.model.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class WalletServiceModel {

    private String id;
    private UserEntity user;
    private StatisticEntity statistic;

}
