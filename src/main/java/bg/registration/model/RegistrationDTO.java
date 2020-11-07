package bg.registration.model;

import bg.common.validators.FieldMatch;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.*;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldMatch(first = "password", second = "repeatPassword",
        message = "Passwords do not match.")
public class RegistrationDTO {

    @NotNull
    @Size(min = 4, max = 100)
    @Email
    private String email;

    @NotBlank
    @Size(min = 5, max = 80, message = "Password must be more than 4 symbols")
    private String password;

    @NotBlank
    private String repeatPassword;
}
