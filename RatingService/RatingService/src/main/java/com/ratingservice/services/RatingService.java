package com.ratingservice.services;

import com.ratingservice.entities.Rating;

import java.util.List;

public interface RatingService {

    //create rating
    Rating createRating(Rating rating);

    //get all ratings
    List<Rating> getAll();

    //get all by UserId
    List<Rating> getRatingsByUserId(String userId);

    //get all by hotelId
    List<Rating> getRatingsByHotelId(String hotelId);

}
