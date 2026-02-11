package colloney.user;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UserPatchRequest {
    private String username;

    @Email
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
}
