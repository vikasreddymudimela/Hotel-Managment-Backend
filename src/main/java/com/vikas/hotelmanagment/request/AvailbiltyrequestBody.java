package com.vikas.hotelmanagment.request;

import java.time.LocalDate;
import java.util.Date;

import lombok.Data;


@Data
public class AvailbiltyrequestBody {
private LocalDate checkindate;
private LocalDate checkoutdate;
private String roomtype;
}
