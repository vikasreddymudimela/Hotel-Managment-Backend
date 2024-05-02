package com.vikas.hotelmanagment.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;



@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookedRoom {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private  LocalDate checkindate;
private LocalDate checkoutdate;
private String guestFullName;
private String guestemail;
private int NumOfChildren;
private int NumOfAdults;
private int totalnumguests;
private String Bookingconfirmationcode;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name="room_id")
private  Room room;
public void calculatetotalguest() {
	this.totalnumguests=this.NumOfAdults+this.NumOfChildren;
}

public void setNumOfChildren(int numOfChildren) {
	NumOfChildren = numOfChildren;
	calculatetotalguest();
}

public void setNumOfAdults(int numOfAdults) {
	NumOfAdults = numOfAdults;
	calculatetotalguest();
}

}
