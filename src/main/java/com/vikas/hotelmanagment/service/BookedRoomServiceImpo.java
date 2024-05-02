package com.vikas.hotelmanagment.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vikas.hotelmanagment.model.BookedRoom;
import com.vikas.hotelmanagment.model.Room;
import com.vikas.hotelmanagment.repository.BookedRoomRepository;
import com.vikas.hotelmanagment.repository.RoomRepsoitory;
import com.vikas.hotelmanagment.Exception.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookedRoomServiceImpo implements BookedRoomService {
	@Autowired
   private final BookedRoomRepository bookedroomrepository;
	private final RoomService roomservice;
	@Override
	public List<BookedRoom> getallbookings() {
		// TODO Auto-generated method stub
		List<BookedRoom> bookings= bookedroomrepository.findAll();
		return bookings;
	}

	@Override
	public BookedRoom findByBookingConfirmationcode(String confirmationcode) {
		// TODO Auto-generated method stub
		if(bookedroomrepository.findByConfirmationcode(confirmationcode) != null) {
			return bookedroomrepository.findByConfirmationcode(confirmationcode);
		}
		else {
			throw new ResourceNotFoundException("there is no booking with the code"+confirmationcode);
		}
	}

	@Override
	public void cancelbooking(Long bookingid) {
		// TODO Auto-generated method stub
		bookedroomrepository.deleteById(bookingid);
	}

	@Override
	public String saveBooking(Long roomid, BookedRoom bookingrequest) {
		// TODO Auto-generated method stub
		if(bookingrequest.getCheckoutdate().isBefore(bookingrequest.getCheckindate())) {
			throw new InvalidbookingrequestException("the checkin date should be before");
		}
		Room room = roomservice.getRoomByid(roomid).get();
		List<BookedRoom> existingbookings = room.getBookings();
		bookingrequest.calculatetotalguest();
		boolean roomisAvailable = roomisAvailable2(bookingrequest,existingbookings);
		if(roomisAvailable) {
			room.addBooking(bookingrequest);
			bookedroomrepository.save(bookingrequest);
		}
		else {
			throw new InvalidbookingrequestException("The room has been booked on selected dates");
		}
		return bookingrequest.getBookingconfirmationcode();
	}

	private boolean roomisAvailable2(BookedRoom bookingrequest, List<BookedRoom> existingbookings) {
		// TODO Auto-generated method stub
		return existingbookings.stream().noneMatch(existingbooking -> bookingrequest.getCheckindate().equals(existingbooking.getCheckindate())
            ||bookingrequest.getCheckoutdate().isBefore(existingbooking.getCheckoutdate())
            ||bookingrequest.getCheckindate().isAfter(existingbooking.getCheckindate())
            &&bookingrequest.getCheckindate().isBefore(existingbooking.getCheckoutdate())
            || bookingrequest.getCheckindate().isBefore(existingbooking.getCheckindate())
            &&bookingrequest.getCheckoutdate().equals(existingbooking.getCheckoutdate())
             || bookingrequest.getCheckindate().isBefore(existingbooking.getCheckindate())
             &&bookingrequest.getCheckoutdate().isAfter(existingbooking.getCheckoutdate())
             ||bookingrequest.getCheckindate().equals(existingbooking.getCheckoutdate())
             &&bookingrequest.getCheckoutdate().equals(existingbooking.getCheckindate())
             ||bookingrequest.getCheckindate().equals(existingbooking.getCheckoutdate())
             && bookingrequest.getCheckoutdate().equals(bookingrequest.getCheckindate())
				);
	}

	@Override
	public List<BookedRoom> getallbookingsbyid(Long roomid) {
		// TODO Auto-generated method stub
		Room theRoom = roomservice.getRoomByid(roomid).get();
		return theRoom.getBookings();
	}

	@Override
	public List<BookedRoom> getbookingsbyemail(String email) {
		// TODO Auto-generated method stub
		List<BookedRoom> bookings = bookedroomrepository.findByGuestemail(email);
		return bookings;
	}

}
