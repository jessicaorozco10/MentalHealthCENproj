package com.example.CENProj.model.Dto;

import com.example.CENProj.model.User;
import lombok.Data;

@Data
public class UserCreatedDto {
    private User user;
    private String errorMsg;

    public void setErrorMsg(String emailAlreadyExists) {

    }

    public void setUser(User save) {
    }
}
