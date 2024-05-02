package com.vikas.hotelmanagment.service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.sql.rowset.serial.SerialException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vikas.hotelmanagment.Exception.InternalServerException;
import com.vikas.hotelmanagment.model.Room;

@Service
public interface RoomService {

	Room addNewRoom(MultipartFile photo, String roomtype, double roomprice) throws SerialException, SQLException;

	List<String> getroomtypes();

	List<Room> getAllRooms();

	byte[] getphotoroombyid(Long roomid) throws SQLException;

	void deleteRoom(Long id);

	Room updateRoom(Long roomid, String roomType, double roomPrice, byte[] photobytes) throws InternalServerException;

	Optional<Room> getRoomByid(Long roomid);

	List<Room> getavailablerooms(LocalDate checkindate, LocalDate checkoutdate, String roomtype);
    
}
