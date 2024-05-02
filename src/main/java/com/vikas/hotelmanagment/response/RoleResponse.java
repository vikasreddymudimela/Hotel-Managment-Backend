package com.vikas.hotelmanagment.response;

import lombok.Data;

@Data
public class RoleResponse {
private Long id;
private String name;
public RoleResponse(Long id,String name) {
	this.id=id;
	this.name=name;
}
}
