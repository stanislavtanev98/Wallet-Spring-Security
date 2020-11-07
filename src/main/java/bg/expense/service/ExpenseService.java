package bg.expense.service;

import bg.expense.model.ExpenseServiceModel;
import bg.user.model.UserEntity;
import bg.user.model.UserServiceModel;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public interface ExpenseService {

    ExpenseServiceModel expenseMoney(UserServiceModel userServiceModel, ExpenseServiceModel expenseServiceModel);

    Collection<ExpenseServiceModel> getUserExpenses(UserServiceModel userServiceModel);

    Collection<ExpenseServiceModel> getLast3Expenses(UserServiceModel userServiceModel);
}
