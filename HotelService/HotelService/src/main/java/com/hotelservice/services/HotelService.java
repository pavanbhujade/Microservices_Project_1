package com.hotelservice.services;

import com.hotelservice.entities.Hotel;

import java.util.List;

public interface HotelService {

    Hotel createHotel(Hotel hotel);

    List<Hotel> getAllHotels();

    Hotel getOne(String hotelId);
}
