package com.example.ReviewAPI.controller;

import com.example.ReviewAPI.model.UserD;
import com.example.ReviewAPI.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(final UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @GetMapping("/displayName")
    public UserD getUser(@PathVariable String displayName) {

        if (displayName == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "No Username provided");
        }
        this.userRepository.findByDisplayName(displayName).setId(null);
        return this.userRepository.save(this.userRepository.findByDisplayName(displayName));

    }

    @GetMapping("")
    public Iterable<UserD> getAllUsers() {
        return this.userRepository.findAll();
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public UserD createUser(@RequestBody UserD user) {
        if (user.getDisplayName() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Display name cannot be empty");
        }

        Optional<UserD> existingUser = Optional.ofNullable(this.userRepository.findByDisplayName(user.getDisplayName()));
        if (existingUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with display name already exists");
        }

        this.userRepository.save(user);
        return user;
    }

    @PutMapping("/{displayName}")
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void updateUser(@RequestBody UserD userUpdated, @PathVariable String displayName) {
        if (userUpdated.getDisplayName() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Display name cannot be empty");
        }

        Optional<UserD> existingUserOptional = Optional.ofNullable(this.userRepository.findByDisplayName(displayName));
        if(existingUserOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This user does not exist");
        }

        UserD existingUser = existingUserOptional.get();
        existingUser.setDisplayName(userUpdated.getDisplayName());
        this.userRepository.save(existingUser);
    }


    @GetMapping("/search")
    public UserD findUserByDisplayName(@RequestParam String displayName) {
        Optional<UserD> userDOptional = Optional.ofNullable(this.userRepository.findByDisplayName(displayName));
        if (userDOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return userDOptional.get();
    }
}
