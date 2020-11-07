package bg.expense.web;

import bg.expense.model.ExpenseBindingModel;
import bg.expense.model.ExpenseServiceModel;
import bg.expense.service.ExpenseService;
import bg.user.model.UserEntity;
import bg.user.model.UserServiceModel;
import bg.user.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/wallet")
@PreAuthorize("hasRole('USER')")
@AllArgsConstructor
public class ExpenseController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final ExpenseService expenseService;

    @GetMapping("/expenses/all")
    public String allExpenses(Model model){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity user = userService.getOrCreateUser(email);
        UserServiceModel userServiceModel = modelMapper.map(user, UserServiceModel.class);

        model.addAttribute("expenses", expenseService.getUserExpenses(userServiceModel));

        return "expense/all-expenses";
    }

    @GetMapping("/expense")
    public String newExpense(Model model){
        ExpenseBindingModel expenseBindingModel;

        if(model.containsAttribute("expense")){
            expenseBindingModel = (ExpenseBindingModel) model.getAttribute("expense");
        } else {
            expenseBindingModel = new ExpenseBindingModel();
        }

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userService.getOrCreateUser(email);
        UserServiceModel userServiceModel = modelMapper.map(user, UserServiceModel.class);
        model.addAttribute("expenses3", expenseService.getLast3Expenses(userServiceModel));

        model.addAttribute("expense", expenseBindingModel);
        return "expense/new";
    }

    @PostMapping("/expense/save")
    public String expense(@Valid @ModelAttribute("expense")ExpenseBindingModel expenseBindingModel,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("expense", expenseBindingModel);
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "expense", bindingResult);

            return "redirect:/wallet/expense";
        }
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userService.getOrCreateUser(email);
        UserServiceModel userServiceModel = modelMapper.map(user, UserServiceModel.class);

        expenseService.expenseMoney(userServiceModel, modelMapper.map(expenseBindingModel, ExpenseServiceModel.class));

        return "redirect:/wallet/expenses/all";
    }
}
