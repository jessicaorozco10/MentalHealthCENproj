package com.example.CENProj.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "DiscussionComments")
public class DiscussionComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String content;
    private LocalDateTime createdDate;
    @ManyToOne
    @JoinColumn(name="discussion_id", nullable=false)
    private Discussion discussion;
    @ManyToOne
    @JoinColumn(name="created_by_user_id", nullable=false)
    private User createdByUser;
}
