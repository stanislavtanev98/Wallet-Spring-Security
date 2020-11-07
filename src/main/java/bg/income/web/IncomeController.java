package bg.income.web;

import bg.income.model.IncomeBindingModel;
import bg.income.model.IncomeServiceModel;
import bg.income.service.IncomeService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/wallet")
@PreAuthorize("hasRole('USER')")
@AllArgsConstructor
public class IncomeController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final IncomeService incomeService;

    @GetMapping("/incomes/all")
    public String allIncomes(Model model){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity user = userService.getOrCreateUser(email);
        UserServiceModel userServiceModel = modelMapper.map(user, UserServiceModel.class);

        model.addAttribute("incomes", incomeService.getUserIncomes(userServiceModel));

        return "income/incomes-all";
    }

    @GetMapping("/income")
    public String newIncomes(Model model){
        IncomeBindingModel incomeBindingModel;

        if(model.containsAttribute("income")){
            incomeBindingModel = (IncomeBindingModel) model.getAttribute("income");
        } else {
            incomeBindingModel = new IncomeBindingModel();
        }

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userService.getOrCreateUser(email);
        UserServiceModel userServiceModel = modelMapper.map(user, UserServiceModel.class);
        List<IncomeServiceModel> incomes = new ArrayList<>(incomeService.getLast3Incomes(userServiceModel));
        model.addAttribute("incomes3", incomes);

        model.addAttribute("income", incomeBindingModel);
        return "income/new";
    }

    @PostMapping("/income/save")
    public String income(@Valid @ModelAttribute("income") IncomeBindingModel incomeBindingModel,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("income", incomeBindingModel);
            redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX + "income", bindingResult);

            return "redirect:/wallet/income";
        }
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userService.getOrCreateUser(email);
        UserServiceModel userServiceModel = modelMapper.map(user, UserServiceModel.class);

        incomeService.addMoney(userServiceModel, modelMapper.map(incomeBindingModel, IncomeServiceModel.class));

        return "redirect:/wallet/incomes/all";
    }

    @DeleteMapping("/income/delete")
    public String delete(@ModelAttribute(name = "deleteId") String deleteId) {
        incomeService.delete(deleteId);
        return "redirect:/wallet/incomes/all";
    }
}
