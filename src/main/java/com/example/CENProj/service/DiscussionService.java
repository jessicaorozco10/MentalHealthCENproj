package com.example.CENProj.service;

import com.example.CENProj.model.Comment;
import com.example.CENProj.model.Discussion;
import com.example.CENProj.model.DiscussionComment;
import com.example.CENProj.model.User;
import com.example.CENProj.repository.DiscussionCommentRepository;
import com.example.CENProj.repository.DiscussionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiscussionService {

    private final DiscussionRepository discussionRepository;
    private final DiscussionCommentRepository discussionCommentRepository;
    public List<Discussion> getAllDiscussions() {
        return this.discussionRepository.findAll();
    }

    public Optional<Discussion> getDiscussionById(int id) {
        return this.discussionRepository.findById(id);
    }

    public Discussion createDiscussion(String title, String content, User user) {
        Discussion discussion = Discussion.builder().title(title).createdByUser(user)
                .createdDate(LocalDateTime.now()).content(content).build();
        return this.discussionRepository.save(discussion);
    }

    public DiscussionComment createComment(String content, int discussionId, User user) {
        Optional<Discussion> discussion = this.discussionRepository.findById(discussionId);
        if(discussion.isEmpty()) return null;
        DiscussionComment discussionComment = new DiscussionComment();
        discussionComment.setContent(content);
        discussionComment.setCreatedByUser(user);
        discussionComment.setDiscussion(discussion.get());
        discussionComment.setCreatedDate(LocalDateTime.now());
        return this.discussionCommentRepository.save(discussionComment);
    }
}
