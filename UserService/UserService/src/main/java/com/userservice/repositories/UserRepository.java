package com.userservice.repositories;

import com.userservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {

    //Custom query and method you can write here
}
