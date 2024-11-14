package com.hotelservice.services.impl;

import com.hotelservice.entities.Hotel;
import com.hotelservice.exceptions.NoDataFoundException;
import com.hotelservice.repositories.HotelRepository;
import com.hotelservice.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.server.UID;
import java.util.List;
import java.util.UUID;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository repository;

    @Override
    public Hotel createHotel(Hotel hotel) {
        String hotelId = UUID.randomUUID().toString();
        hotel.setHotelId(hotelId);
        return repository.save(hotel);
    }

    @Override
    public List<Hotel> getAllHotels() {
        return repository.findAll();
    }

    @Override
    public Hotel getOne(String hotelId) {
        return repository.findById(hotelId).orElseThrow(()->new NoDataFoundException("No data found for given id"));
    }
}
