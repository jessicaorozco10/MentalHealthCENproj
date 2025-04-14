package com.example.CENProj.model;

import com.example.CENProj.model.enums.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private UserType userType;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date publicationDate;
    @ManyToMany
    @JoinTable(name = "user_roles")
    private List<Role> roles;
}
