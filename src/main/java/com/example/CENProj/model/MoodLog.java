package com.example.CENProj.model;

import com.example.CENProj.model.enums.MoodType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mood_log")
public class MoodLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MoodType mood;
    @Column(nullable = false)
    private LocalDateTime createdDate;
    @ManyToOne(optional = false)
    @JoinColumn(name = "created_by_user_id")
    private User createdByUser;
}

