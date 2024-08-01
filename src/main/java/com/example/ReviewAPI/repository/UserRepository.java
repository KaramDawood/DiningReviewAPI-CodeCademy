package com.example.ReviewAPI.repository;

import com.example.ReviewAPI.model.UserD;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserD, Integer> {

    UserD findByDisplayName(String displayName);
}
