package bg;

import bg.expense.model.ExpenseCategory;
import bg.expense.model.ExpenseServiceModel;
import bg.expense.service.ExpenseService;
import bg.user.model.RoleEntity;
import bg.user.model.UserEntity;
import bg.user.model.UserServiceModel;
import bg.user.repository.UserRepository;
import bg.wallet.model.WalletEntity;
import bg.wallet.model.WalletServiceModel;
import bg.wallet.repository.WalletRepository;
import bg.wallet.service.WalletService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class WalletIiApplicationInit implements CommandLineRunner {

    private final UserRepository userRepository;
    private final WalletService walletService;
    private final PasswordEncoder passwordEncoder;
    private final ExpenseService expenseService;
    private final ModelMapper modelMapper;

    @Override
    public void run(String... args) throws Exception {
        if(userRepository.count() == 0){
            // admin
            UserEntity admin = new UserEntity();
            admin.setEmail("admin@wallet.com");
            admin.setPasswordHash(passwordEncoder.encode("topsecret"));

            RoleEntity adminAdminRole = new RoleEntity();
            adminAdminRole.setRole("ROLE_ADMIN");
            RoleEntity adminUserRole = new RoleEntity();
            adminUserRole.setRole("ROLE_USER");

            admin.setRoles(List.of(adminAdminRole, adminUserRole));

            userRepository.save(admin);
            UserServiceModel userServiceModel = modelMapper.map(admin, UserServiceModel.class);
            walletService.createWallet(userServiceModel);

            // user
            UserEntity user = new UserEntity();
            user.setEmail("user@wallet.com");
            user.setPasswordHash(passwordEncoder.encode("topsecret"));

            RoleEntity userUserRole = new RoleEntity();
            userUserRole.setRole("ROLE_USER");

            user.setRoles(List.of(userUserRole));

            userRepository.save(user);
            UserServiceModel userServiceModel2 = modelMapper.map(user, UserServiceModel.class);
            walletService.createWallet(userServiceModel2);
        }

//        // taco
//        UserEntity taco = new UserEntity();
//        taco.setEmail("taco@wallet.com");
//        taco.setPasswordHash(passwordEncoder.encode("topsecret"));
//
//        RoleEntity tacoUserRole = new RoleEntity();
//        tacoUserRole.setRole("ROLE_USER");
//
//        taco.setRoles(List.of(tacoUserRole));
//
//        userRepository.save(taco);
//
//        WalletServiceModel wallet = walletService.createWallet(taco);
//        ExpenseServiceModel expenseServiceModel = new ExpenseServiceModel();
//        expenseServiceModel.setAmount(BigDecimal.valueOf(200));
//        expenseServiceModel.setCategory(ExpenseCategory.FOOD_AND_DRINK);
//        expenseServiceModel.setWallet(modelMapper.map(wallet, WalletEntity.class));
//        expenseService.expenseMoney(taco, expenseServiceModel);
//
//        Collection<ExpenseServiceModel> expenses = expenseService.getExpenses(taco);
//
//        for (ExpenseServiceModel expens : expenses) {
//            System.out.println(expens.getCategory() + " " + expens.getAmount());
//        }

//        UserEntity admin = userRepository.findOneByEmail("admin@wallet.com").orElse(null);
//        UserServiceModel userServiceModel = modelMapper.map(admin, UserServiceModel.class);
//        WalletServiceModel walletByUser = walletService.getWalletByUser(userServiceModel);
//
//        ExpenseServiceModel expenseServiceModel = new ExpenseServiceModel();
//        expenseServiceModel.setAmount(BigDecimal.valueOf(200));
//        expenseServiceModel.setCategory(ExpenseCategory.FOOD_AND_DRINK);
//        expenseServiceModel.setWallet(modelMapper.map(walletByUser, WalletEntity.class));
//        expenseService.expenseMoney(expenseServiceModel);
//
//        ExpenseServiceModel expense2 = new ExpenseServiceModel();
//        expense2.setAmount(BigDecimal.valueOf(300));
//        expense2.setCategory(ExpenseCategory.TAXES);
//        expense2.setWallet(modelMapper.map(walletByUser, WalletEntity.class));
//        expenseService.expenseMoney(expense2);
    }
}
