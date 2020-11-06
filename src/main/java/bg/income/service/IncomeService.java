package bg.income.service;

import bg.expense.model.ExpenseServiceModel;
import bg.income.model.IncomeServiceModel;
import bg.user.model.UserServiceModel;

import java.util.Collection;

public interface IncomeService {

    IncomeServiceModel addMoney(UserServiceModel userServiceModel, IncomeServiceModel incomeServiceModel);

    Collection<IncomeServiceModel> getUserIncomes(UserServiceModel userServiceModel);
}
