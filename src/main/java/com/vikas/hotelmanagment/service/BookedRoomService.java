package com.vikas.hotelmanagment.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.vikas.hotelmanagment.model.BookedRoom;


@Service
public interface BookedRoomService {

	List<BookedRoom> getallbookings();

	BookedRoom findByBookingConfirmationcode(String confirmationcode);

	void cancelbooking(Long bookingid);

	String saveBooking(Long roomid, BookedRoom bookingrequest);

	List<BookedRoom> getallbookingsbyid(Long roomid);

	List<BookedRoom> getbookingsbyemail(String email);

}
