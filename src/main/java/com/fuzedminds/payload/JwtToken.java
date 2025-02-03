package com.fuzedminds.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtToken {

    private String token;
    private String type;

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setType(String type) {
        this.type = type;
    }
}
