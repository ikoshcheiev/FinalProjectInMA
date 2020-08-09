package org.example.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    String firstName;
    String lastName;
    String email;
    String password;
    String confirmPassword;
}
