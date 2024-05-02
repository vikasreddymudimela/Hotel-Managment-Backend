package com.vikas.hotelmanagment.model;

import java.sql.Blob;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@AllArgsConstructor
public class Room {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String roomtype;
private double roomprice;
@Lob
private Blob photo ;
private boolean isbooked = false;
@OneToMany(mappedBy="room",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
private List<BookedRoom> bookings;
public Room() {
	this.bookings = new ArrayList<>();
}
public void addBooking(BookedRoom booking) {
if(booking==null) {
	bookings = new ArrayList<>();
}
bookings.add(booking);
booking.setRoom(this);
isbooked = true;
String bookingcode = RandomStringUtils.randomNumeric(10);
booking.setBookingconfirmationcode(bookingcode);
}
}
