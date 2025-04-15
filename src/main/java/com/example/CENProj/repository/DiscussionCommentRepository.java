package com.example.CENProj.repository;

import com.example.CENProj.model.DiscussionComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscussionCommentRepository extends JpaRepository<DiscussionComment, Integer> {
}
