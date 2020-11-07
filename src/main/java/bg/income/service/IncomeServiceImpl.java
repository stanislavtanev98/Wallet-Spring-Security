package bg.income.service;

import bg.expense.model.ExpenseEntity;
import bg.expense.model.ExpenseServiceModel;
import bg.income.model.IncomeEntity;
import bg.income.model.IncomeServiceModel;
import bg.income.repository.IncomeRepository;
import bg.statistic.model.StatisticEntity;
import bg.statistic.model.StatisticServiceModel;
import bg.statistic.service.StatisticService;
import bg.user.model.UserServiceModel;
import bg.wallet.model.WalletEntity;
import bg.wallet.service.WalletService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class IncomeServiceImpl implements IncomeService {

    private final WalletService walletService;
    private final ModelMapper modelMapper;
    private final IncomeRepository incomeRepository;
    private final StatisticService statisticService;

    @Override
    public IncomeServiceModel addMoney(UserServiceModel userServiceModel, IncomeServiceModel incomeServiceModel) {
        WalletEntity wallet = modelMapper.map(walletService.getWalletByUser(userServiceModel), WalletEntity.class);

        incomeServiceModel.setCreatedOn(Instant.now());
        incomeServiceModel.setWallet(wallet);
        IncomeEntity income = modelMapper.map(incomeServiceModel, IncomeEntity.class);
        incomeRepository.saveAndFlush(income);

        StatisticEntity statistic = wallet.getStatistic();
        statistic.setTotalIncomes(statistic.getTotalExpenses().add(income.getAmount()));
        statistic.setCurrentAmount(statistic.getCurrentAmount().add(income.getAmount()));
        statisticService.updateStatistic(modelMapper.map(statistic, StatisticServiceModel.class));

        return modelMapper.map(income, IncomeServiceModel.class);
    }

    @Override
    public Collection<IncomeServiceModel> getUserIncomes(UserServiceModel userServiceModel) {
        WalletEntity wallet = modelMapper.map(walletService.getWalletByUser(userServiceModel), WalletEntity.class);
        Collection<IncomeEntity> incomes = incomeRepository.findByWallet(wallet);

        return incomes.stream()
                .map(i -> modelMapper.map(i, IncomeServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<IncomeServiceModel> getLast3Incomes(UserServiceModel userServiceModel) {
        WalletEntity wallet = modelMapper.map(walletService.getWalletByUser(userServiceModel), WalletEntity.class);
        Collection<IncomeEntity> incomes = incomeRepository.findByWallet(wallet);

        return incomes.stream()
                .map(i -> modelMapper.map(i, IncomeServiceModel.class))
                .limit(3)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String deleteId) {
        IncomeEntity income = incomeRepository.findById(deleteId).orElse(null);
        if(income != null) {
            WalletEntity wallet = income.getWallet();
            StatisticEntity statistic = wallet.getStatistic();
            statistic.setTotalIncomes(statistic.getTotalExpenses().subtract(income.getAmount()));
            statistic.setCurrentAmount(statistic.getCurrentAmount().subtract(income.getAmount()));
            statisticService.updateStatistic(modelMapper.map(statistic, StatisticServiceModel.class));
        }
        incomeRepository.deleteById(deleteId);
    }
}
