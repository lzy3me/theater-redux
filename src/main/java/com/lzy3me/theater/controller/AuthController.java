package com.lzy3me.theater.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.lzy3me.theater.entities.User;
import com.lzy3me.theater.repositories.UserRepository;
import com.lzy3me.theater.request.AuthSignInBody;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.KeyPair;
import java.util.HashMap;
import java.util.Objects;

@Controller
@RequestMapping(path = "/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    private Curve curve = Jwks.CRV.Ed25519;
    private KeyPair pair = curve.keyPair().build();

    @PostMapping(path = "/sign_in")
    @ResponseBody
    public HashMap<String, Object> authUserSignIn(@RequestBody AuthSignInBody signInBody) throws JsonProcessingException {
        User signInUser;

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

        HashMap<String, String> tokenBody = new HashMap<>();
        tokenBody.put("sub", signInUser.getId().toString());
        tokenBody.put("username", signInUser.getUsername());
        tokenBody.put("email", signInUser.getEmail());

        String jwt = Jwts.builder()
                .claims(tokenBody)
                .signWith(pair.getPrivate(), Jwts.SIG.EdDSA)
                .compact();

        HashMap<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("token", jwt);

        return response;
    }

    @GetMapping(path = "/verify")
    @ResponseBody
    public Claims authVerifyToken(@RequestParam(name = "token") String token) {
        try {
            Claims subject = Jwts.parser()
                    .verifyWith(pair.getPublic())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return subject;
        } catch (JwtException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
