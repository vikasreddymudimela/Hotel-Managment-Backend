package com.vikas.hotelmanagment.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserforRoleResponse {
private Long id;
private String firstname;
private String lastname;
private String email;

}
