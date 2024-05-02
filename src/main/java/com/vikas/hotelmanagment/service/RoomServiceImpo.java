package com.vikas.hotelmanagment.service;

import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vikas.hotelmanagment.Exception.InternalServerException;
import com.vikas.hotelmanagment.Exception.ResourceNotFoundException;
import com.vikas.hotelmanagment.model.Room;
import com.vikas.hotelmanagment.repository.RoomRepsoitory;


@Service
public class RoomServiceImpo implements RoomService {
	@Autowired
 private RoomRepsoitory  roomrepository;
	@Override
	public Room addNewRoom(MultipartFile photo, String roomtype, double roomprice) throws SerialException, SQLException {
		// TODO Auto-generated method stub
		
		Room room = new Room();
		room.setRoomtype(roomtype);
		room.setRoomprice(roomprice);
		if(!photo.isEmpty()) {
			byte[] photobytes;
			try {
				photobytes = photo.getBytes();
				Blob photoBlob = new SerialBlob(photobytes);
				room.setPhoto(photoBlob);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return roomrepository.save(room);
	}
	@Override
	public List<String> getroomtypes() {
		// TODO Auto-generated method stub
		roomrepository.getroomtypes();
		return roomrepository.getroomtypes();
	}
	@Override
	public List<Room> getAllRooms() {
		// TODO Auto-generated method stub
		return roomrepository.findAll();
	}
	@Override
	public byte[] getphotoroombyid(Long roomid) throws SQLException {
		// TODO Auto-generated method stub
		Optional<Room> theroom = roomrepository.findById(roomid);
		if(theroom.isEmpty()) {
			throw new ResourceNotFoundException("sorry room not found");
		}
		Blob photoBlob = theroom.get().getPhoto();
		if(photoBlob!=null) {
			return photoBlob.getBytes(1, (int)photoBlob.length());
		}
		return null;
	}
	@Override
	public void deleteRoom(Long id) {
		// TODO Auto-generated method stub
		Optional<Room> theRoom = roomrepository.findById(id);
		if(theRoom.isPresent()) {
			roomrepository.deleteById(id);
		}
		
	}
	@Override
	public Room updateRoom(Long roomid, String roomType, double roomPrice, byte[] photobytes) throws InternalServerException {
		// TODO Auto-generated method stub
		Room room = roomrepository.findById(roomid).orElseThrow(() -> new ResourceNotFoundException("Room not found"));
		if(roomType != null) room.setRoomtype(roomType);
		if(roomPrice != 0) room.setRoomprice(roomPrice);
		if(photobytes != null && photobytes.length>0) {
			try {
				room.setPhoto(new SerialBlob(photobytes));
			} catch (SQLException e) {
			throw new InternalServerException("Error upading room");	
			}
		}
		
		return roomrepository.save(room);
	}
	@Override
	public Optional<Room> getRoomByid(Long roomid){
		Optional<Room> room =  roomrepository.findById(roomid);
		if(room.isEmpty()) {
			throw new ResourceNotFoundException("No room exists with id");
			}
		else{
			return room; 
		}
	}
	@Override
	public List<Room> getavailablerooms(LocalDate checkindate, LocalDate checkoutdate, String roomtype) {
		// TODO Auto-generated method stub
		return roomrepository.findAvailableRoomsByDatesandType(checkindate, checkoutdate, roomtype);
	}

}
