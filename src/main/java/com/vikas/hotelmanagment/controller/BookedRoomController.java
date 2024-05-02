package com.vikas.hotelmanagment.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vikas.hotelmanagment.Exception.InvalidbookingrequestException;
import com.vikas.hotelmanagment.Exception.ResourceNotFoundException;
import com.vikas.hotelmanagment.model.BookedRoom;
import com.vikas.hotelmanagment.model.Room;
import com.vikas.hotelmanagment.response.BookedRoomResponse;
import com.vikas.hotelmanagment.response.RoomResponse;
import com.vikas.hotelmanagment.service.BookedRoomService;
import com.vikas.hotelmanagment.service.RoomService;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("bookings")
@CrossOrigin(origins = "http://localhost:3000")
public class BookedRoomController {
	private final BookedRoomService bookedroomservice;
	private final RoomService roomservice;
	@GetMapping("all-bookings")
	@CrossOrigin(origins="http://localhost:3000",methods = RequestMethod.GET)
public ResponseEntity<List<BookedRoomResponse>> getallbookings(){
  
	List<BookedRoom> bookings = bookedroomservice.getallbookings();
	List<BookedRoomResponse> bookingresponses = new ArrayList<>();
	for(BookedRoom booking:bookings) {
		BookedRoomResponse  bookingResponse = getBookingResponse(booking);
		bookingresponses.add(bookingResponse);
	}
	return ResponseEntity.ok(bookingresponses);
}
	@GetMapping("user/{userid}")
	@CrossOrigin(origins="http://localhost:3000",methods = RequestMethod.GET)
public ResponseEntity<List<BookedRoomResponse>> getallbookings(@PathVariable("userid") String email){
  
	List<BookedRoom> bookings = bookedroomservice.getbookingsbyemail(email);
	 List<BookedRoomResponse> bookingresponses = new ArrayList<>();
	  for(BookedRoom booking:bookings) {
			BookedRoomResponse  bookingResponse = getBookingResponse(booking);
			bookingresponses.add(bookingResponse);
		}

		return ResponseEntity.ok(bookingresponses);
	}
	
	
	@GetMapping("/booking/room/{roomid}")
	@CrossOrigin(origins="http://localhost:3000",methods = RequestMethod.GET)
	public ResponseEntity<List<BookedRoomResponse>> getbookingbyroomid(@PathVariable Long roomid ){
	  List<BookedRoom> bookings= bookedroomservice.getallbookingsbyid(roomid);
	  List<BookedRoomResponse> bookingresponses = new ArrayList<>();
	  for(BookedRoom booking:bookings) {
			BookedRoomResponse  bookingResponse = getBookingResponse(booking);
			bookingresponses.add(bookingResponse);
		}
		return ResponseEntity.ok(bookingresponses);
	}

@GetMapping("/confirmationcode/{confirmationcode}")
@CrossOrigin(origins="http://localhost:3000",methods = RequestMethod.GET)
public ResponseEntity<?> getbookingbyconfirmationcode(@PathVariable String confirmationcode){
try {
	BookedRoom booking = bookedroomservice.findByBookingConfirmationcode(confirmationcode);
	BookedRoomResponse bookingresponse = getBookingResponse(booking);
	return ResponseEntity.ok(bookingresponse);
}catch(ResourceNotFoundException e) {
	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
}
}
@PostMapping("/room/{roomid}/booking")
@CrossOrigin(origins="http://localhost:3000",methods = RequestMethod.POST)
public ResponseEntity<?> saveBooking(@PathVariable Long roomid,@RequestBody BookedRoom bookingrequest){
	try {
	String confirmationcode = bookedroomservice.saveBooking(roomid,bookingrequest);	
	return ResponseEntity.ok("Room Booked Successfull your booking confirmation code is :"+confirmationcode);
	}catch(InvalidbookingrequestException e) {
	return ResponseEntity.badRequest().body(e.getMessage());	
	}
}
@DeleteMapping("/booking/{bookingid}/delete")
@CrossOrigin(origins="http://localhost:3000",methods = RequestMethod.DELETE)
public void cancelbooking(@PathVariable Long bookingid) {
bookedroomservice.cancelbooking(bookingid);	
}

private BookedRoomResponse getBookingResponse(BookedRoom booking) {
	// TODO Auto-generated method stub
	Room theRoom = roomservice.getRoomByid(booking.getRoom().getId()).get();
	RoomResponse room = new RoomResponse(theRoom.getId(),theRoom.getRoomtype(),theRoom.getRoomprice());
	return  new BookedRoomResponse (booking.getId(),booking.getCheckindate(),booking.getCheckoutdate(),booking.getGuestFullName(),booking.getGuestemail(),booking.getNumOfAdults(),booking.getNumOfChildren(),booking.getTotalnumguests(),booking.getBookingconfirmationcode(), room);
 			
}
}
