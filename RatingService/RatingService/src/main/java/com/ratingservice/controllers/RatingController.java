package com.ratingservice.controllers;

import com.ratingservice.entities.Rating;
import com.ratingservice.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PostMapping
    public ResponseEntity<Rating> createRating(@RequestBody Rating rating) {
        Rating rating1 = ratingService.createRating(rating);
        return ResponseEntity.status(HttpStatus.CREATED).body(rating1);
    }

    @GetMapping
    public ResponseEntity<List<Rating>> getAllRating() {
        List<Rating> rating1 = ratingService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(rating1);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<Rating>> getRatingsByUserId(@PathVariable String userId) {
    List<Rating> rating1 = ratingService.getRatingsByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(rating1);
    }


    @GetMapping("/hotels/{hotelId}")
    public ResponseEntity<List<Rating>> getRatingsByHotelId(@PathVariable String hotelId) {
        List<Rating> rating1 = ratingService.getRatingsByHotelId(hotelId);
        return ResponseEntity.status(HttpStatus.OK).body(rating1);
    }
}
