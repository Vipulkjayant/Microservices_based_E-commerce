package com.project.authservice.Entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class User {
 
     private int id;
     @NotNull(message = "Name cannot be null")
     private String name;
     @Email
     @NotNull(message = "Email cannot be null")
     private String email;
     @Size(max = 12,min = 6)
     private String password;
     private String role="USER";

}
