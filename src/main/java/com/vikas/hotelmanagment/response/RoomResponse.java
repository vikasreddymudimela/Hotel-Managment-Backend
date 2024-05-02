package com.vikas.hotelmanagment.response;

import java.sql.Blob;
import java.util.Base64;
import java.util.List;

import com.vikas.hotelmanagment.model.BookedRoom;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.Lob;
import jakarta.persistence.*;
import lombok.*;


@Data
@NoArgsConstructor
public class RoomResponse {
	
	
	
	private Long id;
	private String roomtype;
	private double roomprice;
	private String photo ;
	private boolean isbooked = false;
	private List<BookedRoomResponse> bookings;
	public RoomResponse(Long id, String roomtype, double roomprice) {
		super();
		this.id = id;
		this.roomtype = roomtype;
		this.roomprice = roomprice;
	}
	public RoomResponse(Long id, String roomtype, double roomprice, byte[] photobytes, boolean isbooked,
			List<BookedRoomResponse> bookings) {
		super();
		this.id = id;
		this.roomtype = roomtype;
		this.roomprice = roomprice;
		this.photo = photobytes != null ? Base64.getEncoder().encodeToString(photobytes):null;
		this.isbooked = isbooked;
		this.bookings = bookings;
	}
	

}
