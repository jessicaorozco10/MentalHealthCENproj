package com.example.CENProj.model;

import com.example.CENProj.model.enums.UserType;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

@Entity
@Data
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    public String email;
    public String password;
    public String firstName;
    public String lastName;
    public UserType userType;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    public Date publicationDate;
}
