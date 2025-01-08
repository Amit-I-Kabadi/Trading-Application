package com.trade.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AuthResponse {

    private String jwt;

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public boolean isTwoFactorAuthEnable() {
        return isTwoFactorAuthEnable;
    }

    public void setTwoFactorAuthEnable(boolean twoFactorAuthEnable) {
        isTwoFactorAuthEnable = twoFactorAuthEnable;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    private boolean status;

    private String message;

    private boolean isTwoFactorAuthEnable;

    private String session;

}
