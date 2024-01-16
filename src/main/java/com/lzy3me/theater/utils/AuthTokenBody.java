package com.lzy3me.theater.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AuthTokenBody {
    private UUID id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
}
