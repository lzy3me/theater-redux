package com.lzy3me.theater.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterBody {
    private String username;
    private String email;
    private String password;
}
