package bg.expense.service;

import bg.expense.model.ExpenseEntity;
import bg.expense.model.ExpenseServiceModel;
import bg.expense.repository.ExpenseRepository;
import bg.statistic.model.StatisticEntity;
import bg.statistic.model.StatisticServiceModel;
import bg.statistic.service.StatisticService;
import bg.user.model.UserEntity;
import bg.user.model.UserServiceModel;
import bg.wallet.model.WalletEntity;
import bg.wallet.service.WalletService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final WalletService walletService;
    private final StatisticService statisticService;
    private final ModelMapper modelMapper;

    @Override
    public ExpenseServiceModel expenseMoney(UserServiceModel userServiceModel, ExpenseServiceModel expenseServiceModel) {
        WalletEntity wallet = modelMapper.map(walletService.getWalletByUser(userServiceModel), WalletEntity.class);

        expenseServiceModel.setCreatedOn(Instant.now());
        expenseServiceModel.setWallet(wallet);
        ExpenseEntity expense = modelMapper.map(expenseServiceModel, ExpenseEntity.class);
        expenseRepository.saveAndFlush(expense);

        StatisticEntity statistic = wallet.getStatistic();
        statistic.setTotalExpenses(statistic.getTotalExpenses().add(expense.getAmount()));
        statistic.setCurrentAmount(statistic.getCurrentAmount().subtract(expense.getAmount()));
        statisticService.updateStatistic(modelMapper.map(statistic, StatisticServiceModel.class));

        return modelMapper.map(expense, ExpenseServiceModel.class);
    }


    @Override
    public Collection<ExpenseServiceModel> getUserExpenses(UserServiceModel userServiceModel) {
        WalletEntity wallet = modelMapper.map(walletService.getWalletByUser(userServiceModel), WalletEntity.class);
        Collection<ExpenseEntity> expenses = expenseRepository.findByWallet(wallet);

        return expenses.stream()
                .map(e -> modelMapper.map(e, ExpenseServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<ExpenseServiceModel> getLast3Expenses(UserServiceModel userServiceModel) {
        WalletEntity wallet = modelMapper.map(walletService.getWalletByUser(userServiceModel), WalletEntity.class);
        Collection<ExpenseEntity> expenses = expenseRepository.findByWallet(wallet);

        return expenses.stream()
                .map(e -> modelMapper.map(e, ExpenseServiceModel.class))
                .limit(3)
                .collect(Collectors.toList());
    }


}
