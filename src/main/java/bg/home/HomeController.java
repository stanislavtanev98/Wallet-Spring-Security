package bg.home;

import bg.statistic.service.StatisticService;
import bg.user.model.UserEntity;
import bg.user.model.UserServiceModel;
import bg.user.service.UserService;
import bg.wallet.model.WalletServiceModel;
import bg.wallet.service.WalletService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class HomeController {

    private final WalletService walletService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping("/")
    public String home(Model model){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity user = userService.getOrCreateUser(email);
        UserServiceModel userServiceModel = modelMapper.map(user, UserServiceModel.class);

        model.addAttribute("statistic", walletService.getWalletByUser(userServiceModel).getStatistic());
        return "home/home";
    }

    @GetMapping("/home")
    public String homeAbsolute(Model model){
        return home(model);
    }

    @PostMapping("/home")
    public String homePost(){
        return "redirect:/home";
    }
}
