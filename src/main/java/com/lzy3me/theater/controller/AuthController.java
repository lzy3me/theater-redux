package com.lzy3me.theater.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.google.gson.Gson;
import com.lzy3me.theater.entities.User;
import com.lzy3me.theater.repositories.UserRepository;
import com.lzy3me.theater.request.AuthSignInBody;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.KeyPair;
import java.util.Objects;

@Controller
@RequestMapping(path = "/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Value("${jwt.secret}")
    private String jwtSecret;

    @PostMapping(path = "/sign_in")
    public @ResponseBody String authUserSignIn(@RequestBody AuthSignInBody signInBody) {
        User signInUser;

        Gson gson = new Gson();

        Curve curve = Jwks.CRV.Ed25519;
        KeyPair pair = curve.keyPair().build();

        // check username and email, should not be empty both
        if (signInBody.getUsername().isEmpty() && signInBody.getEmail().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "username and email is empty");
        }

        // check if username provide, find user by username else find by email
        if (!signInBody.getUsername().isEmpty()) {
            signInUser = userRepository.findByUsername(signInBody.getUsername());
        } else {
            signInUser = userRepository.findByEmail(signInBody.getEmail());
        }

        // check if user fetch in repository is null. throw 401
        if (Objects.isNull(signInUser)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "username or email is not match in system");
        }

        // check password by compare given password and hash in database
        BCrypt.Result result = BCrypt.verifyer().verify(signInBody.getPassword().toCharArray(), signInUser.getHash());

        // if not verified. throw 401
        if (!result.verified) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "username/email/password is not match in system");
        }

        // check user account activation
        // TODO: if new user registered in system, send email for confirm and activate account
//        if (signInUser.isAccountActivation()) {
//            // TODO
//        } else {
//            // TODO
//        }

        String jws = Jwts.builder().content(gson.toJson(signInUser)).signWith(pair.getPrivate(), Jwts.SIG.EdDSA).compact();

        return jws;
    }
}
