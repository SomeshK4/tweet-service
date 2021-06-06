package org.twitter.service.dto;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO {

    @NotBlank
    @Size(min = 6)
    String username;

    @NotBlank
    @Size(min = 8, max = 16)
    String password;

    @NotBlank
    @Email
    String email;

    @NotBlank
    String firstName;

    @NotBlank
    String lastName;
}
