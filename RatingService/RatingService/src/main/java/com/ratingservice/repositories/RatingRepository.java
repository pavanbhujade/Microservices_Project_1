package com.ratingservice.repositories;

import com.ratingservice.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, String> {
    //custom finder method

    List<Rating> findByUserId(String userId);

    List<Rating> findByHotelId(String hotelId);
}
