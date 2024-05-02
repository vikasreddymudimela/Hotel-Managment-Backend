package com.vikas.hotelmanagment.controller;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vikas.hotelmanagment.Exception.InternalServerException;
import com.vikas.hotelmanagment.Exception.ResourceNotFoundException;
import com.vikas.hotelmanagment.Exception.photoretrievalexception;
import com.vikas.hotelmanagment.model.BookedRoom;
import com.vikas.hotelmanagment.model.Room;
import com.vikas.hotelmanagment.request.AvailbiltyrequestBody;
import com.vikas.hotelmanagment.response.BookedRoomResponse;
import com.vikas.hotelmanagment.response.RoomResponse;
import com.vikas.hotelmanagment.service.BookingService;
import com.vikas.hotelmanagment.service.RoomService;
import com.vikas.hotelmanagment.service.RoomServiceImpo;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
@CrossOrigin(origins = "http://localhost:3000")
public class RoomController {
	
	private final RoomService roomservice;
	private final BookingService bookingservice;
	
@PostMapping("/add/new-room")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@CrossOrigin(origins="http://localhost:3000",methods = RequestMethod.POST)
 public ResponseEntity<RoomResponse> addNewroom(@RequestParam("photo") MultipartFile photo ,@RequestParam("roomtype") String roomtype,@RequestParam("roomprice") double roomprice){
	 Room savedroom;
	try {
		savedroom = roomservice.addNewRoom(photo,roomtype,roomprice);
		 RoomResponse response = new RoomResponse(savedroom.getId(),savedroom.getRoomtype(),savedroom.getRoomprice());
		    return ResponseEntity.ok(response);
	} catch (SerialException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  return new ResponseEntity<>(null,HttpStatusCode.valueOf(201));	
 }
@GetMapping("/room-types")
@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
//@PreAuthorize("hasRole('ROLE_ADMIN')")
@CrossOrigin(origins="http://localhost:3000",methods=RequestMethod.GET)
public ResponseEntity<List<String>> getroomtypes(){
List<String> roomtypes =roomservice.getroomtypes();
return new ResponseEntity<>(roomtypes,HttpStatusCode.valueOf(201));	
}
@GetMapping("/all-rooms")
@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
@CrossOrigin(origins="http://localhost:3000")
public ResponseEntity<List<RoomResponse>> getAllrooms() throws SQLException{
List<Room> rooms = roomservice.getAllRooms();
List<RoomResponse> roomresponses = new ArrayList<>();
for(Room room :rooms) {
byte[] photobytes = roomservice.getphotoroombyid(room.getId());	
if(photobytes != null && photobytes.length>0 ) {
	String  base64pro = Base64.encodeBase64String(photobytes);
	RoomResponse roomresponse = getRoomResponse(room);
	roomresponse.setPhoto(base64pro);
	roomresponses.add(roomresponse);
}
}
return ResponseEntity.ok(roomresponses);
}
@PostMapping("available-rooms")
@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
@CrossOrigin(origins="http://localhost:3000",methods=RequestMethod.POST)
public ResponseEntity<List<RoomResponse>> getavailablerooms(@RequestBody AvailbiltyrequestBody avail) throws SQLException{
	System.out.println(avail);
	List<Room> availablerooms = roomservice.getavailablerooms(avail.getCheckindate(),avail.getCheckoutdate(),avail.getRoomtype());
	List<RoomResponse> roomresponses =  new ArrayList<>();
	for(Room room:availablerooms) {
		byte[] photobytes = roomservice.getphotoroombyid(room.getId());
		if(photobytes!=null && photobytes.length>0) {
		String photobase64= Base64.encodeBase64String(photobytes);
		RoomResponse roomresponse = getRoomResponse(room); 
		roomresponse.setPhoto(photobase64);
		roomresponses.add(roomresponse);
		}
	}
	if(roomresponses.isEmpty()) {
		return ResponseEntity.ok(roomresponses);
	}else {
		return ResponseEntity.ok(roomresponses);
	}
}
private RoomResponse getRoomResponse(Room room) {
	// TODO Auto-generated method stub
	List<BookedRoom> bookings = getAllBookingsByRoomId(room.getId());
	List<BookedRoomResponse> bookingInfo = bookings.stream().map(booking -> new BookedRoomResponse(booking.getId(),booking.getCheckindate(),booking.getCheckoutdate(),booking.getBookingconfirmationcode())).toList();
    byte[]	photobytes = null;
    Blob photoblob = room.getPhoto();
    if(photoblob!=null) {
    	try {
    		photobytes = photoblob.getBytes(1,(int) photoblob.length());
    	
    }catch(SQLException e) {
    	throw new photoretrievalexception("error fetching the photo");
    }
}
    return new RoomResponse(room.getId(),room.getRoomtype(),room.getRoomprice(),photobytes,room.isIsbooked(),bookingInfo);
}
private List<BookedRoom> getAllBookingsByRoomId(Long roomid) {
	// TODO Auto-generated method stub
	return bookingservice.getallbookingsbyroomid(roomid);
}

@DeleteMapping("/delete/room/{roomid}")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@CrossOrigin(origins = "http://localhost:3000",methods = RequestMethod.DELETE)
public  ResponseEntity<Void> deleteroom(@PathVariable("roomid") Long id){
	roomservice.deleteRoom(id);
	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
}
@PutMapping("/update/{roomid}")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@CrossOrigin(origins = "http://localhost:3000",methods = RequestMethod.PUT)
public ResponseEntity<RoomResponse> updateRoom(@PathVariable Long roomid,@RequestParam(required = false) String roomType,@RequestParam(required = false) double roomPrice,@RequestParam(required = false) MultipartFile photo) throws IOException, SQLException, InternalServerException{
	byte[] photobytes = photo != null && !photo.isEmpty() ? photo.getBytes(): roomservice.getphotoroombyid(roomid);
	Blob photoBlob = photobytes!= null && photobytes.length>0 ? new SerialBlob(photobytes):null;
Room theRoom = roomservice.updateRoom(roomid,roomType,roomPrice,photobytes);
theRoom.setPhoto(photoBlob);
RoomResponse roomresponse =  getRoomResponse(theRoom);
return ResponseEntity.ok(roomresponse);
}
@GetMapping("/room/{roomid}")
@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
@CrossOrigin(origins = "http://localhost:3000",methods = RequestMethod.GET)
public ResponseEntity<Optional<RoomResponse>> getroombyid(@PathVariable Long roomid){
Optional<Room> theRoom = roomservice.getRoomByid(roomid);	
return theRoom.map((room) -> {
	RoomResponse roomResponse = getRoomResponse(room);
	return ResponseEntity.ok(Optional.of(roomResponse));
}) . orElseThrow(() -> new ResourceNotFoundException("Room not found"));
}

}
