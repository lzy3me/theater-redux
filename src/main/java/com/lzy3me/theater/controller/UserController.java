package com.lzy3me.theater.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.lzy3me.theater.entities.User;
import com.lzy3me.theater.repositories.UserRepository;
import com.lzy3me.theater.request.UserRegisterBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping(path = "/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping(path = "/register")
    public @ResponseBody String registerUser(@RequestBody UserRegisterBody registerBody) {
        String password = registerBody.getPassword();
        String bHashStr = BCrypt.withDefaults().hashToString(12, password.toCharArray());

        User regUser = new User();
        regUser.setUsername(registerBody.getUsername());
        regUser.setEmail(registerBody.getEmail());
        regUser.setHash(bHashStr);
        regUser.setCreatedAt(new Date());

        userRepository.save(regUser);

        return "registered";
    }

    @GetMapping(path = "/search")
    public @ResponseBody Iterable<User> searchUsers() {
        return userRepository.findAll();
    }

    @GetMapping(path = "/{user_id}")
    public @ResponseBody Optional<User> getUserById(@PathVariable("user_id") String user_id) {
        UUID uid = UUID.fromString(user_id);

        return userRepository.findById(uid);
    }
}
