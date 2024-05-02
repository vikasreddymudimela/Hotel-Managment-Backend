package com.vikas.hotelmanagment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vikas.hotelmanagment.model.BookedRoom;

public interface BookedRoomRepository  extends JpaRepository<BookedRoom,Long>{

	
	@Query(value="select * from booked_room where bookingconfirmationcode = ?",nativeQuery=true)
	BookedRoom findByConfirmationcode(String confirmationcode);

	List<BookedRoom> findByGuestemail(String email);

}

