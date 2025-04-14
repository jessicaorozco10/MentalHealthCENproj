package com.example.CENProj.model.ViewModel;

import lombok.Data;

import java.util.List;

@Data
public class BaseViewModel {
    private List<String> errorMessages;
    private List<String> successMessages;
}
