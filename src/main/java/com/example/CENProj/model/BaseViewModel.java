package com.example.CENProj.model;

import lombok.Data;

import java.util.List;

@Data
public class BaseViewModel {
    private List<String> errorMessages;
    private List<String> successMessages;
}
