package com.vikas.hotelmanagment.response;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoleforRoleresponse {
private Long id;
private String Rolename;
private List<UserforRoleResponse> users = new ArrayList<>();
}
