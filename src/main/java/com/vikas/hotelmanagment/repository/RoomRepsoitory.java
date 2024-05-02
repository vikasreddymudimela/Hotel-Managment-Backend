package com.vikas.hotelmanagment.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vikas.hotelmanagment.model.Room;

public interface RoomRepsoitory  extends JpaRepository<Room,Long>{

	
	@Query(value="select distinct roomtype from room",nativeQuery=true)
	List<String> getroomtypes();
	
	
	@Query(value="select * from room r where r.roomtype = :roomtype and r.id not in (select br.room_id from booked_room br where br.checkindate < :checkoutdate and br.checkoutdate >:checkindate)",nativeQuery=true)

	List<Room> findAvailableRoomsByDatesandType(LocalDate checkindate, LocalDate checkoutdate, String roomtype);
      
	

}
