package com.example.CENProj.model.Dto;

import com.example.CENProj.model.User;
import lombok.Data;

@Data
public class UserCreatedDto {
    private User user;
    private String errorMsg;

}
