package com.vikas.hotelmanagment.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vikas.hotelmanagment.model.BookedRoom;
import com.vikas.hotelmanagment.model.Room;
import com.vikas.hotelmanagment.repository.RoomRepsoitory;

@Service
public class BookingService {
	@Autowired
    RoomRepsoitory roomrepo;
	public List<BookedRoom> getallbookingsbyroomid(Long roomid) {
		// TODO Auto-generated method stub
		Room room = roomrepo.findById(roomid).get();
		List<BookedRoom> bookings = room.getBookings(); 
		return bookings;
	}
	

}
