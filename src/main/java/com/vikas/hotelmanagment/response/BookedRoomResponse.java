package com.vikas.hotelmanagment.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookedRoomResponse {
	private Long id;
	private  LocalDate checkindate;
	private LocalDate checkoutdate;
	private String guestFullName;
	private String guestemail;
	private int NumOfChildren;
	private int NumOfAdults;
	private int totalnumguests;
	private String Bookingconfirmationcode;
	private RoomResponse room;
	public BookedRoomResponse(Long id, LocalDate checkindate, LocalDate checkoutdate, String bookingconfirmationcode) {
	
		this.id = id;
		this.checkindate = checkindate;
		this.checkoutdate = checkoutdate;
		Bookingconfirmationcode = bookingconfirmationcode;
	}
	
}
