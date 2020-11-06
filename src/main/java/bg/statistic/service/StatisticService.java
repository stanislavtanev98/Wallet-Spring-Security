package bg.statistic.service;

import bg.statistic.model.StatisticEntity;
import bg.statistic.model.StatisticServiceModel;
import bg.user.model.UserServiceModel;
import bg.wallet.model.WalletServiceModel;

public interface StatisticService {

    StatisticServiceModel createStatistic(WalletServiceModel walletServiceModel);

    void updateStatistic(StatisticServiceModel statistic);
}
