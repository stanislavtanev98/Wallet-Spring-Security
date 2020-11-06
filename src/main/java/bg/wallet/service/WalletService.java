package bg.wallet.service;

import bg.user.model.UserEntity;
import bg.user.model.UserServiceModel;
import bg.wallet.model.WalletServiceModel;

public interface WalletService {

    WalletServiceModel createWallet(UserServiceModel userServiceModel);

    WalletServiceModel getWalletByUser(UserServiceModel userServiceModel);

}
