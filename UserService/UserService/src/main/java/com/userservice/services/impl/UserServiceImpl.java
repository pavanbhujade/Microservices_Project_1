package com.userservice.services.impl;

import com.userservice.entities.Hotel;
import com.userservice.entities.Rating;
import com.userservice.entities.User;
import com.userservice.exceptions.ResourceNotFoundException;
import com.userservice.externalservices.HotelService;
import com.userservice.externalservices.RatingService;
import com.userservice.repositories.UserRepository;
import com.userservice.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private RatingService ratingService;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {
        String randomUserID = UUID.randomUUID().toString();
        user.setUserId(randomUserID);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        List<User> allUsers = userRepository.findAll();
        /*List<User> userList = allUsers.stream().map(user -> {
            user.setRatings(restTemplate
                    .getForObject("http://localhost:8083/ratings/users/" + user.getUserId(), ArrayList.class));
            return user;
        }).collect(Collectors.toList());*/

        List<User> userList = allUsers.stream().map(user -> {
            //Fetch ratings for each user
            Rating[] ratings = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/" + user.getUserId(), Rating[].class);

            List<Rating> ratingList = Arrays.stream(ratings).toList();

            //Fetch Hotel details for each ratings

            List<Rating> ratingWithHotel
                    = ratingList.stream().map(rating -> {
                Hotel hotel = restTemplate.getForObject("http://HOTEL-SERVICE/hotels/" +
                        rating.getHotelId(), Hotel.class);
                rating.setHotel(hotel);
                return rating;

            }).collect(Collectors.toList());

            user.setRatings(ratingWithHotel);
            return user;

        }).collect(Collectors.toList());

        return userList;
    }

    /*@Override
    public User getSingleUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new
                ResourceNotFoundException("User with given id is not found on server !!!! " + userId));
        //http://localhost:8083/ratings/users/3240439b-a74e-42e3-9fbd-71f9969c2815
        Rating[] ratingsOfUser = restTemplate.
                getForObject("http://RATING-SERVICE/ratings/users/" + user.getUserId(), Rating[].class);
        logger.info("{}", Arrays.toString(ratingsOfUser));
        List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();


        List<Rating> ratingList = ratings.stream().map(rating -> {
            //api call to hotel service
            //url: http://localhost:8082/hotels/c6d04e43-7fe7-4cc1-93f9-be130b4f1655
            Hotel hotel = restTemplate.getForObject("http://HOTEL-SERVICE/hotels/" +
                    rating.getHotelId(), Hotel.class);
            //set the hotel to rating

            rating.setHotel(hotel);
            //return the rating
            return rating;
        }).collect(Collectors.toList());

        user.setRatings(ratingList);
        return user;
    }*/

    @Override
    public User getSingleUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new
                ResourceNotFoundException("User with given id is not found on server !!!! " + userId));

        List<Rating> ratingsByUserId = ratingService.getRatingsByUserId(user.getUserId());

        List<Rating> ratingList = ratingsByUserId.stream().map(rating -> {
            Hotel hotelById = hotelService.getHotelById(rating.getHotelId());
            rating.setHotel(hotelById);
            return rating;

        }).collect(Collectors.toList());

        user.setRatings(ratingList);
        return user;
    }
}
