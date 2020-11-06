package bg.statistic.service;

import bg.statistic.model.StatisticEntity;
import bg.statistic.model.StatisticServiceModel;
import bg.statistic.repository.StatisticRepository;
import bg.user.model.UserServiceModel;
import bg.wallet.model.WalletEntity;
import bg.wallet.model.WalletServiceModel;
import bg.wallet.service.WalletService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class StatisticServiceImpl implements StatisticService {

    private final StatisticRepository statisticRepository;
    private final ModelMapper modelMapper;

    @Override
    public StatisticServiceModel createStatistic(WalletServiceModel walletServiceModel) {
        WalletEntity wallet = modelMapper.map(walletServiceModel, WalletEntity.class);
        StatisticEntity statistic = new StatisticEntity();
        statistic.setWallet(wallet);
        statisticRepository.saveAndFlush(statistic);
        wallet.setStatistic(statistic);
        return modelMapper.map(statistic, StatisticServiceModel.class);
    }

    @Override
    public void updateStatistic(StatisticServiceModel statistic) {
        StatisticEntity statisticEntity = modelMapper.map(statistic, StatisticEntity.class);
        statisticRepository.saveAndFlush(statisticEntity);
    }
}
