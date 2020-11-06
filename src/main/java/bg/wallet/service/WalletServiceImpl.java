package bg.wallet.service;

import bg.statistic.model.StatisticServiceModel;
import bg.statistic.service.StatisticService;
import bg.user.model.UserEntity;
import bg.user.model.UserServiceModel;
import bg.wallet.model.WalletEntity;
import bg.wallet.model.WalletServiceModel;
import bg.wallet.repository.WalletRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final StatisticService statisticService;
    private final ModelMapper modelMapper;

    @Override
    public WalletServiceModel createWallet(UserServiceModel userServiceModel) {
        UserEntity userEntity = modelMapper.map(userServiceModel, UserEntity.class);
        WalletEntity walletEntity = new WalletEntity();
        walletEntity.setUser(userEntity);

        walletEntity = this.walletRepository.saveAndFlush(walletEntity);
        WalletServiceModel walletServiceModel = this.modelMapper.map(walletEntity, WalletServiceModel.class);
        statisticService.createStatistic(walletServiceModel);

        return walletServiceModel;
    }

    @Override
    public WalletServiceModel getWalletByUser(UserServiceModel userServiceModel) {
        UserEntity userEntity = modelMapper.map(userServiceModel, UserEntity.class);

        WalletEntity walletRepositoryByUser = this.walletRepository.findByUser(userEntity).orElse(null);

        return this.modelMapper.map(walletRepositoryByUser, WalletServiceModel.class);
    }
}
