package bg.registration;

import bg.registration.model.RegistrationDTO;
import bg.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @GetMapping("/registration")
    public String showRegister(Model model){
        model.addAttribute("formData", new RegistrationDTO());

        return "registration/registration";
    }

    @PostMapping("/registration")
    public String register(@Valid @ModelAttribute("formData")
                                       RegistrationDTO registrationDTO,
                           BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "registration/registration";
        }

        if(userService.existsUser(registrationDTO.getEmail())){
            bindingResult.rejectValue("email", "error.email",
                    "An account with this email already exists.");
            return "registration/registration";
        }

        userService.createAndLoginUser(registrationDTO.getEmail(), registrationDTO.getPassword());

        return "redirect:/home";
    }
}
